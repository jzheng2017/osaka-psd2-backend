package API.Banks.Rabobank;

import API.Banks.Client;
import API.DTO.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.reflect.TypeToken;

import java.net.URI;
import java.util.ArrayList;

public class RabobankClient implements Client {
    public static final String SCOPES = "ais.balances.read%20ais.transactions.read-90days%20ais.transactions.read-history%20caf.fundsconfirmation.read%20pi.bulk.read-write";
    public static final String CLIENT_ID = "c451778c-db2c-451e-8f57-9bb62422329e";
    public static final String OAUTH_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/oauth2";
    private static final String AIS_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/payments/account-information/ais/v3";
    private static final String PIS_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/payments/payment-initiation/pis/v1";

    private Gson gson;
    private RabobankMapper mapper;
    private RaboUtil util;

    public RabobankClient() {
        gson = new Gson();
        mapper = new RabobankMapper();
        util = new RaboUtil();
    }

    public void setUtil(RaboUtil util) {
        this.util = util;
    }

    public URI getAuthorizationUrl(String redirectUrl, String state) {
        return URI.create(OAUTH_BASE + "/authorize?client_id=" + CLIENT_ID + "&scope=" + SCOPES + "&redirect_uri=" + redirectUrl + "&response_type=code&state="+state);
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
        var response = util.get(AIS_BASE, endpoint, token);

        var listType = new TypeToken<ArrayList<Account>>(){}.getType();
        return gson.fromJson(response.getAsJsonArray("accounts").toString(), listType);
    }

    public Number getBalance(String token, String id) {
        var responseJson = util.get(AIS_BASE, "/accounts/" + id + "/balances", token);
        var balancesJson = responseJson.get("balances").getAsJsonArray();
        var balanceJson = balancesJson.get(0).getAsJsonObject();
        var balanceAmountJson = balanceJson.get("balanceAmount").getAsJsonObject();
        return balanceAmountJson.get("amount").getAsNumber();
    }

    public AccountDetails getAccountDetails(String token, String id) {
        var transactions = util.get(AIS_BASE, "/accounts/" + id + "/transactions?bookingStatus=booked", token);
        return mapper.mapToAccountDetails(transactions);
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

    public void revoke(String refreshToken) {
        //Not yet available in RaboBank API
    }
}
