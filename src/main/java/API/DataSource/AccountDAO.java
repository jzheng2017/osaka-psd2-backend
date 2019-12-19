package API.DataSource;

import API.DTO.AccountCategory;
import API.DTO.CreateAccountCategoryRequest;
import API.DTO.User;
import API.DataSource.core.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.function.Supplier;
import java.util.logging.Level;
import java.util.logging.Logger;

public class AccountDAO {
    private Database db;
    private static final Logger LOGGER = Logger.getLogger(UserDAO.class.getName());

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
        AccountCategory category = getAccountCategoryByIban(userId, iban);
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
            LOGGER.severe("DATABASE ERROR" + e);
        }
        return false;
    }


    public ArrayList<AccountCategory> getAccountCategoriesByUserId(String token) {
        ArrayList<AccountCategory> categories = new ArrayList<>();
        try {
            ResultSet rs = db.query("get.user.account.categories", new String[]{token});
            while (rs.next()) {
                categories.add(new AccountCategory(rs.getString("name"), rs.getString("id")));
            }
        } catch (SQLException e) {
            LOGGER.severe("DATABASE ERROR" + e);
        }
        return categories;
    }

    public AccountCategory getAccountCategoryByIban(String token, String iban) {
        try {
            ResultSet rs = db.query("get.user.account.category.by.iban", new String[]{iban, token});
            if (rs.first()) {
                return new AccountCategory(rs.getString("name"), rs.getString("id"));
            }
        } catch (SQLException  e) {
            LOGGER.severe("DATABASE ERROR" + e);
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
            LOGGER.severe("DATABASE ERROR" + e);
        }
        return null;
    }

    public String getAccountCategoryById(String categoryId, String token) {
        try {
            ResultSet rs = db.query("get.user.account.category.by.id", new String[]{categoryId, token});
            if (rs.first()) {
                return rs.getString("name");
            }
        } catch (SQLException e) {
            LOGGER.severe("DATABASE ERROR" + e);
        }
        return null;
    }

    public void setDb(Database db) {
        this.db = db;
    }
}
