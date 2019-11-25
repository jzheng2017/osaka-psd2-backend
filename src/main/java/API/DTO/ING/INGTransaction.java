package API.DTO.ING;

import java.util.ArrayList;

public class INGTransaction {
    private ArrayList<INGBooking> booked;
    private ArrayList<INGBooking> pending;
    private INGAccount account;
    private INGTransaction transactions;

    public INGTransaction(ArrayList<INGBooking> booked, ArrayList<INGBooking> pending, INGAccount account, INGTransaction transactions) {
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

    public INGAccount getAccount() {
        return account;
    }

    public void setAccount(INGAccount account) {
        this.account = account;
    }
}

