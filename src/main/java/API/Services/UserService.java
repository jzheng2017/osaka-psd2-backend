package API.Services;

import API.Adapters.BaseAdapter;
import API.Adapters.BankAdapter;
import API.DTO.Auth.LoginResponse;
import API.DTO.BankToken;
import API.DTO.User;
import API.Datasource.BankTokenDao;
import API.Datasource.UserDAO;
import API.HashedPassword;
import java.util.List;
import java.util.UUID;

public class UserService {
    private UserDAO userDAO;
    private BankTokenDao bankTokenDao;

    public UserService() {
        this.userDAO = new UserDAO();
        this.bankTokenDao = new BankTokenDao();
    }

    public LoginResponse register(String name, String email, String password) {
        User user = userDAO.getUserByEmail(email);
        if (user != null) {
            return null;
        }

        var hashedPassword = HashedPassword.generate(password);

        userDAO.registerUser(name, email, hashedPassword);
        return login(email, password);
    }

    public LoginResponse login(String email, String password) {
        User user = userDAO.getUserByEmail(email);

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

    private void refreshAccessTokens(User user) {
        List<BankToken> bankTokens = bankTokenDao.getBankTokensForUser(user);

        for (BankToken bankToken : bankTokens) {
            BaseAdapter adapter = new BankAdapter(bankToken.getBank());
            BankToken refreshedBankToken = adapter.refresh(bankToken.getRefreshToken());
            refreshedBankToken.setId(bankToken.getId());
            bankTokenDao.updateBankToken(refreshedBankToken);
        }
    }

    public void attachBankAccount(String token, BankToken bankToken) {
        User user = userDAO.getUserByToken(token);
        bankTokenDao.attachBankAccountToUser(user, bankToken.getBank(), bankToken.getAccessToken(), bankToken.getRefreshToken());
    }
}
