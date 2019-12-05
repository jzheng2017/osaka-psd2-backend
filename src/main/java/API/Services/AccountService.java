package API.Services;

import API.Adapters.BankAdapter;
import API.DTO.*;
import API.DTO.Responses.AccountsResponse;
import API.DataSource.AccountDAO;
import API.DataSource.BankTokenDao;
import API.DataSource.UserDAO;

import java.util.ArrayList;

public class AccountService {
    private UserDAO userDAO = new UserDAO();
    private BankTokenDao bankTokenDao = new BankTokenDao();
    private AccountDAO accountDAO = new AccountDAO();

    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    public void setBankTokenDao(BankTokenDao bankTokenDao) {
        this.bankTokenDao = bankTokenDao;
    }



    public AccountsResponse getUserAccounts(String token) {
        var user = userDAO.getUserByToken(token);
        var bankTokens = bankTokenDao.getBankTokensForUser(user);

        ArrayList<Account> accounts = new ArrayList<>();
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
                AccountCategory accountCategory = accountDAO.getAccountCategoryByIban(user, account.getIban());
                if (accountCategory != null) {
                    account.setCategory(accountCategory.getName());
                }
                account.setTableId(bankToken.getId());
                accounts.add(account);
            }
        }
        AccountsResponse response = new AccountsResponse();
        response.setAccounts(accounts);
        response.setBalance(total);

        return response;
    }


    private double getBalanceFromBalances(Balance balance) {
        Balance tempBalance = balance.getBalances().get(0);
        return tempBalance.getBalanceAmount().getAmount();
    }

    public AccountDetails getAccountDetails(String token, String id, String tableId) {
        var user = userDAO.getUserByToken(token);
        var bankToken = bankTokenDao.getBankTokensForUser(user, tableId);
        var adapter = new BankAdapter(bankToken.getBank());

        AccountDetails details = adapter.getAccountDetails(bankToken.getAccessToken(), id);

        if (details != null) {
            Account tempAccount = details.getAccount();
            Balance currentBalance = adapter.getAccountBalances(bankToken.getAccessToken(), id);
            tempAccount.setBalance(getBalanceFromBalances(currentBalance));
            details.setAccount(tempAccount);
            return details;
        }
        return null;
    }

    public boolean assignAccountToCategory(String token, CreateAccountCategoryRequest request) {
        User user = userDAO.getUserByToken(token);
        return accountDAO.addToAccountCategory(request, user);
    }

    public boolean addNewCategory(String token, CreateAccountCategoryRequest request) {
        User user = userDAO.getUserByToken(token);
        return accountDAO.createAccountCategory(request, user);
    }

    public ArrayList<AccountCategory> getAllCategories(String token) {
        User user = userDAO.getUserByToken(token);
        if (user != null) {
            return accountDAO.getAccountCategoriesByUserId(user);
        } else {
            return null;
        }
    }
}
