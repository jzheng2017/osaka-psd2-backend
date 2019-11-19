package nl.han.ica.oose.osaka.dto;

public class TransactionComment {
    private int bankId;
    private int transactionId;
    private String comment;

    public TransactionComment(){

    }

    public TransactionComment(int bankId, int transactionId, String text) {
        this.bankId = bankId;
        this.transactionId = transactionId;
        this.comment = text;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
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
