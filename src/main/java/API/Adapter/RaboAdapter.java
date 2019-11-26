package API.Adapter;

import API.DTO.*;
import API.RABO.Controller.RabobankController;

import java.net.URI;
import java.net.URISyntaxException;

public class RaboAdapter implements Adapter {
    private RabobankController controller = new RabobankController();

    @Override
    public URI getAuthorizationUrl(String redirectUrl, String state) {
        return controller.getAuthorizationUrl(redirectUrl, state);
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
    public BankToken refresh(String code) {
        return controller.refresh(code);
    }

    @Override
    public BankToken token(String code) {
        return controller.token(code);
    }

    @Override
    public String checkEnoughBalance(String token) {
        return controller.checkEnoughBalance(token);
    }
}
