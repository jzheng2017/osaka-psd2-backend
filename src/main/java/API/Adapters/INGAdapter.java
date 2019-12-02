package API.Adapters;

import API.Banks.ING.INGClient;
import API.DTO.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class INGAdapter implements BaseAdapter {
    private INGClient ingClient = new INGClient();
    private static Logger log = Logger.getLogger(INGAdapter.class.getName());

    @Override
    public URI getAuthorizationUrl(String redirectUrl, String state) {
        try {
            return new URI(ingClient.getAuthorizationUrl(redirectUrl, state));
        } catch (URISyntaxException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return null;
    }

    @Override
    public TransactionResponse getPaymentLink(String token, PaymentRequest paymentRequest) {
        return null;
    }

    @Override
    public Account getUserAccounts(String token) {
        return ingClient.getUserAccounts(token);
    }

    @Override
    public Balance getAccountBalances(String token, String id)  {
        return ingClient.getAccountBalances(token,id);
    }

    @Override
    public Transaction getAccountTransactions(String token, String id)  {
        return ingClient.getAccountTransactions(token, id);
    }

    @Override
    public BankToken refresh(String code) {
        return ingClient.refresh(code);
    }

    @Override
    public BankToken token(String code) {
        return ingClient.token(code);
    }

    public void setIngClient(INGClient ingClient) {
        this.ingClient = ingClient;
    }
}

