package API.Banks.Dummy;

import API.DTO.Account;
import API.DataSource.DummyDAO;
import API.DTO.Account;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.mockito.Mockito.*;

public class DummyBankFakeDataFactoryTest {
    private DummyBankFakeDataFactory sut;
    private DummyDAO dummyDAO;

    @BeforeEach
    void setup() {
        sut = new DummyBankFakeDataFactory();
        dummyDAO = mock(DummyDAO.class);
        sut.setDummyDAO(dummyDAO);
        when(dummyDAO.getAccountById(anyString())).thenReturn(new Account());
        when(dummyDAO.getAllAccounts()).thenReturn(new ArrayList<>());
        when(dummyDAO.getAllTransactions(anyString())).thenReturn(new ArrayList<>());
    }

    @Test
    void getAccountsReturnsListOfAccounts() {
        Assertions.assertNotNull(sut.getAccounts());
    }

    @Test
    void getBalanceReturnsBalance() {
        Assertions.assertNotNull(sut.getBalanceFromAccounts("1"));
    }

    @Test
    void getAccountReturnsAccount() {
        Assertions.assertNotNull(sut.getAccount("1"));
    }

    @Test
    void getTransactionsReturnsThreeTransactionSets() {
        Assertions.assertNotNull(sut.getTransactions("1"));
    }

    @Test
    void getTransactionsReturnsListOfTransactions(){
        Account account = mock(Account.class);
        when(account.getId()).thenReturn("1");
        Assertions.assertNotNull(sut.getTransactions("1"));
    }
}
