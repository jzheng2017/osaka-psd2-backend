package API.DTO;

import java.util.ArrayList;

public class Insight {
    private Account account;
    private ArrayList<Insight> insights;
    private String amount;
    private String type;

    public Insight(Account account, ArrayList<Insight> insights, String amount, String type) {
        this.account = account;
        this.insights = insights;
        this.amount = amount;
        this.type = type;
    }

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

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ArrayList<Insight> getInsights() {
        return insights;
    }

    public void setInsights(ArrayList<Insight> insights) {
        this.insights = insights;
    }
}
