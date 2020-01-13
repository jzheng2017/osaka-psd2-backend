package DataSource.core;

import API.DataSource.core.Database;
import API.DataSource.util.DatabaseProperties;
import API.DataSource.util.SqlLoader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DatabaseTest {
    private Database sut;
    private SqlLoader mockedSqlLoader;
    private DatabaseProperties mockedDatabaseProperties;
    private Connection mockedConnection;
    private PreparedStatement mockedPreparedStatement;
    private ResultSet mockedResultSet;

    @BeforeEach
    void setup() {
        sut = new Database();
        mockedDatabaseProperties = mock(DatabaseProperties.class);
        mockedSqlLoader = mock(SqlLoader.class);
        mockedConnection = mock(Connection.class);
        mockedPreparedStatement = mock(PreparedStatement.class);
        mockedResultSet = mock(ResultSet.class);
        sut.setDbProperties(mockedDatabaseProperties);
        sut.setSqlLoader(mockedSqlLoader);
        Database.setConnection(mockedConnection);
    }

    @Test
    void callToMakeQueryReturnsResultSet() throws SQLException {
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
        when(mockedSqlLoader.get(anyString())).thenReturn("");
        when(mockedPreparedStatement.execute()).thenReturn(true);
        when(mockedPreparedStatement.getResultSet()).thenReturn(mockedResultSet);
        Assertions.assertNotNull(sut.query("", new String[]{"1", "2"}));
    }

    @Test
    void getConnectionReturnsNewConnection() {
        Database.setConnection(null);
        when(mockedDatabaseProperties.getDriver()).thenReturn("com.mysql.cj.jdbc.Driver");
        when(mockedDatabaseProperties.getConnectionString()).thenReturn("jdbc:mysql://localhost:3306/psd2db?user=root&password=root");

        sut.getConnection();

        verify(mockedDatabaseProperties).getDriver();
        verify(mockedDatabaseProperties).getConnectionString();

    }

    @Test
    void databaseConstructorRunsSuccesfully() {
        sut = new Database("user");
    }
}
