package API.DTO;

import java.util.ArrayList;

public class Insight {
    private Account account;
    private ArrayList<Account> accounts;
    private ArrayList<Transaction> expectedTransactions;

    public Insight(Account account, ArrayList<Transaction> expectedTransactions) {
        this.account = account;
        this.expectedTransactions = expectedTransactions;
    }

    public Insight(ArrayList<Account> accounts, ArrayList<Transaction> expectedTransactions) {
        this.accounts = accounts;
        this.expectedTransactions = expectedTransactions;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ArrayList<Transaction> getExpectedTransactions() {
        return expectedTransactions;
    }

    public void setExpectedTransactions(ArrayList<Transaction> expectedTransactions) {
        this.expectedTransactions = expectedTransactions;
    }
}
