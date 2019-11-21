package API.DTO;

public class Links {
    private String account;
    private String balances;
    private String transaction;

    public Links(String account, String balances, String transaction) {
        this.account = account;
        this.balances = balances;
        this.transaction = transaction;
    }

    public Links() {
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public String getBalances() {
        return balances;
    }

    public void setBalances(String balances) {
        this.balances = balances;
    }

    public String getTransaction() {
        return transaction;
    }

    public void setTransaction(String transaction) {
        this.transaction = transaction;
    }
}
