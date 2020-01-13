package API.Services;

import API.Banks.ClientFactory;
import API.DTO.Bank;
import API.DTO.BankConnection;
import API.DTO.BankToken;
import API.DataSource.BankTokenDao;
import API.DataSource.UserDAO;
import javax.inject.Inject;
import java.io.IOException;
import java.net.URI;
import java.util.Objects;
import java.util.Properties;
import java.util.logging.Logger;

public class BankService {
    private UserDAO userDAO;
    private BankTokenDao bankTokenDao;
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());
    private static final String BANK_KEY = "{{BANK}}";
    private static final String REDIRECT_URI = "http://localhost:8080/connect/" + BANK_KEY + "/finish";
    private static final String FINAL_REDIRECT_URL = "http://localhost:4200/overzicht/rekeningen";
    private static final String FINAL_PAYMENT_URL = "http://localhost:4200/overmaken";

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Inject
    public void setBankTokenDao(BankTokenDao bankTokenDao) {
        this.bankTokenDao = bankTokenDao;
    }

    public Bank[] all() {
        return Bank.values();
    }

    public URI connect(Bank bank, String token) {
        if(this.canConnect(token)) {
            var client = ClientFactory.getClient(bank);
            var redirectUrl = REDIRECT_URI.replace(BANK_KEY, bank.toString());
            return client.getAuthorizationUrl(redirectUrl, token);
        }
        return null;
    }

    public URI finish(Bank bank, String code, String token) {
        var adapter = ClientFactory.getClient(bank);

        BankToken bankToken = adapter.token(code);
        bankToken.setBank(bank);

        if(adapter.isPaymentToken(bankToken.getAccessToken())) {
            var payment = adapter.pay(bankToken.getAccessToken(), token);
            return URI.create(FINAL_PAYMENT_URL+"?success="+payment.isPaid());
        }

        this.attachBankAccount(token, bankToken);
        return URI.create(FINAL_REDIRECT_URL);
    }

    public BankConnection getConnections(String token) {
        return new BankConnection(canConnect(token), getAllowedConnections());
    }

    private int getAllowedConnections() {
        try {
            Properties properties = new Properties();
            properties.load(Objects.requireNonNull(getClass().getClassLoader().getResourceAsStream("connections.properties")));
        return Integer.parseInt(properties.getProperty("amountOfConnections"));
        } catch (IOException e) {
            LOGGER.severe("PROPERTY DOESNT EXIST" + e);
        }

        return 0;
    }

    private boolean canConnect(String token) {
        int connections = userDAO.getUserConnections(token);
        return connections < this.getAllowedConnections();
    }

    public void delete(String token, String tableid) {
        var bankToken = bankTokenDao.getBankTokensForUser(token, tableid);
        var client = ClientFactory.getClient(bankToken.getBank());
        client.revoke(bankToken.getRefreshToken());
        bankTokenDao.deleteBankToken(tableid, token);
    }

    private void attachBankAccount(String token, BankToken bankToken) {
        var user = userDAO.getUserByToken(token);
        bankTokenDao.attachBankAccountToUser(user, bankToken.getBank(), bankToken.getAccessToken(), bankToken.getRefreshToken());
    }
}
