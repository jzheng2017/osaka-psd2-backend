package API.Adapters;

import API.DTO.*;
import org.bouncycastle.jcajce.provider.symmetric.ARC4;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BankAdapterTest {

    private BankAdapter bankAdapterUnderTest;
    private BaseAdapter mockedAdapter;

    @BeforeEach
    void setUp() {
        bankAdapterUnderTest = new BankAdapter(Bank.RABOBANK);
        mockedAdapter = Mockito.mock(BaseAdapter.class);
        bankAdapterUnderTest.setAdapter(mockedAdapter);
    }

    @Test
    void testGetAuthorizationUrl() throws Exception {
        // Setup
        final URI expectedResult = new URI("http://example.com/");

        // Run the test
        final URI result = bankAdapterUnderTest.getAuthorizationUrl("redirectUrl", "state");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetUserAccounts() {
        // Setup
        final Account expectedResult = new Account("Id", "iban", "name", "accountType", "currency", new ArrayList<>(Arrays.asList()), 0.0f);

        // Run the test
        final Account result = bankAdapterUnderTest.getUserAccounts("token");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetAccountBalances() {
        // Setup
        final Balance expectedResult = new Balance();

        // Run the test
        final Balance result = bankAdapterUnderTest.getAccountBalances("token", "id");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetAccountTransactions() {
        // Setup
        final Transaction expectedResult = new Transaction();

        // Run the test
        final Transaction result = bankAdapterUnderTest.getAccountTransactions("token", "id");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testToken() {
        // Setup
        final BankToken expectedResult = new BankToken(0, Bank.RABOBANK, "accessToken", "refreshToken");

        // Run the test
        final BankToken result = bankAdapterUnderTest.token("code");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testRefresh() {
        // Setup
        final BankToken expectedResult = new BankToken(0, Bank.RABOBANK, "accessToken", "refreshToken");

        // Run the test
        final BankToken result = bankAdapterUnderTest.refresh("code");

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
