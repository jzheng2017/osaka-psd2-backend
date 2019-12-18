package API.DataSource;

import API.DTO.Account;
import API.DTO.Transaction;
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

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class DummyDAOTest {

    private DummyDAO dummyDAOUnderTest;
    private Database db;
    final Account expectedAccount = new Account("id", "generic", "generic", "generic", 0.0);
    final Transaction expectedTransaction = new Transaction("generic","generic",expectedAccount,expectedAccount,false,"generic","generic");
    @Mock
    private ResultSet mockedResultset;

    @BeforeEach
    void setUp() throws SQLException {
        MockitoAnnotations.initMocks(this);
        dummyDAOUnderTest = new DummyDAO();
        db = Mockito.mock(Database.class);
        dummyDAOUnderTest.setDb(db);
        when(db.query(anyString(),any())).thenReturn(mockedResultset);
        when(mockedResultset.next()).thenReturn(true).thenReturn(false);
        when(mockedResultset.first()).thenReturn(true).thenReturn(false);
        when(mockedResultset.getString(anyString())).thenReturn("generic");
        when(mockedResultset.getInt(anyString())).thenReturn(0);
    }

    @Test
    void testGetAllAccounts() {
        // Setup

        // Run the test
        final ArrayList<Account> result = dummyDAOUnderTest.getAllAccounts();

        // Verify the results
        assertEquals(expectedAccount.getIban(), result.get(0).getIban());
    }

    @Test
    void testGetAccountById() {
        // Setup

        // Run the test
        final Account result = dummyDAOUnderTest.getAccountById("accountId");

        // Verify the results
        assertEquals(expectedAccount.getIban(), result.getIban());
    }

    @Test
    void testGetAllTransactions() {
        // Setup

        // Run the test
        final ArrayList<Transaction> result = dummyDAOUnderTest.getAllTransactions("accountId");

        // Verify the results
        assertEquals(expectedTransaction.getDate(), result.get(0).getDate());
    }
}
