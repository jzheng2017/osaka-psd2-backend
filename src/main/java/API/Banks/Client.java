package API.Banks;

import API.DTO.*;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import java.net.URI;
import java.util.ArrayList;

public abstract class Client {
    protected Gson gson;

    public Client() {
        gson = new Gson();
    }

    public abstract URI getAuthorizationUrl(String redirectUrl, String state);
    public abstract BankToken token(String code);
    public abstract BankToken refresh(String code);
    public abstract ArrayList<Account> getUserAccounts(String token);
    public abstract Number getBalance(String token, String id);
    public abstract AccountDetails getAccountDetails(String token, String id);
    public abstract TransactionResponse initiateTransaction(String token, PaymentRequest paymentRequest);
    public abstract void revoke(String refreshToken);
    public abstract boolean isPaymentToken(String token);
    public abstract Payment pay(String token, String id);

    protected BankToken responseToBankToken(JsonObject response) {
        var bankToken = new BankToken();

        if(response.has("access_token"))
            bankToken.setAccessToken(response.get("access_token").getAsString());

        if(response.has("refresh_token"))
            bankToken.setRefreshToken(response.get("refresh_token").getAsString());

        return bankToken;
    }
}
