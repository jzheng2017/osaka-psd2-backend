package API.Banks.Dummy;

import API.Banks.Client;
import API.DTO.*;

import javax.inject.Inject;
import java.net.URI;
import java.util.ArrayList;

public class DummyClient implements Client {
    private DummyBankFakeDataFactory dummyBankFakeDataFactory;

    @Inject
    public void setDummyBankFakeDataFactory(DummyBankFakeDataFactory dummyBankFakeDataFactory) {
        this.dummyBankFakeDataFactory = dummyBankFakeDataFactory;
    }

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
        return (ArrayList)dummyBankFakeDataFactory.getAccounts();
    }

    @Override
    public Number getBalance(String token, String id) {
        return dummyBankFakeDataFactory.getBalanceFromAccounts(id);
    }


    @Override
    public AccountDetails getAccountDetails(String token, String id) {
        AccountDetails accountDetails = new AccountDetails();
        Account account = dummyBankFakeDataFactory.getAccount(id);
        accountDetails.setAccount(account);
        accountDetails.setTransactions((ArrayList<Transaction>) dummyBankFakeDataFactory.getTransactions(account));
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
