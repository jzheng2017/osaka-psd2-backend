package API.Banks.Dummy;

import API.Banks.Client;
import API.DTO.*;
import javax.inject.Inject;
import java.net.URI;
import java.util.ArrayList;

public class DummyClient extends Client {
    private DummyUtil util;
    public static final String DUMMY_AUTHORIZATION_BASE = "http://localhost:8080/dummy/DUMMY";

    public DummyClient() {
        this.util = new DummyUtil();
    }

    @Inject
    public void setUtil(DummyUtil util) {
        this.util = util;
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
        return util.getAccounts();
    }

    @Override
    public Number getBalance(String token, String id) {
        return util.getBalanceFromAccounts(id);
    }

    @Override
    public AccountDetails getAccountDetails(String token, String id) {
        AccountDetails accountDetails = new AccountDetails();
        Account account = util.getAccount(id);
        accountDetails.setAccount(account);
        accountDetails.setTransactions(util.getTransactions(account.getId()));
        return accountDetails;
    }


    @Override
    public TransactionResponse initiateTransaction(String token, PaymentRequest paymentRequest) {
        // Niet mogelijk binnen de dummy bank
        return null;
    }

    @Override
    public void revoke(String refreshToken) {
        // Niet mogelijk binnen de dummy bank
    }

    @Override
    public boolean isPaymentToken(String token) {
        // Niet mogelijk binnen de dummy bank
        return false;
    }

    @Override
    public Payment pay(String token, String id) {
        // Niet mogelijk binnen de dummy bank
        return null;
    }
}
