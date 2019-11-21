package API.DTO.RABO;

public class RaboBooking {
    private String bookingDate;
    private String iban;
    private String currency;
    private RaboAccount creditorAccount;
    private String creditorName;

    public RaboBooking(String bookingDate, String iban, String currency, RaboAccount creditorAccount, String creditorName) {
        this.bookingDate = bookingDate;
        this.iban = iban;
        this.currency = currency;
        this.creditorAccount = creditorAccount;
        this.creditorName = creditorName;
    }

    public RaboBooking() {
    }

    public String getCreditorName() {
        return creditorName;
    }

    public void setCreditorName(String creditorName) {
        this.creditorName = creditorName;
    }

    public String getBookingDate() {
        return bookingDate;
    }

    public void setBookingDate(String bookingDate) {
        this.bookingDate = bookingDate;
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public RaboAccount getCreditorAccount() {
        return creditorAccount;
    }

    public void setCreditorAccount(RaboAccount creditorAccount) {
        this.creditorAccount = creditorAccount;
    }
}
