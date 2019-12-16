package API.Banks.Dummy;

import API.Banks.Client;
import API.DTO.*;

import java.net.URI;
import java.util.ArrayList;

public class DummyClient implements Client {
    @Override
    public URI getAuthorizationUrl(String redirectUrl, String state) {
        return null;
    }

    @Override
    public BankToken token(String code) {
        return null;
    }

    @Override
    public BankToken refresh(String code) {
        return null;
    }

    @Override
    public ArrayList<Account> getUserAccounts(String token) {
        return null;
    }

    @Override
    public Balance getAccountBalances(String token, String id) {
        return null;
    }

    @Override
    public AccountDetails getAccountDetails(String token, String id) {
        return null;
    }

    @Override
    public TransactionResponse initiateTransaction(String token, PaymentRequest paymentRequest) {
        return null;
    }

    @Override
    public void revoke(String refreshToken) {

    }
}
