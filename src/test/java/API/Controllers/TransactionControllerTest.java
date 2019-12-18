package API.Controllers;

import API.DTO.AddToCategoryRequest;
import API.DTO.TransactionCategory;
import API.DTO.User;
import API.Services.TransactionService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class TransactionControllerTest {

    private TransactionController transactionControllerUnderTest;
    private TransactionService transactionService;

    @BeforeEach
    void setUp() {
        transactionControllerUnderTest = new TransactionController();
        transactionService = Mockito.mock(TransactionService.class);
        transactionControllerUnderTest.setTransactionService(transactionService);
    }

    @Test
    void testCreateCategory() {
        // Setup
        final TransactionCategory category = new TransactionCategory();
        category.setId(0);
        category.setUser(new User(0, "name", "email", "password", "token"));
        category.setName("name");
        category.setColor("color");

        final Response.Status expectedResult = Response.Status.OK;

        // Run the test
        when(transactionService.createCategory(category,"token")).thenReturn(new TransactionCategory());
        final Response result = transactionControllerUnderTest.createCategory(category, "token");

        // Verify the results
        assertEquals(expectedResult.getStatusCode(), result.getStatus());
    }

    @Test
    void testAssignToCategory() {
        // Setup
        final AddToCategoryRequest request = new AddToCategoryRequest();
        request.setContent("content");

        final Response.Status expectedResult = Response.Status.OK;

        // Run the test
        final Response result = transactionControllerUnderTest.assignToCategory(0, request, "token");

        // Verify the results
        assertEquals(expectedResult.getStatusCode(), result.getStatus());
    }

    @Test
    void testCreateCategory400() {
        // Setup
        final TransactionCategory category = new TransactionCategory();
        category.setId(0);
        category.setUser(new User(0, "name", "email", "password", "token"));
        category.setName("name");
        category.setColor("color");

        final Response.Status expectedResult = Response.Status.BAD_REQUEST;

        // Run the test
        final Response result = transactionControllerUnderTest.createCategory(category, "");

        // Verify the results
        assertEquals(expectedResult.getStatusCode(), result.getStatus());
    }

    @Test
    void testAssignToCategory400() {
        // Setup
        final AddToCategoryRequest request = new AddToCategoryRequest();
        request.setContent("content");

        final Response.Status expectedResult = Response.Status.BAD_REQUEST;

        // Run the test
        final Response result = transactionControllerUnderTest.assignToCategory(0, request, "");

        // Verify the results
        assertEquals(expectedResult.getStatusCode(), result.getStatus());
    }

    @Test
    void testGetCategories400() {
        // Setup
        final Response.Status expectedResult = Response.Status.BAD_REQUEST;

        // Run the test
        final Response result = transactionControllerUnderTest.getCategories("");

        // Verify the results
        assertEquals(expectedResult.getStatusCode(), result.getStatus());
    }
    @Test
    void testGetCategories() {
        // Setup
        final Response.Status expectedResult = Response.Status.OK;

        // Run the test
        when(transactionService.getCategories(anyString())).thenReturn(new ArrayList<>());
        final Response result = transactionControllerUnderTest.getCategories("token");

        // Verify the results
        assertEquals(expectedResult.getStatusCode(), result.getStatus());
    }
}
