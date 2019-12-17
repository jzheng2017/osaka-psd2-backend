package API.Banks.Dummy;

import API.Banks.Client;
import API.DTO.*;

import javax.inject.Inject;
import java.net.URI;
import java.util.ArrayList;

public class DummyClient implements Client {
    private DummyBankFakeDataFactory factory;
    public static final String DUMMY_AUTHORIZATION_BASE = "http://localhost:8080/dummy/dummy";

    public DummyClient() {
        this.factory = new DummyBankFakeDataFactory();
    }

    @Override
    public URI getAuthorizationUrl(String redirectUrl, String state) {
        return URI.create(DUMMY_AUTHORIZATION_BASE + "?redirect_uri=" + redirectUrl + "&state=" + state);
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
        return factory.getAccounts();
    }

    @Override
    public Number getBalance(String token, String id) {
        return 0;
//        return DummyBankFakeDataFactory.getBalanceFromAccounts(id);
    }

    @Override
    public AccountDetails getAccountDetails(String token, String id) {
        AccountDetails accountDetails = new AccountDetails();
        Account account = factory.getAccount(id);
        accountDetails.setAccount(account);
        accountDetails.setTransactions(factory.getTransactions(account));
        return accountDetails;
    }


    @Override
    public TransactionResponse initiateTransaction(String token, PaymentRequest paymentRequest) {
        return null;
    }

    @Override
    public void revoke(String refreshToken) {

    }
}
