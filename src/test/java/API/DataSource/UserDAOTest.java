package API.DataSource;

import API.DTO.AccountAttach;
import API.DTO.User;
import API.DataSource.core.Database;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class UserDAOTest {

    private UserDAO userDAOUnderTest;
    private Database db;
    private final String generic = "generic";
    private final User user = new User(0, generic, generic, generic, generic);

    @BeforeEach
    void setUp() throws SQLException {
        userDAOUnderTest = new UserDAO();
        MockitoAnnotations.initMocks(this);
        db = Mockito.mock(Database.class);
        userDAOUnderTest.setDb(db);
        when(db.query(anyString(), any())).thenReturn(mockedResultset);
        when(mockedResultset.next()).thenReturn(true).thenReturn(false);
        when(mockedResultset.first()).thenReturn(true).thenReturn(false);
        when(mockedResultset.getString(anyString())).thenReturn(generic);
        when(mockedResultset.getInt(anyString())).thenReturn(0);
    }

    @Mock
    private ResultSet mockedResultset;

    @Test
    void testRegisterUser() {
        // Setup

        // Run the test
        userDAOUnderTest.registerUser("name", "email", "password");
        // Verify the results
        verify(db).query(anyString(),any());
    }

    @Test
    void testGetUserByEmail() {
        // Setup

        // Run the test
        final User result = userDAOUnderTest.getUserByEmail("email");

        // Verify the results
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void testGetUserByToken() {
        // Setup

        // Run the test
        final User result = userDAOUnderTest.getUserByToken("token");

        // Verify the results
        assertEquals(user.getEmail(), result.getEmail());
    }

    @Test
    void testGetUserByEmailError() throws SQLException {
        // Setup

        // Run the test
        when(mockedResultset.next()).thenReturn(false);
        final User result = userDAOUnderTest.getUserByEmail("email");

        // Verify the results
        assertNull(result);
    }

    @Test
    void testGetUserByTokenError() throws SQLException {
        // Setup

        // Run the test
        when(mockedResultset.next()).thenReturn(false);
        final User result = userDAOUnderTest.getUserByToken("token");

        // Verify the results
        assertNull(result);
    }

    @Test
    void testUpdateUserToken() {
        // Setup

        // Run the test
        userDAOUnderTest.updateUserToken(user);

        // Verify the results
        verify(db).query(anyString(),any());
    }

    @Test
    void testGetAttachedAccounts() {
        // Setup

        // Run the test
        final ArrayList<AccountAttach> result = userDAOUnderTest.getAttachedAccounts("token");

        // Verify the results
        assertNotNull(result);
    }

    @Test
    void testGetUserConnections() {
        // Setup
        final int expectedResult = 0;

        // Run the test
        final int result = userDAOUnderTest.getUserConnections("token");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetUserConnectionsError() throws SQLException {
        // Setup
        final int expectedResult = 0;

        // Run the test
        when(mockedResultset.next()).thenReturn(false);
        final int result = userDAOUnderTest.getUserConnections("token");

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
