package API.Services;

import API.DTO.Auth.LoginResponse;
import API.DTO.User;
import API.DataSource.UserDAO;

import java.util.UUID;

public class UserService {
    private UserDAO userDAO;

    public UserService() {
        this.userDAO = new UserDAO();
    }

    public LoginResponse register(String name, String email, String password) {
        userDAO.registerUser(name, email, password);
        return login(email, password);
    }

    public LoginResponse login(String email, String password) {
        User user = userDAO.getUserByCredentials(email, password);


        if(user != null) {
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
