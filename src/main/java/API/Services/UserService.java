package API.Services;

import API.Banks.BankClient;
import API.DTO.*;
import API.DTO.Auth.LoginResponse;
import API.DTO.Auth.RegisterRequest;
import API.DataSource.BankTokenDao;
import API.DataSource.UserDAO;
import API.HashedPassword;
import com.google.gson.JsonObject;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Properties;
import java.util.UUID;

public class UserService {
    private UserDAO userDAO;
    private BankTokenDao bankTokenDao;


    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Inject
    public void setBankTokenDao(BankTokenDao bankTokenDao) {
        this.bankTokenDao = bankTokenDao;
    }

    public LoginResponse register(RegisterRequest request) {
        var email = request.getEmail();
        var password = request.getPassword();
        var name = request.getName();
        var user = userDAO.getUserByEmail(email);

        if (user != null)
            return null;

        var hashedPassword = HashedPassword.generate(password);

        userDAO.registerUser(name, email, hashedPassword);
        return login(email, password);
    }

    public LoginResponse login(String email, String password) {
        var user = userDAO.getUserByEmail(email);

        if (user != null && user.checkPassword(password)) {
            var response = new LoginResponse();

            user.setToken(UUID.randomUUID().toString());
            userDAO.updateUserToken(user);

            response.setName(user.getName());
            response.setEmail(user.getEmail());
            response.setToken(user.getToken());

            refreshAccessTokens(user);

            return response;
        }

        return null;
    }

    public User getUserByToken(String token){
        return userDAO.getUserByToken(token);
    }

    private void refreshAccessTokens(User user) {
        var bankTokens = bankTokenDao.getBankTokensForUser(user);

        for (BankToken bankToken : bankTokens) {
            var client = new BankClient(bankToken.getBank());
            BankToken refreshedBankToken = client.refresh(bankToken.getRefreshToken());
            refreshedBankToken.setId(bankToken.getId());
            bankTokenDao.updateBankToken(refreshedBankToken);
        }
    }

    public void attachBankAccount(String token, BankToken bankToken) {
        var user = userDAO.getUserByToken(token);
        bankTokenDao.attachBankAccountToUser(user, bankToken.getBank(), bankToken.getAccessToken(), bankToken.getRefreshToken());
    }

    public ArrayList<AccountAttach> getAttachedAccounts(String token){
        var user = userDAO.getUserByToken(token);
        return userDAO.getAttachedAccounts(user);
    }

    public void deleteBankAccount(String token, String tableid) {
        var user = userDAO.getUserByToken(token);
        var bankToken = bankTokenDao.getBankTokensForUser(user, tableid);
        var client = new BankClient(bankToken.getBank());
        client.revoke(bankToken.getRefreshToken());
        bankTokenDao.deleteBankToken(tableid, user);
    }

    public BankConnection checkIfAvailable(String token) {
        try {
            Properties properties = new Properties();
            properties.load(getClass().getClassLoader().getResourceAsStream("connections.properties"));
            final int allowedConnections = Integer.parseInt(properties.getProperty("amountOfConnections"));
            int connections = userDAO.getUserConnections(token);
            boolean limitReached = connections > allowedConnections;
            return new BankConnection(limitReached,allowedConnections);
        } catch (IOException e) {
            return null;
        }
    }
}
