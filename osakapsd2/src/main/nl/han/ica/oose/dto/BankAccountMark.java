package nl.han.ica.oose.dto;

public class BankAccountMark {
    private int userId;
    private int bankAccountNumber;
    private String color;

    public BankAccountMark(){

    }
    public BankAccountMark(int user_id, int bankAccountNumber, String color) {
        this.userId = user_id;
        this.bankAccountNumber = bankAccountNumber;
        this.color = color;
    }

    public String getColor() {
        return color;
    }

    public void setColor(String color) {
        this.color = color;
    }

    public int getBankAccountNumber() {
        return bankAccountNumber;
    }

    public void setBankAccountNumber(int bankAccountNumber) {
        this.bankAccountNumber = bankAccountNumber;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
