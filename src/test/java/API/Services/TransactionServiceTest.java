package API.Services;

import API.DTO.AddToCategoryRequest;
import API.DTO.TransactionCategory;
import API.DTO.User;
import API.DataSource.TransactionDAO;
import API.DataSource.UserDAO;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Collections;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

class TransactionServiceTest {

    private TransactionService transactionServiceUnderTest;
    private TransactionDAO transactionDAO;
    private UserDAO userDAO;
    private String token = "token";
    private User user = new User();

    @BeforeEach
    void setUp() {
        transactionServiceUnderTest = new TransactionService();
        transactionDAO = Mockito.mock(TransactionDAO.class);
        userDAO = Mockito.mock(UserDAO.class);
        transactionServiceUnderTest.setTransactionDAO(transactionDAO);
        transactionServiceUnderTest.setUserDAO(userDAO);
        Mockito.when(userDAO.getUserByToken(token)).thenReturn(user);
    }

    @Test
    void testCreateCategory() {
        // Setup
        final TransactionCategory category = new TransactionCategory();
        category.setId(0);
        category.setUser(new User(0, "name", "email", "password", "token"));
        category.setName("name");
        category.setColor("color");

        final TransactionCategory expectedResult = new TransactionCategory();
        expectedResult.setId(0);
        expectedResult.setUser(new User(0, "name", "email", "password", "token"));
        expectedResult.setName("name");
        expectedResult.setColor("color");

        // Run the test
        Mockito.when(transactionDAO.createCategory(Mockito.any())).thenReturn(expectedResult);
        final TransactionCategory result = transactionServiceUnderTest.createCategory(category, "token");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testAddToCategory() {
        // Setup
        final AddToCategoryRequest request = new AddToCategoryRequest();
        request.setContent("content");

        // Run the test
        Mockito.when(transactionDAO.getCategory(Mockito.anyInt(),Mockito.any())).thenReturn(new TransactionCategory());
        transactionServiceUnderTest.addToCategory(0, request, "token");
        Mockito.verify(transactionDAO).addTransactionToCategory(Mockito.any(),Mockito.anyString());
        // Verify the results
    }

    @Test
    void testGetCategories() {
        // Setup
        final List<TransactionCategory> expectedResult = Collections.emptyList();

        // Run the test
        final List<TransactionCategory> result = transactionServiceUnderTest.getCategories("token");
        Mockito.when(transactionDAO.getCategories(Mockito.any())).thenReturn(expectedResult);
        // Verify the results
        assertEquals(expectedResult, result);
    }
}
