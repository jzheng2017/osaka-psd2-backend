package API.DTO.ING;

import API.DTO.TransactionAmount;

public class INGBooking {
    private String transactionType;
    private TransactionAmount transactionAmount;
    private String bookingDate;
    private String remittanceInformationUnstructured;
    private String endToEndId;
    private String transactionId;

    public INGBooking(String transactionType, TransactionAmount transactionAmount, String bookingDate, String remittanceInformationUnstructured, String endToEndId, String transactionId) {
        this.transactionType = transactionType;
        this.transactionAmount = transactionAmount;
        this.bookingDate = bookingDate;
        this.remittanceInformationUnstructured = remittanceInformationUnstructured;
        this.endToEndId = endToEndId;
        this.transactionId = transactionId;
    }

    public INGBooking() {
    }

    public String getTransactionType() {
        return transactionType;
    }

    public void setTransactionType(String transactionType) {
        this.transactionType = transactionType;
    }

    public TransactionAmount getTransactionAmount() {
        return transactionAmount;
    }

    public void setTransactionAmount(TransactionAmount transactionAmount) {
        this.transactionAmount = transactionAmount;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getRemittanceInformationUnstructured() {
        return remittanceInformationUnstructured;
    }

    public void setRemittanceInformationUnstructured(String remittanceInformationUnstructured) {
        this.remittanceInformationUnstructured = remittanceInformationUnstructured;
    }

    public String getEndToEndId() {
        return endToEndId;
    }

    public void setEndToEndId(String endToEndId) {
        this.endToEndId = endToEndId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }
}
