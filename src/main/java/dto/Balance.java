package dto;

import java.util.ArrayList;

public class Balance {
    private Account account;
    private ArrayList<Balance> balances = new ArrayList<>();
    private String balanceType;
    private BalanceAmount balanceAmount;
    private String lastChangeDateTime;
    private String referenceDate;

    public Balance() {
    }

    public Balance(Account account, ArrayList<Balance> balances, String balanceType, BalanceAmount balanceAmount, String lastChangeDateTime, String referenceDate) {
        this.account = account;
        this.balances = balances;
        this.balanceType = balanceType;
        this.balanceAmount = balanceAmount;
        this.lastChangeDateTime = lastChangeDateTime;
        this.referenceDate = referenceDate;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public ArrayList<Balance> getBalances() {
        return balances;
    }

    public void setBalances(ArrayList<Balance> balances) {
        this.balances = balances;
    }

    public String getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }

    public BalanceAmount getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BalanceAmount balanceAmount) {
        this.balanceAmount = balanceAmount;
    }

    public String getLastChangeDateTime() {
        return lastChangeDateTime;
    }

    public void setLastChangeDateTime(String lastChangeDateTime) {
        this.lastChangeDateTime = lastChangeDateTime;
    }

    public String getReferenceDate() {
        return referenceDate;
    }

    public void setReferenceDate(String referenceDate) {
        this.referenceDate = referenceDate;
    }
}
