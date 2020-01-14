package API.Banks.Dummy;

import API.DTO.Account;
import API.DTO.Transaction;
import API.DataSource.DummyDAO;
import javax.inject.Inject;
import java.util.ArrayList;

public class DummyUtil {
    private DummyDAO dummyDAO;

    @Inject
    public void setDummyDAO(DummyDAO dummyDAO) {
        this.dummyDAO = dummyDAO;
    }

    public DummyUtil() {
        dummyDAO = new DummyDAO();
    }

    public Account getAccount(String id) {
        return dummyDAO.getAccountById(id);
    }

    public ArrayList<Account> getAccounts() {
        return dummyDAO.getAllAccounts();
    }

    public ArrayList<Transaction> getTransactions(String accountId) {
        return dummyDAO.getAllTransactions(accountId);
    }


    public Number getBalanceFromAccounts(String accountToSearch) {
        ArrayList<Account> accounts = dummyDAO.getAllAccounts();
        return accounts.stream().filter(account -> account.getId().equals(accountToSearch)).findFirst().orElse(null).getBalance().intValue();
    }
}
