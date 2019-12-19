package API.DataSource;

import API.DTO.Account;
import API.DTO.Transaction;
import API.DataSource.core.Database;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class DummyDAO {
    private Database db;
    private static final Logger LOGGER = Logger.getLogger(DummyDAO.class.getName());

    public DummyDAO() {
        db = new Database("dummy");
    }

    public ArrayList<Account> getAllAccounts() {
        var accounts = new ArrayList<Account>();
        try {
            var resultSet = db.query("get.all.accounts.dummy", new String[]{});
            while (resultSet.next()) {
                accounts.add(
                        new Account(
                                String.valueOf(resultSet.getInt("user_id")),
                                resultSet.getString("iban"),
                                resultSet.getString("name"),
                                resultSet.getString("currency"),
                                resultSet.getDouble("balance")
                        )
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,e.toString());
        }
        return accounts;
    }

    public Account getAccountById(String accountId) {
        try {
            var resultSet = db.query("get.account.from.userid.dummy", new String[]{accountId});
            if (resultSet.first()) {
                return new Account(
                        String.valueOf(resultSet.getInt("user_id")),
                        resultSet.getString("iban"),
                        resultSet.getString("name"),
                        resultSet.getString("currency"),
                        resultSet.getDouble("balance")
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,e.toString());
        }
        return null;
    }


    public ArrayList<Transaction> getAllTransactions(String accountId) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        try {
            var resultSet = db.query("get.all.transactions.for.account.dummy", new String[]{accountId});
            while (resultSet.next()) {
                transactions.add(
                        new Transaction(
                                resultSet.getString("date"),
                                resultSet.getString("transactionType"),
                                getAccountById(resultSet.getString("creditorAccountId")),
                                getAccountById(resultSet.getString("debtorAccountId")),
                                resultSet.getBoolean("received"),
                                resultSet.getString("amount"),
                                String.valueOf(resultSet.getInt("transactionId"))
                        )
                );
            }
        } catch (SQLException e) {
            LOGGER.log(Level.SEVERE,e.toString());
        }
        return transactions;
    }

    public void setDb(Database db) {
        this.db = db;
    }
}
