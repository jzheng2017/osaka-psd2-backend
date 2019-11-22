package API.ING.Controller;

import API.DTO.ING.AccessTokenING;
import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.Transaction;
import API.ING.Service.INGAccountService;

public class INGAccountController {
    private INGAccountService ingAccountService = new INGAccountService();

//    @Inject
//    public void setIngAccountService(INGAccountService ingAccountService) {
//        this.ingAccountService = ingAccountService;
//    }

    public Account getUserAccounts(String token) {
        return ingAccountService.getUserAccounts(token);
    }

    public Balance getAccountBalances(String token, String accountID) {
        return ingAccountService.getAccountBalances(token ,accountID);
    }

    public Transaction getAccountTransactions(String accountID) {
        return ingAccountService.getAccountTransactions(accountID);
    }

    public AccessTokenING authorize() {
        return ingAccountService.authorize();
    }

    public AccessTokenING getCustomerAuthorizationToken(String code) {
        return ingAccountService.getAuthorizationCode(code);
    }
}
