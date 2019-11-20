package API.DTO;

public class TransactionDTO {
    private String iban;

    public TransactionDTO(String iban) {
        this.iban = iban;
    }

    public TransactionDTO() {
    }

    public String getIban() {
        return iban;
    }

    public void setIban(String iban) {
        this.iban = iban;
    }
}
