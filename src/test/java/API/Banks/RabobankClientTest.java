package API.Banks;

import API.Banks.Rabobank.RabobankClient;
import API.Banks.Util.RaboUtil;
import API.DTO.*;
import API.DTO.RABO.RaboTransaction;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import java.util.ArrayList;
import java.util.Collections;
import java.util.UUID;
import static org.junit.jupiter.api.Assertions.assertEquals;

class RabobankClientTest {
    private final String EXAMPLE_CODE = UUID.randomUUID().toString();

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

    @Test
    void testGetAuthorizationUrl() {
        // Arrange
        String redirectUrl = "REDIRECT";
        String state = "STATE";

        var expected = RabobankClient.OAUTH_BASE + "/authorize?client_id=" + RabobankClient.CLIENT_ID + "&scope=" + RabobankClient.SCOPES + "&redirect_uri=" + redirectUrl + "&response_type=code&state=" + state;

        // Act
        var result = client.getAuthorizationUrl(redirectUrl, state);

        // Assert
        assertEquals(expected, result);
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
        var expected = new Account("ID", "IBAN", "NAME", "CURRENCY", new ArrayList<>(Collections.emptyList()), 0.0d);

        Mockito.when(mockedUtil.doGetRequest(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(gson.toJson(expected));

        // Act
        var result = client.getUserAccounts(EXAMPLE_CODE);

        // Assert
        assertEquals(expected.getId(), result.getId());
        assertEquals(expected.getIban(), result.getIban());
        assertEquals(expected.getName(), result.getName());
        assertEquals(expected.getCurrency(), result.getCurrency());
        assertEquals(expected.getBalance(), result.getBalance());
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

    @Test
    void testGetAccountTransactions() {
        // Arrange
        var expected = new Transaction();
        var transaction = new RaboTransaction();

        var account = new Account();
        account.setBalance(100.0);
        transaction.setAccount(account);

        var transactions = new RaboTransaction();
        transactions.setPending(new ArrayList<>(Collections.emptyList()));
        transactions.setBooked(new ArrayList<>(Collections.emptyList()));
        transaction.setTransactions(transactions);

        expected.setAccount(account);

        Mockito.when(mockedUtil.doGetRequest(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(gson.toJson(transaction));

        // Act
        var result = client.getAccountTransactions(EXAMPLE_CODE, "1234-1234-abc-abc");

        // Assert
        assertEquals(expected.getAmount(), result.getAmount());
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
