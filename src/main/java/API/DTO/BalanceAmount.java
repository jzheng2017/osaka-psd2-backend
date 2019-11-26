package API.DTO;

public class BalanceAmount {
    private String currency;
    private float amount;

    public BalanceAmount() {
    }

    public BalanceAmount(String currency, int amount) {
        this.currency = currency;
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public float getAmount() {
        return amount;
    }

    public void setAmount(float amount) {
        this.amount = amount;
    }
}
