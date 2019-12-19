package API.Banks;

import API.DTO.*;
import API.Utils.WebClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;

import java.net.URI;
import java.util.ArrayList;

public abstract class Client {
    protected WebClient webClient;
    protected Gson gson;

    public Client() {
        gson = new Gson();
        var key = getPrivateKey();
        var cert = getCertificate();

        if(key != null && cert != null) {
            webClient = new WebClient(key, cert);
        }
    }

    public abstract URI getAuthorizationUrl(String redirectUrl, String state);
    public abstract BankToken token(String code);
    public abstract BankToken refresh(String code);
    public abstract ArrayList<Account> getUserAccounts(String token);
    public abstract Number getBalance(String token, String id);
    public abstract AccountDetails getAccountDetails(String token, String id);
    public abstract TransactionResponse initiateTransaction(String token, PaymentRequest paymentRequest);

    protected String getPrivateKey() {
        return null;
    }

    protected String getCertificate() {
        return null;
    }

    public void revoke(String refreshToken) {
    }

    public boolean isPaymentToken(String token) {
        return false;
    }

    public Payment pay(String token, String id) {
        return null;
    }

    protected BankToken responseToBankToken(JsonObject response) {
        var bankToken = new BankToken();

        if(response.has("access_token"))
            bankToken.setAccessToken(response.get("access_token").getAsString());

        if(response.has("refresh_token"))
            bankToken.setRefreshToken(response.get("refresh_token").getAsString());

        return bankToken;
    }
}
