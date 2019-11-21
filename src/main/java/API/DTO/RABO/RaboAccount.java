package API.DTO.RABO;

import java.util.ArrayList;

public class RaboAccount {
    private int bban;
    private String currency;
    private String iban;
    private String name;
    private String resourceId;
    private String status;
    private ArrayList<RaboAccount> accounts;

    public RaboAccount() {
    }

    public RaboAccount(int bban, String currency, String iban, String name, String resourceId, String status) {
        this.bban = bban;
        this.currency = currency;
        this.iban = iban;
        this.name = name;
        this.resourceId = resourceId;
        this.status = status;
    }

    public int getBban() {
        return bban;
    }

    public void setBban(int bban) {
        this.bban = bban;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
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

    public String getResourceId() {
        return resourceId;

    }

    public ArrayList<RaboAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<RaboAccount> accounts) {
        this.accounts = accounts;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
