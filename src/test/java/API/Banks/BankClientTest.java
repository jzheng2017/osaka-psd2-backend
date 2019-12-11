package API.Banks;

import API.Banks.Rabobank.RabobankClient;
import API.DTO.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;
import java.util.ArrayList;
import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;

class BankClientTest {

//    private Client bankClientUnderTest;
//    private Client client;
//
//    @BeforeEach
//    void setUp() {
//        bankClientUnderTest = ClientFactory.getClient(Bank.RABOBANK);
//        client = Mockito.mock(RabobankClient.class);
//        bankClientUnderTest.setClient(client);
//    }
//
//    @Test
//    void testGetAuthorizationUrl() throws Exception {
//        // Setup
//        final URI expectedResult = new URI("http://example.com/");
//
//        // Run the test
//        Mockito.when(client.getAuthorizationUrl("redirectUrl","state")).thenReturn(URI.create("http://example.com/"));
//        final URI result = bankClientUnderTest.getAuthorizationUrl("redirectUrl", "state");
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testGetUserAccounts() {
//        // Setup
//        final ArrayList<Account> expectedResult = new ArrayList<>(Collections.emptyList());
//
//        // Run the test
//        Mockito.when(client.getUserAccounts("token")).thenReturn(expectedResult);
//        final ArrayList<Account> result = bankClientUnderTest.getUserAccounts("token");
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testGetAccountBalances() {
//        // Setup
//        final Balance expectedResult = new Balance();
//
//        // Run the test
//        Mockito.when(client.getAccountBalances("token","id")).thenReturn(expectedResult);
//        final Balance result = bankClientUnderTest.getAccountBalances("token", "id");
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testGetAccountDetails() {
//        // Setup
//        final AccountDetails expectedResult = new AccountDetails();
//        expectedResult.setAccount(new Account("id", "iban", "name", "currency", 0.0));
//        expectedResult.setTransactions(new ArrayList<>(Collections.emptyList()));
//
//        // Run the test
//        Mockito.when(client.getAccountDetails("token","id")).thenReturn(expectedResult);
//        final AccountDetails result = bankClientUnderTest.getAccountDetails("token", "id");
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testToken() {
//        // Setup
//        final BankToken expectedResult = new BankToken(0, Bank.RABOBANK, "accessToken", "refreshToken");
//
//        // Run the test
//        Mockito.when(client.token("code")).thenReturn(expectedResult);
//        final BankToken result = bankClientUnderTest.token("code");
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testRefresh() {
//        // Setup
//        final BankToken expectedResult = new BankToken(0, Bank.RABOBANK, "accessToken", "refreshToken");
//
//        // Run the test
//        Mockito.when(client.refresh("code")).thenReturn(expectedResult);
//        final BankToken result = bankClientUnderTest.refresh("code");
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testInitiateTransaction() throws Exception {
//        // Setup
//        final PaymentRequest paymentRequest = new PaymentRequest();
//        paymentRequest.setInformation("information");
//        paymentRequest.setCurrency(Currency.EUR);
//        paymentRequest.setReceiver(new Account("id", "iban", "name", "currency", 0.0));
//        paymentRequest.setSender(new Account("id", "iban", "name", "currency", 0.0));
//        paymentRequest.setAmount(0.0);
//        paymentRequest.setIp("ip");
//
//        final TransactionResponse expectedResult = new TransactionResponse();
//        expectedResult.setUrl(new URI("http://example.com/"));
//
//        // Run the test
//        Mockito.when(client.initiateTransaction("token", paymentRequest)).thenReturn(expectedResult);
//        final TransactionResponse result = bankClientUnderTest.initiateTransaction("token", paymentRequest);
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testRevoke() {
//        // Setup
//
//        // Run the test
//        bankClientUnderTest.revoke("refreshToken");
//        Mockito.verify(client).revoke("refreshToken");
//        // Verify the results
//    }
}
