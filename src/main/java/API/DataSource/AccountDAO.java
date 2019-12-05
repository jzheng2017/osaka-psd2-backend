package API.DataSource;

import API.DTO.Account;
import API.DTO.Transaction;
import API.DTO.User;
import API.DataSource.core.Database;

import java.util.logging.Logger;

public class AccountDAO {
    private Database db;
    private static Logger log = Logger.getLogger(UserDAO.class.getName());

    public AccountDAO() {
        db = new Database("account");
    }

    public void createAccountType(Account account, User user, String name) {
        var iban = account.getIban();
        var userId = String.valueOf(user.getId());

        db.query("insert.user.account.type", new String[]{ name, iban, userId });
    }

    public void createTransactionCategory(Transaction transaction, User user, String name, String color) {
        db.query("insert.user.transaction.category", new String[]{  });
    }
}
