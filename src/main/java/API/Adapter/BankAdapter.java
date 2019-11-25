package API.Adapter;

import API.DTO.AccessToken;
import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.Transaction;


public abstract class BankAdapter {

    public abstract Account getUserAccounts(String token);

    public abstract Balance getAccountBalances(String token, String id);

    public abstract Transaction getAccountTransactions(String token, String id);

    public abstract AccessToken token(String code);

//    public abstract String authorize();
    public abstract String checkEnoughBalance(String code);

    public abstract String refresh(String code);
}
