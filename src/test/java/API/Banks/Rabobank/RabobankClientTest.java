package API.Banks.Rabobank;

import API.Banks.Rabobank.Util.RaboUtil;
import API.DTO.*;
import API.DTO.RABO.RaboBooking;
import API.DTO.RABO.RaboTransaction;
import com.google.gson.Gson;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class RabobankClientTest {
    private final String SCOPES = "ais.balances.read%20ais.transactions.read-90days%20ais.transactions.read-history%20caf.fundsconfirmation.read";
    private final String CLIENT_ID = "c451778c-db2c-451e-8f57-9bb62422329e";
    private final String OAUTH_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/oauth2";
    private final String AIS_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/payments/account-information/ais/v3";

    private RabobankClient rabobankClientUnderTest;
    private RaboUtil mockedUtil;
    private String code = "code";
    private String token = "token";
    private String id = "id";
    private Gson gson = new Gson();


    @BeforeEach
    void setUp() {
        rabobankClientUnderTest = new RabobankClient();
        mockedUtil = Mockito.mock(RaboUtil.class);
        rabobankClientUnderTest.setUtil(mockedUtil);
    }

    @Test
    void testGetAuthorizationUrl() {
        // Setup
        String redirectUrl = "redirectUrl";
        String state = "state";
        final String expectedResult = "https://api-sandbox.rabobank.nl/openapi/sandbox/oauth2/authorize?client_id=c451778c-db2c-451e-8f57-9bb62422329e&scope=ais.balances.read%20ais.transactions.read-90days%20ais.transactions.read-history%20caf.fundsconfirmation.read%20pi.bulk.read-write&redirect_uri=redirectUrl&response_type=code&state=state";

        // Run the test
        final String result = rabobankClientUnderTest.getAuthorizationUrl(redirectUrl, state);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testToken() {
        // Setup
        final String body = "grant_type=authorization_code&code=" + code;
        final BankToken expectedResult = new BankToken(0, Bank.RABOBANK, "accessToken", "refreshToken");
        Mockito.when(mockedUtil.getBankToken(code, body)).thenReturn(expectedResult);
        // Run the test
        final BankToken result = rabobankClientUnderTest.token(code);
        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testRefresh() {
        // Setup
        String body = "grant_type=refresh_token&refresh_token=" + code;
        final BankToken expectedResult = new BankToken(0, Bank.RABOBANK, "accessToken", "refreshToken");
        Mockito.when(mockedUtil.getBankToken(code, body)).thenReturn(expectedResult);

        // Run the test
        final BankToken result = rabobankClientUnderTest.refresh(code);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetUserAccounts() {
        // Setup
        final Account expectedResult = new Account("id", "iban", "name", "currency", new ArrayList<>(Arrays.asList()), 0.0d);
        String endpoint = "/accounts";
        Mockito.when(mockedUtil.doGetRequest(AIS_BASE,endpoint,token)).thenReturn(gson.toJson(expectedResult));
        // Run the test
        final Account result = rabobankClientUnderTest.getUserAccounts("token");

        // Verify the results
        assertEquals(expectedResult.getIban(), result.getIban());
    }

    @Test
    void testGetAccountBalances() {
        // Setup
        final Balance expectedResult = new Balance();
        expectedResult.setBalanceType("Expected");
        String endpoint = "/accounts/" + id + "/balances";
        Mockito.when(mockedUtil.doGetRequest(AIS_BASE,endpoint,token)).thenReturn(gson.toJson(expectedResult));
        // Run the test
        final Balance result = rabobankClientUnderTest.getAccountBalances("token", "id");

        // Verify the results
        assertEquals(expectedResult.getBalanceType(), result.getBalanceType());
    }

    @Test
    void testGetAccountTransactions() {
        // Setup
        final Transaction expectedResult = new Transaction();

        final RaboTransaction transaction = new RaboTransaction();
        Account account = new Account();
        account.setBalance(100.0);
        transaction.setAccount(account);
        RaboTransaction innerTransactions = new RaboTransaction();
        innerTransactions.setPending(new ArrayList<>(Arrays.asList()));
        innerTransactions.setBooked(new ArrayList<>(Arrays.asList()));
        transaction.setTransactions(innerTransactions);
        expectedResult.setAccount(account);
        String endpoint = "/accounts/" + id + "/transactions?bookingStatus=booked";
        Mockito.when(mockedUtil.doGetRequest(AIS_BASE,endpoint,token)).thenReturn(gson.toJson(transaction));
//         Run the test
        final Transaction result = rabobankClientUnderTest.getAccountTransactions("token", "id");

        // Verify the results
        assertEquals(expectedResult.getAmount(), result.getAmount());
    }
}
