package API.Services;

import API.DTO.Account;
import API.DTO.Insight;
import API.DTO.Transaction;
import API.Services.Util.InsightUtil;

import javax.inject.Inject;
import javax.persistence.criteria.CriteriaBuilder;
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
        insight.setExpectedIncome(insightUtil.getRecurringIncome(getAllTransactions(allAccounts, token)));
        return insight;    }

    public Insight getFutureExpenses(String token) {
        var allAccounts = accountService.getUserAccounts(token).getAccounts();
        Insight insight = new Insight(allAccounts);
        insight.setExpectedExpenses(insightUtil.getRecurringExpenses(getAllTransactions(allAccounts, token)));
        return insight;
    }

    public Insight getFutureInsights(String token) {
        var allAccounts = accountService.getUserAccounts(token).getAccounts();
        var allTransactions = getAllTransactions(allAccounts, token);
        //Predictions here
        return new Insight(allAccounts, insightUtil.getRecurringExpenses(allTransactions),insightUtil.getRecurringIncome(allTransactions));
    }

    public Insight getFutureIncomeForAccount(String token, String accountId, String tableId) {
        var accountDetails = accountService.getAccountDetails(token, accountId, tableId);
        Insight insight = new Insight(accountDetails.getAccount());
        insight.setExpectedIncome(insightUtil.getRecurringIncome(accountDetails.getTransactions()));
        return insight;
    }

    public Insight getFutureExpensesForAccount(String token, String accountId, String tableId) {
        var accountDetails = accountService.getAccountDetails(token, accountId, tableId);
        Insight insight = new Insight(accountDetails.getAccount());
        insight.setExpectedExpenses(insightUtil.getRecurringExpenses(accountDetails.getTransactions()));
        return insight;
    }

    public Insight getFutureInsightsForAccount(String token, String accountId, String tableId) {
        var accountDetails = accountService.getAccountDetails(token, accountId, tableId);
        var allTransactions = accountDetails.getTransactions();
        ArrayList<Transaction> insights = new ArrayList<>();
        insights.addAll(insightUtil.getRecurringExpenses(allTransactions));
        insights.addAll(insightUtil.getRecurringIncome(allTransactions));
        //Predictions here
        Insight insight = new Insight(accountDetails.getAccount());
        insight.setMixedExpected(insights);
        return insight;
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
