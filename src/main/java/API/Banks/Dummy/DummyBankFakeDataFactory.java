package API.Banks.Dummy;

import API.DTO.*;
import API.DataSource.DummyDAO;

import javax.inject.Inject;
import java.util.ArrayList;
import java.util.List;

public class DummyBankFakeDataFactory {
    private DummyDAO dummyDAO;

    @Inject
    public void setDummyDAO(DummyDAO dummyDAO) {
        this.dummyDAO = dummyDAO;
    }

    public DummyBankFakeDataFactory() {
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


    public Number getBalanceFromAccounts(String _account) {
        ArrayList<Account> accounts = dummyDAO.getAllAccounts();
        return accounts.stream().filter(account -> account.getId().equals(_account)).findFirst().orElse(null).getBalance().intValue();
    }
}
