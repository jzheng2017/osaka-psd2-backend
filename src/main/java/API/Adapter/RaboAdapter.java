package API.Adapter;

import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.BankToken;
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
        return controller.getUserAccounts(token);
    }

    @Override
    public Balance getAccountBalances(String token, String id) {
        return controller.getAccountBalances(token,id);
    }

    @Override
    public Transaction getAccountTransactions(String token, String id) {
        return controller.getAccountTransactions(token, id);
    }

    public String authorize() {
        return controller.authorize();
    }

    @Override
    public String checkEnoughBalance(String code) {
        return controller.checkEnoughBalance(code);
    }

    @Override
    public BankToken refresh(String code) {
        return controller.refresh(code);
    }

    @Override
    public BankToken token(String code) {
        return controller.token(code);
    }
}
