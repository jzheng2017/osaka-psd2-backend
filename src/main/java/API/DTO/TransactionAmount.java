package API.DTO;

public class TransactionAmount {
    private String amount;
    private String currency;

    public TransactionAmount(String amount, String currency) {
        this.amount = amount;
        this.currency = currency;
    }

    public TransactionAmount() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }
}
