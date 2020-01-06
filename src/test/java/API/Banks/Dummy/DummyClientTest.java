package API.Banks.Dummy;

import API.DTO.Account;
import API.DTO.PaymentRequest;
import API.DTO.Transaction;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.MockitoAnnotations;

import java.net.URI;
import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertNull;
import static org.mockito.Mockito.*;

public class DummyClientTest {
    private DummyClient sut;
    private DummyUtil dummyUtil;

    @BeforeEach
    void setup() {
        MockitoAnnotations.initMocks(this);
        sut = new DummyClient();
        dummyUtil = mock(DummyUtil.class);
        sut.setFactory(dummyUtil);
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
        when(dummyUtil.getAccounts()).thenReturn(new ArrayList<>());
        Assertions.assertNotNull(sut.getUserAccounts(""));
    }

    @Test
    void getUserAccountsCallsGetAccountsFunctionFromFactory() {
        when(dummyUtil.getAccounts()).thenReturn(new ArrayList<>());
        sut.getUserAccounts("");
        verify(dummyUtil).getAccounts();
    }

    @Test
    void getUserAccountBalanceReturnsBalance() {
        when(dummyUtil.getBalanceFromAccounts(anyString())).thenReturn(0);
        Assertions.assertNotNull(sut.getBalance("", ""));
    }

    @Test
    void getUserAccountBalanceCallsGetBalanceFunctionFromFactory() {
        when(dummyUtil.getBalanceFromAccounts(anyString())).thenReturn(0);
        sut.getBalance("", "");
        verify(dummyUtil).getBalanceFromAccounts(anyString());
    }

    @Test
    void getAuthorizationurlReturnsNewUri() {
        assertNotNull(sut.getAuthorizationUrl("redirect","state"));
    }

    @Test
    void getAccountDetailsReturnsAccountDetails() {
        when(dummyUtil.getAccount(anyString())).thenReturn(new Account());
        when(dummyUtil.getTransactions(any())).thenReturn(new ArrayList<>());
        assertNotNull(sut.getAccountDetails("token","id"));
    }

    @Test
    void getAccountDetailsReturnsNotNull() {
        when(dummyUtil.getAccount(anyString())).thenReturn(new Account());
        when(dummyUtil.getTransactions(any())).thenReturn(new ArrayList<Transaction>());
        Assertions.assertNotNull(sut.getAccountDetails("", ""));
    }

    @Test
    void intitiateTransaction() {
        assertNull(sut.initiateTransaction("token", new PaymentRequest()));
    }

    @Test
    void revokeDoesNothing() {
        sut.revoke("refresh");
    }
}
