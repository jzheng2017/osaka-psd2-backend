package API.Banks.Dummy;

import API.DTO.Balance;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class DummyClientTest {
    private DummyClient sut;
    private DummyBankFakeDataFactory mockedDummyBankFakeDataFactory;

    @BeforeEach
    void setup() {
        sut = new DummyClient();
        mockedDummyBankFakeDataFactory = mock(DummyBankFakeDataFactory.class);
        sut.setDummyBankFakeDataFactory(mockedDummyBankFakeDataFactory);
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
        when(mockedDummyBankFakeDataFactory.getBalanceFromAccounts(anyString())).thenReturn(new Balance());
        Assertions.assertNotNull(sut.getAccountBalances("", ""));
    }

    @Test
    void getUserAccountBalanceCallsGetBalanceFunctionFromFactory() {
        when(mockedDummyBankFakeDataFactory.getBalanceFromAccounts(anyString())).thenReturn(new Balance());
        sut.getAccountBalances("","");
        verify(mockedDummyBankFakeDataFactory).getBalanceFromAccounts(anyString());
    }
}
