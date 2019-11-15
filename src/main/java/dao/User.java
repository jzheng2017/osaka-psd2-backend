package dao;

import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class User {

    private java.sql.Connection Connection;
    DatabaseProperties dp = new DatabaseProperties();
    dp.databaseProperties();
    Class.forName(dp.getDriver());
    Connection = DriverManager.getConnection(dp.getConnectionstring());

    public void registerUser(String email, String password) throws SQLException {
        PreparedStatement registerUser = Connection.prepareStatement("INSERT INTO users () VALUES()");
        registerUser.setString(1, string);
        registerUser.executeQuery();
    }

    public boolean checkLogin() throws ClassNotFoundException, SQLException {
        boolean loginValid = false;


        return loginValid;
    }



}
