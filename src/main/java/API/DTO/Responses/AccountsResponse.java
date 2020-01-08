package API.DTO.Responses;

import API.DTO.Account;

import java.util.ArrayList;

public class AccountsResponse {
    private Double balance;
    private ArrayList<Account> accounts;

    public AccountsResponse(Double balance, ArrayList<Account> accounts) {
        this.balance = balance;
        this.accounts = accounts;
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
}
