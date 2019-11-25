package API.ING.Controller;

import API.DTO.*;
import API.ING.Service.INGAccountService;

import javax.inject.Inject;

public class INGAccountController {
    private INGAccountService ingAccountService;

    @Inject
    public void setIngAccountService(INGAccountService ingAccountService) {
        this.ingAccountService = ingAccountService;
    }

    public Account getUserAccounts(String token) {
        return ingAccountService.getUserAccounts(token);
    }

    public Balance getAccountBalances(String token, String accountID) {
        return ingAccountService.getAccountBalances(token ,accountID);
    }

    public Transaction getAccountTransactions(String token,String accountID) {
        return ingAccountService.getAccountTransactions(token,accountID);
    }

    public BankToken authorize() {
        return ingAccountService.authorize();
    }

    public BankToken getCustomerAuthorizationToken(String code) {
        return ingAccountService.getAuthorizationCode(code);
    }

    public BankToken refresh(String code) {
        return ingAccountService.refresh(code);
    }
}
