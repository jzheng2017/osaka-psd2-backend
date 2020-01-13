package API.Banks.ING;

import API.DTO.Account;
import API.DTO.Currency;
import API.DTO.PaymentRequest;
import API.Utils.WebClient;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;

class INGUtilTest {
    private final String expected401Error = "<html>\r\n" +
            "<head><title>401 Authorization Required</title></head>\r\n" +
            "<body>\r\n" +
            "<center><h1>401 Authorization Required</h1></center>\r\n" +
            "<hr><center>ING Webserver</center>\r\n" +
            "</body>\r\n" +
            "</html>\r\n";
    private INGUtil ingUtilUnderTest;

    @BeforeEach
    void setUp() {
        ingUtilUnderTest = new INGUtil();
        WebClient webClient = Mockito.mock(WebClient.class);
        ingUtilUnderTest.setWebClient(webClient);
        Mockito.when(webClient.post(anyString(),any(),anyString())).thenReturn(new JsonObject());
        Mockito.when(webClient.get(anyString(),any())).thenReturn(new JsonObject());
    }

    //During testing the sandbox will produce null or strings containing a 401 error.
    //The tests are run to test all the generator methods
    @Test
    void testGetAccessToken() {
        // Setup
        // Run the test
        final JsonObject result = ingUtilUnderTest.getAccessToken("body", "/oauth2/token");

        // Verify the results
        assertNull(result);
    }

    @Test
    void testGetCustomerAccessToken() {
        // Setup

        // Run the test
        final JsonObject result = ingUtilUnderTest.getCustomerAccessToken("body", "code", "/oauth2/token");

        // Verify the results
        assertNotNull(result);
    }

    @Test
    void testDoAPIPostRevoke() {
        // Setup

        // Run the test
        final JsonObject result = ingUtilUnderTest.doAPIPostRevoke("token", "/oauth2/token/revoke", "body");

        // Verify the results
        assertNotNull(result);
    }

    @Test
    void testBuildPaymentRequest() {
        // Setup
        final PaymentRequest paymentRequest = new PaymentRequest();
        paymentRequest.setInformation("information");
        paymentRequest.setCurrency(Currency.EUR);
        paymentRequest.setReceiver(new Account("id", "iban", "name", "currency", 0.0));
        paymentRequest.setSender(new Account("id", "iban", "name", "currency", 0.0));
        paymentRequest.setAmount(0.0);
        paymentRequest.setIp("ip");

        final String expectedResult = "{\"debtorAccount\":{\"iban\":\"iban\"},\"instructedAmount\":{\"amount\":0,\"currency\":\"EUR\"},\"creditorAccount\":{\"iban\":\"iban\"},\"creditorName\":\"name\",\"remittanceInformationUnstructured\":\"information\"}";

        // Run the test
        final JsonObject result = ingUtilUnderTest.buildPaymentRequest(paymentRequest);

        // Verify the results
        assertEquals(expectedResult, result.toString());
    }
}
