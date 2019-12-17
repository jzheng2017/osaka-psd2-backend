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
        BankToken bankToken = new BankToken();
        bankToken.setAccessToken("123");
        bankToken.setRefreshToken("123");
        return bankToken;
    }

    @Override
    public BankToken refresh(String code) {
        BankToken bankToken = new BankToken();
        bankToken.setAccessToken("123");
        bankToken.setRefreshToken("123");
        return bankToken;
    }

    @Override
    public ArrayList<Account> getUserAccounts(String token) {
        return (ArrayList)DummyBankFakeDataFactory.getAccounts();
    }

    @Override
    public Number getBalance(String token, String id) {
        return 0;
//        return DummyBankFakeDataFactory.getBalanceFromAccounts(id);
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
