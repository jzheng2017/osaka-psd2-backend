package nl.han.ica.oose.dto;

public class TransactionComment {
    private int bankId;
    private int transactionId;
    private String text;

    public TransactionComment(){

    }

    public TransactionComment(int bankId, int transactionId, String text) {
        this.bankId = bankId;
        this.transactionId = transactionId;
        this.text = text;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public int getBankId() {
        return bankId;
    }

    public void setBankId(int bankId) {
        this.bankId = bankId;
    }
}
