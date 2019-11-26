package API.Adapter;

import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.BankToken;
import API.DTO.Transaction;

public interface Adapter {
    Account getUserAccounts(String token);
    Balance getAccountBalances(String token, String id);
    Transaction getAccountTransactions(String token, String id);
    BankToken token(String code);
    BankToken refresh(String code);
    String checkEnoughBalance(String token);
}
