package API.Banks.Dummy;

import API.DTO.Account;
import API.DTO.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.net.URI;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.Mockito.*;

public class DummyClientTest {
    private DummyClient sut;
    private DummyBankFakeDataFactory mockedDummyBankFakeDataFactory;

    @BeforeEach
    void setup() {
        sut = new DummyClient();
        mockedDummyBankFakeDataFactory = mock(DummyBankFakeDataFactory.class);
        sut.setFactory(mockedDummyBankFakeDataFactory);
    }

    @Test
    void getAuthorizationUrlReturnsURI() {
        Assertions.assertNotNull(sut.getAuthorizationUrl("", ""));
    }

    @Test
    void getAuthorizationUrlGeneratesCorrectURI() {
        final String DUMMY_AUTHORIZATION_BASE = "http://localhost:8080/dummy/dummy";
        final String redirectUrl = "broodjesate";
        final String state = "komtopdetv";
        final URI expectedURI = URI.create(DUMMY_AUTHORIZATION_BASE + "?redirect_uri=" + redirectUrl + "&state=" + state);

        Assertions.assertEquals(expectedURI, sut.getAuthorizationUrl(redirectUrl, state));
    }

    @Test
    void tokenReturnBankToken() {
        Assertions.assertNotNull(sut.token(""));
    }

    @Test
    void refreshReturnBankToken() {
        Assertions.assertNotNull(sut.refresh(""));
    }

    @Test
    void getUserAccountsReturnsListOfAccounts() {
        when(mockedDummyBankFakeDataFactory.getAccounts()).thenReturn(new ArrayList<>());
        Assertions.assertNotNull(sut.getUserAccounts(""));
    }

    @Test
    void getUserAccountsCallsGetAccountsFunctionFromFactory() {
        when(mockedDummyBankFakeDataFactory.getAccounts()).thenReturn(new ArrayList<>());
        sut.getUserAccounts("");
        verify(mockedDummyBankFakeDataFactory).getAccounts();
    }

    @Test
    void getUserAccountBalanceReturnsBalance() {
        when(mockedDummyBankFakeDataFactory.getBalanceFromAccounts(anyString())).thenReturn(0);
        Assertions.assertNotNull(sut.getBalance("", ""));
    }

    @Test
    void getUserAccountBalanceCallsGetBalanceFunctionFromFactory() {
        when(mockedDummyBankFakeDataFactory.getBalanceFromAccounts(anyString())).thenReturn(0);
        sut.getBalance("", "");
        verify(mockedDummyBankFakeDataFactory).getBalanceFromAccounts(anyString());
    }

    @Test
    void getAuthorizationurlReturnsNewUri() {
        assertNotNull(sut.getAuthorizationUrl("redirect","state"));
    }

    @Test
    void getAccountDetailsReturnsAccountDetails() {
        when(mockedDummyBankFakeDataFactory.getAccount(anyString())).thenReturn(new Account());
        when(mockedDummyBankFakeDataFactory.getTransactions(any())).thenReturn(new ArrayList<>());
        assertNotNull(sut.getAccountDetails("token","id"));
    }

    @Test
    void getAccountDetailsCallsFactoryGetAccountMethod() {
        sut.getAccountDetails("", "");
        verify(mockedDummyBankFakeDataFactory).getAccount(anyString());
    }

    @Test
    void getAccountDetailsCallsFactoryGetTransactionMethod() {
        sut.getAccountDetails("", "");
        verify(mockedDummyBankFakeDataFactory).getTransactions(any());
    }

    @Test
    void getAccountDetailsReturnsNotNull() {
        when(mockedDummyBankFakeDataFactory.getAccount(anyString())).thenReturn(new Account());
        when(mockedDummyBankFakeDataFactory.getTransactions(any())).thenReturn(new ArrayList<Transaction>());
        Assertions.assertNotNull(sut.getAccountDetails("", ""));

    }
}
