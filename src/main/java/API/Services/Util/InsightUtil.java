package API.Services.Util;

import API.DTO.Transaction;
import API.DTO.TransactionTypes;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.TimeZone;
import java.util.stream.Collectors;

public class InsightUtil {
    public ArrayList<Transaction> getRecurringExpenses(ArrayList<Transaction> allTransactions) {
        ArrayList<Transaction> recurringPayments = new ArrayList<>();
        allTransactions = getExpense(allTransactions);
        for (Transaction transaction : allTransactions) {
            String transactionType = transaction.getType().toLowerCase();
            if (transactionType.contains(TransactionTypes.INCASSO.toLowerCase()) || transactionType.equals(TransactionTypes.INCASSO.toLowerCase())) {
                transaction.setDate(setDateToNextMonth());
                recurringPayments.add(transaction);
            }
        }
        return recurringPayments;
    }

    public ArrayList<Transaction> getRecurringIncome(ArrayList<Transaction> allTransactions) {
        ArrayList<Transaction> recurringIncome = new ArrayList<>();
        allTransactions = getIncome(allTransactions);
        for (Transaction transaction : allTransactions) {
            String transactionType = transaction.getType().toLowerCase();
            if (transactionType.contains(TransactionTypes.INCOME.toLowerCase()) || transactionType.equals(TransactionTypes.INCOME.toLowerCase())) {
                transaction.setDate(setDateToNextMonth());
                recurringIncome.add(transaction);
            }
        }
        return recurringIncome;
    }

    private String setDateToNextMonth() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("CET"));
        calendar.add(Calendar.MONTH, 1);
        return dateFormat.format(calendar.getTime());
    }

    private ArrayList<Transaction> getIncome(ArrayList<Transaction> transactions) {
        return transactions.stream().filter(transaction -> !transaction.getReceived()).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Transaction> getExpense(ArrayList<Transaction> transactions) {
        return transactions.stream().filter(Transaction::getReceived).collect(Collectors.toCollection(ArrayList::new));
    }

}
