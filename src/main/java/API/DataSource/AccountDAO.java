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

    public void addToAccountCategory(CreateAccountCategoryRequest request, User user) {
        String categoryId = request.getCategoryId();
        String iban = request.getIban();
        String userId = String.valueOf(user.getId());
        Boolean exists = checkIfAccountExists(userId, iban);
        if(exists) {
            db.query("update.user.account.to.category", new String[]{categoryId, userId, iban});
        } else
        db.query("insert.user.account.to.category", new String[]{iban, userId, categoryId});
    }

    private Boolean checkIfAccountExists(String userId, String iban) {
        try {
            ResultSet resultSet = db.query("check.if.user.account.category.exists", new String[]{userId, iban});
            if(resultSet.first()) {
                return true;
            }
        } catch (SQLException | NullPointerException e) {
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
