package API.Services.Util;

import API.DTO.Account;
import API.DTO.Transaction;
import static API.DTO.TransactionTypes.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InsightUtilTest {

    private InsightUtil insightUtilUnderTest;
    private ArrayList<Transaction> allTransactions;

    @BeforeEach
    void setUp() {
        allTransactions = generateTransactionsArray();
        insightUtilUnderTest = new InsightUtil();
    }

    private ArrayList<Transaction> generateTransactionsArray() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("10-10-2000", INCASSO, new Account(), new Account(), false, "250","1"));
        transactions.add(new Transaction("10-10-2000", INCOME, new Account(), new Account(), true, "500","1"));
        transactions.add(new Transaction("10-10-2000", TAX, new Account(), new Account(), false, "250","1"));
        return transactions;
    }


    @Test
    void testGetRecurringExpenses() {
        // Setup
        final String expectedResult = "250";

        // Run the test
        final ArrayList<Transaction> result = insightUtilUnderTest.getRecurringExpenses(allTransactions);

        // Verify the results
        assertEquals(expectedResult, result.get(0).getAmount());
    }

    @Test
    void testGetRecurringIncome() {
        // Setup
        final String expectedResult = "500";
        // Run the test
        final ArrayList<Transaction> result = insightUtilUnderTest.getRecurringIncome(allTransactions);

        // Verify the results
        assertEquals(expectedResult, result.get(0).getAmount());
    }
}