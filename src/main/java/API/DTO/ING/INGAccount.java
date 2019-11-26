package API.DTO.ING;

import java.util.ArrayList;

public class INGAccount {
    private String resourceId;
    private String product;
    private String iban;
    private String name;
    private String currency;
    private ArrayList<INGAccount> accounts;

    public INGAccount(String resourceId, String product, String iban, String name, String currency, ArrayList<INGAccount> accounts) {
        this.resourceId = resourceId;
        this.product = product;
        this.iban = iban;
        this.name = name;
        this.currency = currency;
        this.accounts = accounts;
    }

    public INGAccount() {
    }

    public String getResourceId() {
        return resourceId;
    }

    public void setResourceId(String resourceId) {
        this.resourceId = resourceId;
    }

    public String getProduct() {
        return product;
    }

    public void setProduct(String product) {
        this.product = product;
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

    public ArrayList<INGAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<INGAccount> accounts) {
        this.accounts = accounts;
    }
}
