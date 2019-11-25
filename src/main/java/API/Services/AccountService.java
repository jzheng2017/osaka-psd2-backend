package API.Services;

import API.Adapter.BankAdapter;
import API.Adapter.INGAdapter;
import API.Adapter.RaboAdapter;
import API.DTO.*;
import API.DTO.RABO.RaboAccount;
import API.DataSource.BankTokenDao;
import API.DataSource.UserDAO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class AccountService {
    private UserDAO userDAO;
    private BankTokenDao bankTokenDao;

    private INGAdapter ingAdapter = new INGAdapter();
    private RaboAdapter raboAdapter = new RaboAdapter();

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
        for(BankToken bankToken : bankTokens) {
            var adapter = new BankAdapter(bankToken.getBank());
            var tempAccounts = adapter.getUserAccounts(bankToken.getAccessToken()).getAccounts();

            for(Account account : tempAccounts) {
                var balance = getBalanceFromBalances(adapter.getAccountBalances(bankToken.getAccessToken(), account.getID()));
                account.setBalance(balance);
                accounts.add(account);
                total += balance;
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

    public Transaction getAccountDetails(String bank, String token, String id) {
        BankAdapter bankAdapter = getBankAdapter(bank);
        Balance currentBalance = bankAdapter.getAccountBalances(token, id);
        Transaction tempTransaction = bankAdapter.getAccountTransactions(token, id);
        Account tempAccount = tempTransaction.getAccount();
        tempAccount.setBalance(getBalanceFromBalances(currentBalance));
        tempTransaction.setAccount(tempAccount);
        return tempTransaction;
    }

    public AccessToken authorizeING() {
        AccessToken application = ingAdapter.authorize();
        return ingAdapter.getCustomerAuthorizationToken(application.getAccess_token());
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
}
