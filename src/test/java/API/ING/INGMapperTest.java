package API.ING;

import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.ING.INGAccount;
import API.DTO.ING.INGBalance;
import API.DTO.ING.INGTransaction;
import API.DTO.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class INGMapperTest {

    private INGMapper ingMapperUnderTest;

    @BeforeEach
    void setUp() {
        ingMapperUnderTest = new INGMapper();
    }

    @Test
    void testMapToAccount() {
        // Setup
        final INGAccount ingAccount = new INGAccount("resourceId", "product", "iban", "name", "currency", new ArrayList<>(Arrays.asList()));
        final Account expectedResult = new Account("ID", "iban", "name", "accountType", "currency", new ArrayList<>(Arrays.asList()));

        // Run the test
        final Account result = ingMapperUnderTest.mapToAccount(ingAccount);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testMapToBalance() {
        // Setup
        final INGBalance ingBalance = new INGBalance(null, new ArrayList<>(Arrays.asList()), "balanceType", null, "lastChangeDateTime", "referenceDate");
        final Balance expectedResult = new Balance();

        // Run the test
        final Balance result = ingMapperUnderTest.mapToBalance(ingBalance);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testMapToTransaction() {
        // Setup
        final INGTransaction ingTransaction = new INGTransaction(new ArrayList<>(Arrays.asList()), new ArrayList<>(Arrays.asList()), null, null);
        final Transaction expectedResult = new Transaction();

        // Run the test
        final Transaction result = ingMapperUnderTest.mapToTransaction(ingTransaction);

        // Verify the results
        assertEquals(expectedResult, result);
    }

}
