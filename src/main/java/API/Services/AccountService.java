package API.Services;

import API.Adapter.BankAdapter;
import API.Adapter.INGAdapter;
import API.Adapter.RaboAdapter;
import API.DTO.*;
import API.DataSource.BankTokenDao;
import API.DataSource.UserDAO;

import javax.inject.Inject;
import java.util.ArrayList;

public class AccountService {
    private UserDAO userDAO;
    private BankTokenDao bankTokenDao;
    private INGAdapter ingAdapter;
    private RaboAdapter raboAdapter;

    @Inject
    public void setIngAdapter(INGAdapter ingAdapter) {
        this.ingAdapter = ingAdapter;
    }

    @Inject
    public void setRaboAdapter(RaboAdapter raboAdapter) {
        this.raboAdapter = raboAdapter;
    }

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Inject
    public void setBankTokenDao(BankTokenDao bankTokenDao) {
        this.bankTokenDao = bankTokenDao;
    }

    public Account getUserAccounts(String token) {
        var user = userDAO.getUserByToken(token);
        var bankTokens = bankTokenDao.getBankTokensForUser(user);

        ArrayList<Account> accounts = new ArrayList<>();
        float total = 0;
        for (BankToken bankToken : bankTokens) {
            var adapter = new BankAdapter(bankToken.getBank());
            var tempAccounts = adapter.getUserAccounts(bankToken.getAccessToken()).getAccounts();

            for (Account account : tempAccounts) {
                var accountBalance = adapter.getAccountBalances(bankToken.getAccessToken(), account.getID());
                if (accountBalance != null) {
                    var balance = getBalanceFromBalances(accountBalance);
                    total += balance;
                    account.setBalance(balance);
                }
                account.setTableID(bankToken.getId());
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

    public Transaction getAccountDetails(String token, String id, String tableID) {
        var user = userDAO.getUserByToken(token);
        var bankToken = bankTokenDao.getBankTokensForUser(user, tableID);
        var adapter = new BankAdapter(bankToken.getBank());

        Transaction tempTransaction = adapter.getAccountTransactions(bankToken.getAccessToken(), id);
        if (tempTransaction != null) {
            Account tempAccount = tempTransaction.getAccount();
            Balance currentBalance = adapter.getAccountBalances(bankToken.getAccessToken(), id);
            tempAccount.setBalance(getBalanceFromBalances(currentBalance));
            tempTransaction.setAccount(tempAccount);
            return tempTransaction;
        } else return null;
    }

    public BankToken authorizeING() {
        BankToken application = ingAdapter.authorize();
        return ingAdapter.getCustomerAuthorizationToken(application.getAccessToken());
    }

    public String authorizeRABO() {
        return raboAdapter.authorize();
    }

    public BankToken token(String bank, String code) {
        BankAdapter bankAdapter = getBankAdapter(bank);
        return bankAdapter.token(code);
    }

    public BankToken refresh(String bank, String code) {
        BankAdapter bankAdapter = getBankAdapter(bank);
        return bankAdapter.refresh(code);
    }

    private BankAdapter getBankAdapter(String bank) {
        return new BankAdapter(bank.equals("rabo") ? Bank.RABOBANK : Bank.ING);
    }

    public String checkEnoughBalance(String bank, String token) {
        return null;
    }
}
