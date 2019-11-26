package API.Adapter;
import API.DTO.*;

import java.net.URI;

public class BankAdapter implements Adapter {
    private Adapter adapter;

    public BankAdapter(Bank name) {
        switch (name) {
            case RABOBANK:
                adapter = new RaboAdapter();
                break;
            case ING:
                adapter = new INGAdapter();
                break;
        }
    }

    @Override
    public URI getAuthorizationUrl(String redirectUrl, String state) {
        return adapter.getAuthorizationUrl(redirectUrl, state);
    }

    @Override
    public Account getUserAccounts(String token) {
        return adapter.getUserAccounts(token);
    }

    @Override
    public Balance getAccountBalances(String token, String id) {
        return adapter.getAccountBalances(token, id);
    }

    @Override
    public Transaction getAccountTransactions(String token, String id) {
        return adapter.getAccountTransactions(token, id);
    }

    @Override
    public BankToken token(String code) {
        return adapter.token(code);
    }

    @Override
    public BankToken refresh(String code) {
        return adapter.refresh(code);
    }

    @Override
    public String checkEnoughBalance( String token) {
        return adapter.checkEnoughBalance(token);
    }


}
