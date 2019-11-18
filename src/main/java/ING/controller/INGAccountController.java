package ING.controller;

import ING.service.INGAccountService;
import datasource.Transaction;
import dto.Account;
import dto.Balance;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

public class INGAccountController {
    @Autowired
    private INGAccountService ingAccountService;

    @GetMapping()
    public Account getUserAccounts() {
        return ingAccountService.getUserAccounts();
    }

    @GetMapping()
    public Balance getAccountBalances(String accountID) {
        return ingAccountService.getAccountBalances(accountID);
    }

    @GetMapping()
    public Transaction getAccountTransactions(String accountID) {
        return ingAccountService.getAccountTransactions(accountID);
    }
}
