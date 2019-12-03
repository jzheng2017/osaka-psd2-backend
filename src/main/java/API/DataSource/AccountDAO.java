package API.DataSource;

import API.DataSource.core.Database;

import java.util.logging.Logger;

public class AccountDAO {
    private Database db;
    private static Logger log = Logger.getLogger(AccountDAO.class.getName());

    public AccountDAO(){
        db = new Database("account");
    }

    public void addTransactionCategory(int userId, String IBAN, String categoryName, String categoryColor){

        db.query("insert.user.transaction.category", new String[]{String.valueOf(userId), IBAN, categoryName, categoryColor});
    }

}
