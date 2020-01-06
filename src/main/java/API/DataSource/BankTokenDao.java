package API.DataSource;

import API.DTO.Bank;
import API.DTO.BankToken;
import API.DTO.User;
import API.DataSource.core.Database;
import API.Errors.Error;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Logger;

public class BankTokenDao {
    private Database db;
    private static final Logger LOGGER = Logger.getLogger(BankTokenDao.class.getName());
    public BankTokenDao() {
        db = new Database("bank");
    }

    public void attachBankAccountToUser(User user, Bank bank, String accessToken, String refreshToken) {
        var userId = String.valueOf(user.getId());
        var bankStr = String.valueOf(bank);
        db.query("insert.user.account.attachment", new String[]{userId, bankStr, accessToken, refreshToken});
    }

    public List<BankToken> getBankTokensForUser(String token) {
        List<BankToken> bankTokens = new ArrayList<>();
        try {
            ResultSet rs = db.query("select.user.bank.tokens", new String[]{token});
            while (rs.next()) {
                BankToken bankToken = new BankToken(rs.getInt("id"), Bank.valueOf(rs.getString("bank")), rs.getString("access_token"), rs.getString("refresh_token"));
                bankTokens.add(bankToken);
            }
        } catch (SQLException e) {
            LOGGER.severe(Error.DATABASEERROR + e);
        }
        return bankTokens;
    }

    public BankToken getBankTokensForUser(String token, String id) {
        try {
            ResultSet rs = db.query("select.user.bank.tokens.with.tokenid", new String[]{id,token});
            if (rs.first()) {
                return new BankToken(rs.getInt("id"), Bank.valueOf(rs.getString("bank")), rs.getString("access_token"), rs.getString("refresh_token"));
            }
        } catch (SQLException e) {
            LOGGER.severe(Error.DATABASEERROR + e);
        }
        return null;
    }

    public void updateBankToken(BankToken bankToken) {
        var id = String.valueOf(bankToken.getId());
        if (bankToken.getAccessToken() != null && bankToken.getRefreshToken() != null) {
            db.query("update.user.bank.tokens", new String[]{bankToken.getAccessToken(), bankToken.getRefreshToken(), id});
        }
    }

    public void deleteBankToken(String tableid,String token) {
        var id = String.valueOf(tableid);
        db.query("delete.user.bank.token", new String[] {id, token});
    }

    public void setDb(Database db) {
        this.db = db;
    }
}
