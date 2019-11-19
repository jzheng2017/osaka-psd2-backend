package API.DataSource;

import API.DataSource.util.DatabaseProperties;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

public class Transaction {

    private java.sql.Connection Connection;
    DatabaseProperties dp = new DatabaseProperties();
//    dp.databaseProperties();
//    Class.forName(dp.getDriver());
//    Connection = DriverManager.getConnection(dp.getConnectionstring());


    public ArrayList<Transaction> getTransactionsForBankAccount() throws SQLException {
        ArrayList<Transaction> transactionList = new ArrayList();

        PreparedStatement getTransactions = Connection.prepareStatement("SELECT * FROM transactions WHERE bankAccountNumber = ?");
        ResultSet rs = getTransactions.executeQuery();

        while (rs.next()) {
            Transaction transaction = new Transaction();

            transactionList.add(transaction);
        }
        return transactionList;
    }

    public void makeTransaction() throws SQLException {

        PreparedStatement makeTransaction = Connection.prepareStatement("INSERT INTO transactions () VALUES() WHERE bankAccountNumber = ?");
//        makeTransaction.setString(1, string);
        makeTransaction.executeQuery();

    }

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
