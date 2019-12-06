package API.Banks;

import API.DTO.*;

import java.net.URI;
import java.util.ArrayList;

public interface BaseClient {
    public URI getAuthorizationUrl(String redirectUrl, String state);

    public ArrayList<Account> getUserAccounts(String token);

    public Balance getAccountBalances(String token, String id);

    public AccountDetails getAccountDetails(String token, String id);

    public BankToken token(String code);

    public BankToken refresh(String code);

    public TransactionResponse initiateTransaction(String token, PaymentRequest paymentRequest);

    public void revoke(String refreshToken);
}
