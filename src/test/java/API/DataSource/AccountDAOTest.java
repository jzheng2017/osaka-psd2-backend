package API.DataSource;

import API.DTO.AccountCategory;
import API.DTO.CreateAccountCategoryRequest;
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

import static org.junit.jupiter.api.Assertions.*;

class AccountDAOTest {

    private AccountDAO accountDAOUnderTest;
    private Database db;
    final User user = new User(0, "name", "email", "password", "token");

    @Mock
    private ResultSet mockedResultset;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        accountDAOUnderTest = new AccountDAO();
        db = Mockito.mock(Database.class);
        accountDAOUnderTest.setDb(db);
        Mockito.when(db.query(Mockito.anyString(),Mockito.any())).thenReturn(mockedResultset);
        Mockito.when(mockedResultset.next()).thenReturn(true).thenReturn(false);
        Mockito.when(mockedResultset.first()).thenReturn(true).thenReturn(false);
        Mockito.when(mockedResultset.getString(Mockito.anyString())).thenReturn("generic");
        Mockito.when(mockedResultset.getInt(Mockito.anyString())).thenReturn(0);

    }

    @Test
    void testCreateAccountCategory() {
        // Setup
        final CreateAccountCategoryRequest request = new CreateAccountCategoryRequest("name", "iban");

        // Run the test
        final AccountCategory result = accountDAOUnderTest.createAccountCategory(request, user);
        // Verify the results
        assertNotNull(result);
    }
    @Test
    void testCreateAccountCategoryNull() throws SQLException {
        // Setup
        final CreateAccountCategoryRequest request = new CreateAccountCategoryRequest("name", "iban");

        // Run the test
        Mockito.when(mockedResultset.first()).thenReturn(false);
        final AccountCategory result = accountDAOUnderTest.createAccountCategory(request, user);
        // Verify the results
        assertNull(result);
    }

    @Test
    void testAddToAccountCategory() throws SQLException {
        // Setup
        final CreateAccountCategoryRequest request = new CreateAccountCategoryRequest("name", "iban");

        // Run the test
        Mockito.when(mockedResultset.first()).thenReturn(true);
        final AccountCategory result = accountDAOUnderTest.addToAccountCategory(request, user);

        // Verify the results
        assertNotNull(result);
    }

    @Test
    void testAddToAccountCategoryNull() throws SQLException {
        // Setup
        final CreateAccountCategoryRequest request = new CreateAccountCategoryRequest("name", "iban");

        // Run the test
        Mockito.when(mockedResultset.first()).thenReturn(false).thenReturn(true);
        final AccountCategory result = accountDAOUnderTest.addToAccountCategory(request, user);

        // Verify the results
        assertNotNull(result);
    }

    @Test
    void testGetAccountCategoriesByUserId() throws SQLException {
        // Setup
        // Run the test
        Mockito.when(mockedResultset.next()).thenReturn(true).thenReturn(false);
        final ArrayList<AccountCategory> result = accountDAOUnderTest.getAccountCategoriesByUserId("token");

        // Verify the results
        assertNotNull(result);
    }


    @Test
    void testGetAccountCategoriesByUserIdNull() throws SQLException {
        // Setup
        // Run the test
        Mockito.when(mockedResultset.next()).thenReturn(false);
        final ArrayList<AccountCategory> result = accountDAOUnderTest.getAccountCategoriesByUserId("token");

        // Verify the results
        assertTrue(result.isEmpty());
    }

    @Test
    void testGetAccountCategoryByIban() throws SQLException {
        // Setup
        // Run the test
        Mockito.when(mockedResultset.first()).thenReturn(false);
        final AccountCategory result = accountDAOUnderTest.getAccountCategoryByIban("token", "iban");

        // Verify the results
        assertNull(result);
    }

    @Test
    void testGetAccountCategoryById() throws SQLException {
        // Setup
        final String expectedResult = "result";

        // Run the test
        Mockito.when(mockedResultset.first()).thenReturn(true);
        final String result = accountDAOUnderTest.getAccountCategoryById("categoryId", "token");

        // Verify the results
        assertNotNull( result);
    }
    @Test
    void testGetAccountCategoryByIdNull() throws SQLException {
        // Setup

        // Run the test
        Mockito.when(mockedResultset.first()).thenReturn(false);
        final String result = accountDAOUnderTest.getAccountCategoryById("categoryId", "token");

        // Verify the results
        assertNull(result);
    }
}
