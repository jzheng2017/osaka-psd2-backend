package API.Adapter;

import API.DTO.*;

public class BankAdapter implements Adapter {
    private static final String RABOBANK_NAME = "rabobank";
    private static final String ING_NAME = "ing";

    private Adapter adapter;

    public BankAdapter(String name) {
        switch (name) {
            case RABOBANK_NAME:
                adapter = new RaboAdapter();
                break;
            case ING_NAME:
                adapter = new INGAdapter();
                break;
        }
    }

    @Override
    public Account getUserAccounts(String token) {
        return adapter.getUserAccounts(token);
    }

    @Override
    public Balance getAccountBalances(String token, String id) {
        return getAccountBalances(token, id);
    }

    @Override
    public Transaction getAccountTransactions(String token, String id) {
        return getAccountTransactions(token, id);
    }

    @Override
    public BankToken token(String code) {
        return token(code);
    }

    @Override
    public BankToken refresh(String code) {
        return null;
    }
}
