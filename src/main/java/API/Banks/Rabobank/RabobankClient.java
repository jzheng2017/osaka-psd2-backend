package API.Banks.Rabobank;

import API.Banks.Client;
import API.DTO.*;
import com.google.gson.reflect.TypeToken;
import java.net.URI;
import java.util.ArrayList;

public class RabobankClient extends Client {
    public static final String SCOPES = "ais.balances.read%20ais.transactions.read-90days%20ais.transactions.read-history%20caf.fundsconfirmation.read%20pi.bulk.read-write";
    public static final String CLIENT_ID = "c451778c-db2c-451e-8f57-9bb62422329e";
    public static final String OAUTH_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/oauth2";
    private static final String AIS_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/payments/account-information/ais/v3";
    private static final String PIS_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/payments/payment-initiation/pis/v1";

    private RabobankMapper mapper;
    private RaboUtil util;

    public RabobankClient() {
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
        var body = "grant_type=authorization_code&code=" + code;
        var response = util.getBankToken(body);
        return responseToBankToken(response);
    }

    public BankToken refresh(String code) {
        var body = "grant_type=refresh_token&refresh_token=" + code;
        var response = util.getBankToken(body);
        return responseToBankToken(response);
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

        var links = response.get("_links").getAsJsonObject();
        var scaRedirect = links.get("scaRedirect").getAsJsonObject();
        var href = URI.create(scaRedirect.get("href").getAsString());

        TransactionResponse transactionResponse = new TransactionResponse();
        transactionResponse.setUrl(href);

        return transactionResponse;
    }

    @Override
    public void revoke(String refreshToken) {
        // Niet mogelijk binnen de Rabobank
    }

    @Override
    public boolean isPaymentToken(String token) {
        // Niet mogelijk binnen de Rabobank
        return false;
    }

    @Override
    public Payment pay(String token, String id) {
        // Niet mogelijk binnen de Rabobank
        return null;
    }
}
