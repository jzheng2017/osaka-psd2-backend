package API.DTO;

public class UserBankDTO {
    private int userId;
    private int bankId;
    private String bankAccountNumber;

    public UserBankDTO() {

    }

    public UserBankDTO(int userId, int bankId, String bankAccountNumber) {
        this.userId = userId;
        this.bankId = bankId;
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
