package API.DTO;

public class Transaction {
    private String id;
    private String date;
    private Account sender;
    private Account receiver;
    private String amount;
    private Boolean received;
    private String type;
    private Boolean booked;
    private TransactionCategory category;

    public Transaction(String date, Account account, String transactionType, Account creditorAccount) {
        this.date = date;
        this.receiver = creditorAccount;
    }

    public Transaction(String date, String transactionType, Account creditorAccount, Account debtorAccount, String isAfschrift, String amount) {
        this.date = date;
        this.receiver = creditorAccount;
        this.sender = debtorAccount;
        this.amount = amount;
    }

    public Transaction() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public Account getReceiver() {
        return receiver;
    }

    public Account getSender() {
        return sender;
    }

    public void setSender(Account sender) {
        this.sender = sender;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public void setReceiver(Account receiver) {
        this.receiver = receiver;
    }

    public Boolean getReceived() {
        return received;
    }

    public void setReceived(Boolean received) {
        this.received = received;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Boolean getBooked() {
        return booked;
    }

    public void setBooked(Boolean booked) {
        this.booked = booked;
    }

    public TransactionCategory getCategory() {
        return category;
    }

    public void setCategory(TransactionCategory category) {
        this.category = category;
    }
}
