package API.DTO;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;

public class Account {
    @SerializedName("resourceId")
    private String id;
    private String iban;
    private String name;
    private String currency;
    private Double balance;
    private ArrayList<Account> accounts;
    private Integer tableId;

    public Account() {
    }

    public Account(String id, String iban, String name, String currency, ArrayList<Account> accounts, Double balance) {
        this.id = id;
        this.iban = iban;
        this.name = name;
        this.currency = currency;
        this.accounts = accounts;
        this.balance = balance;
    }

    public Integer getTableId() {
        return tableId;
    }

    public void setTableId(int tableId) {
        this.tableId = tableId;
    }

    public Double getBalance() {
        return balance;
    }

    public void setBalance(Double balance) {
        this.balance = balance;
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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

}
