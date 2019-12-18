package API.Banks.Rabobank;

import API.DTO.BankToken;
import API.DTO.PaymentRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class RabobankClientTest {
    private static final String EXAMPLE_CODE = UUID.randomUUID().toString();

    private RabobankClient client;
    private RaboUtil mockedUtil;
    private Gson gson;

    @BeforeEach
    void setup() {
        client = new RabobankClient();
        mockedUtil = Mockito.mock(RaboUtil.class);
        client.setUtil(mockedUtil);
        gson = new Gson();
    }

    private String generateExamplePaymentInitiationResponse(String paymentId, String href) {
        var object = new JsonObject();
        object.addProperty("paymentId", paymentId);
        object.addProperty("transactionStatus", "ACTC");

        var links = new JsonObject();

        var redirect = new JsonObject();
        redirect.addProperty("href", href);
        links.add("scaRedirect", redirect);
        object.add("_links", links);

        return gson.toJson(object);
    }

    private String generateExampleAccountsResponse(int count) {
        var accounts = new JsonArray();

        for (int i = 0; i < count; i++) {
            var account = new JsonObject();
            account.addProperty("resourceId", "XXX");
            account.addProperty("currency", "EUR");
            account.addProperty("iban", "NL69INGB0123456789");
            account.addProperty("status", "enabled");
            account.addProperty("name", "Rabobank Nederland B.V.");

            accounts.add(account);
        }

        var example = new JsonObject();
        example.add("accounts", accounts);

        return gson.toJson(example);
    }

    @Test
    void testGetAuthorizationUrl() {
        // Arrange
        String redirectUrl = "REDIRECT";
        String state = "STATE";

        var expected = RabobankClient.OAUTH_BASE + "/authorize?client_id=" + RabobankClient.CLIENT_ID + "&scope=" + RabobankClient.SCOPES + "&redirect_uri=" + redirectUrl + "&response_type=code&state=" + state;

        // Act
        var result = client.getAuthorizationUrl(redirectUrl, state);

        // Assert
        assertEquals(expected, result.toString());
    }

    @Test
    void testToken() {
        // Arrange
        var body = "grant_type=authorization_code&code=" + EXAMPLE_CODE;
        var expectedBankToken = new BankToken();
        expectedBankToken.setAccessToken("AAIkYzQ1MTc3OGMtZGIyYy00NTFlLThmNTctOWJiNjI0MjIzMjllAlsAId1lGEs_UdKfJekTG91N44ZRg8s0W0OXL17z5KEQtgakaByo66J2cxqczGIn_vDv2clzP8GkvuzgH4fI87TR38nNey1M5yqsrbKLuUoMTKU9eecFELS20FUYrhnytuipGTu2fxbl1GBeHo_NEKpp9cqkeHr_9QZo593vQ7NrnWWWbG62FYmyMQ2R2HfDcSKJPHC51sXmGwL-6XS4PFG3AfTg2lPnHC-KkSHu2plpQ3HCLhrIOfZMzbI7z2hNSpOq6nBB27rNOGcQqDiyLg");
        var expected = "{\"token_type\":\"Bearer\",\"access_token\":\"AAIkYzQ1MTc3OGMtZGIyYy00NTFlLThmNTctOWJiNjI0MjIzMjllAlsAId1lGEs_UdKfJekTG91N44ZRg8s0W0OXL17z5KEQtgakaByo66J2cxqczGIn_vDv2clzP8GkvuzgH4fI87TR38nNey1M5yqsrbKLuUoMTKU9eecFELS20FUYrhnytuipGTu2fxbl1GBeHo_NEKpp9cqkeHr_9QZo593vQ7NrnWWWbG62FYmyMQ2R2HfDcSKJPHC51sXmGwL-6XS4PFG3AfTg2lPnHC-KkSHu2plpQ3HCLhrIOfZMzbI7z2hNSpOq6nBB27rNOGcQqDiyLg\",\"expires_in\":3600,\"consented_on\":1576674233,\"scope\":\"ais.balances.read ais.transactions.read-90days ais.transactions.read-history caf.fundsconfirmation.read pi.bulk.read-write\",\"refresh_token\":\"AAJ61p8lMlpkO8Okz5eBolZrlwdGd95UPoLTaPeVPY5600q3UMcd_7xxSwKQnSRQPK7zZNpqkt4h9Z6iIeafL3OrqjgOOKz34EoW7_glfWi1sExxSmnVro_E-9uA2y8GNv95mQPPLEKXJN61_rQTJRp6JNi7ItKmXzItHscuEJDssY3nBisvsgD4UnpoMPC6AOTm-SSh_XaFknHzlUnw-KdUY7dpcWisIYSFkBLzkbH57HvOmrgPM2BMvzu1fu6EO27A6aOJWVHD0yF0u-6u4tP2\",\"refresh_token_expires_in\":157784760}";

        Mockito.when(mockedUtil.getBankToken(body)).thenReturn(gson.fromJson(expected, JsonObject.class));

        // Act
        var result = client.token(EXAMPLE_CODE);

        // Assert
        assertEquals(expectedBankToken.getAccessToken(), result.getAccessToken());
    }

    @Test
    void testRefresh() {
        // Arrange
        var body = "grant_type=refresh_token&refresh_token=" + EXAMPLE_CODE;
        var expectedBankToken = new BankToken();
        expectedBankToken.setAccessToken("AAIkYzQ1MTc3OGMtZGIyYy00NTFlLThmNTctOWJiNjI0MjIzMjllAlsAId1lGEs_UdKfJekTG91N44ZRg8s0W0OXL17z5KEQtgakaByo66J2cxqczGIn_vDv2clzP8GkvuzgH4fI87TR38nNey1M5yqsrbKLuUoMTKU9eecFELS20FUYrhnytuipGTu2fxbl1GBeHo_NEKpp9cqkeHr_9QZo593vQ7NrnWWWbG62FYmyMQ2R2HfDcSKJPHC51sXmGwL-6XS4PFG3AfTg2lPnHC-KkSHu2plpQ3HCLhrIOfZMzbI7z2hNSpOq6nBB27rNOGcQqDiyLg");
        var expected = "{\"token_type\":\"Bearer\",\"access_token\":\"AAIkYzQ1MTc3OGMtZGIyYy00NTFlLThmNTctOWJiNjI0MjIzMjllAlsAId1lGEs_UdKfJekTG91N44ZRg8s0W0OXL17z5KEQtgakaByo66J2cxqczGIn_vDv2clzP8GkvuzgH4fI87TR38nNey1M5yqsrbKLuUoMTKU9eecFELS20FUYrhnytuipGTu2fxbl1GBeHo_NEKpp9cqkeHr_9QZo593vQ7NrnWWWbG62FYmyMQ2R2HfDcSKJPHC51sXmGwL-6XS4PFG3AfTg2lPnHC-KkSHu2plpQ3HCLhrIOfZMzbI7z2hNSpOq6nBB27rNOGcQqDiyLg\",\"expires_in\":3600,\"consented_on\":1576674233,\"scope\":\"ais.balances.read ais.transactions.read-90days ais.transactions.read-history caf.fundsconfirmation.read pi.bulk.read-write\",\"refresh_token\":\"AAJ61p8lMlpkO8Okz5eBolZrlwdGd95UPoLTaPeVPY5600q3UMcd_7xxSwKQnSRQPK7zZNpqkt4h9Z6iIeafL3OrqjgOOKz34EoW7_glfWi1sExxSmnVro_E-9uA2y8GNv95mQPPLEKXJN61_rQTJRp6JNi7ItKmXzItHscuEJDssY3nBisvsgD4UnpoMPC6AOTm-SSh_XaFknHzlUnw-KdUY7dpcWisIYSFkBLzkbH57HvOmrgPM2BMvzu1fu6EO27A6aOJWVHD0yF0u-6u4tP2\",\"refresh_token_expires_in\":157784760}";

        Mockito.when(mockedUtil.getBankToken(body)).thenReturn(gson.fromJson(expected, JsonObject.class));

        // Act
        final BankToken result = client.refresh(EXAMPLE_CODE);

        // Assert
        assertEquals(expectedBankToken.getAccessToken(), result.getAccessToken());
    }

    @Test
    void testGetUserAccounts() {
        // Arrange
        int count = 3;
        var exampleResponse = generateExampleAccountsResponse(count);

        Mockito.when(mockedUtil.get(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(gson.fromJson(exampleResponse, JsonObject.class));

        // Act
        var accounts = client.getUserAccounts(EXAMPLE_CODE);

        // Assert
        assertEquals(count, accounts.size());
    }

    @Test
    void testGetAccountBalances() {
        // Arrange
        var expected = 12;
        var expectedJson = "{\"account\":{\"iban\":\"NL39RABO0320130878\",\"currency\":\"EUR\"},\"balances\":[{\"balanceAmount\":{\"amount\":\"30.0\",\"currency\":\"EUR\"},\"balanceType\":\"expected\",\"lastChangeDateTime\":\"2019-12-16T18:56:18.448Z\"}]}";
        Mockito.when(mockedUtil.get(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(gson.fromJson(expectedJson, JsonObject.class));

        // Act
        var result = client.getBalance(EXAMPLE_CODE, "1234-1234-abc-abc");

        // Assert
        assertNotNull(result);
    }

    private String generateExampleTransactions(int count) {
        var example = new JsonObject();
        var transactions = new JsonObject();
        var booked = new JsonArray();

        for (int i = 0; i < count; i++) {
            var transaction = new JsonObject();
            transaction.addProperty("entryReference", "12345ABC");
            transaction.addProperty("bookingDate", "2016-10-01");

            var transactionAmount = new JsonObject();
            transactionAmount.addProperty("amount", 100);
            transactionAmount.addProperty("currency", "EUR");

            var creditorAccount = new JsonObject();
            creditorAccount.addProperty("iban", "NL39RABO0320130878");
            creditorAccount.addProperty("creditorName", "Frits");

            var debtorAccount = new JsonObject();
            debtorAccount.addProperty("iban", "NL39RABO0320130878");
            debtorAccount.addProperty("debtorName", "Frits");

            transaction.add("creditorAccount", creditorAccount);
            transaction.add("debtorAccount", debtorAccount);
            transaction.add("transactionAmount", transactionAmount);

            booked.add(transaction);
        }

        transactions.add("booked", booked);

        example.add("transactions", transactions);

        var account = new JsonObject();
        account.addProperty("iban", "NL39RABO0320130878");
        account.addProperty("currency", "EUR");
        example.add("account", account);

        return gson.toJson(example);
    }

    @Test
    void testGetAccountDetails() {
        // Arrange
        var count = 10;
        var exampleResponse = generateExampleTransactions(count);

        Mockito.when(mockedUtil.get(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(gson.fromJson(exampleResponse, JsonObject.class));

        // Act
        var transactions = client.getAccountDetails(EXAMPLE_CODE, "").getTransactions();

        // Assert
        assertEquals(count, transactions.size());
    }

    @Test
    void testInitiateTransaction() {
        // Arrange
        var paymentRequest = new PaymentRequest();
        var paymentId = UUID.randomUUID().toString();
        var href = "https://betalen.rabobank.nl/afronden-web/deeps/deeplink/deeplink/pi/ucp/single-credit-transfers/start?paymentinitiationid="+paymentId+"/dummylink";
        var exampleResponse = generateExamplePaymentInitiationResponse(paymentId, href);

        Mockito.when(mockedUtil.doPaymentInitiationRequest(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(gson.fromJson(exampleResponse,JsonObject.class));

        // Act
        var response = client.initiateTransaction(EXAMPLE_CODE, paymentRequest);

        // Assert
        assertEquals(href, response.getUrl().toString());
    }

    @Test
    void revokeDoesNothing() {
        client.revoke("refresh");
    }
}
