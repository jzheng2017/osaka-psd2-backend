package API.DTO.RABO;

import API.DTO.Links;

import javax.ws.rs.core.Link;
import java.util.ArrayList;

public class RaboAccount {
    private Links links;
    private String currency;
    private String iban;
    private String name;
    private String resourceID;
    private String status;
    private ArrayList<RaboAccount> accounts = new ArrayList<>();

    public RaboAccount() {
    }

    public RaboAccount(Links links, String currency, String iban, String name, String resourceID, String status) {
        this.links = links;
        this.currency = currency;
        this.iban = iban;
        this.name = name;
        this.resourceID = resourceID;
        this.status = status;
    }

    public Links getLinks() {
        return links;
    }

    public void setLinks(Links links) {
        this.links = links;
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

    public String getResourceID() {
        return resourceID;

    }

    public ArrayList<RaboAccount> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<RaboAccount> accounts) {
        this.accounts = accounts;
    }

    public void setResourceID(String resourceID) {
        this.resourceID = resourceID;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
