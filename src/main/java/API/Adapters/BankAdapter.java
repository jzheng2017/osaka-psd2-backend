package API.Adapters;
import API.DTO.*;

import java.net.URI;
import java.util.ArrayList;

public class BankAdapter implements BaseAdapter {
    private BaseAdapter adapter;

    public BankAdapter(Bank name) {
        switch (name) {
            case RABOBANK:
                adapter = new RabobankAdapter();
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
    public ArrayList<Account> getUserAccounts(String token) {
        return adapter.getUserAccounts(token);
    }

    @Override
    public Balance getAccountBalances(String token, String id) {
        return adapter.getAccountBalances(token, id);
    }

    @Override
    public AccountDetails getAccountDetails(String token, String id) {
        return adapter.getAccountDetails(token, id);
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
    public boolean isRequestedAmountAvailable(String token, PaymentRequest paymentRequest) {
        return adapter.isRequestedAmountAvailable(token, paymentRequest);
    }

    @Override
    public TransactionResponse initiateTransaction(String token, PaymentRequest paymentRequest) {
        return adapter.initiateTransaction(token, paymentRequest);
    }

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
    }
}
