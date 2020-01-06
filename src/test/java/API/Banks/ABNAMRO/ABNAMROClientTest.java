package API.Banks.ABNAMRO;

import API.DTO.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.math.BigDecimal;
import java.net.URI;
import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class ABNAMROClientTest {

    private ABNAMROClient abnamroClientUnderTest;

    @BeforeEach
    void setUp() {
        abnamroClientUnderTest = new ABNAMROClient();
    }

//    @Test
//    void testGetAuthorizationUrl() throws Exception {
//        // Setup
//        final URI expectedResult = new URI("http://example.com/");
//
//        // Run the test
//        final URI result = abnamroClientUnderTest.getAuthorizationUrl("redirectUrl", "state");
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
//        final BankToken result = abnamroClientUnderTest.token("code");
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
//        final BankToken result = abnamroClientUnderTest.refresh("code");
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testGetUserAccounts() {
//        // Setup
//        final ArrayList<Account> expectedResult = new ArrayList<>(Arrays.asList());
//
//        // Run the test
//        final ArrayList<Account> result = abnamroClientUnderTest.getUserAccounts("token");
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testGetBalance() {
//        // Setup
//        final Number expectedResult = new BigDecimal("0.00");
//
//        // Run the test
//        final Number result = abnamroClientUnderTest.getBalance("token", "id");
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
//        expectedResult.setTransactions(new ArrayList<>(Arrays.asList()));
//
//        // Run the test
//        final AccountDetails result = abnamroClientUnderTest.getAccountDetails("token", "id");
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
//        final TransactionResponse result = abnamroClientUnderTest.initiateTransaction("token", paymentRequest);
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testIsPaymentToken() {
//        // Setup
//
//        // Run the test
//        final boolean result = abnamroClientUnderTest.isPaymentToken("token");
//
//        // Verify the results
//        assertTrue(result);
//    }
//
//    @Test
//    void testPay() {
//        // Setup
//        final Payment expectedResult = new Payment();
//        expectedResult.setPaid(false);
//        expectedResult.setId("id");
//
//        // Run the test
//        final Payment result = abnamroClientUnderTest.pay("token", "id");
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
}
