package API.Banks.ING;

import API.Banks.BankClient;
import API.Banks.BaseClient;
import API.DTO.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import javax.inject.Inject;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;

public class INGClient implements BaseClient {
    public static final String DUMMY_AUTHORIZATION_BASE = "http://steinmilder.nl:8080/dummy/ing";

    private Gson gson;
    private INGMapper mapper;
    private INGUtil util;

    @Inject
    public void setUtil(INGUtil util) {
        this.util = util;
    }

    @Inject
    public void setMapper(INGMapper mapper) {
        this.mapper = mapper;
    }

    public INGClient() {
        gson = new Gson();
        util = new INGUtil();
        mapper = new INGMapper();
    }

    public URI getAuthorizationUrl(String redirectUrl, String state) {
        return URI.create(DUMMY_AUTHORIZATION_BASE+"?redirect_uri=" + redirectUrl + "&state=" + state);
    }

    public BankToken authorize() {
        var body = "grant_type=client_credentials";
        var url = "/oauth2/token";
        var output = util.getAccessToken(body, url);

        return gson.fromJson(output, BankToken.class);
    }

    public BankToken token(String code) {
        BankToken application = authorize();
        var body = "grant_type=authorization_code&code=" + code;
        var url = "/oauth2/token";
        var request = util.getCustomerAccessToken(body, application.getAccessToken(), url);
        return gson.fromJson(request, BankToken.class);
    }

    public BankToken refresh(String refreshToken) {
        return token("2c1c404c-c960-49aa-8777-19c805713edf");
    }

    public ArrayList<Account> getUserAccounts(String accessToken) {
        var url = "/v3/accounts";
        var json = util.doApiRequest(accessToken, url);
        var response = gson.fromJson(json, JsonObject.class);

        Type listType = new TypeToken<ArrayList<Account>>(){}.getType();
        return gson.fromJson(response.getAsJsonArray("accounts").toString(), listType);
    }

    public Balance getAccountBalances(String accessToken, String accountID) {
        var url = "/v3/accounts/" + accountID + "/balances?balanceTypes=expected";
        var response = util.doApiRequest(accessToken, url);
        return gson.fromJson(response, Balance.class);
    }

    public AccountDetails getAccountDetails(String accessToken, String accountID) {
        var url = "/v2/accounts/" + accountID + "/transactions?dateFrom=2016-10-01&dateTo=2016-11-21&limit=10";
        var json = util.doApiRequest(accessToken, url);
        var response = gson.fromJson(json, JsonObject.class);
        return mapper.mapToAccountDetails(response);
    }

    public TransactionResponse initiateTransaction(String accessToken, PaymentRequest paymentRequest) {
        var url = "/v1/payments/sepa-credit-transfers";
        var request = util.buildPaymentRequest(paymentRequest);
        var body = gson.toJson(request);
        var result = util.doAPIPostRequest(authorize().getAccessToken(), url, body, paymentRequest.getIp());

        var jsonObject = gson.fromJson(result, JsonObject.class);
        var links = jsonObject.get("_links").getAsJsonObject();
        var href = URI.create(links.get("scaRedirect").getAsString());

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setUrl(href);

        return transactionResponse;
    }

    public void revoke(String refreshToken) {
        String url = "/oauth2/token/revoke";
        String accessToken = authorize().getAccessToken();
        util.doAPIPostRevoke(accessToken, url, refreshToken);
    }
}
