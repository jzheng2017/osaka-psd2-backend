package API.DTO;

import java.util.ArrayList;

public class Transaction {
    private String datum;
    private Account account;
    private ArrayList<Transaction> transactions;
    private String transactionType;
    private Account creditorAccount;

    public Transaction(String datum, Account account, ArrayList<Transaction> transactions, String transactionType, Account creditorAccount) {
        this.datum = datum;
        this.account = account;
        this.transactions = transactions;
        this.transactionType = transactionType;
        this.creditorAccount = creditorAccount;
    }

    public Transaction() {
    }

    public String getDatum() {
        return datum;
    }

    public void setDatum(String datum) {
        this.datum = datum;
    }

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

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public Account getCreditorAccount() {
        return creditorAccount;
    }

    public void setCreditorAccount(Account creditorAccount) {
        this.creditorAccount = creditorAccount;
    }
}
