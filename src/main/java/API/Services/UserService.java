package API.Services;

import API.Banks.BankClient;
import API.DTO.AccountAttach;
import API.DTO.Auth.LoginResponse;
import API.DTO.Auth.RegisterRequest;
import API.DTO.BankToken;
import API.DTO.User;
import API.DataSource.BankTokenDao;
import API.DataSource.UserDAO;
import API.HashedPassword;
import javax.inject.Inject;
import java.util.ArrayList;
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
}
