package API.Adapter;

import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.BankToken;
import API.DTO.Transaction;

public interface Adapter {
    public Account getUserAccounts(String token);
    public Balance getAccountBalances(String token, String id);
    public Transaction getAccountTransactions(String token, String id);
    public BankToken token(String code);
    public BankToken refresh(String code);
    public String checkEnoughBalance(String token);
}
