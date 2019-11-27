package API.Datasource;

import API.Datasource.util.DatabaseProperties;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public class TransactionDAO {

    private java.sql.Connection Connection;
    DatabaseProperties dp = new DatabaseProperties();

    public void addTransactionComment(int transactionId, String commentText) throws SQLException {
        PreparedStatement makeTransaction = Connection.prepareStatement("INSERT INTO transactions () VALUES() WHERE transactionId = ?");
//        makeTransaction.setString(1, transactionId);
        makeTransaction.executeQuery();
    }

    public void removeTransactionComment(int transactionId) throws SQLException {
        PreparedStatement makeTransaction = Connection.prepareStatement("DELETE comment FROM transactions WHERE transactionId = ?");
        makeTransaction.setInt(1, transactionId);
        makeTransaction.executeQuery();
    }
}
