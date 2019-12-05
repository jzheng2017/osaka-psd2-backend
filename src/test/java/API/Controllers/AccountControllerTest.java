package API.Controllers;

import API.DTO.Account;
import API.DTO.AccountCategory;
import API.DTO.AccountDetails;
import API.DTO.CreateAccountCategoryRequest;
import API.DTO.Responses.AccountsResponse;
import API.Services.AccountService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountControllerTest {

    private AccountController accountControllerUnderTest;
    private AccountService mockedAccountService;
    private String token = "token";
    private String id = "id";
    private String tableid = "tableid";
    private AccountsResponse accountsResponse = new AccountsResponse();

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

        //Run the test
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(new Account());
        accountsResponse.setAccounts(accounts);
        Mockito.when(mockedAccountService.getUserAccounts(token)).thenReturn(accountsResponse);
        final Response result = accountControllerUnderTest.getUserAccounts(token);
        //Verify the results
        assertEquals(expectedResult, result.getStatus());
    }

    @Test
    void testGetUserAccounts404() {
        // Setup
        final int expectedResult = Response.Status.BAD_REQUEST.getStatusCode();

        // Run the test
        Mockito.when(mockedAccountService.getUserAccounts(token)).thenReturn(accountsResponse);
        final Response result = accountControllerUnderTest.getUserAccounts(token);
        // Verify the results
        assertEquals(expectedResult, result.getStatus());
    }


    @Test
    void testGetAccountTransactions() {
        // Setup
        final int expectedResult = Response.Status.OK.getStatusCode();

        // Run the test
        Mockito.when(mockedAccountService.getAccountDetails(token, id, tableid)).thenReturn(new AccountDetails());
        final Response result = accountControllerUnderTest.getAccountDetails(id, token, tableid);
        accountControllerUnderTest.getAccountDetails(token, id, tableid);
        // Verify the results
        assertEquals(expectedResult, result.getStatus());
    }

    @Test
    void testGetAccountTransactions404() {
        // Setup
        final int expectedResult = Response.Status.BAD_REQUEST.getStatusCode();

        // Run the test
        Mockito.when(mockedAccountService.getAccountDetails(id, token, tableid)).thenReturn(null);
        final Response result = accountControllerUnderTest.getAccountDetails(id, token, tableid);

        // Verify the results
        assertEquals(expectedResult, result.getStatus());
    }

    @Test
    void testAssignAccountToType() {
        // Setup
        final CreateAccountCategoryRequest request = new CreateAccountCategoryRequest("name", "iban");
        final Response.Status expectedResult = Response.Status.CREATED;

        // Run the test
        final Response result = accountControllerUnderTest.assignAccountToType(request, "token");

        // Verify the results
        assertEquals(expectedResult.getStatusCode(), result.getStatus());
    }

    @Test
    void testAssignAccountToType400() {
        // Setup
        final CreateAccountCategoryRequest request = new CreateAccountCategoryRequest("name", "iban");
        final Response.Status expectedResult = Response.Status.BAD_REQUEST;

        // Run the test
        final Response result = accountControllerUnderTest.assignAccountToType(request, "");

        // Verify the results
        assertEquals(expectedResult.getStatusCode(), result.getStatus());
    }

    @Test
    void testGetAllAccountCategories() {
        // Setup
        final int expectedResult = Response.Status.OK.getStatusCode();

        // Run the test
        ArrayList<AccountCategory> categories = new ArrayList<>();
        categories.add(new AccountCategory("yes", ""));
        Mockito.when(mockedAccountService.getAllCategories("token")).thenReturn(categories);
        final Response result = accountControllerUnderTest.getAllAccountCategories("token");

        // Verify the results
        assertEquals(expectedResult, result.getStatus());
    }

    @Test
    void testGetAllAccountCategories400() {
        // Setup
        final int expectedResult = Response.Status.BAD_REQUEST.getStatusCode();

        // Run the test
        ArrayList<AccountCategory> categories = new ArrayList<>();
        categories.add(new AccountCategory("yes", ""));
        Mockito.when(mockedAccountService.getAllCategories("")).thenReturn(categories);
        final Response result = accountControllerUnderTest.getAllAccountCategories("token");

        // Verify the results
        assertEquals(expectedResult, result.getStatus());
    }

    @Test
    void testAddCategory() {
        // Setup
        final CreateAccountCategoryRequest request = new CreateAccountCategoryRequest("name", "iban");
        final int expectedResult = Response.Status.CREATED.getStatusCode();

        // Run the test
        final Response result = accountControllerUnderTest.addCategory("token", request);

        // Verify the results
        assertEquals(expectedResult, result.getStatus());
    }

    @Test
    void testAddCategory400() {
        // Setup
        final CreateAccountCategoryRequest request = new CreateAccountCategoryRequest("name", "iban");
        final int expectedResult = Response.Status.BAD_REQUEST.getStatusCode();

        // Run the test
        final Response result = accountControllerUnderTest.addCategory("", request);

        // Verify the results
        assertEquals(expectedResult, result.getStatus());
    }
}
