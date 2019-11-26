package API.Datasource.core;

import API.Datasource.util.DatabaseProperties;
import API.Datasource.util.SqlLoader;

import java.sql.*;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Database {
    private DatabaseProperties dbProperties;
    private SqlLoader sqlLoader;
    private static Connection connection;
    private static Logger log = Logger.getLogger(Database.class.getName());

    public Database(String resource) {
        this.sqlLoader = new SqlLoader(resource);
        this.dbProperties = new DatabaseProperties();
        getConnection();
    }

    public Database() {

    }

    public ResultSet query(String sql, String[] parameters) {
        ResultSet result = null;
        try {
            PreparedStatement statement = connection.prepareStatement(sqlLoader.get(sql));

            if (parameters != null) {
                for (int i = 1; i <= parameters.length; i++) {
                    statement.setString(i, parameters[i - 1]);
                }
            }

            if (statement.execute()) {
                result = statement.getResultSet();
            }
        } catch (SQLException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
        return result;
    }


    public void getConnection() {
        try {
            Class.forName(dbProperties.getDriver());

            if (connection == null) {
                connection = DriverManager.getConnection(dbProperties.getConnectionString());
            }

        } catch (SQLException | ClassNotFoundException e) {
            log.log(Level.SEVERE, e.getMessage());
        }
    }


    public void setDbProperties(DatabaseProperties dbProperties) {
        this.dbProperties = dbProperties;
    }

    public static void setConnection(Connection connection) {
        Database.connection = connection;
    }

    public void setSqlLoader(SqlLoader sqlLoader) {
        this.sqlLoader = sqlLoader;
    }

    public void setLog(Logger log) {
        this.log = log;
    }
}
