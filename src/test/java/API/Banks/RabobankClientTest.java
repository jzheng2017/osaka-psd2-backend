package API.Banks;

import API.Banks.Rabobank.RabobankClient;
import API.Banks.Rabobank.RaboUtil;
import API.DTO.*;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

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
        var expected = new BankToken(0, Bank.RABOBANK, "ACCESS", "REFRESH");

        Mockito.when(mockedUtil.getBankToken(body)).thenReturn(expected);

        // Act
        var result = client.token(EXAMPLE_CODE);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void testRefresh() {
        // Arrange
        var body = "grant_type=refresh_token&refresh_token=" + EXAMPLE_CODE;
        var expected = new BankToken(0, Bank.RABOBANK, "ACCESS", "REFRESH");

        Mockito.when(mockedUtil.getBankToken(body)).thenReturn(expected);

        // Act
        final BankToken result = client.refresh(EXAMPLE_CODE);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void testGetUserAccounts() {
        // Arrange
        int count = 3;
        var exampleResponse = generateExampleAccountsResponse(count);

        Mockito.when(mockedUtil.doGetRequest(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(exampleResponse);

        // Act
        var accounts = client.getUserAccounts(EXAMPLE_CODE);

        // Assert
        assertEquals(count, accounts.size());
    }

    @Test
    void testGetAccountBalances() {
        // Arrange
        var expected = new Balance();
        expected.setBalanceType("Expected");

        Mockito.when(mockedUtil.doGetRequest(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(gson.toJson(expected));

        // Act
        var result = client.getAccountBalances(EXAMPLE_CODE, "1234-1234-abc-abc");

        // Assert
        assertEquals(expected.getBalanceType(), result.getBalanceType());
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

            var debtorAccount = new JsonObject();
            debtorAccount.addProperty("iban", "NL39RABO0320130878");

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

        Mockito.when(mockedUtil.doGetRequest(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(exampleResponse);

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

        Mockito.when(mockedUtil.doPaymentInitiationRequest(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(exampleResponse);

        // Act
        var response = client.initiateTransaction(EXAMPLE_CODE, paymentRequest);

        // Assert
        assertEquals(href, response.getUrl().toString());
    }
}
