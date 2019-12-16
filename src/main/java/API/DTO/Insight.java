package API.DTO;

public class Insight {
    private String amount;
    private String type;

    public Insight(String amount, String type) {
        this.amount = amount;
        this.type = type;
    }

    public Insight() {
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
