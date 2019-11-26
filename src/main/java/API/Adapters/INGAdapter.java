package API.Adapters;

import API.DTO.*;
import API.Banks.ING.INGClient;
import java.net.URI;
import java.net.URISyntaxException;

public class INGAdapter implements BaseAdapter {
    private INGClient ingClient = new INGClient();

    @Override
    public URI getAuthorizationUrl(String redirectUrl, String state) {
        try {
            return new URI(ingClient.getAuthorizationUrl(redirectUrl, state));
        } catch (URISyntaxException e) {
            e.printStackTrace();
        }
        return null;
    }

    @Override
    public Account getUserAccounts(String token) {
        return ingClient.getUserAccounts(token);
    }

    @Override
    public Balance getAccountBalances(String token, String id)  {
        return ingClient.getAccountBalances(token,id);
    }

    @Override
    public Transaction getAccountTransactions(String token, String id)  {
        return ingClient.getAccountTransactions(token, id);
    }

    @Override
    public BankToken refresh(String code) {
        return ingClient.refresh(code);
    }

    @Override
    public BankToken token(String code) {
        return ingClient.token(code);
    }
}

