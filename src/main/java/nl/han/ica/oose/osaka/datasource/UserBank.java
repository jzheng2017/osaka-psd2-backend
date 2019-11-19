package nl.han.ica.oose.osaka.datasource;

import nl.han.ica.oose.osaka.dto.Bank;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class UserBank {

    private java.sql.Connection Connection;
//    DatabaseProperties dp = new DatabaseProperties();
//    dp.databaseProperties();
//    Class.forName(dp.getDriver());
//    Connection = DriverManager.getConnection(dp.getConnectionstring());

    public void attatchBankAccountToUser(String token, Bank bank) throws SQLException {
        PreparedStatement attatchBankAccount = Connection.prepareStatement("INSERT INTO users () VALUES() WHERE token = ?");
        attatchBankAccount.setString(1, token);
        attatchBankAccount.executeQuery();
    }

    public void markBankAccount(String token, Bank bank) throws SQLException {
        PreparedStatement markBankAccount = Connection.prepareStatement("INSERT INTO users () VALUES() WHERE token = ?");
        markBankAccount.setString(1, token);
        markBankAccount.executeQuery();
    }

    public void unmarkBankAccount(String token, Bank bank) throws SQLException {
        PreparedStatement markBankAccount = Connection.prepareStatement("INSERT INTO bank_account_mark () VALUES() WHERE token = ?");
        markBankAccount.setString(1, token);
        markBankAccount.executeQuery();
    }
}
