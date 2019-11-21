package API.DTO.RABO;

import API.DTO.BalanceAmount;

import java.util.ArrayList;

public class RaboTransaction {
    private RaboAccount account;
    private ArrayList<RaboTransaction> transactions;
    private ArrayList<RaboBooking> booked;
    private ArrayList<RaboBooking> pending;
    private BalanceAmount instructedAmount;
    private String raboTransactionTypeName;
    private String raboBookingDateTime;

    public RaboTransaction(RaboAccount account, ArrayList<RaboTransaction> transactions, ArrayList<RaboBooking> booked, ArrayList<RaboBooking> pending, BalanceAmount instructedAmount, String raboTransactionTypeName, String raboBookingDateTime) {
        this.account = account;
        this.transactions = transactions;
        this.booked = booked;
        this.pending = pending;
        this.instructedAmount = instructedAmount;
        this.raboTransactionTypeName = raboTransactionTypeName;
        this.raboBookingDateTime = raboBookingDateTime;
    }

    public RaboTransaction() {
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

    public ArrayList<RaboTransaction> getTransactions() {
        return transactions;
    }

    public void setTransactions(ArrayList<RaboTransaction> transactions) {
        this.transactions = transactions;
    }

    public ArrayList<RaboBooking> getBooked() {
        return booked;
    }

    public void setBooked(ArrayList<RaboBooking> booked) {
        this.booked = booked;
    }

    public BalanceAmount getInstructedAmount() {
        return instructedAmount;
    }

    public void setInstructedAmount(BalanceAmount instructedAmount) {
        this.instructedAmount = instructedAmount;
    }

    public String getRaboTransactionTypeName() {
        return raboTransactionTypeName;
    }

    public void setRaboTransactionTypeName(String raboTransactionTypeName) {
        this.raboTransactionTypeName = raboTransactionTypeName;
    }

    public String getRaboBookingDateTime() {
        return raboBookingDateTime;
    }

    public void setRaboBookingDateTime(String raboBookingDateTime) {
        this.raboBookingDateTime = raboBookingDateTime;
    }
}
