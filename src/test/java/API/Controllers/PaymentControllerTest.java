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
    private PaymentController paymentController;
    private PaymentService paymentService;
    private PaymentRequest paymentRequest;

    @BeforeEach
    void setup() {
        paymentRequest = new PaymentRequest();
        paymentController = new PaymentController();
        paymentService = Mockito.mock(PaymentService.class);
        paymentController.setPaymentService(paymentService);


        paymentRequest.setInformation("information");
        paymentRequest.setCurrency(Currency.EUR);
        paymentRequest.setReceiver(new Account("id", "iban", "name", "currency", 0.0));
        paymentRequest.setSender(new Account("id", "iban", "name", "currency", 0.0));
        paymentRequest.setAmount(0.0);
        paymentRequest.setIp("ip");
    }

    @Test
    void doPayent() {
        // Arrange
        var expected = Response.Status.OK;
        Mockito.when(paymentService.initiateTransaction("token",paymentRequest, "tableid")).thenReturn(new TransactionResponse());

        // Act
        var result = paymentController.doPayment("token", paymentRequest, "tableid");

        // Assert
        assertEquals(expected.getStatusCode(), result.getStatus());
    }

    @Test
    void doPayment400() {
        // Setup
        var expected = Response.Status.BAD_REQUEST;
        Mockito.when(paymentService.initiateTransaction("token",paymentRequest, "tableid")).thenReturn(null);

        // Run the test
        var result = paymentController.doPayment("token", paymentRequest, "tableid");

        // Verify the results
        assertEquals(expected.getStatusCode(), result.getStatus());
    }

}
