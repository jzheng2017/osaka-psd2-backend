package API.ING.Controller;

import API.DTO.AccessToken;
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

    public Transaction getAccountTransactions(String token,String accountID) {
        return ingAccountService.getAccountTransactions(token,accountID);
    }

    public AccessToken authorize() {
        return ingAccountService.authorize();
    }

    public AccessToken getCustomerAuthorizationToken(String code) {
        return ingAccountService.getAuthorizationCode(code);
    }
}
