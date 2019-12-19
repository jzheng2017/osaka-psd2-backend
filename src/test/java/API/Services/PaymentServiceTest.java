package API.Services;

import API.Banks.ING.INGClient;
import API.DTO.*;
import API.DataSource.BankTokenDao;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class PaymentServiceTest {

    private PaymentService paymentServiceUnderTest;
    private BankTokenDao bankTokenDao;
    private String token = "token";
    private User user = new User();
    @BeforeEach
    void setUp() {
        paymentServiceUnderTest = new PaymentService();
        bankTokenDao = Mockito.mock(BankTokenDao.class);
        paymentServiceUnderTest.setBankTokenDao(bankTokenDao);
    }

    @Test
    void testInitiateTransaction() throws Exception {
        // Setup
        final PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setInformation("Test");
        paymentRequest.setCurrency(Currency.EUR);
        Account receiver = new Account();
        receiver.setName("Mw B Mol");
        receiver.setIban("NL69INGB6789012345");
        paymentRequest.setReceiver(receiver);
        Account sender = new Account();
        sender.setIban("NL69INGB0123456789");
        paymentRequest.setSender(sender);
        paymentRequest.setAmount(1.0);
        paymentRequest.setIp("156.114.161.8");

        final URI expectedUrl = new URI("https://myaccount.ing.com/payment-initiation/a278ec6d-d65a-469b-b964-792da2843a6e/NL/?redirect_uri=https%3A%2F%2Fexample.com%2Fredirect");

        // Run the test
        BankToken bankToken = new BankToken();
        bankToken.setBank(Bank.ING);
        bankToken.setAccessToken(new INGClient().token("2c1c404c-c960-49aa-8777-19c805713edf").getAccessToken());

        when(bankTokenDao.getBankTokensForUser(any(), anyString())).thenReturn(bankToken);
        final TransactionResponse result = paymentServiceUnderTest.initiateTransaction(token, paymentRequest, "tableid");

        // Verify the results
        assertEquals(expectedUrl, result.getUrl());
    }
}
