package API.Adapter;

import API.DTO.AccessToken;
import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.Transaction;
import API.ING.Controller.INGAccountController;

import javax.inject.Inject;

public class INGAdapter extends BankAdapter {
    public final static String baseUrl = "https://api.sandbox.ing.com";

    private INGAccountController controller = new INGAccountController();

//    @Inject
//    public void setController(INGAccountController controller) {
//        this.controller = controller;
//    }

    @Override
    public Account getUserAccounts(String token) {
        return controller.getUserAccounts();
    }

    @Override
    public Balance getAccountBalances(String token, String id)  {
        return controller.getAccountBalances(id);
    }

    @Override
    public Transaction getAccountTransactions(String token, String id)  {
        return controller.getAccountTransactions(id);
    }

    @Override
    public String authorize() {
        return controller.authorize();
    }

    @Override
    public AccessToken token(String code) {
        return null;
    }
}

