package API.Services.Util;

import API.DTO.Transaction;
import API.DTO.TransactionTypes;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Logger;
import java.util.stream.Collectors;

public class InsightUtil {
    private static final Logger LOGGER = Logger.getLogger(InsightUtil.class.getName());

    public ArrayList<Transaction> getRecurringExpenses(ArrayList<Transaction> allTransactions) {
        ArrayList<Transaction> recurringPayments = new ArrayList<>();
        allTransactions = getExpense(allTransactions);
        for (Transaction transaction : allTransactions) {
            String transactionType = transaction.getType().toLowerCase();
            if (transactionType.contains(TransactionTypes.INCASSO.toLowerCase()) || transactionType.equals(TransactionTypes.INCASSO.toLowerCase())) {
                transaction.setDate(setDateToNextMonth(transaction.getDate()));
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
                transaction.setDate(setDateToNextMonth(transaction.getDate()));
                recurringIncome.add(transaction);
            }
        }
        return recurringIncome;
    }

    public String setDateToNextMonth(String date) {
        try {
            date = date.replace("-", "/");
            Date tempDate = new SimpleDateFormat("dd/MM/yyyy").parse(date);
            Long tempTime = tempDate.getTime();
            Long secondsInAMonth = 2592000000L;
            tempDate.setTime(tempTime + secondsInAMonth);
            return new SimpleDateFormat("dd-MM-yyyy").format(tempDate);
        } catch (ParseException e) {
            LOGGER.info(e.toString());
        }
        return "";
    }

    private ArrayList<Transaction> getIncome(ArrayList<Transaction> transactions) {
        return transactions.stream().filter(transaction -> !transaction.getReceived()).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Transaction> getExpense(ArrayList<Transaction> transactions) {
        return transactions.stream().filter(Transaction::getReceived).collect(Collectors.toCollection(ArrayList::new));
    }

}
