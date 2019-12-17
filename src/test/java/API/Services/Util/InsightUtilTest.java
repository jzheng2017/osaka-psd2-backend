package API.Services.Util;

import API.DTO.Transaction;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class InsightUtilTest {

    private InsightUtil insightUtilUnderTest;

    @BeforeEach
    void setUp() {
        insightUtilUnderTest = new InsightUtil();
    }


//    @Test
//    void testGetRecurringExpenses() {
//        // Setup
//        final ArrayList<Transaction> allTransactions = new ArrayList<>(Arrays.asList());
//        final ArrayList<Transaction> expectedResult = new ArrayList<>(Arrays.asList());
//
//        // Run the test
//        final ArrayList<Transaction> result = insightUtilUnderTest.getRecurringExpenses(allTransactions);
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }
//
//    @Test
//    void testGetRecurringIncome() {
//        // Setup
//        final ArrayList<Transaction> allTransactions = new ArrayList<>(Arrays.asList());
//        final ArrayList<Transaction> expectedResult = new ArrayList<>(Arrays.asList());
//
//        // Run the test
//        final ArrayList<Transaction> result = insightUtilUnderTest.getRecurringIncome(allTransactions);
//
//        // Verify the results
//        assertEquals(expectedResult, result);
//    }

//    @Test
//    void testDate() {
//        String currentDate = "17/11/2019";
//
//        var result = insightUtilUnderTest.setDateToNextMonth(currentDate);
//
//        assertEquals("17-12-2019", result);
//    }
}
