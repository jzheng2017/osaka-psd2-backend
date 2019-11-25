package API.DTO;

import java.util.ArrayList;

public class Account {
    private String ID;
    private String iban;
    private String name;
    private String accountType;
    private String currency;
    private String bank;
    private ArrayList<Account> accounts;

    public Account() {
    }

    public Account(String ID, String iban, String name, String currency, String bank) {
        this.ID = ID;
        this.iban = iban;
        this.name = name;
        this.currency = currency;
        this.bank = bank;
    }

    public Account(String ID, String iban, String name, String accountType, String currency, ArrayList<Account> accounts) {
        this.ID = ID;
        this.iban = iban;
        this.name = name;
        this.accountType = accountType;
        this.currency = currency;
        this.accounts = accounts;
    }

    public String getBank() {
        return bank;
    }

    public void setBank(String bank) {
        this.bank = bank;
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

}
