package API.Banks.ING;

import API.Banks.ING.INGClient;
import API.Banks.ING.INGMapper;
import API.Banks.ING.INGUtil;
import API.DTO.AccountDetails;
import API.DTO.BankToken;
import API.DTO.PaymentRequest;
import com.google.gson.Gson;
import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class INGClientTest {
    private static final String EXAMPLE_CODE = UUID.randomUUID().toString();

    private INGClient client;
    private INGUtil mockedUtil;
    private INGMapper mapper;
    private Gson gson;

    @BeforeEach
    void setup() {
        client = new INGClient();
        mockedUtil = Mockito.mock(INGUtil.class);
        mapper = Mockito.mock(INGMapper.class);
        client.setUtil(mockedUtil);
        client.setMapper(mapper);
        gson = new Gson();

        var exampleTokenResponse = generateExampleTokenResponse(EXAMPLE_CODE);
        Mockito.when(mockedUtil.getAccessToken(Mockito.anyString(), Mockito.anyString())).thenReturn(exampleTokenResponse);
        Mockito.when(mockedUtil.getCustomerAccessToken(Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(exampleTokenResponse);
    }

    private String generateExampleTokenResponse(String accessToken) {
        var example = new JsonObject();
        example.addProperty("access_token", accessToken);
        example.addProperty("expires_in", 905);
        example.addProperty("scope", "XXX");
        example.addProperty("token_type", "Bearer");

        return gson.toJson(example);
    }

    private String generateExampleAccountsResponse(int count) {
        var accounts = new JsonArray();

        for (int i = 0; i < count; i++) {
            var account = new JsonObject();
            account.addProperty("resourceId", "XXX");
            account.addProperty("product", "Betaalrekening");
            account.addProperty("iban", "NL69INGB0123456789");
            accounts.add(account);
        }

        var example = new JsonObject();
        example.add("accounts", accounts);

        return gson.toJson(example);
    }

    private String generateRandomBalancesResponse(int amount) {
        var example = new JsonObject();
        var balances = new JsonArray();

        var balance = new JsonObject();
        balance.addProperty("balanceType", "expected");
        balance.addProperty("lastChangeDateTime", "2018-07-01T09:16:54.991Z");

        var balanceAmount = new JsonObject();
        balanceAmount.addProperty("amount", amount);
        balanceAmount.addProperty("currency", "EUR");
        balance.add("balanceAmount", balanceAmount);

        balances.add(balance);
        example.add("balances", balances);

        return gson.toJson(example);
    }

    private String generateExampleTransactions(int count) {
        var example = new JsonObject();
        var transactions = new JsonObject();
        var booked = new JsonArray();

        for (int i = 0; i < count / 2; i++) {
            var transaction = new JsonObject();
            transaction.addProperty("transactionType", "Diversen");
            transaction.addProperty("transactionId", "12345ABC");
            transaction.addProperty("bookingDate", "2016-10-01");
            transaction.addProperty("remittanceInformationUnstructured", "RC AFREK. REK. 92 EUR<br>COR.PERIODE 01.07 T/M 30.09.2016<br>CREDITRENTE<br>Valutadatum: 01-10-2016");
            transaction.addProperty("endToEndId", "NOTPROVIDED");

            var transactionAmount = new JsonObject();
            if (i == 2) {
                transactionAmount.addProperty("amount", -100);
            } else
                transactionAmount.addProperty("amount", 100);
            transactionAmount.addProperty("currency", "EUR");

            transaction.add("transactionAmount", transactionAmount);
            booked.add(transaction);
        }

        transactions.add("booked", booked);
        transactions.add("pending", booked);

        example.add("transactions", transactions);

        var account = new JsonObject();
        account.addProperty("iban", "NL69INGB0123456789");
        account.addProperty("currency", "EUR");
        example.add("account", account);

        return gson.toJson(example);
    }

    private String generateExamplePaymentInitiationResponse(String paymentId, String href) {
        var object = new JsonObject();
        object.addProperty("transactionStatus", "RCVD");

        var links = new JsonObject();
        links.addProperty("scaRedirect", href);
        links.addProperty("self", "https://api.ing.com/v1/payments/sepa-credit-transfers/" + paymentId);
        links.addProperty("status", "https://api.ing.com/v1/payments/sepa-credit-transfers/" + paymentId + "/status");
        object.add("_links", links);

        object.addProperty("paymentId", paymentId);
        return gson.toJson(object);
    }

    @Test
    void testGetAuthorizationUrl() {
        // Arrange
        String redirectUrl = "REDIRECT";
        String state = "STATE";

        var expected = INGClient.DUMMY_AUTHORIZATION_BASE + "?redirect_uri=" + redirectUrl + "&state=" + state;

        // Act
        var result = client.getAuthorizationUrl(redirectUrl, state);

        // Assert
        assertEquals(expected, result.toString());
    }

    @Test
    void testAuthorize() {
        // Arrange

        // Act
        var result = client.authorize();

        // Assert
        assertEquals(EXAMPLE_CODE, result.getAccessToken());
    }

    @Test
    void testToken() {
        // Arrange

        // Act
        var result = client.token(EXAMPLE_CODE);

        // Assert
        assertEquals(EXAMPLE_CODE, result.getAccessToken());
    }

    @Test
    void testRefresh() {
        // Arrange

        // Act
        var result = client.refresh(EXAMPLE_CODE);

        // Assert
        assertEquals(EXAMPLE_CODE, result.getAccessToken());
    }

    @Test
    void testGetUserAccounts() {
        // Arrange
        int count = 3;
        var exampleResponse = generateExampleAccountsResponse(count);

        Mockito.when(mockedUtil.doApiRequest(Mockito.anyString(), Mockito.anyString())).thenReturn(exampleResponse);

        // Act
        var accounts = client.getUserAccounts(EXAMPLE_CODE);

        // Assert
        assertEquals(count, accounts.size());
    }

    @Test
    void testGetAccountBalances() {
        // Arrange
        var amount = 100;

        var exampleResponse = generateRandomBalancesResponse(amount);

        Mockito.when(mockedUtil.doApiRequest(Mockito.anyString(), Mockito.anyString())).thenReturn(exampleResponse);

        // Act
        var balances = client.getAccountBalances(EXAMPLE_CODE, "").getBalances();
        var balance = balances.get(0);

        // Assert
        assertEquals(amount, balance.getBalanceAmount().getAmount());
    }

    @Test
    void testGetAccountDetailsWithMapper() {
        // Arrange
        var count = 10;
        client.setMapper(new INGMapper());
        var exampleResponse = generateExampleTransactions(count);
        AccountDetails expected = new AccountDetails();
        expected.setTransactions(new ArrayList<>());
        Mockito.when(mockedUtil.doApiRequest(Mockito.anyString(), Mockito.anyString())).thenReturn(exampleResponse);
        // Act
        var transactions = client.getAccountDetails(EXAMPLE_CODE, "").getTransactions();

        // Assert
        assertEquals(count, transactions.size());
    }

    @Test
    void testInitiateTransaction() {
        // Arrange
        var paymentRequest = new PaymentRequest();
        paymentRequest.setIp("XXX");

        var paymentId = UUID.randomUUID().toString();
        var href = "https://myaccount.ing.com/payment-initiation/" + paymentId + "/NL/?redirect_uri=https%3A%2F%2Fexample.com%2Fredirect";
        var exampleResponse = generateExamplePaymentInitiationResponse(paymentId, href);

        Mockito.when(mockedUtil.doAPIPostRequest(Mockito.anyString(), Mockito.anyString(), Mockito.anyString(), Mockito.anyString())).thenReturn(exampleResponse);

        // Act
        var response = client.initiateTransaction(EXAMPLE_CODE, paymentRequest);

        // Assert
        assertEquals(href, response.getUrl().toString());
    }

    @Test
    void testRevoke() {
        client.revoke("refresh");
        Mockito.verify(mockedUtil).doAPIPostRevoke(Mockito.anyString(), Mockito.anyString(), Mockito.anyString());
    }
}
