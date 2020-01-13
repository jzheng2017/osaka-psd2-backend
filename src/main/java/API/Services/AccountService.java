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
        var bankTokens = bankTokenDao.getBankTokensForUser(token);
        var accounts = new ArrayList<Account>();
        var total = 0.0;
        for (BankToken bankToken : bankTokens) {
            Client client = ClientFactory.getClient(bankToken.getBank());
            var tempAccounts = client.getUserAccounts(bankToken.getAccessToken());
            for (Account account : tempAccounts) {
                var balance = client.getBalance(bankToken.getAccessToken(), account.getId()).doubleValue();
                total += balance;
                account.setBalance(balance);
                account.setCategory(getAccountCategory(token, account));
                account.setTableId(bankToken.getId());
                accounts.add(account);
            }
        }
        var response = new AccountsResponse(total, accounts);

        if(response.getAccounts().isEmpty())
            return null;

        return response;
    }

    private String getAccountCategory(String token, Account account) {
        var accountCategory = accountDAO.getAccountCategoryByIban(token, account.getIban());
        if (accountCategory != null)
            return accountCategory.getName();

        return null;
    }

    private void setTransactionsCategory(ArrayList<Transaction> transactions,String token) {
        for (Transaction transaction : transactions) {
            var category = transactionDAO.getCategoryForTransaction(token, transaction);
            if (category != null) {
                transaction.setCategory(category);
            }
        }
    }

    public AccountDetails getAccountDetails(String token, String id, String tableId) {
        var bankToken = bankTokenDao.getBankTokensForUser(token, tableId);
        var client = ClientFactory.getClient(bankToken.getBank());
        var details = client.getAccountDetails(bankToken.getAccessToken(), id);
        if(details.getAccount() != null) {
            details.getAccount().setBalance(client.getBalance(bankToken.getAccessToken(), id).doubleValue());
            setTransactionsCategory(details.getTransactions(), token);
            return details;
        } else return null;
    }

    public Transaction getTransactionDetails(String token, String accountId, String transactionId, String tableId) {
        var transactions = getAccountDetails(token, accountId, tableId).getTransactions();
        if (transactions != null) {
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
        return accountDAO.createAccountCategory(request, user);
    }

    public ArrayList<AccountCategory> getAllCategories(String token) {
        return accountDAO.getAccountCategoriesByUserId(token);
    }

    public AccountsResponse getUserAccountsCategorized(String token, String categoryId) {
        var accountsResponse = getUserAccounts(token);
        var categoryName = accountDAO.getAccountCategoryById(categoryId, token);
        var accountsFiltered = new ArrayList<Account>();
        var total = 0.0;
        for (Account account : accountsResponse.getAccounts()) {
            if (account.getCategory() != null && account.getCategory().equals(categoryName)) {
                accountsFiltered.add(account);
                total += account.getBalance();
            }
        }
        accountsResponse.setAccounts(accountsFiltered);
        accountsResponse.setBalance(total);
        return accountsResponse;
    }
}
