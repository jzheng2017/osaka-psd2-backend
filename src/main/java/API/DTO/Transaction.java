package API.DTO;

public class Transaction {
    private String iban;

    public Transaction(String iban) {
        this.iban = iban;
    }

    public Transaction() {
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
