package API.Banks;

import API.DTO.*;
import com.google.gson.Gson;

import java.net.URI;
import java.util.ArrayList;

public abstract class BankClient {
    protected Gson gson;

    protected BankClient() {
        gson = new Gson();
    }

    public abstract URI getAuthorizationUrl(String redirectUrl, String state);
    public abstract BankToken token(String code);
    public abstract BankToken refresh(String code);
    public abstract ArrayList<Account> getUserAccounts(String token);
    public abstract Balance getAccountBalances(String token, String id);
    public abstract AccountDetails getAccountDetails(String token, String id);
    public abstract TransactionResponse initiateTransaction(String token, PaymentRequest paymentRequest);
    public abstract void revoke(String refreshToken);
}
