package API.DTO;

import java.util.ArrayList;

public class Transaction {
    private String datum;
    private Account account;
    private Account debtorAccount;
    private ArrayList<Transaction> transactions;
    private String transactionType;
    private Account creditorAccount;
    private String isAfschrift;

    public Transaction(String datum, Account account, ArrayList<Transaction> transactions, String transactionType, Account creditorAccount) {
        this.datum = datum;
        this.account = account;
        this.transactions = transactions;
        this.transactionType = transactionType;
        this.creditorAccount = creditorAccount;
    }

    public Transaction(String datum, String transactionType, Account creditorAccount, Account debtorAccount,  String isAfschrift) {
        this.datum = datum;
        this.transactionType = transactionType;
        this.creditorAccount = creditorAccount;
        this.debtorAccount = debtorAccount;
        this.isAfschrift = isAfschrift;
    }

    public Transaction() {
    }

    public Account getCreditorAccount() {
        return creditorAccount;
    }

    public void setIsAfschrift(String isAfschrift) {
        this.isAfschrift = isAfschrift;
    }

    public Account getDebtorAccount() {
        return debtorAccount;
    }

    public String getIsAfschrift() {
        return isAfschrift;
    }

    public void setAfschrift(String afschrift) {
        isAfschrift = afschrift;
    }

    public void setDebtorAccount(Account debtorAccount) {
        this.debtorAccount = debtorAccount;
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

    public void setCreditorAccount(Account creditorAccount) {
        this.creditorAccount = creditorAccount;
    }
}
