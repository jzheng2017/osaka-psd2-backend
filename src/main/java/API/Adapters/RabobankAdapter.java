package API.Adapters;

import API.Banks.Rabobank.RabobankClient;
import API.Banks.Rabobank.Util.RaboUtil;
import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.BankToken;
import API.DTO.Transaction;

import java.net.URI;
import java.net.URISyntaxException;
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
    public Account getUserAccounts(String token) {
        return rabobankClient.getUserAccounts(token);
    }

    @Override
    public Balance getAccountBalances(String token, String id) {
        return rabobankClient.getAccountBalances(token,id);
    }

    @Override
    public Transaction getAccountTransactions(String token, String id) {
        return rabobankClient.getAccountTransactions(token, id);
    }

    @Override
    public BankToken refresh(String code) {
        return rabobankClient.refresh(code);
    }

    @Override
    public BankToken token(String code) {
        return rabobankClient.token(code);
    }
}
