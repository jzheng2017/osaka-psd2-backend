package API.Controllers;

import API.DTO.Account;
import API.DTO.Currency;
import API.DTO.PaymentRequest;
import API.DTO.TransactionResponse;
import API.Services.PaymentService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import javax.ws.rs.core.Response;

import static org.junit.jupiter.api.Assertions.assertEquals;

class PaymentControllerTest {

    private PaymentController paymentControllerUnderTest;
    private PaymentService paymentService;
    private final PaymentRequest paymentRequest = new PaymentRequest();

    @BeforeEach
    void setUp() {
        paymentControllerUnderTest = new PaymentController();
        paymentService = Mockito.mock(PaymentService.class);
        paymentControllerUnderTest.setPaymentService(paymentService);
        paymentRequest.setInformation("information");
        paymentRequest.setCurrency(Currency.EUR);
        paymentRequest.setReceiver(new Account("id", "iban", "name", "currency", 0.0));
        paymentRequest.setSender(new Account("id", "iban", "name", "currency", 0.0));
        paymentRequest.setAmount(0.0);
        paymentRequest.setIp("ip");
    }

    @Test
    void doPayent() {
        // Setup
        final Response.Status expectedResult = Response.Status.OK;

        // Run the test
        Mockito.when(paymentService.initiateTransaction("token",paymentRequest, "tableid")).thenReturn(new TransactionResponse());
        final Response result = paymentControllerUnderTest.doPayment("token", paymentRequest, "tableid");

        // Verify the results
        assertEquals(expectedResult.getStatusCode(), result.getStatus());
    }

    @Test
    void doPayment400() {
        // Setup
        final Response.Status expectedResult = Response.Status.BAD_REQUEST;

        // Run the test
        Mockito.when(paymentService.initiateTransaction("token",paymentRequest, "tableid")).thenReturn(null);
        final Response result = paymentControllerUnderTest.doPayment("token", paymentRequest, "tableid");

        // Verify the results
        assertEquals(expectedResult.getStatusCode(), result.getStatus());
    }
}
