package API.Banks.Rabobank;

import API.DTO.Account;
import API.DTO.BankToken;
import API.DTO.Currency;
import API.DTO.PaymentRequest;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNull;

class RaboUtilTest {
    private RaboUtil raboUtilUnderTest;

    @BeforeEach
    void setUp() {
        raboUtilUnderTest = new RaboUtil();
    }

    @Test
    void testGetBankToken() {
        // Setup
        // Run the test
       // final BankToken result = raboUtilUnderTest.getBankToken("body");

        // Verify the results
        //assertNull(result.getAccessToken());
    }

    @Test
    void testDoPaymentInitiationRequest() {
        // Setup

        // Run the test
        String PIS_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/payments/payment-initiation/pis/v1";
        //final String result = raboUtilUnderTest.doPaymentInitiationRequest(PIS_BASE, "/payments/sepa-credit-transfers", "token", "payload", "redirect");

        // Verify the results
        String badPostRequest = "{ \"httpCode\":\"400\", \"httpMessage\":\"Bad Request\", \"moreInformation\":\"The body of the request, which was expected to be JSON, was invalid, and could not be decoded. The start of an object { or an array [ was expected.\" }";
        //assertEquals(badPostRequest, result);
    }

    @Test
    void testDoPostRequest() {
        // Setup
        final String expectedResult = "{ \"error\":\"invalid_client\" }";

        // Run the test
        String OAUTH_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/oauth2";
        //final String result = raboUtilUnderTest.doPostRequest(OAUTH_BASE, "/token", "payload", "authorization");

        // Verify the results
        //assertEquals(expectedResult, result);
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
