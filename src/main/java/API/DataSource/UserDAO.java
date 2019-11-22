package API.DataSource;

import API.DTO.User;
import API.DataSource.core.Database;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private Database db ;

    public UserDAO() {
        db = new Database("user");
    }

    public boolean userExistsWithEmail(String email) {
        ResultSet checkForExistingEmail = db.query("select.email.where.email.is", new String[] { email });
        try {
            return checkForExistingEmail.next();
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }

    public void registerUser(String name, String email, String password) {
        db.query("insert.user.in.db", new String[] { name, email, password });
    }

    public User getUserByEmail(String email) {
        ResultSet rs =  db.query("select.user.by.login.email", new String[] { email });

        try {
            if(rs.next()) {
                return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getString("token"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public User getUserByCredentials(String email, String password) {
        ResultSet rs =  db.query("select.user.by.login.email", new String[] { email });

        try {
            while (rs.next()) {
                if(password.equals(rs.getString("password")))
                    return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getString("token"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }

    public void updateUserToken(User user) {
        db.query("update.user.token", new String[] { user.getToken(), String.valueOf(user.getId()) });
    }
}
