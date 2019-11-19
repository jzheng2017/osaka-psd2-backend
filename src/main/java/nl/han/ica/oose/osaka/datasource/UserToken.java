package nl.han.ica.oose.osaka.datasource;

import java.sql.SQLException;

public class UserToken {

    private java.sql.Connection Connection;

    public void setTokenInDb(String token) throws ClassNotFoundException, SQLException {

        /*
        DatabaseProperties dp = new DatabaseProperties();
        dp.databaseProperties();
        Class.forName(dp.getDriver());
        Connection = DriverManager.getConnection(dp.getConnectionstring());

        PreparedStatement s = Connection.prepareStatement("UPDATE gebruikers SET token = ? WHERE gebruikersnaam = ?");
        //Set variables

        s.execute();

         */
    }
}
