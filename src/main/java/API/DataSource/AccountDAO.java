package API.DataSource;

import API.DTO.AccountCategory;
import API.DTO.CreateAccountCategoryRequest;
import API.DTO.User;
import API.DataSource.core.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountDAO {
    private Database db;
    private static Logger log = Logger.getLogger(UserDAO.class.getName());

    public AccountDAO() {
        db = new Database("account");
    }

    public AccountCategory createAccountCategory(CreateAccountCategoryRequest request, User user) {
        String name = request.getName();
        String userId = String.valueOf(user.getId());
        db.query("insert.user.account.category", new String[]{name, userId});
        return getAccountCategoryByName(name, userId);
    }

    public AccountCategory addToAccountCategory(CreateAccountCategoryRequest request, User user) {
        String categoryId = request.getId();
        String iban = request.getIban();
        String userId = String.valueOf(user.getId());
        Boolean exists = checkIfAccountExists(userId, iban);
        if (exists) {
            db.query("update.user.account.to.category", new String[]{categoryId, userId, iban});
        } else {
            db.query("insert.user.account.to.category", new String[]{iban, userId, categoryId});
        }
        AccountCategory category = getAccountCategoryByIban(user, iban);
        category.setIban(iban);
        return category;
    }

    private Boolean checkIfAccountExists(String userId, String iban) {
        try {
            ResultSet resultSet = db.query("check.if.user.account.category.exists", new String[]{userId, iban});
            if (resultSet.first()) {
                return true;
            }
        } catch (SQLException e) {
            log.log(Level.INFO, Arrays.toString(e.getStackTrace()));
        }
        return false;
    }


    public ArrayList<AccountCategory> getAccountCategoriesByUserId(User user) {
        String userid = String.valueOf(user.getId());
        ArrayList<AccountCategory> categories = new ArrayList<>();
        try {
            ResultSet rs = db.query("get.user.account.categories", new String[]{userid});
            while (rs.next()) {
                categories.add(new AccountCategory(rs.getString("name"), rs.getString("id")));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
        return categories;
    }

    public AccountCategory getAccountCategoryByIban(User user, String iban) {
        String userid = String.valueOf(user.getId());
        try {
            ResultSet rs = db.query("get.user.account.category.by.iban", new String[]{iban, userid});
            if (rs.first()) {
                return new AccountCategory(rs.getString("name"), rs.getString("id"));
            }
        } catch (SQLException  e) {
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    private AccountCategory getAccountCategoryByName(String name, String userId) {
        try {
            ResultSet rs = db.query("get.user.account.category.by.name", new String[]{name, userId});
            if (rs.first()) {
                return new AccountCategory(rs.getString("name"), rs.getString("id"));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    public String getAccountCategoryById(String categoryId, int id) {
        String userId = String.valueOf(id);
        try {
            ResultSet rs = db.query("get.user.account.category.by.id", new String[]{categoryId, userId});
            if (rs.first()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    public void setDb(Database db) {
        this.db = db;
    }
}
