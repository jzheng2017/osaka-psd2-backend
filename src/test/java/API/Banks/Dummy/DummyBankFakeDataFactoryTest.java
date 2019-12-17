package API.Banks.Dummy;

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
    void getAccountsReturnsListOfAccounts(){
        Assertions.assertNotNull(sut.getAccounts());
    }
    @Test
    void getAccountsReturnsListOfAccountsNotEmpty(){
        Assertions.assertTrue(sut.getAccounts().size() > 0);
    }

    @Test
    void getBalanceReturnsBalance(){
        Assertions.assertNotNull(sut.getBalanceFromAccounts("1"));
    }
}
