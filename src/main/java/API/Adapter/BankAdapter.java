package API.Adapter;

import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.BankToken;
import API.DTO.Transaction;


public abstract class BankAdapter {

    public abstract Account getUserAccounts(String token);

    public abstract Balance getAccountBalances(String token, String id);

    public abstract Transaction getAccountTransactions(String token, String id);

    public abstract BankToken token(String code);

    public abstract String checkEnoughBalance(String code);

    public abstract BankToken refresh(String code);
}
