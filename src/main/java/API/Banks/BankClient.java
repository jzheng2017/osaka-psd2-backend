package API.Banks;

import API.Banks.ING.INGClient;
import API.Banks.Rabobank.RabobankClient;
import API.DTO.*;
import java.net.URI;
import java.util.ArrayList;

public class BankClient implements BaseClient {
    private BaseClient client;

    public BankClient(Bank name) {
        switch (name) {
            case RABOBANK:
                client = new RabobankClient();
                break;
            case ING:
                client = new INGClient();
                break;
        }
    }

    public URI getAuthorizationUrl(String redirectUrl, String state) {
        return client.getAuthorizationUrl(redirectUrl, state);
    }

    public ArrayList<Account> getUserAccounts(String token) {
        return client.getUserAccounts(token);
    }

    public Balance getAccountBalances(String token, String id) {
        return client.getAccountBalances(token, id);
    }

    public AccountDetails getAccountDetails(String token, String id) {
        return client.getAccountDetails(token, id);
    }

    public BankToken token(String code) {
        return client.token(code);
    }

    public BankToken refresh(String code) {
        return client.refresh(code);
    }

    public TransactionResponse initiateTransaction(String token, PaymentRequest paymentRequest) {
        return client.initiateTransaction(token, paymentRequest);
    }

    @Override
    public void revoke(String refreshToken) {
        client.revoke(refreshToken);
    }

    public void setClient(BaseClient client) {
        this.client = client;
    }
}
