package API.DTO;

import java.util.ArrayList;

public class Account {
    private String ID;
    private String iban;
    private String name;
    private String accountType;
    private String currency;
    private String linkToBalance;
    private String linkToTransactions;
    private String linkToAccount;
    private ArrayList<Account> accounts = new ArrayList<>();

    public Account() {
    }

    public Account(String ID, String iban, String name, String currency, String linkToBalance, String linkToTransactions,  String linkToAccount) {
        this.ID = ID;
        this.iban = iban;
        this.name = name;
        this.currency = currency;
        this.linkToBalance = linkToBalance;
        this.linkToTransactions = linkToTransactions;
        this.linkToAccount = linkToAccount;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public String getID() {
        return ID;
    }

    public void setID(String ID) {
        this.ID = ID;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAccountType() {
        return accountType;
    }

    public void setAccountType(String accountType) {
        this.accountType = accountType;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getLinkToBalance() {
        return linkToBalance;
    }

    public void setLinkToBalance(String linkToBalance) {
        this.linkToBalance = linkToBalance;
    }

    public String getLinkToTransactions() {
        return linkToTransactions;
    }

    public void setLinkToTransactions(String linkToTransactions) {
        this.linkToTransactions = linkToTransactions;
    }

    public String getLinkToAccount() {
        return linkToAccount;
    }

    public void setLinkToAccount(String linkToAccount) {
        this.linkToAccount = linkToAccount;
    }
}
