package API.Adapters;

import API.Banks.Rabobank.RabobankClient;
import API.DTO.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RabobankAdapter implements BaseAdapter {
    private RabobankClient rabobankClient = new RabobankClient();
    private static Logger log = Logger.getLogger(RabobankAdapter.class.getName());

    @Override
    public URI getAuthorizationUrl(String redirectUrl, String state) {
        try {
            return new URI(rabobankClient.getAuthorizationUrl(redirectUrl, state));
        } catch (URISyntaxException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return null;
    }

    @Override
    public ArrayList<Account> getUserAccounts(String token) {
        return rabobankClient.getUserAccounts(token);
    }

    @Override
    public Balance getAccountBalances(String token, String id) {
        return rabobankClient.getAccountBalances(token, id);
    }

    @Override
    public AccountDetails getAccountDetails(String token, String id) {
        return rabobankClient.getAccountDetails(token, id);
    }

    @Override
    public BankToken refresh(String code) {
        return rabobankClient.refresh(code);
    }

    @Override
    public boolean isRequestedAmountAvailable(String token, PaymentRequest paymentRequest) {
        return false;
    }

    @Override
    public TransactionResponse initiateTransaction(String token, PaymentRequest paymentRequest) {
        return rabobankClient.initiateTransaction(token, paymentRequest);
    }

    @Override
    public BankToken token(String code) {
        return rabobankClient.token(code);
    }

    public void setRabobankClient(RabobankClient rabobankClient) {
        this.rabobankClient = rabobankClient;
    }
}
