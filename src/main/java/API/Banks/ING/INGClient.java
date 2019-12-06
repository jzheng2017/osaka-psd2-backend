package API.Banks.ING;

import API.DTO.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import javax.inject.Inject;
import java.lang.reflect.Type;
import java.net.URI;
import java.util.ArrayList;

public class INGClient {
    public static final String DUMMY_AUTHORIZATION_BASE = "http://localhost:8080/dummy/ing";
    private Gson gson;
    private INGMapper mapper;
    private INGUtil util;

    @Inject
    public void setUtil(INGUtil util) {
        this.util = util;
    }

    public INGClient() {
        this.util = new INGUtil();
        this.gson = new Gson();
        this.mapper = new INGMapper();
    }

    public String getAuthorizationUrl(String redirectUrl, String state) {
        return DUMMY_AUTHORIZATION_BASE+"?redirect_uri=" + redirectUrl + "&state=" + state;
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

    public ArrayList<Account> getUserAccounts(String code) {
        var url = "/v3/accounts";
        var json = util.doApiRequest(code, url);
        var response = gson.fromJson(json, JsonObject.class);

        Type listType = new TypeToken<ArrayList<Account>>(){}.getType();
        return gson.fromJson(response.getAsJsonArray("accounts").toString(), listType);
    }

    public Balance getAccountBalances(String code, String accountID) {
        var url = "/v3/accounts/" + accountID + "/balances?balanceTypes=expected";
        var response = util.doApiRequest(code, url);
        return gson.fromJson(response, Balance.class);
    }

    public AccountDetails getAccountDetails(String code, String accountID) {
        var url = "/v2/accounts/" + accountID + "/transactions?dateFrom=2016-10-01&dateTo=2016-11-21&limit=10";
        var json = util.doApiRequest(code, url);
        var response = gson.fromJson(json, JsonObject.class);
        return mapper.mapToAccountDetails(response);
    }

    public boolean isRequestedAmountAvailable(String token, PaymentRequest paymentRequest) {
        Account accountToCheckFunds = getAccountByIban(token, paymentRequest.getReceiver().getIban());
        if (accountToCheckFunds != null) {
            Balance balance = getAccountBalances(token,accountToCheckFunds.getId());
            return util.getBalanceFromBalances(balance) >= paymentRequest.getAmount();
        } else
            return false;
    }

    private Account getAccountByIban(String token, String iban) {
        return null;
        /*
        Account accountsToSearch = getUserAccounts(token);
        return util.getAccountByIban(accountsToSearch, iban);

         */
    }

    public TransactionResponse initiateTransaction(String token, PaymentRequest paymentRequest) {
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

    public void revoke(String token) {
        var url = "/oauth2/token/revoke";
        BankToken accessToken = authorize();
        util.doAPIPostRevoke(accessToken.getAccessToken(),url,token);
    }
}
