package API.RABO.Controller;

import API.RABO.Service.RabobankService;

import javax.inject.Inject;

public class RabobankController {
    private RabobankService rabobankService;

    @Inject
    public void setRabobankService(RabobankService rabobankService) {
        this.rabobankService = rabobankService;
    }
//
//    public String authorize() {
//        return rabobankService.authorize();
//    }
//
//    public AccessToken token(String code) {
//        return rabobankService.token(code);
//    }
//
//    public Account getUserAccounts(String token)  {
//        return rabobankService.getUserAccounts(token);
//    }
//
//    public Balance getAccountBalances(String token, String id) {
//        return rabobankService.getAccountBalances(token, id);
//    }
//
//    public Transaction getAccountTransactions(String token, String id) {
//        return rabobankService.getAccountTransactions(token, id);
//    }
}
