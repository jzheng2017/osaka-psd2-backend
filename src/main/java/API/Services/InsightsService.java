package API.Services;

import API.DTO.*;
import API.DataSource.AccountDAO;
import API.DataSource.BankTokenDao;
import API.DataSource.UserDAO;
import API.Services.Util.InsightUtil;

import javax.inject.Inject;
import java.lang.reflect.Array;
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
        return new Insight(allAccounts, insightUtil.getRecurringIncome(getAllTransactions(allAccounts, token)));
    }

    public Insight getFutureExpenses(String token) {
        var allAccounts = accountService.getUserAccounts(token).getAccounts();
        return new Insight(allAccounts, insightUtil.getRecurringExpenses(getAllTransactions(allAccounts, token)));
    }

    public Insight getFutureInsights(String token) {
        var allAccounts = accountService.getUserAccounts(token).getAccounts();
        var allTransactions = getAllTransactions(allAccounts, token);
        ArrayList<Transaction> insights = new ArrayList<>();
        insights.addAll(insightUtil.getRecurringExpenses(allTransactions));
        insights.addAll(insightUtil.getRecurringIncome(allTransactions));
        //Predictions here
        return new Insight(allAccounts, insights);
    }

    public Insight getFutureIncomeForAccount(String token, String accountId, String tableId) {
        var accountDetails = accountService.getAccountDetails(token, accountId, tableId);
        return new Insight(accountDetails.getAccount(), insightUtil.getRecurringIncome(accountDetails.getTransactions()));
    }

    public Insight getFutureExpensesForAccount(String token, String accountId, String tableId) {
        var accountDetails = accountService.getAccountDetails(token, accountId, tableId);
        return new Insight(accountDetails.getAccount(), insightUtil.getRecurringExpenses(accountDetails.getTransactions()));
    }

    public Insight getFutureInsightsForAccount(String token, String accountId, String tableId) {
        var accountDetails = accountService.getAccountDetails(token, accountId, tableId);
        var allTransactions = accountDetails.getTransactions();
        ArrayList<Transaction> insights = new ArrayList<>();
        insights.addAll(insightUtil.getRecurringExpenses(allTransactions));
        insights.addAll(insightUtil.getRecurringIncome(allTransactions));
        //Predictions here
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
