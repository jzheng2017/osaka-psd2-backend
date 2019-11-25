package API.Services;

import API.Adapter.BankAdapter;
import API.Adapter.RaboAdapter;
import API.DTO.Auth.LoginResponse;
import API.DTO.BankToken;
import API.DTO.User;
import API.DataSource.BankTokenDao;
import API.DataSource.UserDAO;
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
        if(user != null) {
            return null;
        }

        var hashedPassword = HashedPassword.generate(password);

        userDAO.registerUser(name, email, hashedPassword);
        return login(email, password);
    }

    public LoginResponse login(String email, String password) {
        User user = userDAO.getUserByEmail(email);

        if(user != null && user.checkPassword(password)) {
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

        for(BankToken bankToken : bankTokens) {
            BankAdapter adapter = new RaboAdapter();
            BankToken refreshedBankToken = adapter.refresh(bankToken.getRefreshToken());
            
        }
    }

    public void attachBankAccount(String token, String accessToken, String refreshToken) {
        User user = userDAO.getUserByToken(token);
        bankTokenDao.attachBankAccountToUser(user, accessToken, refreshToken);
    }
}
