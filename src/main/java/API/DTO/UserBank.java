package API.DTO;

public class UserBank {
    private int userId;
    private int bankId;
    private String bankAccountNumber;

    public UserBank(){

    }
    public UserBank(int userId, int bank_id, String bankAccountNumber) {
        this.userId = userId;
        this.bankId = bank_id;
        this.bankAccountNumber = bankAccountNumber;
    }

    public String getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(String bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
