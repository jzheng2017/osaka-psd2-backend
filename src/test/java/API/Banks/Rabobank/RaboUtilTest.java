package API.Banks.Rabobank;

import API.DTO.Account;
import API.DTO.Currency;
import API.DTO.PaymentRequest;
import API.Utils.WebClient;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.verify;

class RaboUtilTest {
    private RaboUtil raboUtilUnderTest;
    private WebClient webClient;

    @BeforeEach
    void setUp() {
        raboUtilUnderTest = new RaboUtil();
        webClient = mock(WebClient.class);
        raboUtilUnderTest.setWebClient(webClient);
    }

    @Test
    void testGetBankToken() {
        // Setup
        // Run the test
        raboUtilUnderTest.getBankToken("body");

        // Verify the results
        verify(webClient).post(anyString(),any(),any());
    }

    @Test
    void testDoPaymentInitiationRequest() {
        // Setup

        // Run the test
        String PIS_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/payments/payment-initiation/pis/v1";
        raboUtilUnderTest.doPaymentInitiationRequest(PIS_BASE, "/payments/sepa-credit-transfers", "token", "payload", "redirect");

        // Verify the results
        verify(webClient).post(anyString(),any(),any());
    }

    @Test
    void testGetRequest() {
        // Setup

        // Run the test
        String OAUTH_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/oauth2";
        raboUtilUnderTest.get(OAUTH_BASE, "/token", "payload");

        // Verify the results
        verify(webClient).get(anyString(),any());
    }

    @Test
    void testGeneratePaymentJSON() {
        // Setup
        final PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setInformation("information");
        paymentRequest.setCurrency(Currency.EUR);
        paymentRequest.setReceiver(new Account("id", "iban", "name", "currency", 0.0));
        paymentRequest.setSender(new Account("id", "iban", "name", "currency", 0.0));
        paymentRequest.setAmount(0.0);
        paymentRequest.setIp("ip");

        final String expectedResult = "{\"creditorAccount\":{\"iban\":\"iban\"},\"creditorName\":\"name\",\"instructedAmount\":{\"content\":0.0,\"currency\":\"EUR\"},\"remittanceInformationUnstructured\":\"information\"}";

        // Run the test
        final JsonObject result = raboUtilUnderTest.generatePaymentJSON(paymentRequest);

        // Verify the results
        assertEquals(expectedResult, result.toString());
    }
}
