package API.DTO.ING;

import API.DTO.BalanceAmount;

import java.util.ArrayList;

public class INGBalance {
    private INGAccount account;
    private ArrayList<INGBalance> balances;
    private String balanceType;
    private BalanceAmount balanceAmount;
    private String lastChangeDateTime;
    private String referenceDate;

    public INGBalance(INGAccount account, ArrayList<INGBalance> balances, String balanceType, BalanceAmount balanceAmount, String lastChangeDateTime, String referenceDate) {
        this.account = account;
        this.balances = balances;
        this.balanceType = balanceType;
        this.balanceAmount = balanceAmount;
        this.lastChangeDateTime = lastChangeDateTime;
        this.referenceDate = referenceDate;
    }

    public INGBalance() {
    }

    public INGAccount getAccount() {
        return account;
    }

    public void setAccount(INGAccount account) {
        this.account = account;
    }

    public ArrayList<INGBalance> getBalances() {
        return balances;
    }

    public void setBalances(ArrayList<INGBalance> balances) {
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
