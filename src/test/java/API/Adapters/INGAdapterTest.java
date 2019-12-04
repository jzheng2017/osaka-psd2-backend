package API.Adapters;

import API.Banks.ING.INGClient;
import API.DTO.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class INGAdapterTest {

    private INGAdapter ingAdapterUnderTest;
    private INGClient ingClient;
    private final String urlString = "http://example.com/";
    private final String redirectUrl = "redirectUrl";
    private final String state = "state";

    @BeforeEach
    void setUp() {
        ingAdapterUnderTest = new INGAdapter();
        ingClient = Mockito.mock(INGClient.class);
        ingAdapterUnderTest.setIngClient(ingClient);
    }

    @Test
    void testGetAuthorizationUrl() throws Exception {
        // Setup
        final URI expectedResult = new URI(urlString);

        // Run the test
        Mockito.when(ingClient.getAuthorizationUrl(redirectUrl,state)).thenReturn(urlString);
        final URI result = ingAdapterUnderTest.getAuthorizationUrl(redirectUrl,state);
        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetAuthorizationUrlReturnsNull() throws Exception {
        // Setup
        final URI expectedResult = null;

        // Run the test
        Mockito.when(ingClient.getAuthorizationUrl(redirectUrl,state)).thenReturn("%%%%%%%");
        final URI result = ingAdapterUnderTest.getAuthorizationUrl(redirectUrl,state);
        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetUserAccounts() {
        // Setup
        Account account = new Account("Id", "iban", "name", "currency", 0.0d);
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(account);

        // Run the test
        Mockito.when(ingClient.getUserAccounts("token")).thenReturn(accounts);
        final ArrayList<Account> result = ingAdapterUnderTest.getUserAccounts("token");

        // Verify the results
        assertEquals(accounts, result);
    }

    @Test
    void testGetAccountBalances() {
        // Setup
        final Balance expectedResult = new Balance();

        // Run the test
        Mockito.when(ingClient.getAccountBalances("token","id")).thenReturn(expectedResult);

        final Balance result = ingAdapterUnderTest.getAccountBalances("token", "id");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetAccountTransactions() {
        // Setup
        final AccountDetails expectedResult = new AccountDetails();

        // Run the test
        Mockito.when(ingClient.getAccountDetails("token","id")).thenReturn(expectedResult);

        final AccountDetails result = ingAdapterUnderTest.getAccountDetails("token", "id");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testToken() {
        // Setup
        final BankToken expectedResult = new BankToken(0, Bank.ING, "accessToken", "refreshToken");

        // Run the test
        Mockito.when(ingClient.token("code")).thenReturn(expectedResult);

        final BankToken result = ingAdapterUnderTest.token("code");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testRefresh() {
        // Setup
        final BankToken expectedResult = new BankToken(0, Bank.ING, "accessToken", "refreshToken");

        // Run the test
        Mockito.when(ingClient.refresh("code")).thenReturn(expectedResult);
        final BankToken result = ingAdapterUnderTest.refresh("code");

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
