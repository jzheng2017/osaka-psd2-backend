package API.DataSource;

import API.DTO.Bank;
import API.DTO.User;
import API.DataSource.core.Database;

public class UserBankAccountDAO {
    private Database db = new Database("bank");

    public void setDatabase(Database database){
        this.db = database;
    }

    public void attachBankAccountToUser(User user, Bank bank, String refreshToken) {
        var userId = String.valueOf(user.getId());
        var bankId = String.valueOf(bank.getId());
        db.query("insert.user.account.attachment", new String[] { userId, bankId, refreshToken });
    }

    public void markBankAccount(String token, Bank bank) {
        /*
        PreparedStatement markBankAccount = Connection.prepareStatement("INSERT INTO users () VALUES() WHERE token = ?");
        markBankAccount.setString(1, token);
        markBankAccount.executeQuery();
         */
    }

    public void unmarkBankAccount(String token, Bank bank) {
        /*
        PreparedStatement markBankAccount = Connection.prepareStatement("INSERT INTO bank_account_mark () VALUES() WHERE token = ?");
        markBankAccount.setString(1, token);
        markBankAccount.executeQuery();
         */
    }
}
