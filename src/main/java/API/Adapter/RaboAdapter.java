package API.Adapter;

import API.DTO.AccessToken;
import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.Transaction;
import API.RABO.Controller.RabobankController;

import javax.inject.Inject;

public class RaboAdapter extends BankAdapter {
    private RabobankController controller;

    @Inject
    public void setController(RabobankController controller) {
        this.controller = controller;
    }

    @Override
    public Account getUserAccounts(String token) {
//        return controller.getUserAccounts(token);
    return null;
    }

    @Override
    public Balance getAccountBalances(String token, String id) {
//        return controller.getAccountBalances(token,id);
        return null;

    }

    @Override
    public Transaction getAccountTransactions(String token, String id) {
//        return controller.getAccountTransactions(token, id);
        return null;

    }

    @Override
    public String authorize() {
//        return controller.authorize();
        return null;

    }

    @Override
    public AccessToken token(String code) {
//        return controller.token(code);
        return null;
    }
}
