package API.Adapters;
import API.DTO.*;

import java.net.URI;

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
    public TransactionResponse getPaymentLink(String token, PaymentRequest paymentRequest) {
        return adapter.getPaymentLink(token, paymentRequest);
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
    public boolean isRequestedAmountAvailable(String token, PaymentRequest paymentRequest) {
        return adapter.isRequestedAmountAvailable(token, paymentRequest);
    }

    @Override
    public String doPaymentRequest(String token, PaymentRequest paymentRequest) {
        return adapter.doPaymentRequest(token, paymentRequest);
    }

    public void setAdapter(BaseAdapter adapter) {
        this.adapter = adapter;
    }
}
