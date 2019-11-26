package API.DTO.RABO;

import API.DTO.BalanceAmount;

import java.util.ArrayList;

public class RaboTransaction {
    private RaboAccount account;
    private RaboTransaction transactions;
    private ArrayList<RaboBooking> booked;
    private ArrayList<RaboBooking> pending;
    private String links;

    public RaboTransaction(RaboAccount account, RaboTransaction transactions, ArrayList<RaboBooking> booked, ArrayList<RaboBooking> pending, String links) {
        this.account = account;
        this.transactions = transactions;
        this.booked = booked;
        this.pending = pending;
        this.links = links;
    }

    public RaboTransaction(ArrayList<RaboBooking> booked, ArrayList<RaboBooking> pending, String links) {
        this.booked = booked;
        this.pending = pending;
        this.links = links;
    }

    public RaboTransaction() {
    }

    public String getLinks() {
        return links;
    }

    public void setLinks(String links) {
        this.links = links;
    }

    public ArrayList<RaboBooking> getPending() {
        return pending;
    }

    public void setPending(ArrayList<RaboBooking> pending) {
        this.pending = pending;
    }

    public RaboAccount getAccount() {
        return account;
    }

    public void setAccount(RaboAccount account) {
        this.account = account;
    }

    public RaboTransaction getTransactions() {
        return transactions;
    }

    public void setTransactions(RaboTransaction transactions) {
        this.transactions = transactions;
    }

    public ArrayList<RaboBooking> getBooked() {
        return booked;
    }

    public void setBooked(ArrayList<RaboBooking> booked) {
        this.booked = booked;
    }

    public void setInstructedAmount(BalanceAmount instructedAmount) {
    }

}
