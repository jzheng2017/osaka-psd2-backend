package API.DataSource;

import API.DTO.Bank;
import API.DTO.BankToken;
import API.DTO.User;
import API.DataSource.core.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class BankTokenDao {
    private Database db;

    public BankTokenDao() {
        db = new Database("bank");
    }

    public void attachBankAccountToUser(User user, String accessToken, String refreshToken) {
        var userId = String.valueOf(user.getId());
        var bankId = "1";
        db.query("insert.user.account.attachment", new String[] { userId, bankId, accessToken, refreshToken });
    }

    public List<BankToken> getBankTokensForUser(User user) {
        var userId = String.valueOf(user.getId());

        ResultSet rs = db.query("select.user.bank.tokens", new String[] { userId });

        List<BankToken> bankTokens = new ArrayList<>();

        try {
            while(rs.next()) {
                bankTokens.add(new BankToken(rs.getInt("id"), rs.getString("access_token"), rs.getString("refresh_token")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return bankTokens;
    }

    public void updateBankToken(BankToken bankToken) {
        var id = String.valueOf(bankToken.getId());
        db.query("update.user.bank.tokens", new String[] { bankToken.getAccessToken(), bankToken.getRefreshToken(), id });
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
