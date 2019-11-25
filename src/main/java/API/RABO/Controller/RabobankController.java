package API.RABO.Controller;

import API.DTO.*;
import API.RABO.Service.RabobankService;

public class RabobankController {
    private RabobankService rabobankService = new RabobankService();

//    @Inject
//    public void setRabobankService(RabobankService rabobankService) {
//        this.rabobankService = rabobankService;
//    }

    public String authorize() {
        return rabobankService.authorize();
    }

    public BankToken token(String code) {
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

    public String checkEnoughBalance(String code) {
        return rabobankService.checkEnoughBalance(code);
    }

    public BankToken refresh(String code) {
        return rabobankService.refresh(code);
    }
}
