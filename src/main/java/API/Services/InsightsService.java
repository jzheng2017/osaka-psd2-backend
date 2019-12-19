package API.Services;

import API.DTO.Account;
import API.DTO.Insight;
import API.DTO.Transaction;
import API.Services.Util.InsightUtil;

import javax.inject.Inject;
import java.util.ArrayList;

public class InsightsService {
    private AccountService accountService;
    private InsightUtil insightUtil;

    @Inject
    public void setInsightUtil(InsightUtil insightUtil) {
        this.insightUtil = insightUtil;
    }

    @Inject
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public Insight getFutureIncome(String token) {
        var allAccounts = accountService.getUserAccounts(token).getAccounts();
        Insight insight = new Insight(allAccounts);
        ArrayList<Transaction> transactions = getAllTransactions(allAccounts, token);
        ArrayList<Transaction> recurringIncome = insightUtil.getRecurringIncome(transactions);
        transactions.removeAll(recurringIncome);
        recurringIncome.add(insightUtil.getAverageIncome(transactions));
        insight.setExpectedIncome(recurringIncome);
        return insight;
    }

    public Insight getFutureExpenses(String token) {
        var allAccounts = accountService.getUserAccounts(token).getAccounts();
        Insight insight = new Insight(allAccounts);
        ArrayList<Transaction> transactions = getAllTransactions(allAccounts, token);
        ArrayList<Transaction> recurringExpenses = insightUtil.getRecurringExpenses(transactions);
        transactions.removeAll(recurringExpenses);
        recurringExpenses.add(insightUtil.getAverageExpenses(transactions));
        insight.setExpectedExpenses(recurringExpenses);
        return insight;
    }

    public Insight getFutureInsights(String token) {
        var allAccounts = accountService.getUserAccounts(token).getAccounts();
        var allTransactions = getAllTransactions(allAccounts, token);
        ArrayList<Transaction> recurringExpenses = insightUtil.getRecurringExpenses(allTransactions);
        ArrayList<Transaction> recurringIncome = insightUtil.getRecurringIncome(allTransactions);
        allTransactions.removeAll(recurringExpenses);
        allTransactions.removeAll(recurringIncome);
        recurringExpenses.add(insightUtil.getAverageExpenses(allTransactions));
        recurringIncome.add(insightUtil.getAverageIncome(allTransactions));
        return new Insight(
                allAccounts,
                recurringExpenses,
                recurringIncome,
                insightUtil.getTotalAverage(recurringIncome),
                insightUtil.getTotalAverage(recurringExpenses)
        );
    }

    public Insight getFutureIncomeForAccount(String token, String accountId, String tableId) {
        var accountDetails = accountService.getAccountDetails(token, accountId, tableId);
        Insight insight = new Insight(accountDetails.getAccount());
        ArrayList<Transaction> transactions = accountDetails.getTransactions();
        ArrayList<Transaction> insights = new ArrayList<>(insightUtil.getRecurringIncome(transactions));
        transactions.removeAll(insights);
        insights.add(insightUtil.getAverageIncome(transactions));
        insight.setExpectedIncome(insights);
        return insight;
    }

    public Insight getFutureExpensesForAccount(String token, String accountId, String tableId) {
        var accountDetails = accountService.getAccountDetails(token, accountId, tableId);
        Insight insight = new Insight(accountDetails.getAccount());
        ArrayList<Transaction> transactions = accountDetails.getTransactions();
        ArrayList<Transaction> insights = new ArrayList<>(insightUtil.getRecurringExpenses(transactions));
        transactions.removeAll(insights);
        insights.add(insightUtil.getAverageExpenses(transactions));
        insight.setExpectedExpenses(insights);
        return insight;
    }

    public Insight getFutureInsightsForAccount(String token, String accountId, String tableId) {
        var accountDetails = accountService.getAccountDetails(token, accountId, tableId);
        var allTransactions = accountDetails.getTransactions();
        ArrayList<Transaction> insights = new ArrayList<>();
        insights.addAll(insightUtil.getRecurringExpenses(allTransactions));
        insights.addAll(insightUtil.getRecurringIncome(allTransactions));
        allTransactions.removeAll(insights);
        insights.add(insightUtil.getAverageIncome(allTransactions));
        insights.add(insightUtil.getAverageExpenses(allTransactions));
        return new Insight(accountDetails.getAccount(), insights);
    }

    private ArrayList<Transaction> getAllTransactions(ArrayList<Account> accounts, String token) {
        ArrayList<Transaction> allTransactions = new ArrayList<>();
        for (Account account : accounts) {
            var transactionsForAccount = accountService.getAccountDetails(token, account.getId(), account.getTableId().toString());
            if (transactionsForAccount != null && !transactionsForAccount.getTransactions().isEmpty())
                allTransactions.addAll(transactionsForAccount.getTransactions());
        }
        return allTransactions;
    }
}
