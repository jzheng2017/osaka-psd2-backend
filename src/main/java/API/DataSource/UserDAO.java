package API.DataSource;

import API.DTO.AccountAttach;
import API.DTO.User;
import API.DataSource.core.Database;

import java.sql.Array;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class UserDAO {
    private Database db;
    private static Logger log = Logger.getLogger(UserDAO.class.getName());

    public UserDAO() {
        db = new Database("user");
    }

    public void registerUser(String name, String email, String password) {
        db.query("insert.user.in.db", new String[]{name, email, password});
    }

    public User getUserByEmail(String email) {
        ResultSet rs = db.query("select.user.by.login.email", new String[]{email});

        try {
            if (rs.next()) {
                return new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getString("token"));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }

        return null;
    }

    public User getUserByToken(String token) {
            ResultSet rs = db.query("select.user.by.login.token", new String[]{token});

        try {
            if (rs.next()) {
                User user = new User(rs.getInt("id"), rs.getString("name"), rs.getString("email"), rs.getString("password"), rs.getString("token"));
                return user;
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }

        return null;
    }

    public void updateUserToken(User user) {
        db.query("update.user.token", new String[]{user.getToken(), String.valueOf(user.getId())});
    }

    public ArrayList<AccountAttach> getAttachedAccounts(User user){
        ArrayList<AccountAttach> attachedAccounts = new ArrayList<>();
        try {
            var userId = String.valueOf(user.getId());
            ResultSet rs = db.query("select.user.attached.accounts", new String[]{userId});
            while (rs.next()) {
                AccountAttach accountAttach = new AccountAttach(rs.getInt("id"), rs.getString("bank"), rs.getInt("user_id"));
                attachedAccounts.add(accountAttach);
            }
        } catch (SQLException | NullPointerException e) {
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }

        return attachedAccounts;
    }
}
