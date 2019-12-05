package API.DataSource;

import API.DTO.*;
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

    public boolean createAccountCategory(CreateAccountCategoryRequest request, User user) {
        try {
            String name = request.getName();
            String userId = String.valueOf(user.getId());

            db.query("insert.user.account.category", new String[]{name, userId});
        } catch (NullPointerException e) {
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
            return false;
        }
        return true;
    }

    public void createTransactionCategory(Transaction transaction, User user, String name, String color) {
        db.query("insert.user.transaction.category", new String[]{});
    }

    public boolean addToAccountCategory(CreateAccountCategoryRequest request, User user) {
        String name = request.getName();
        String iban = request.getIban();
        String userId = String.valueOf(user.getId());
        String categoryId = getCategoryIdByName(name, userId);
        if(categoryId != null) {
            db.query("insert.user.account.to.category", new String[]{iban, userId, categoryId});
            return true;
        } else return false;
    }

    private String getCategoryIdByName(String name, String userId) {
        try {
            ResultSet rs = db.query("get.user.account.category.by.name", new String[]{name, userId});
            if (rs.first()) {
                return rs.getString("id");
            }
        } catch (SQLException | NullPointerException e) {
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    public ArrayList<AccountCategory> getAccountCategoriesByUserId(User user) {
        String userid = String.valueOf(user.getId());
        ArrayList<AccountCategory> categories = new ArrayList<>();
        try {
            ResultSet rs = db.query("get.user.account.categories", new String[]{userid});
            while (rs.next()) {
                categories.add(new AccountCategory(rs.getString("name"), rs.getString("id")));
            }
        } catch (SQLException | NullPointerException e) {
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
        } catch (SQLException | NullPointerException e) {
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
        return null;
    }
}
