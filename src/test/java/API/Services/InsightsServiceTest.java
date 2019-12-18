package API.Services;

import API.DTO.*;
import API.DTO.Responses.AccountsResponse;
import API.Services.Util.InsightUtil;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.mockito.Mockito;

import java.util.ArrayList;
import java.util.Arrays;

import static API.DTO.TransactionTypes.INCASSO;
import static API.DTO.TransactionTypes.INCOME;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.when;

class InsightsServiceTest {

    private InsightsService insightsServiceUnderTest;
    private AccountService accountService;
    private InsightUtil insightUtil;
    private ArrayList<Transaction> allTransactions;
    private ArrayList<Account> accounts;
    private final String expectedResultIncome = "250";
    private final String expectedResult = "500";
    private final int incomeId = 0;
    private final int expenseId = 1;

    @BeforeEach
    void setUp() {
        allTransactions = generateTransactionsArray();
        accounts = generateAccounts();
        AccountsResponse accountsResponse = new AccountsResponse();
        AccountDetails accountDetails = new AccountDetails();
        accountDetails.setTransactions(allTransactions);
        accountsResponse.setAccounts(accounts);
        insightsServiceUnderTest = new InsightsService();
        insightUtil = Mockito.mock(InsightUtil.class);
        insightsServiceUnderTest.setInsightUtil(insightUtil);
        accountService = Mockito.mock(AccountService.class);
        insightsServiceUnderTest.setAccountService(accountService);
        when(accountService.getUserAccounts(anyString())).thenReturn(accountsResponse);
        when(accountService.getAccountDetails(anyString(), anyString(), anyString())).thenReturn(accountDetails);
        when(insightUtil.getRecurringIncome(any())).thenReturn(allTransactions);
        when(insightUtil.getRecurringExpenses(any())).thenReturn(allTransactions);
    }

    private ArrayList<Account> generateAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();
        Account account = new Account("1", "NL61QHU123812391", "John Doe", "Euro", 500.50);
        account.setTableId(10);
        accounts.add(account);
        return accounts;
    }

    private ArrayList<Transaction> generateTransactionsArray() {
        ArrayList<Transaction> transactions = new ArrayList<>();
        transactions.add(new Transaction("10-10-2000", INCASSO, new Account(), new Account(), true, "250", "1"));
        transactions.add(new Transaction("10-10-2000", INCOME, new Account(), new Account(), false, "500","1"));
        return transactions;
    }

    @Test
    void testGetFutureIncome() {
        // Setup
        // Run the test
        final Insight result = insightsServiceUnderTest.getFutureIncome("token");

        // Verify the results
        assertEquals(expectedResultIncome, result.getExpectedIncome().get(incomeId).getAmount());
    }

    @Test
    void testGetFutureExpenses() {
        // Setup
        // Run the test
        final Insight result = insightsServiceUnderTest.getFutureExpenses("token");

        // Verify the results
        assertEquals(expectedResult, result.getExpectedExpenses().get(expenseId).getAmount());
    }

    @Test
    void testGetFutureInsights() {
        // Setup
        // Run the test
        final Insight result = insightsServiceUnderTest.getFutureInsights("token");

        // Verify the results
        assertEquals(expectedResult, result.getExpectedExpenses().get(expenseId).getAmount());
        assertEquals(expectedResultIncome, result.getExpectedIncome().get(incomeId).getAmount());
    }

    @Test
    void testGetFutureIncomeForAccount() {
        // Setup
        // Run the test
        final Insight result = insightsServiceUnderTest.getFutureIncomeForAccount("token", "accountId", "tableId");

        // Verify the results
        assertEquals(expectedResultIncome, result.getExpectedIncome().get(incomeId).getAmount());
    }

    @Test
    void testGetFutureExpensesForAccount() {
        // Setup
        // Run the test
        final Insight result = insightsServiceUnderTest.getFutureExpensesForAccount("token", "accountId", "tableId");

        // Verify the results
        assertEquals(expectedResult, result.getExpectedExpenses().get(expenseId).getAmount());
    }

    @Test
    void testGetFutureInsightsForAccount() {
        // Setup
        // Run the test
        final Insight result = insightsServiceUnderTest.getFutureInsightsForAccount("token", "accountId", "tableId");

        // Verify the results
        assertEquals(expectedResult, result.getMixedExpected().get(expenseId).getAmount());
        assertEquals(expectedResultIncome, result.getMixedExpected().get(incomeId).getAmount());    }
}
