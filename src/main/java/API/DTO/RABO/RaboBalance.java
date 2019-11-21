package API.DTO.RABO;

import API.DTO.BalanceAmount;

import java.util.ArrayList;

public class RaboBalance {
    private RaboAccount account;
    private ArrayList<RaboBalance> balances = new ArrayList<>();
    private String balanceType;
    private String lastChangeDateTime;
    private BalanceAmount balanceAmount;

    public RaboBalance(RaboAccount account, ArrayList<RaboBalance> balances, String balanceType, String lastChangeDateTime, BalanceAmount balanceAmount) {
        this.account = account;
        this.balances = balances;
        this.balanceType = balanceType;
        this.lastChangeDateTime = lastChangeDateTime;
        this.balanceAmount = balanceAmount;
    }

    public RaboBalance() {
    }

    public RaboAccount getAccount() {
        return account;
    }

    public void setAccount(RaboAccount account) {
        this.account = account;
    }

    public ArrayList<RaboBalance> getBalances() {
        return balances;
    }

    public void setBalances(ArrayList<RaboBalance> balances) {
        this.balances = balances;
    }

    public String getBalanceType() {
        return balanceType;
    }

    public void setBalanceType(String balanceType) {
        this.balanceType = balanceType;
    }

    public String getLastChangeDateTime() {
        return lastChangeDateTime;
    }

    public void setLastChangeDateTime(String lastChangeDateTime) {
        this.lastChangeDateTime = lastChangeDateTime;
    }

    public BalanceAmount getBalanceAmount() {
        return balanceAmount;
    }

    public void setBalanceAmount(BalanceAmount balanceAmount) {
        this.balanceAmount = balanceAmount;
    }
}
