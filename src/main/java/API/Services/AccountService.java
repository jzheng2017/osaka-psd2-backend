package API.Services;

import API.Adapters.BankAdapter;
import API.DTO.*;
import API.DTO.Responses.AccountsResponse;
import API.DataSource.AccountDAO;
import API.DataSource.BankTokenDao;
import API.DataSource.TransactionDAO;
import API.DataSource.UserDAO;
import javax.inject.Inject;
import java.util.ArrayList;

public class AccountService {
    private UserDAO userDAO;
    private BankTokenDao bankTokenDao;
    private AccountDAO accountDAO;
    private TransactionDAO transactionDAO;

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
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
    public void setTransactionDAO(TransactionDAO transactionDAO) {
        this.transactionDAO = transactionDAO;
    }

    public AccountsResponse getUserAccounts(String token) {
        var user = userDAO.getUserByToken(token);
        var bankTokens = bankTokenDao.getBankTokensForUser(user);

        var accounts = new ArrayList<Account>();
        double total = 0;
        for (BankToken bankToken : bankTokens) {
            var adapter = new BankAdapter(bankToken.getBank());
            var tempAccounts = adapter.getUserAccounts(bankToken.getAccessToken());

            for (Account account : tempAccounts) {
                var accountBalance = adapter.getAccountBalances(bankToken.getAccessToken(), account.getId());
                if (accountBalance != null) {
                    var balance = getBalanceFromBalances(accountBalance);
                    total += balance;
                    account.setBalance(balance);
                }

                var accountCategory = accountDAO.getAccountCategoryByIban(user, account.getIban());
                if (accountCategory != null) {
                    account.setCategory(accountCategory.getName());
                }
                account.setTableId(bankToken.getId());
                accounts.add(account);
            }
        }

        var response = new AccountsResponse();
        response.setAccounts(accounts);
        response.setBalance(total);

        return response;
    }


    private double getBalanceFromBalances(Balance balance) {
        var tempBalance = balance.getBalances().get(0);
        return tempBalance.getBalanceAmount().getAmount();
    }

    private void setTransactionsCategory(ArrayList<Transaction> transactions, User user) {
        for(Transaction transaction : transactions) {
            var category = transactionDAO.getCategoryForTransaction(user, transaction);

            if(category != null) {
                transaction.setCategory(category);
            }
        }
    }

    public AccountDetails getAccountDetails(String token, String id, String tableId) {
        var user = userDAO.getUserByToken(token);
        var bankToken = bankTokenDao.getBankTokensForUser(user, tableId);
        var adapter = new BankAdapter(bankToken.getBank());

        var details = adapter.getAccountDetails(bankToken.getAccessToken(), id);

        if (details != null) {
            var tempAccount = details.getAccount();
            var currentBalance = adapter.getAccountBalances(bankToken.getAccessToken(), id);
            tempAccount.setBalance(getBalanceFromBalances(currentBalance));
            details.setAccount(tempAccount);

            setTransactionsCategory(details.getTransactions(), user);

            return details;
        }
        return null;
    }

    public AccountCategory assignAccountToCategory(String token, CreateAccountCategoryRequest request) {
        var user = userDAO.getUserByToken(token);
        return accountDAO.addToAccountCategory(request, user);
    }

    public AccountCategory addNewCategory(String token, CreateAccountCategoryRequest request) {
        var user = userDAO.getUserByToken(token);
        return  accountDAO.createAccountCategory(request, user);
    }

    public ArrayList<AccountCategory> getAllCategories(String token) {
        var user = userDAO.getUserByToken(token);

        if (user != null)
            return accountDAO.getAccountCategoriesByUserId(user);

        return null;
    }
}
