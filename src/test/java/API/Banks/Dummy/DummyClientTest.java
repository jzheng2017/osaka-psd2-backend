package API.Banks.Dummy;

import API.DTO.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

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
        sut.getBalance("","");
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
}