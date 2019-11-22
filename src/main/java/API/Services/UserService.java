package API.Services;

import API.DTO.Auth.RegisterRequest;
import API.DataSource.UserDAO;
import javax.inject.Inject;

public class UserService {
    private UserDAO userDAO;

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void register(RegisterRequest registerRequest) {
        String email = registerRequest.getEmail();
        String password = registerRequest.getPassword();
        userDAO.registerUser(email, password);
    }
}
