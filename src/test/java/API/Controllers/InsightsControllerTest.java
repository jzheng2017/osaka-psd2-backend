package API.Controllers;

import API.DTO.Account;
import API.DTO.ErrorMessage;
import API.DTO.Insight;
import API.Services.InsightsService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import javax.ws.rs.core.Response;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

class InsightsControllerTest {

    private InsightsController insightsControllerUnderTest;
    private Account account = new Account("id", "NL49ING","Test","EUR",200.0);
    private Insight insightToReturn = new Insight(account);
    private ErrorMessage errorMessage;
    @BeforeEach
    void setUp() {
        ArrayList<String> errorMessages = new ArrayList<>();
        errorMessages.add("INVALID_TOKEN");
        errorMessage = new ErrorMessage(Response.Status.BAD_REQUEST, errorMessages);
        InsightsService service;
        insightsControllerUnderTest = new InsightsController();
        service = mock(InsightsService.class);
        insightsControllerUnderTest.setInsightsService(service);
        when(service.getFutureExpenses(anyString())).thenReturn(insightToReturn);
        when(service.getFutureExpensesForAccount(anyString(), anyString(), anyString())).thenReturn(insightToReturn);
        when(service.getFutureIncome(anyString())).thenReturn(insightToReturn);
        when(service.getFutureIncomeForAccount(anyString(),anyString(), anyString())).thenReturn(insightToReturn);
        when(service.getFutureInsights(anyString())).thenReturn(insightToReturn);
        when(service.getFutureInsightsForAccount(anyString(),anyString(),anyString())).thenReturn(insightToReturn);
    }

    @Test
    void testFutureIncome() {
        // Setup
        final var expectedResult = insightToReturn;

        // Run the test
        final Response result = insightsControllerUnderTest.futureIncome("token");

        // Verify the results
        assertEquals(expectedResult, result.getEntity());
    }

    @Test
    void testFutureExpenses() {
        // Setup
        final var expectedResult = insightToReturn;

        // Run the test
        final Response result = insightsControllerUnderTest.futureExpenses("token");

        // Verify the results
        assertEquals(expectedResult, result.getEntity());
    }

    @Test
    void testFutureTransactions() {
        // Setup
        final var expectedResult = insightToReturn;

        // Run the test
        final Response result = insightsControllerUnderTest.futureTransactions("token");

        // Verify the results
        assertEquals(expectedResult, result.getEntity());
    }

    @Test
    void testFutureIncomeForAccount() {
        // Setup
        final var expectedResult = insightToReturn;

        // Run the test
        final Response result = insightsControllerUnderTest.futureIncomeForAccount("token", "accountId", "tableId");

        // Verify the results
        assertEquals(expectedResult, result.getEntity());
    }

    @Test
    void testFutureExpensesForAccount() {
        // Setup
        final var expectedResult = insightToReturn;

        // Run the test
        final Response result = insightsControllerUnderTest.futureExpensesForAccount("token", "accountId", "tableId");

        // Verify the results
        assertEquals(expectedResult, result.getEntity());
    }

    @Test
    void testFutureTransactionsForAccount() {
        // Setup
        final var expectedResult = insightToReturn;

        // Run the test
        final Response result = insightsControllerUnderTest.futureTransactionsForAccount("token", "accountId", "tableId");

        // Verify the results
        assertEquals(expectedResult, result.getEntity());
    }

    @Test
    void testFutureIncomeReturnsError() {
        // Setup
        final var expectedResult = errorMessage;

        // Run the test
        final Response result = insightsControllerUnderTest.futureIncome("");

        // Verify the results
        assertEquals(expectedResult.getErrorCode().getStatusCode(), result.getStatus());
    }

    @Test
    void testFutureExpensesReturnsError() {
        // Setup
        final var expectedResult = errorMessage;

        // Run the test
        final Response result = insightsControllerUnderTest.futureExpenses("");

        // Verify the results
        assertEquals(expectedResult.getErrorCode().getStatusCode(), result.getStatus());
    }

    @Test
    void testFutureTransactionsReturnsError() {
        // Setup
        final var expectedResult = errorMessage;

        // Run the test
        final Response result = insightsControllerUnderTest.futureTransactions("");

        // Verify the results
        assertEquals(expectedResult.getErrorCode().getStatusCode(), result.getStatus());
    }

    @Test
    void testFutureIncomeForAccountReturnsError() {
        // Setup
        final var expectedResult = errorMessage;

        // Run the test
        final Response result = insightsControllerUnderTest.futureIncomeForAccount("", "accountId", "tableId");

        // Verify the results
        assertEquals(expectedResult.getErrorCode().getStatusCode(), result.getStatus());
    }

    @Test
    void testFutureExpensesForAccountReturnsError() {
        // Setup
        final var expectedResult = errorMessage;

        // Run the test
        final Response result = insightsControllerUnderTest.futureExpensesForAccount("", "accountId", "tableId");

        // Verify the results
        assertEquals(expectedResult.getErrorCode().getStatusCode(), result.getStatus());
    }

    @Test
    void testFutureTransactionsForAccountReturnsError() {
        // Setup
        final var expectedResult = errorMessage;

        // Run the test
        final Response result = insightsControllerUnderTest.futureTransactionsForAccount("", "accountId", "tableId");
        // Verify the results
        assertEquals(expectedResult.getErrorCode().getStatusCode(), result.getStatus());
    }
}
