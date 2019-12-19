package API.Services.Util;

import API.DTO.Account;
import API.DTO.Transaction;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.stream.Collectors;

import static API.DTO.TransactionTypes.*;

public class InsightUtil {

    public ArrayList<Transaction> getRecurringExpenses(ArrayList<Transaction> allTransactions) {
        ArrayList<Transaction> recurringPayments = new ArrayList<>();
        allTransactions = getExpense(allTransactions);
        String[] expenseTypes = new String[]{INCASSO, TELEFOONABBO, GASWATERLICHT, HUUR, HYPOTHEEK, INTERNETTV, VERZEKERING};
        filterTransactions(allTransactions, recurringPayments, expenseTypes);
        return recurringPayments;
    }

    public ArrayList<Transaction> getRecurringIncome(ArrayList<Transaction> allTransactions) {
        ArrayList<Transaction> recurringIncome = new ArrayList<>();
        allTransactions = getIncome(allTransactions);
        String[] incomeTypes = new String[]{INCOME, TOESLAG, STUDIEFINANCIERING, INCASSO};
        filterTransactions(allTransactions, recurringIncome, incomeTypes);
        return recurringIncome;
    }

    private void filterTransactions(ArrayList<Transaction> allTransactions, ArrayList<Transaction> recurringIncome, String[] transactionTypes) {
        for (Transaction transaction : allTransactions) {
            String transactionType = transaction.getType().toLowerCase();
            Arrays.stream(transactionTypes).forEach(type -> {
                        if (transactionType.contains(type.toLowerCase()) || transactionType.equalsIgnoreCase(type)) {
                            transaction.setDate(setDateToNextMonth());
                            recurringIncome.add(transaction);
                        }
                    }
            );
        }
    }

    public Transaction getAverageIncome(ArrayList<Transaction> allTransactions) {
        allTransactions = getIncome(allTransactions);
        Account debtorAccount = new Account();
        debtorAccount.setIban("Onbekend");
        debtorAccount.setName(VERWACHTEINKOMST);
        return getTransaction(allTransactions, VERWACHTEINKOMST, new Account(), debtorAccount);
    }

    public Transaction getAverageExpenses(ArrayList<Transaction> allTransactions) {
        allTransactions = getExpense(allTransactions);
        Account creditorAccount = new Account();
        creditorAccount.setIban("Onbekend");
        creditorAccount.setName(VERWACHTEUITGAVE);
        return getTransaction(allTransactions, VERWACHTEUITGAVE, creditorAccount, new Account());
    }

    private Transaction getTransaction(ArrayList<Transaction> allTransactions, String expectation, Account creditorAccount, Account debtorAccount) {
        double totalMoneyTransferred = 0;
        int totalTransactions = 0;
        for (Transaction transaction : allTransactions) {
            String transactionType = transaction.getType().toLowerCase();
            if (transactionType.contains(OVERBOEKING.toLowerCase()) || transactionType.equals(OVERBOEKING.toLowerCase())) {
                totalMoneyTransferred += Double.parseDouble(transaction.getAmount());
                totalTransactions++;
            }
        }
        double average = totalMoneyTransferred / totalTransactions;
        if (average > 0) {
            return new Transaction(setDateToNextMonth(), expectation, creditorAccount, debtorAccount, true, average + "", "");
        } else return new Transaction(setDateToNextMonth(), expectation, creditorAccount, debtorAccount, true, 0 + "", "");
    }

    private String setDateToNextMonth() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("CET"));
        calendar.add(Calendar.MONTH, 1);
        return dateFormat.format(calendar.getTime());
    }

    private ArrayList<Transaction> getIncome(ArrayList<Transaction> transactions) {
        return transactions.stream().filter(Transaction::getReceived).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Transaction> getExpense(ArrayList<Transaction> transactions) {
        return transactions.stream().filter(transaction -> !transaction.getReceived()).collect(Collectors.toCollection(ArrayList::new));
    }

    public int getTotalAverage(ArrayList<Transaction> transactions) {
        int total = 0;
        for (Transaction transaction: transactions) {
            total += Double.parseDouble(transaction.getAmount());
        }
        return total;
    }
}
