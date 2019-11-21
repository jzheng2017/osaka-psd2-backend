package API.DTO.RABO;

public class InstructedAmount {
    private int amount;
    private String sourceCurrency;

    public InstructedAmount(int amount, String sourceCurrency) {
        this.amount = amount;
        this.sourceCurrency = sourceCurrency;
    }

    public InstructedAmount() {
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public String getSourceCurrency() {
        return sourceCurrency;
    }

    public void setSourceCurrency(String sourceCurrency) {
        this.sourceCurrency = sourceCurrency;
    }
}
