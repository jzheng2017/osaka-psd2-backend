package API.DataSource;

import API.DataSource.core.Database;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {
    private Database db = new Database("user");

    public void setDatabase(Database database){
        this.db = database;
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

    public void registerUser(String email, String password) {
        db.query("insert.user.in.db", new String[] { email, password });
    }

    public boolean checkLogin(String email, String password) {
        ResultSet rs =  db.query("select.user.by.login.email", new String[] { email });

        try {
            while (rs.next()) {
                if(password.equals(rs.getString("password")))
                    return true;
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return false;
    }
}
