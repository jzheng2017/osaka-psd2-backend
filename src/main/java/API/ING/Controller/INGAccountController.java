package API.ING.Controller;

import API.DTO.AccessToken;
import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.Transaction;
import API.ING.Service.INGAccountService;

import javax.inject.Inject;

public class INGAccountController {
    private INGAccountService ingAccountService = new INGAccountService();

//    @Inject
//    public void setIngAccountService(INGAccountService ingAccountService) {
//        this.ingAccountService = ingAccountService;
//    }

    public Account getUserAccounts() {
        return ingAccountService.getUserAccounts();
    }

    public Balance getAccountBalances(String accountID) {
        return ingAccountService.getAccountBalances(accountID);
    }

    public Transaction getAccountTransactions(String accountID) {
        return ingAccountService.getAccountTransactions(accountID);
    }

    public String authorize() {
        return ingAccountService.authorize();
    }

    public String getCustomerAuthorizationToken(String code) {
        return ingAccountService.getAuthorizationCode(code);
    }
}
