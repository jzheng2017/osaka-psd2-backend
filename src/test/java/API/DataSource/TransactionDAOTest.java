package API.DataSource;

import API.DTO.Account;
import API.DTO.Transaction;
import API.DTO.TransactionCategory;
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
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class TransactionDAOTest {

    private TransactionDAO transactionDAOUnderTest;
    final TransactionCategory transactionCategory = new TransactionCategory();
    private Database db;
    private final String generic = "generic";
    private final User user = new User(0, generic, generic, generic, generic);

    @BeforeEach
    void setUp() throws SQLException {
        transactionDAOUnderTest = new TransactionDAO();
        transactionCategory.setId(0);
        transactionCategory.setUser(user);
        transactionCategory.setName(generic);
        transactionCategory.setColor(generic);
        MockitoAnnotations.initMocks(this);
        db = Mockito.mock(Database.class);
        transactionDAOUnderTest.setDb(db);
        when(db.query(anyString(), any())).thenReturn(mockedResultset);
        when(mockedResultset.next()).thenReturn(true).thenReturn(false);
        when(mockedResultset.first()).thenReturn(true).thenReturn(false);
        when(mockedResultset.getString(anyString())).thenReturn(generic);
        when(mockedResultset.getInt(anyString())).thenReturn(0);
    }

    @Mock
    private ResultSet mockedResultset;

    @Test
    void testCreateCategory() {
        // Setup

        // Run the test
        final TransactionCategory result = transactionDAOUnderTest.createCategory(transactionCategory);

        // Verify the results
        assertEquals(transactionCategory.getColor(), result.getColor());
    }

    @Test
    void testGetCategory() {
        // Setup

        // Run the test
        final TransactionCategory result = transactionDAOUnderTest.getCategory(0, user);

        // Verify the results
        assertEquals(transactionCategory.getColor(), result.getColor());
    }

    @Test
    void testGetCategoryForTransaction() {
        // Setup
        Account account = new Account(generic,generic,generic,generic,200.0);
        final Transaction transaction = new Transaction(generic,generic,account,account,true,generic,generic);

        // Run the test
        final TransactionCategory result = transactionDAOUnderTest.getCategoryForTransaction("token", transaction);

        // Verify the results
        assertEquals(transactionCategory.getColor(), result.getColor());
    }

    @Test
    void testAddTransactionToCategory() {
        // Setup

        // Run the test
        transactionDAOUnderTest.addTransactionToCategory(transactionCategory, "content");

        // Verify the results
    }

    @Test
    void testGetCategories() {
        // Setup
        final List<TransactionCategory> expectedResult = new ArrayList<>();
        expectedResult.add(transactionCategory);

        // Run the test
        final List<TransactionCategory> result = transactionDAOUnderTest.getCategories("token");

        // Verify the results
        assertEquals(expectedResult.get(0).getColor(), result.get(0).getColor());
    }
}
