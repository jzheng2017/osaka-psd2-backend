package API.Adapter;

import API.DTO.AccessToken;
import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.Transaction;
import API.ING.Controller.INGAccountController;

public class INGAdapter extends BankAdapter {

    private INGAccountController controller = new INGAccountController();

//    @Inject
//    public void setController(INGAccountController controller) {
//        this.controller = controller;
//    }

    @Override
    public Account getUserAccounts(String token) {
        return controller.getUserAccounts(token);
    }

    @Override
    public Balance getAccountBalances(String token, String id)  {
        return controller.getAccountBalances(token,id);
    }

    @Override
    public Transaction getAccountTransactions(String token, String id)  {
        return controller.getAccountTransactions(token,id);
    }

    public AccessToken authorize() {
        return controller.authorize();
    }

    public AccessToken getCustomerAuthorizationToken(String code) {
        return controller.getCustomerAuthorizationToken(code);
    }

    @Override
    public String checkEnoughBalance(String code) {
        return null;
    }

    @Override
    public String refresh(String code) {
        return null;
    }

    @Override
    public AccessToken token(String code) {
        return null;
    }
}

