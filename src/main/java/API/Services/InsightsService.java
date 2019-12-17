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
    private AccountDAO accountDAO;
    private BankTokenDao bankTokenDao;
    private UserDAO userDAO;
    private AccountService accountService;
    private InsightUtil insightUtil;

    @Inject
    public void setInsightUtil(InsightUtil insightUtil) {
        this.insightUtil = insightUtil;
    }

    @Inject
    public void setBankTokenDao(BankTokenDao bankTokenDao) {
        this.bankTokenDao = bankTokenDao;
    }

    @Inject
    public void setAccountDAO(AccountDAO accountDAO) {
        this.accountDAO = accountDAO;
    }

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Inject
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    public ArrayList<Insight> getFutureIncome(String token) {
        return insightUtil.getRecurringIncome(getAllTransactions(token));
    }

    public ArrayList<Insight> getFutureExpenses(String token) {
        return insightUtil.getRecurringExpenses(getAllTransactions(token));
    }

    public ArrayList<Insight> getFutureInsights(String token) {
        var allTransactions = getAllTransactions(token);
        ArrayList<Insight> insights = new ArrayList<>();
        insights.addAll(insightUtil.getRecurringExpenses(allTransactions));
        insights.addAll(insightUtil.getRecurringIncome(allTransactions));
        //Predictions here
        return insights;
    }

    public ArrayList<Insight> getFutureIncomeForAccount(String token, String accountId, String tableId) {
        return insightUtil.getRecurringExpenses(getAllTransactionsForAccount(token, accountId,tableId));
    }

    public ArrayList<Insight> getFutureExpensesForAccount(String token, String accountId, String tableId) {
        return insightUtil.getRecurringExpenses(getAllTransactionsForAccount(token,accountId,tableId));
    }

    public ArrayList<Insight> getFutureInsightsForAccount(String token, String accountId, String tableId) {
        var allTransactions = getAllTransactionsForAccount(token, accountId, tableId);
        ArrayList<Insight> insights = new ArrayList<>();
        insights.addAll(insightUtil.getRecurringExpenses(allTransactions));
        insights.addAll(insightUtil.getRecurringIncome(allTransactions));
        //Predictions here
        return insights;
    }

    private ArrayList<Transaction> getAllTransactionsForAccount(String token, String accountId, String tableId) {
        var account = accountService.getAccountDetails(token,accountId,tableId);
        return account.getTransactions();
    }

    private ArrayList<Transaction> getAllTransactions(String token) {
        ArrayList<Transaction> allTransactions = new ArrayList<>();
        var allAccounts = accountService.getUserAccounts(token).getAccounts();
        for (Account account : allAccounts) {
            var transactionsForAccount = accountService.getAccountDetails(token, account.getId(), account.getTableId().toString());
            if (transactionsForAccount != null && !transactionsForAccount.getTransactions().isEmpty())
                allTransactions.addAll(transactionsForAccount.getTransactions());
        }
        return allTransactions;
    }
}
