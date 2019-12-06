package API.Adapters;

import API.DTO.*;

import java.net.URI;
import java.util.ArrayList;

public interface BaseAdapter {
    URI getAuthorizationUrl(String redirectUrl, String state);
    ArrayList<Account> getUserAccounts(String token);
    Balance getAccountBalances(String token, String id);
    AccountDetails getAccountDetails(String token, String id);
    BankToken token(String code);
    BankToken refresh(String code);
    TransactionResponse initiateTransaction(String token, PaymentRequest paymentRequest);
}
