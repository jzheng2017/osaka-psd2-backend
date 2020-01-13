package API.Services;

import API.Banks.ClientFactory;
import API.DTO.AccountAttach;
import API.DTO.Auth.LoginResponse;
import API.DTO.Auth.RegisterRequest;
import API.DTO.BankToken;
import API.DTO.User;
import API.DataSource.BankTokenDao;
import API.DataSource.UserDAO;
import API.Utils.HashedPassword;
import javax.inject.Inject;
import java.util.ArrayList;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserService {
    private UserDAO userDAO;
    private BankTokenDao bankTokenDao;
    private static final Logger LOGGER = Logger.getLogger(UserService.class.getName());

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
            var token = UUID.randomUUID().toString();
            user.setToken(token);
            userDAO.updateUserToken(user);

            response.setName(user.getName());
            response.setEmail(user.getEmail());
            response.setToken(user.getToken());

            refreshAccessTokens(token);

            return response;
        }

        return null;
    }

    public User getUserByToken(String token) {
        return userDAO.getUserByToken(token);
    }

    private void refreshAccessTokens(String token) {
        var bankTokens = bankTokenDao.getBankTokensForUser(token);
        for (BankToken bankToken : bankTokens) {
            var client = ClientFactory.getClient(bankToken.getBank());
            BankToken refreshedBankToken = client.refresh(bankToken.getRefreshToken());
            refreshedBankToken.setId(bankToken.getId());
            if (refreshedBankToken.getAccessToken() == null) {
                LOGGER.log(Level.INFO,"NO ACCESS TOKEN FOUND FOR " + bankToken.getBank());
            } else {
                bankTokenDao.updateBankToken(refreshedBankToken);
            }
        }
    }

    public ArrayList<AccountAttach> getAttachedAccounts(String token) {
        return userDAO.getAttachedAccounts(token);
    }
}
