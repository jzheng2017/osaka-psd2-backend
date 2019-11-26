package API.Adapters;

import API.DTO.*;
import API.Banks.Rabobank.RabobankClient;

import java.net.URI;
import java.net.URISyntaxException;

public class RabobankAdapter implements BaseAdapter {
    private RabobankClient rabobankClient = new RabobankClient();

    @Override
    public URI getAuthorizationUrl(String redirectUrl, String state) {
        try {
            return new URI(rabobankClient.getAuthorizationUrl(redirectUrl, state));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account getUserAccounts(String token) {
        return rabobankClient.getUserAccounts(token);
    }

    @Override
    public Balance getAccountBalances(String token, String id) {
        return rabobankClient.getAccountBalances(token,id);
    }

    @Override
    public Transaction getAccountTransactions(String token, String id) {
        return rabobankClient.getAccountTransactions(token, id);
    }

    @Override
    public BankToken refresh(String code) {
        return rabobankClient.refresh(code);
    }

    @Override
    public BankToken token(String code) {
        return rabobankClient.token(code);
    }
}
