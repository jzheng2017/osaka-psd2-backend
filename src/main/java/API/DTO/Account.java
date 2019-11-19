package API.DTO;

import java.util.ArrayList;

public class Account {
    private String ID;
    private String iban;
    private String name;
    private String accountType;
    private String currency;
    private String maskedPan; //geen idee wat dit is
    private String linkToBalance;
    private String linkToTransactions;
    private ArrayList<Account> accounts = new ArrayList<>();

    public Account() {
    }

    public Account(String ID, String iban, String name, String accountType, String currency, String maskedPan, String linkToBalance, String linkToTransactions, ArrayList<Account> accounts) {
        this.ID = ID;
        this.iban = iban;
        this.name = name;
        this.accountType = accountType;
        this.currency = currency;
        this.maskedPan = maskedPan;
        this.linkToBalance = linkToBalance;
        this.linkToTransactions = linkToTransactions;
        this.accounts = accounts;
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

    public String getMaskedPan() {
        return maskedPan;
    }

    public void setMaskedPan(String maskedPan) {
        this.maskedPan = maskedPan;
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
}
