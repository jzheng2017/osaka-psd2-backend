package API.DataSource;

import API.DTO.Bank;
import API.DTO.BankToken;
import API.DTO.User;
import API.DataSource.core.Database;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BankTokenDao {
    private Database db;
    private static Logger log = Logger.getLogger(BankTokenDao.class.getName());
    public BankTokenDao() {
        db = new Database("bank");
    }

    public void attachBankAccountToUser(User user, Bank bank, String accessToken, String refreshToken) {
        var userId = String.valueOf(user.getId());
        var bankStr = String.valueOf(bank);
        db.query("insert.user.account.attachment", new String[]{userId, bankStr, accessToken, refreshToken});
    }

    public List<BankToken> getBankTokensForUser(User user) {
        var userId = String.valueOf(user.getId());
        ResultSet rs = db.query("select.user.bank.tokens", new String[]{userId});
        List<BankToken> bankTokens = new ArrayList<>();

        try {
            while (rs.next()) {
                BankToken bankToken = new BankToken(rs.getInt("id"), Bank.valueOf(rs.getString("bank")), rs.getString("access_token"), rs.getString("refresh_token"));
                bankTokens.add(bankToken);
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }

        return bankTokens;
    }

    public BankToken getBankTokensForUser(User user, String id) {
        var userId = String.valueOf(user.getId());
        ResultSet rs = db.query("select.user.bank.tokens.with.tokenid", new String[]{userId, id});
        try {
            if (rs.first()) {
                return new BankToken(rs.getInt("id"), Bank.valueOf(rs.getString("bank")), rs.getString("access_token"), rs.getString("refresh_token"));
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
        return null;
    }

    public void updateBankToken(BankToken bankToken) {
        var id = String.valueOf(bankToken.getId());
        db.query("update.user.bank.tokens", new String[]{bankToken.getAccessToken(), bankToken.getRefreshToken(), id});
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
