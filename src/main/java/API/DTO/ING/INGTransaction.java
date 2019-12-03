package API.DTO.ING;

import API.DTO.Account;

import java.util.ArrayList;

public class INGTransaction {
    private ArrayList<INGBooking> booked;
    private ArrayList<INGBooking> pending;
    private Account account;
    private INGTransaction transactions;

    public INGTransaction(ArrayList<INGBooking> booked, ArrayList<INGBooking> pending, Account account, INGTransaction transactions) {
        this.booked = booked;
        this.pending = pending;
        this.account = account;
        this.transactions = transactions;
    }

    public INGTransaction() {
    }

    public ArrayList<INGBooking> getBooked() {
        return booked;
    }

    public void setBooked(ArrayList<INGBooking> booked) {
        this.booked = booked;
    }

    public ArrayList<INGBooking> getPending() {
        return pending;
    }

    public INGTransaction getTransactions() {
        return transactions;
    }

    public void setTransactions(INGTransaction transactions) {
        this.transactions = transactions;
    }

    public void setPending(ArrayList<INGBooking> pending) {
        this.pending = pending;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}

