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
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AccountControllerTest {
    private static final String TOKEN = UUID.randomUUID().toString();
    private static final String ID = "ID";
    private static final String TABLE_ID = "TABLE_ID";

    private AccountController accountController;
    private AccountService mockedAccountService;

    @BeforeEach
    void setup() {
        accountController = new AccountController();
        mockedAccountService = Mockito.mock(AccountService.class);
        accountController.setAccountService(mockedAccountService);
    }

    @Test
    void testGetUserAccountsOK() {
        // Arrange
        var expected = Response.Status.OK.getStatusCode();

        var response = new AccountsResponse();
        var accounts = new ArrayList<Account>();
        accounts.add(new Account());
        response.setAccounts(accounts);

        Mockito.when(mockedAccountService.getUserAccounts(TOKEN)).thenReturn(response);

        // Act
        var result = accountController.getUserAccounts(TOKEN);

        // Assert
        assertEquals(expected, result.getStatus());
    }

    @Test
    void testGetUserAccounts404() {
        // Arrange
        var expected = Response.Status.BAD_REQUEST.getStatusCode();
        var response = new AccountsResponse();
        Mockito.when(mockedAccountService.getUserAccounts(TOKEN)).thenReturn(response);

        // Act
        var result = accountController.getUserAccounts(TOKEN);

        // Assert
        assertEquals(expected, result.getStatus());
    }

    @Test
    void testGetAccountTransactions() {
        // Arrange
        var expected = Response.Status.OK.getStatusCode();
        Mockito.when(mockedAccountService.getAccountDetails(TOKEN, ID, TABLE_ID)).thenReturn(new AccountDetails());

        // Act
        var result = accountController.getAccountDetails(ID, TOKEN, TABLE_ID);

        // Assert
        assertEquals(expected, result.getStatus());
    }

    @Test
    void testGetAccountTransactions404() {
        // Arrange
        var expected = Response.Status.BAD_REQUEST.getStatusCode();
        Mockito.when(mockedAccountService.getAccountDetails(ID, TOKEN, TABLE_ID)).thenReturn(null);

        // Act
        var result = accountController.getAccountDetails(ID, TOKEN, TABLE_ID);

        // Assert
        assertEquals(expected, result.getStatus());
    }

    @Test
    void testAssignAccountToType() {
        // Arrange
        var request = new CreateAccountCategoryRequest("name", "iban");
        var expected = Response.Status.CREATED;

        // Act
        Mockito.when(mockedAccountService.assignAccountToCategory("token",request)).thenReturn(new AccountCategory());
        var result = accountController.assignAccountToType(request, "token");

        // Assert
        assertEquals(expected.getStatusCode(), result.getStatus());
    }

    @Test
    void testAssignAccountToType400() {
        // Arrange
        var request = new CreateAccountCategoryRequest("name", "iban");
        var expected = Response.Status.BAD_REQUEST;

        // Act
        var result = accountController.assignAccountToType(request, "");

        // Assert
        assertEquals(expected.getStatusCode(), result.getStatus());
    }

    @Test
    void testGetAllAccountCategories() {
        // Arrange
        var expected = Response.Status.OK;
        var categories = new ArrayList<AccountCategory>();
        categories.add(new AccountCategory());
        Mockito.when(mockedAccountService.getAllCategories("token")).thenReturn(categories);

        // Act
        var result = accountController.getAllAccountCategories("token");

        // Assert
        assertEquals(expected.getStatusCode(), result.getStatus());
    }

    @Test
    void testGetAllAccountCategories400() {
        // Arrange
        var expected = Response.Status.BAD_REQUEST;

        var categories = new ArrayList<AccountCategory>();
        categories.add(new AccountCategory());
        Mockito.when(mockedAccountService.getAllCategories("")).thenReturn(categories);

        // Act
        var result = accountController.getAllAccountCategories("token");

        // Assert
        assertEquals(expected.getStatusCode(), result.getStatus());
    }

    @Test
    void testAddCategory() {
        // Arrange
        var request = new CreateAccountCategoryRequest("name", "iban");
        var expected = Response.Status.CREATED;

        // Act
        Mockito.when(mockedAccountService.addNewCategory("token",request)).thenReturn(new AccountCategory());
        var result = accountController.addCategory("token", request);

        // Assert
        assertEquals(expected.getStatusCode(), result.getStatus());
    }

    @Test
    void testAddCategory400() {
        // Arrange
        var request = new CreateAccountCategoryRequest("name", "iban");
        var expected = Response.Status.BAD_REQUEST;

        // Act
        var result = accountController.addCategory("", request);

        // Assert
        assertEquals(expected.getStatusCode(), result.getStatus());
    }
}
