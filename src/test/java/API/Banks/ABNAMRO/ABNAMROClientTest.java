package API.Banks.ABNAMRO;

import API.DTO.Bank;
import API.DTO.BankToken;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.net.URI;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ABNAMROClientTest {
    private ABNAMROClient abnamroClient;
    private ABNAMROUtil mockedABNAMROUtil;
    private Gson gson;

    @BeforeEach
    void setup() {
        gson = new Gson();
        abnamroClient = new ABNAMROClient();
        mockedABNAMROUtil = Mockito.mock(ABNAMROUtil.class);
        abnamroClient.setAbnamroUtil(mockedABNAMROUtil);
    }

    @Test
    void testGetAuthorizationUrl() {
        // Arrange
        var expected = "URL";
        Mockito.when(mockedABNAMROUtil.getAuthorizationUrl(Mockito.anyString())).thenReturn(URI.create(expected));

        // Act
        var result = abnamroClient.getAuthorizationUrl("XXX", "XXX");

        // Assert
        assertEquals(expected, result.toString());
    }

    @Test
    void testToken() {
        // Arrange
        var expectedBankToken = new BankToken();
        expectedBankToken.setAccessToken("AAIkYzQ1MTc3OGMtZGIyYy00NTFlLThmNTctOWJiNjI0MjIzMjllAlsAId1lGEs_UdKfJekTG91N44ZRg8s0W0OXL17z5KEQtgakaByo66J2cxqczGIn_vDv2clzP8GkvuzgH4fI87TR38nNey1M5yqsrbKLuUoMTKU9eecFELS20FUYrhnytuipGTu2fxbl1GBeHo_NEKpp9cqkeHr_9QZo593vQ7NrnWWWbG62FYmyMQ2R2HfDcSKJPHC51sXmGwL-6XS4PFG3AfTg2lPnHC-KkSHu2plpQ3HCLhrIOfZMzbI7z2hNSpOq6nBB27rNOGcQqDiyLg");
        var expected = "{\"token_type\":\"Bearer\",\"access_token\":\"AAIkYzQ1MTc3OGMtZGIyYy00NTFlLThmNTctOWJiNjI0MjIzMjllAlsAId1lGEs_UdKfJekTG91N44ZRg8s0W0OXL17z5KEQtgakaByo66J2cxqczGIn_vDv2clzP8GkvuzgH4fI87TR38nNey1M5yqsrbKLuUoMTKU9eecFELS20FUYrhnytuipGTu2fxbl1GBeHo_NEKpp9cqkeHr_9QZo593vQ7NrnWWWbG62FYmyMQ2R2HfDcSKJPHC51sXmGwL-6XS4PFG3AfTg2lPnHC-KkSHu2plpQ3HCLhrIOfZMzbI7z2hNSpOq6nBB27rNOGcQqDiyLg\",\"expires_in\":3600,\"consented_on\":1576674233,\"scope\":\"ais.balances.read ais.transactions.read-90days ais.transactions.read-history caf.fundsconfirmation.read pi.bulk.read-write\",\"refresh_token\":\"AAJ61p8lMlpkO8Okz5eBolZrlwdGd95UPoLTaPeVPY5600q3UMcd_7xxSwKQnSRQPK7zZNpqkt4h9Z6iIeafL3OrqjgOOKz34EoW7_glfWi1sExxSmnVro_E-9uA2y8GNv95mQPPLEKXJN61_rQTJRp6JNi7ItKmXzItHscuEJDssY3nBisvsgD4UnpoMPC6AOTm-SSh_XaFknHzlUnw-KdUY7dpcWisIYSFkBLzkbH57HvOmrgPM2BMvzu1fu6EO27A6aOJWVHD0yF0u-6u4tP2\",\"refresh_token_expires_in\":157784760}";

        Mockito.when(mockedABNAMROUtil.getBankToken(Mockito.anyString())).thenReturn(gson.fromJson(expected, JsonObject.class));


        // Act
        var result = abnamroClient.token("XXX");

        // Assert
        assertEquals(expectedBankToken.getAccessToken(), result.getAccessToken());
    }

    @Test
    void testRefresh() {
        // Arrange
        var expectedBankToken = new BankToken();
        expectedBankToken.setAccessToken("AAIkYzQ1MTc3OGMtZGIyYy00NTFlLThmNTctOWJiNjI0MjIzMjllAlsAId1lGEs_UdKfJekTG91N44ZRg8s0W0OXL17z5KEQtgakaByo66J2cxqczGIn_vDv2clzP8GkvuzgH4fI87TR38nNey1M5yqsrbKLuUoMTKU9eecFELS20FUYrhnytuipGTu2fxbl1GBeHo_NEKpp9cqkeHr_9QZo593vQ7NrnWWWbG62FYmyMQ2R2HfDcSKJPHC51sXmGwL-6XS4PFG3AfTg2lPnHC-KkSHu2plpQ3HCLhrIOfZMzbI7z2hNSpOq6nBB27rNOGcQqDiyLg");
        var expected = "{\"token_type\":\"Bearer\",\"access_token\":\"AAIkYzQ1MTc3OGMtZGIyYy00NTFlLThmNTctOWJiNjI0MjIzMjllAlsAId1lGEs_UdKfJekTG91N44ZRg8s0W0OXL17z5KEQtgakaByo66J2cxqczGIn_vDv2clzP8GkvuzgH4fI87TR38nNey1M5yqsrbKLuUoMTKU9eecFELS20FUYrhnytuipGTu2fxbl1GBeHo_NEKpp9cqkeHr_9QZo593vQ7NrnWWWbG62FYmyMQ2R2HfDcSKJPHC51sXmGwL-6XS4PFG3AfTg2lPnHC-KkSHu2plpQ3HCLhrIOfZMzbI7z2hNSpOq6nBB27rNOGcQqDiyLg\",\"expires_in\":3600,\"consented_on\":1576674233,\"scope\":\"ais.balances.read ais.transactions.read-90days ais.transactions.read-history caf.fundsconfirmation.read pi.bulk.read-write\",\"refresh_token\":\"AAJ61p8lMlpkO8Okz5eBolZrlwdGd95UPoLTaPeVPY5600q3UMcd_7xxSwKQnSRQPK7zZNpqkt4h9Z6iIeafL3OrqjgOOKz34EoW7_glfWi1sExxSmnVro_E-9uA2y8GNv95mQPPLEKXJN61_rQTJRp6JNi7ItKmXzItHscuEJDssY3nBisvsgD4UnpoMPC6AOTm-SSh_XaFknHzlUnw-KdUY7dpcWisIYSFkBLzkbH57HvOmrgPM2BMvzu1fu6EO27A6aOJWVHD0yF0u-6u4tP2\",\"refresh_token_expires_in\":157784760}";

        Mockito.when(mockedABNAMROUtil.getBankToken(Mockito.anyString())).thenReturn(gson.fromJson(expected, JsonObject.class));

        // Act
        var result = abnamroClient.refresh("XXX");

        // Assert
        assertEquals(expectedBankToken.getAccessToken(), result.getAccessToken());
    }

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
