package API.Services;

import API.Banks.Client;
import API.Banks.ClientFactory;
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
            Client client = ClientFactory.getClient(bankToken.getBank());
            var tempAccounts = client.getUserAccounts(bankToken.getAccessToken());
            for (Account account : tempAccounts) {
                var accountBalance = client.getAccountBalances(bankToken.getAccessToken(), account.getId());
                total = getTotal(total, account, accountBalance);
                account.setCategory(getAccountCategory(user,account));
                getAccountCategory(user, account);
                account.setTableId(bankToken.getId());
                accounts.add(account);
            }
        }
        var response = new AccountsResponse();
        response.setAccounts(accounts);
        response.setBalance(total);
        return response;
    }

    private double getTotal(double total, Account account, Balance accountBalance) {
        if (accountBalance != null) {
            var balance = getBalanceFromBalances(accountBalance);
            total += balance;
            account.setBalance(balance);
        }
        return total;
    }


    private String getAccountCategory(User user, Account account) {
        var accountCategory = accountDAO.getAccountCategoryByIban(user, account.getIban());
        if (accountCategory != null) {
           return accountCategory.getName();
        } else return null;
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
        var client = ClientFactory.getClient(bankToken.getBank());

        var details = client.getAccountDetails(bankToken.getAccessToken(), id);

        if (details != null) {
            var tempAccount = details.getAccount();
            var currentBalance = client.getAccountBalances(bankToken.getAccessToken(), id);
            tempAccount.setBalance(getBalanceFromBalances(currentBalance));
            details.setAccount(tempAccount);

            setTransactionsCategory(details.getTransactions(), user);

            return details;
        }
        return null;
    }

    public Transaction getTransactionDetails(String token, String accountId, String transactionId, String tableId) {
        var transactions = getAccountDetails(token, accountId, tableId).getTransactions();
        if(transactions != null) {
            for (Transaction t : transactions) {
                if (t.getId().equals(transactionId)) {
                    return t;
                }
            }
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

        return new ArrayList<>();
    }

    public AccountsResponse getUserAccountsCategorized(String token, String categoryId) {
        var user = userDAO.getUserByToken(token);
        var accountsResponse = getUserAccounts(token);
        var accountsUnfiltered = accountsResponse.getAccounts();
        var categoryName = accountDAO.getAccountCategoryById(categoryId, user.getId());
        var accountsFiltered = new ArrayList<Account>();
        Double total = 0.0;
        for (Account account:accountsUnfiltered) {
            if(account.getCategory() != null && account.getCategory().equals(categoryName)) {
                accountsFiltered.add(account);
                total+=account.getBalance();
            }
        }
        accountsResponse.setAccounts(accountsFiltered);
        accountsResponse.setBalance(total);
        return accountsResponse;
    }
}
