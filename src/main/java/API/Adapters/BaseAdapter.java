package API.Adapters;

import API.DTO.*;

import java.net.URI;

public interface BaseAdapter {
    URI getAuthorizationUrl(String redirectUrl, String state);
    Account getUserAccounts(String token);
    Balance getAccountBalances(String token, String id);
    Transaction getAccountTransactions(String token, String id);
    BankToken token(String code);
    BankToken refresh(String code);
    boolean isRequestedAmountAvailable(String token, PaymentRequest paymentRequest);
    TransactionResponse initiateTransaction(String token, PaymentRequest paymentRequest);
}
