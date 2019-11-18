package Adapter;

import Adapter.BankAdapter;
import datasource.Transaction;
import dto.Account;
import dto.Balance;

public class RaboAdapter extends BankAdapter {
    @Override
    public Account getUserAccounts() {
        return null;
    }

    @Override
    public Balance getAccountBalances(String id) {
        return null;
    }

    @Override
    public Transaction getAccountTransactions(String id) {
        return null;
    }
}
