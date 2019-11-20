package API.RABO.Controller;

import API.DTO.AccessToken;
import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.Transaction;
import API.RABO.Service.RabobankService;

import javax.inject.Inject;

public class RabobankController {
    private RabobankService rabobankService = new RabobankService();

//    @Inject
//    public void setRabobankService(RabobankService rabobankService) {
//        this.rabobankService = rabobankService;
//    }

    public String authorize() {
        return rabobankService.authorize();
    }

    public String token(String code) {
        return rabobankService.token(code);
    }

    public Account getUserAccounts(String token)  {
        return rabobankService.getUserAccounts(token);
    }

    public Balance getAccountBalances(String token, String id) {
        return rabobankService.getAccountBalances(token, id);
    }

    public Transaction getAccountTransactions(String token, String id) {
        return rabobankService.getAccountTransactions(token, id);
    }
}
