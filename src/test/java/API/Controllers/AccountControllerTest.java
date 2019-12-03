package API.Controllers;

import API.DTO.Account;
import API.DTO.Transaction;
import API.Services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountControllerTest {

    private AccountController accountControllerUnderTest;
    private AccountService mockedAccountService;
    private String token = "token";
    private String id = "id";
    private String tableid = "tableid";

    @BeforeEach
    void setUp() {
        accountControllerUnderTest = new AccountController();
        mockedAccountService = Mockito.mock(AccountService.class);
        accountControllerUnderTest.setAccountService(mockedAccountService);
    }

    @Test
    void testGetUserAccounts() {
        // Setup
        final int expectedResult = Response.Status.OK.getStatusCode();

        // Run the test
        Mockito.when(mockedAccountService.getUserAccounts(token)).thenReturn(new Account());
        final Response result = accountControllerUnderTest.getUserAccounts(token);
        // Verify the results
        assertEquals(expectedResult, result.getStatus());
    }

    @Test
    void testGetUserAccounts404() {
        // Setup
        final int expectedResult = Response.Status.BAD_REQUEST.getStatusCode();

        // Run the test
        Mockito.when(mockedAccountService.getUserAccounts(token)).thenReturn(null);
        final Response result = accountControllerUnderTest.getUserAccounts(token);
        // Verify the results
        assertEquals(expectedResult, result.getStatus());
    }


    @Test
    void testGetAccountTransactions() {
        // Setup
        final int expectedResult = Response.Status.OK.getStatusCode();

        // Run the test
        Mockito.when(mockedAccountService.getAccountDetails(token, id, tableid)).thenReturn(new Transaction());
        final Response result = accountControllerUnderTest.getAccountTransactions(id, token, tableid);
        accountControllerUnderTest.getAccountTransactions(token, id, tableid);
        // Verify the results
        assertEquals(expectedResult, result.getStatus());
    }

    @Test
    void testGetAccountTransactions404() {
        // Setup
        final int expectedResult = Response.Status.BAD_REQUEST.getStatusCode();

        // Run the test
        Mockito.when(mockedAccountService.getAccountDetails(id, token, tableid)).thenReturn(null);
        final Response result = accountControllerUnderTest.getAccountTransactions(id, token, tableid);

        // Verify the results
        assertEquals(expectedResult, result.getStatus());
    }
}
