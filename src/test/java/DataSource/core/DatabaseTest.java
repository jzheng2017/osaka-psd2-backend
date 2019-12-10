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
import java.util.logging.Logger;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.*;

public class DatabaseTest {
    private Database sut;
    private SqlLoader mockedSqlLoader;
    private DatabaseProperties mockedDatabaseProperties;
    private Connection mockedConnection;
    private PreparedStatement mockedPreparedStatement;
    private ResultSet mockedResultSet;
    private Logger mockedLogger;

    @BeforeEach
    void setup() {
        sut = new Database();
        mockedDatabaseProperties = mock(DatabaseProperties.class);
        mockedSqlLoader = mock(SqlLoader.class);
        mockedConnection = mock(Connection.class);
        mockedPreparedStatement = mock(PreparedStatement.class);
        mockedResultSet = mock(ResultSet.class);
        mockedLogger = mock(Logger.class);
        sut.setDbProperties(mockedDatabaseProperties);
        sut.setSqlLoader(mockedSqlLoader);
        sut.setLog(mockedLogger);
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
    void whenStatementExecuteThrowsSQLExceptionLogIsCalled() throws SQLException {
        when(mockedConnection.prepareStatement(anyString())).thenReturn(mockedPreparedStatement);
        when(mockedSqlLoader.get(anyString())).thenReturn("");

        when(mockedPreparedStatement.execute()).thenThrow(new SQLException("test"));
        sut.query("", null);
        verify(mockedLogger).log(any(), anyString());
    }

    @Test
    void getConnectionReturnsNewConnection() {
        Database.setConnection(null);
        when(mockedDatabaseProperties.getDriver()).thenReturn("com.mysql.cj.jdbc.Driver");
        when(mockedDatabaseProperties.getConnectionString()).thenReturn("jdbc:mysql://localhost:3306/psd2db?user=tristan_admin&password=MazdaMiata5");

        sut.getConnection();

        verify(mockedDatabaseProperties).getDriver();
        verify(mockedDatabaseProperties).getConnectionString();

    }

    @Test
    void databaseConstructorRunsSuccesfully() {
        sut = new Database("user");
    }
}
