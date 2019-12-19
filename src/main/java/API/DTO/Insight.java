package API.DTO;

import java.util.ArrayList;

public class Insight {
    private Account account;
    private ArrayList<Account> accounts;
    private ArrayList<Transaction> expectedExpenses;
    private ArrayList<Transaction> expectedIncome;
    private ArrayList<Transaction> mixedExpected;
    private int totalAverageExpectedIncome;
    private int totalAverageExpectedExpense;

    public Insight(Account account) {
        this.account = account;
    }

    public Insight(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public Insight(ArrayList<Account> accounts, ArrayList<Transaction> expectedExpenses, ArrayList<Transaction> expectedIncome, int totalAverageExpectedIncome, int totalAverageExpectedExpense) {
        this.accounts = accounts;
        this.expectedExpenses = expectedExpenses;
        this.expectedIncome = expectedIncome;
        this.totalAverageExpectedIncome = totalAverageExpectedIncome;
        this.totalAverageExpectedExpense = totalAverageExpectedExpense;
    }

    public Insight(Account account, ArrayList<Transaction> mixedExpected) {
        this.account = account;
        this.mixedExpected = mixedExpected;
    }

    public int getTotalAverageExpectedIncome() {
        return totalAverageExpectedIncome;
    }

    public void setTotalAverageExpectedIncome(int totalAverageExpectedIncome) {
        this.totalAverageExpectedIncome = totalAverageExpectedIncome;
    }

    public int getTotalAverageExpectedExpense() {
        return totalAverageExpectedExpense;
    }

    public void setTotalAverageExpectedExpense(int totalAverageExpectedExpense) {
        this.totalAverageExpectedExpense = totalAverageExpectedExpense;
    }

    public ArrayList<Transaction> getMixedExpected() {
        return mixedExpected;
    }

    public void setMixedExpected(ArrayList<Transaction> mixedExpected) {
        this.mixedExpected = mixedExpected;
    }

    public ArrayList<Transaction> getExpectedExpenses() {
        return expectedExpenses;
    }

    public void setExpectedExpenses(ArrayList<Transaction> expectedExpenses) {
        this.expectedExpenses = expectedExpenses;
    }

    public ArrayList<Transaction> getExpectedIncome() {
        return expectedIncome;
    }

    public void setExpectedIncome(ArrayList<Transaction> expectedIncome) {
        this.expectedIncome = expectedIncome;
    }

    public ArrayList<Account> getAccounts() {
        return accounts;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
