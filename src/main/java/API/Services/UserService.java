package API.Services;

import API.DTO.Auth.LoginResponse;
import API.DTO.User;
import API.DataSource.UserDAO;
import API.HashedPassword;

import java.util.UUID;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
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

        System.out.println(user.getPassword());

        if(user != null && user.checkPassword(password)) {
            var response = new LoginResponse();

            user.setToken(UUID.randomUUID().toString());
            userDAO.updateUserToken(user);

            response.setName(user.getName());
            response.setEmail(user.getEmail());
            response.setToken(user.getToken());

            return response;
        }

        return null;
    }
}
