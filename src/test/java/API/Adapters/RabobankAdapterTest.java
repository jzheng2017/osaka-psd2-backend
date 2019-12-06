package API.Adapters;

import API.Banks.Rabobank.RabobankClient;
import API.DTO.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RabobankAdapterTest {
/*
    private RabobankAdapter rabobankAdapterUnderTest;
    private RabobankClient rabobankClient;
    private final String urlString = "http://example.com/";
    private final String redirectUrl = "redirectUrl";
    private final String state = "state";

    @BeforeEach
    void setUp() {
        rabobankAdapterUnderTest = new RabobankAdapter();
        rabobankClient = Mockito.mock(RabobankClient.class);
        rabobankAdapterUnderTest.setRabobankClient(rabobankClient);
    }

    @Test
    void testGetAuthorizationUrl() throws Exception {
        // Setup
        final URI expectedResult = new URI(urlString);

        // Run the test
        //Mockito.when(rabobankClient.getAuthorizationUrl(redirectUrl,state)).thenReturn(urlString);
        //final URI result = rabobankAdapterUnderTest.getAuthorizationUrl(redirectUrl,state);
        // Verify the results
        //assertEquals(expectedResult, result);
    }

    @Test
    void testGetAuthorizationUrlReturnsNull() {
        // Setup
        final URI expectedResult = null;

        // Run the test
        //Mockito.when(rabobankClient.getAuthorizationUrl(redirectUrl,state)).thenReturn("%%%%%%%");
        //final URI result = rabobankAdapterUnderTest.getAuthorizationUrl(redirectUrl,state);
        // Verify the results
        //assertEquals(expectedResult, result);
    }

    @Test
    void testGetUserAccounts() {
        // Setup
        Account account = new Account("Id", "iban", "name", "currency", 0.0d);
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.add(account);

        // Run the test
        Mockito.when(rabobankClient.getUserAccounts("token")).thenReturn(accounts);
        final ArrayList<Account> result = rabobankAdapterUnderTest.getUserAccounts("token");

        // Verify the results
        assertEquals(accounts, result);
    }

    @Test
    void testGetAccountBalances() {
        // Setup
        final Balance expectedResult = new Balance();

        // Run the test
        Mockito.when(rabobankClient.getAccountBalances("token","id")).thenReturn(expectedResult);

        final Balance result = rabobankAdapterUnderTest.getAccountBalances("token", "id");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetAccountTransactions() {
        // Setup
        final AccountDetails expectedResult = new AccountDetails();

        // Run the test
        Mockito.when(rabobankClient.getAccountDetails("token","id")).thenReturn(expectedResult);

        final AccountDetails result = rabobankAdapterUnderTest.getAccountDetails("token", "id");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testToken() {
        // Setup
        final BankToken expectedResult = new BankToken(0, Bank.RABOBANK, "accessToken", "refreshToken");

        // Run the test
        Mockito.when(rabobankClient.token("code")).thenReturn(expectedResult);

        final BankToken result = rabobankAdapterUnderTest.token("code");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testRefresh() {
        // Setup
        final BankToken expectedResult = new BankToken(0, Bank.RABOBANK, "accessToken", "refreshToken");

        // Run the test
        Mockito.when(rabobankClient.refresh("code")).thenReturn(expectedResult);
        final BankToken result = rabobankAdapterUnderTest.refresh("code");

        // Verify the results
        assertEquals(expectedResult, result);
    }

 */
}
