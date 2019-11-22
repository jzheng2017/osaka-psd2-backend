import API.DataSource.UserDAO;

public class MainTest {

    public static void main(String[] args) {
        UserDAO userDAO = new UserDAO();
        userDAO.registerUser("", "", "");
    }
}
