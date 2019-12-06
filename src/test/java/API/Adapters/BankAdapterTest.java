package API.Adapters;

import API.Banks.ING.INGClient;
import API.DTO.*;
import API.DTO.Responses.AccountsResponse;
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
    private INGClient mockedAdapter;
    private final String urlString = "http://example.com/";
    private final String redirectUrl = "redirectUrl";
    private final String state = "state";

    @BeforeEach
    void setUp() {
        bankAdapterUnderTest = new BankAdapter(Bank.RABOBANK);
        mockedAdapter = Mockito.mock(INGClient.class);
        bankAdapterUnderTest.setClient(mockedAdapter);
    }

    @Test
    void testGetAuthorizationUrl() throws Exception {
        // Setup
        final URI expectedResult = new URI(urlString);

        // Run the test
        Mockito.when(mockedAdapter.getAuthorizationUrl(redirectUrl,state)).thenReturn(new URI(urlString));
        final URI result = bankAdapterUnderTest.getAuthorizationUrl(redirectUrl,state);
        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetUserAccounts() {
        // Setup
        ArrayList<Account> account = new ArrayList<>();
        Account tempAccount = new Account();
        tempAccount.setName("hello");
        account.add(tempAccount);

        // Run the test
        Mockito.when(mockedAdapter.getUserAccounts("token")).thenReturn(account);
        final ArrayList result = bankAdapterUnderTest.getUserAccounts("token");

        // Verify the results
        assertEquals(account, result);
    }

    @Test
    void testGetAccountBalances() {
        // Setup
        final Balance expectedResult = new Balance();

        // Run the test
        Mockito.when(mockedAdapter.getAccountBalances("token","id")).thenReturn(expectedResult);

        final Balance result = bankAdapterUnderTest.getAccountBalances("token", "id");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetAccountTransactions() {
        // Setup
        final AccountDetails expectedResult = new AccountDetails();

        // Run the test
        Mockito.when(mockedAdapter.getAccountDetails("token","id")).thenReturn(expectedResult);

        final AccountDetails result = bankAdapterUnderTest.getAccountDetails("token", "id");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testToken() {
        // Setup
        final BankToken expectedResult = new BankToken(0, Bank.RABOBANK, "accessToken", "refreshToken");

        // Run the test
        Mockito.when(mockedAdapter.token("code")).thenReturn(expectedResult);

        final BankToken result = bankAdapterUnderTest.token("code");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testRefresh() {
        // Setup
        final BankToken expectedResult = new BankToken(0, Bank.RABOBANK, "accessToken", "refreshToken");

        // Run the test
        Mockito.when(mockedAdapter.refresh("code")).thenReturn(expectedResult);
        final BankToken result = bankAdapterUnderTest.refresh("code");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testInitiatieTransaction() {
        // Setup
        TransactionResponse transactionResponse = new TransactionResponse();
        PaymentRequest request = new PaymentRequest();
        // Run the test
        Mockito.when(mockedAdapter.initiateTransaction("code", request)).thenReturn(transactionResponse);
        final TransactionResponse result = bankAdapterUnderTest.initiateTransaction("code", request);

        // Verify the results
        assertEquals(transactionResponse, result);
    }

    @Test
    void testRevoke() {
        bankAdapterUnderTest.revoke("refresh");
        Mockito.verify(mockedAdapter).revoke("refresh");
    }
}
