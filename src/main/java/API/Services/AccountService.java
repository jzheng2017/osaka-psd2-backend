package API.Services;

import API.Adapters.BankAdapter;
import API.DTO.*;
import API.DTO.Responses.AccountsResponse;
import API.DataSource.BankTokenDao;
import API.DataSource.TransactionDAO;
import API.DataSource.UserDAO;

import java.util.ArrayList;

public class AccountService {
    private UserDAO userDAO = new UserDAO();
    private BankTokenDao bankTokenDao = new BankTokenDao();
    private TransactionDAO transactionDAO = new TransactionDAO();

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

        AccountDetails details = adapter.getAccountDetails(bankToken.getAccessToken(), id);

        if (details != null) {
            Account tempAccount = details.getAccount();
            Balance currentBalance = adapter.getAccountBalances(bankToken.getAccessToken(), id);
            tempAccount.setBalance(getBalanceFromBalances(currentBalance));
            details.setAccount(tempAccount);

            setTransactionsCategory(details.getTransactions(), user);

            return details;
        }
        return null;
    }
}
