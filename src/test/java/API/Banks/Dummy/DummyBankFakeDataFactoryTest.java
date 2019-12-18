package API.Banks.Dummy;

import API.DTO.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.mockito.Mockito.*;

public class DummyBankFakeDataFactoryTest {
    private DummyBankFakeDataFactory sut;

    @BeforeEach
    void setup() {
        sut = new DummyBankFakeDataFactory();
    }

    @Test
    void getAccountsReturnsListOfAccounts() {
        Assertions.assertNotNull(sut.getAccounts());
    }

    @Test
    void getAccountsReturnsListOfAccountsNotEmpty() {
        Assertions.assertTrue(sut.getAccounts().size() > 0);
    }

    @Test
    void getBalanceReturnsBalance() {
        Assertions.assertNotNull(sut.getBalanceFromAccounts("1"));
    }

    @Test
    void getTransactionsCallsAccountGetId() {
        Account account = mock(Account.class);
        when(account.getId()).thenReturn("1");
        sut.getTransactions(account);
        verify(account).getId();
    }

    @Test
    void getTransactionsReturnsListOfTransactions(){
        Account account = mock(Account.class);
        when(account.getId()).thenReturn("1");
        Assertions.assertNotNull(sut.getTransactions(account));
    }
}
