package API.DataSource;

import API.DTO.Account;
import API.DTO.Transaction;
import API.DTO.TransactionCategory;
import API.DTO.User;
import API.DataSource.core.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TransactionDAO {
    private Database db;
    private static Logger log = Logger.getLogger(TransactionDAO.class.getName());

    public TransactionDAO() {
        db = new Database("transaction");
    }

    private TransactionCategory instantiateCategory(ResultSet resultSet) {
        var category = new TransactionCategory();

        try {
            if (resultSet.next()) {
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                category.setColor(resultSet.getString("color"));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }

        return category;
    }

    public TransactionCategory createCategory(TransactionCategory category) {
        var name = category.getName();
        var color = category.getColor();
        var user = category.getUser();
        var userId = String.valueOf(user.getId());

        db.query("insert.transaction.category", new String[]{ name, color, userId });
        return getLastCreatedCategory(user);
    }

    private TransactionCategory getLastCreatedCategory(User user) {
        var userId = String.valueOf(user.getId());
        ResultSet resultSet = db.query("select.transaction.category.last.created", new String[]{ userId });
        return instantiateCategory(resultSet);
    }

    public TransactionCategory getCategory(int categoryId, User user) {
        var id = String.valueOf(categoryId);
        var userId = String.valueOf(user.getId());
        ResultSet resultSet = db.query("select.transaction.category", new String[]{ id, userId });
        return instantiateCategory(resultSet);
    }

    public TransactionCategory getCategoryForTransaction(User user, Transaction transaction) {
        var account = getAccountFromTransaction(transaction);
        var iban = account.getIban();
        var name = account.getName();

        var userId = String.valueOf(user.getId());
        ResultSet resultSet = db.query("select.transaction.category.from.transaction", new String[]{ iban, name, userId });
        return instantiateCategory(resultSet);
    }

    private Account getAccountFromTransaction(Transaction transaction) {
        var received = transaction.getReceived();

        if(received != null && received)
            return transaction.getSender();

        return transaction.getReceiver();
    }

    public void addTransactionToCategory(TransactionCategory category, String content) {
        var categoryId = String.valueOf(category.getId());

        db.query("insert.transaction.into.category", new String[]{ categoryId, content });
    }

    public List<TransactionCategory> getCategories(User user) {
        var categories = new ArrayList<TransactionCategory>();

        var userId = String.valueOf(user.getId());
        var resultSet = db.query("select.transaction.categories.for.user", new String[] { userId });

        try {
            while(resultSet.next()) {
                var category = new TransactionCategory();
                category.setId(resultSet.getInt("id"));
                category.setName(resultSet.getString("name"));
                category.setColor(resultSet.getString("color"));

                categories.add(category);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }

        return categories;
    }
}
