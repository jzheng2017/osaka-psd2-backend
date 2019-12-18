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
    void getAccountReturnsAccount() {
        Assertions.assertNotNull(sut.getAccount("1"));
    }

    @Test
    void getTransactionsReturnsThreeTransactionSets() {
        Account account1 = new Account("1", "NL61QHU123812391", "John Doe", "Euro", 500.50);
        Account account2 = new Account("2", "NL61QHU123815124", "Jane Doe", "Euro", 123.51);
        Account account3 = new Account("3", "NL61QHU122351491", "James Doe", "Euro", 61.68);
        Assertions.assertNotNull(sut.getTransactions(account1));
        Assertions.assertNotNull(sut.getTransactions(account2));
        Assertions.assertNotNull(sut.getTransactions(account3));
    }
}
