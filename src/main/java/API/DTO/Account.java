package API.DTO;

import java.util.ArrayList;

public class Account {
    private String id;
    private String iban;
    private int tableId;
    private String name;
    private String accountType;
    private String currency;
    private Bank bank;
    private float balance;
    private ArrayList<Account> accounts;

    public Account() {
    }

    public Account(String Id, String iban, String name, String currency, Bank bank) {
        this.id = Id;
        this.iban = iban;
        this.name = name;
        this.currency = currency;
        this.bank = bank;
    }

    public Account(String Id, String iban, String name, String accountType, String currency, ArrayList<Account> accounts, float balance) {
        this.id = Id;
        this.iban = iban;
        this.name = name;
        this.accountType = accountType;
        this.currency = currency;
        this.accounts = accounts;
        this.balance = balance;
    }

    public int getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public float getBalance() {
        return balance;
    }

    public void setBalance(float balance) {
        this.balance = balance;
    }

    public Bank getBank() {
        return bank;
    }

    public void setBank(Bank bank) {
        this.bank = bank;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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
