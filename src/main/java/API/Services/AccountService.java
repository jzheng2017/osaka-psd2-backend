package API.Services;

import API.Adapters.BankAdapter;
import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.BankToken;
import API.DTO.Transaction;
import API.DataSource.BankTokenDao;
import API.DataSource.UserDAO;
import java.util.ArrayList;

public class AccountService {
    private UserDAO userDAO = new UserDAO();
    private BankTokenDao bankTokenDao = new BankTokenDao();


    public Account getUserAccounts(String token) {
        var user = userDAO.getUserByToken(token);
        var bankTokens = bankTokenDao.getBankTokensForUser(user);

        ArrayList<Account> accounts = new ArrayList<>();
        float total = 0;
        for (BankToken bankToken : bankTokens) {
            var adapter = new BankAdapter(bankToken.getBank());
            var tempAccounts = adapter.getUserAccounts(bankToken.getAccessToken()).getAccounts();

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
        Account accountToReturn = new Account();
        accountToReturn.setAccounts(accounts);
        accountToReturn.setBalance(total);
        return accountToReturn;
    }


    private float getBalanceFromBalances(Balance balance) {
        Balance tempBalance = balance.getBalances().get(0);
        return tempBalance.getBalanceAmount().getAmount();
    }

    public Transaction getAccountDetails(String token, String id, String tableId) {
        var user = userDAO.getUserByToken(token);
        var bankToken = bankTokenDao.getBankTokensForUser(user, tableId);
        var adapter = new BankAdapter(bankToken.getBank());

        Transaction tempTransaction = adapter.getAccountTransactions(bankToken.getAccessToken(), id);

        if (tempTransaction != null) {
            Account tempAccount = tempTransaction.getAccount();
            Balance currentBalance = adapter.getAccountBalances(bankToken.getAccessToken(), id);
            tempAccount.setBalance(getBalanceFromBalances(currentBalance));
            tempTransaction.setAccount(tempAccount);
            return tempTransaction;
        }
        return null;
    }
}
