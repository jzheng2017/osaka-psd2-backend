package API.Adapters;

import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.BankToken;
import API.DTO.Transaction;

import java.net.URI;

public interface BaseAdapter {
    URI getAuthorizationUrl(String redirectUrl, String state);
    Account getUserAccounts(String token);
    Balance getAccountBalances(String token, String id);
    Transaction getAccountTransactions(String token, String id);
    BankToken token(String code);
    BankToken refresh(String code);
}
