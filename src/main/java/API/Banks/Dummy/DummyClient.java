package API.Banks.Dummy;

import API.Banks.Client;
import API.DTO.*;

import java.net.URI;
import java.util.ArrayList;

public class DummyClient implements Client {

    public static final String DUMMY_AUTHORIZATION_BASE = "http://localhost:8080/dummy/dummy";

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
        return (ArrayList) DummyBankFakeDataFactory.getAccounts();
    }

    @Override
    public Balance getAccountBalances(String token, String id) {
        return DummyBankFakeDataFactory.getBalanceFromAccounts(id);
    }

    @Override
    public AccountDetails getAccountDetails(String token, String id) {
        AccountDetails accountDetails = new AccountDetails();
        Account account = DummyBankFakeDataFactory.getAccount(id);
        accountDetails.setAccount(account);
        accountDetails.setTransactions((ArrayList<Transaction>) DummyBankFakeDataFactory.getTransactions(account));
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
