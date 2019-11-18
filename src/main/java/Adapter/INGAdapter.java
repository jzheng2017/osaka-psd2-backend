package Adapter;

import Adapter.BankAdapter;
import ING.controller.INGAccountController;
import datasource.Transaction;
import dto.Account;
import dto.Balance;
import org.springframework.beans.factory.annotation.Autowired;

public class INGAdapter extends BankAdapter {
    @Autowired
    INGAccountController controller;

    @Override
    public Account getUserAccounts() {
        return controller.getUserAccounts();
    }

    @Override
    public Balance getAccountBalances(String id)  {
        return controller.getAccountBalances(id);
    }

    @Override
    public Transaction getAccountTransactions(String id)  {
        return controller.getAccountTransactions(id);
    }
}
