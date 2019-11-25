package API.Adapter;

import API.DTO.*;
import API.ING.Controller.INGAccountController;

import javax.inject.Inject;

public class INGAdapter extends BankAdapter {

    private INGAccountController controller;

    @Inject
    public void setController(INGAccountController controller) {
        this.controller = controller;
    }

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

    public BankToken authorize() {
        return controller.authorize();
    }

    public BankToken getCustomerAuthorizationToken(String code) {
        return controller.getCustomerAuthorizationToken(code);
    }

    @Override
    public String checkEnoughBalance(String code) {
        return null;
    }

    @Override
    public BankToken refresh(String code) {
        return controller.refresh(code);
    }

    @Override
    public BankToken token(String code) {
        return null;
    }
}

