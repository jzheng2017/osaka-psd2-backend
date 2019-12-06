package API.Banks.Rabobank;

import API.DTO.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;
import java.net.URI;
import java.util.ArrayList;

public class RabobankClient {
    public static final String SCOPES = "ais.balances.read%20ais.transactions.read-90days%20ais.transactions.read-history%20caf.fundsconfirmation.read%20pi.bulk.read-write";
    public static final String CLIENT_ID = "c451778c-db2c-451e-8f57-9bb62422329e";
    public static final String OAUTH_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/oauth2";
    private static final String AIS_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/payments/account-information/ais/v3";
    private static final String PIS_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/payments/payment-initiation/pis/v1";

    private Gson gson;
    private RabobankMapper mapper;
    private RaboUtil util;

    public RabobankClient() {
        this.gson = new Gson();
        this.mapper = new RabobankMapper();
        this.util = new RaboUtil();
    }

    public String getAuthorizationUrl(String redirectUrl, String state) {
        return OAUTH_BASE + "/authorize?client_id=" + CLIENT_ID + "&scope=" + SCOPES + "&redirect_uri=" + redirectUrl + "&response_type=code&state="+state;
    }

    public BankToken token(String code) {
        String body = "grant_type=authorization_code&code=" + code;
        return util.getBankToken(body);
    }

    public BankToken refresh(String code) {
        String body = "grant_type=refresh_token&refresh_token=" + code;
        return util.getBankToken(body);
    }

    public ArrayList<Account> getUserAccounts(String token) {
        var endpoint = "/accounts";
        var json = util.doGetRequest(AIS_BASE, endpoint, token);
        var response = gson.fromJson(json, JsonObject.class);

        var listType = new TypeToken<ArrayList<Account>>(){}.getType();
        return gson.fromJson(response.getAsJsonArray("accounts").toString(), listType);
    }

    public Balance getAccountBalances(String token, String id) {
        String endpoint = "/accounts/" + id + "/balances";
        String result = util.doGetRequest(AIS_BASE, endpoint, token);
        return gson.fromJson(result, Balance.class);
    }

    public AccountDetails getAccountDetails(String token, String id) {
        var endpoint = "/accounts/" + id + "/transactions?bookingStatus=booked";
        var json = util.doGetRequest(AIS_BASE, endpoint, token);
        var response = gson.fromJson(json, JsonObject.class);
        return mapper.mapToAccountDetails(response);
    }

    public TransactionResponse initiateTransaction(String token, PaymentRequest paymentRequest) {
        var endpoint = "/payments/sepa-credit-transfers";
        var body = util.generatePaymentJSON(paymentRequest);
        var response = util.doPaymentInitiationRequest(PIS_BASE, endpoint, token, gson.toJson(body), "");

        JsonObject object = gson.fromJson(response, JsonObject.class);
        var links = object.get("_links").getAsJsonObject();
        var scaRedirect = links.get("scaRedirect").getAsJsonObject();
        var href = URI.create(scaRedirect.get("href").getAsString());

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setUrl(href);

        return transactionResponse;
    }

    public void revoke(String refreshToken, String accessToken) {

    }

    public void setUtil(RaboUtil util) {
        this.util = util;
    }


}
