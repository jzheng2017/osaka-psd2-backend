package Adapter;

import datasource.Transaction;
import dto.Account;
import dto.Balance;

public abstract class BankAdapter {
    public abstract Account getUserAccounts();

    public abstract Balance getAccountBalances(String id);

    public abstract Transaction getAccountTransactions(String id);
}
