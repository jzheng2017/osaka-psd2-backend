package API.Adapter;

import API.DTO.*;


public abstract class BankAdapter {

    public abstract Account getUserAccounts(String token);

    public abstract Balance getAccountBalances(String token, String id);

    public abstract Transaction getAccountTransactions(String token, String id);

    public abstract BankToken token(String code);

//    public abstract String authorize();
    public abstract String checkEnoughBalance(String code);

    public abstract BankToken refresh(String code);
}
