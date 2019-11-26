package API.ING.Controller;

import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.BankToken;
import API.DTO.Transaction;
import API.ING.Service.INGAccountService;

public class INGAccountController {
    private INGAccountService ingAccountService = new INGAccountService();

    public Account getUserAccounts(String token) {
        return ingAccountService.getUserAccounts(token);
    }

    public Balance getAccountBalances(String token, String accountID) {
        return ingAccountService.getAccountBalances(token, accountID);
    }

    public Transaction getAccountTransactions(String token, String accountID) {
        return ingAccountService.getAccountTransactions(token, accountID);
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
