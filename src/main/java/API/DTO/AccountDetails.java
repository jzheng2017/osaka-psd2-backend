package API.DTO;

import java.util.ArrayList;

public class AccountDetails {
    private Account account;
    private ArrayList<Transaction> transactions;

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ArrayList<Transaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<Transaction> transactions) {
        this.transactions = transactions;
    }
}
