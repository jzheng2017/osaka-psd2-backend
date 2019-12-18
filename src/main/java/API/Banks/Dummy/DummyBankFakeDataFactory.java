package API.Banks.Dummy;

import API.DTO.*;
import API.DataSource.DummyDAO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class DummyBankFakeDataFactory {
    private ArrayList<Account> accounts;
    private DummyDAO dummyDAO;

    @Inject
    public void setDummyDAO(DummyDAO dummyDAO) {
        this.dummyDAO = dummyDAO;
    }

    public DummyBankFakeDataFactory() {
        dummyDAO = new DummyDAO();
        accounts = getAccounts();
    }

    public Account getAccount(String id) {
        return dummyDAO.getAccountById(id);
    }

    public ArrayList<Account> getAccounts() {
        ArrayList<Account> accounts = dummyDAO.getAllAccounts();
        this.accounts = accounts;
        return accounts;
    }

    public ArrayList<Transaction> getTransactions(String accountId) {
        return dummyDAO.getAllTransactions(accountId);
    }


    public Number getBalanceFromAccounts(String _account) {
        return accounts.stream().filter(account -> account.getId().equals(_account)).findFirst().orElse(null).getBalance().intValue();
    }
}
