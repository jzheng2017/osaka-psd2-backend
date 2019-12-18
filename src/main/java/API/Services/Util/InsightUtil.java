package API.Services.Util;

import API.DTO.Account;
import API.DTO.Transaction;

import java.text.SimpleDateFormat;
import java.util.*;
import java.util.stream.Collectors;

import static API.DTO.TransactionTypes.*;

public class InsightUtil {
    private String[] expenseTypes = new String[]{INCASSO, TELEFOONABBO, GASWATERLICHT, HUUR, HYPOTHEEK, INTERNETTV, VERZEKERING};
    private String[] incomeTypes = new String[]{INCOME, TOESLAG, STUDIEFINANCIERING, INCASSO};

    public ArrayList<Transaction> getRecurringExpenses(ArrayList<Transaction> allTransactions) {
        ArrayList<Transaction> recurringPayments = new ArrayList<>();
        allTransactions = getExpense(allTransactions);
        for (Transaction transaction : allTransactions) {
            String transactionType = transaction.getType().toLowerCase();
            Arrays.stream(expenseTypes).forEach(expenseType -> {
                        if (transactionType.contains(expenseType.toLowerCase()) || transactionType.equals(expenseType.toLowerCase())) {
                            transaction.setDate(setDateToNextMonth());
                            recurringPayments.add(transaction);
                        }
                    }
            );

        }
        return recurringPayments;
    }

    public ArrayList<Transaction> getRecurringIncome(ArrayList<Transaction> allTransactions) {
        ArrayList<Transaction> recurringIncome = new ArrayList<>();
        allTransactions = getIncome(allTransactions);
        for (Transaction transaction : allTransactions) {
            String transactionType = transaction.getType().toLowerCase();
            Arrays.stream(incomeTypes).forEach(incomeType -> {
                        if (transactionType.contains(incomeType.toLowerCase()) || transactionType.equals(incomeType.toLowerCase())) {
                            transaction.setDate(setDateToNextMonth());
                            recurringIncome.add(transaction);
                        }
                    }
            );
        }
        return recurringIncome;
    }

    //Deze functies Lijken op elkaar, refactoren
    public Transaction getAverageIncome(ArrayList<Transaction> allTransactions) {
        allTransactions = getIncome(allTransactions);
        double totalSpent = 0;
        int totalTransactions = 0;
        for (Transaction transaction : allTransactions) {
            String transactionType = transaction.getType().toLowerCase();
            if (transactionType.contains(OVERBOEKING.toLowerCase()) || transactionType.equals(OVERBOEKING.toLowerCase())) {
                totalSpent += Double.parseDouble(transaction.getAmount());
                totalTransactions++;
            }
        }
        Account creditorAccount = new Account();
        creditorAccount.setIban("Onbekend");
        creditorAccount.setName(VERWACHTEINKOMST);
        double totalIncome = totalSpent / totalTransactions;
        if (totalIncome > 0) {
            return new Transaction(setDateToNextMonth(), VERWACHTEINKOMST, new Account(), creditorAccount, true, totalIncome + "", "");
        } else return new Transaction(setDateToNextMonth(), VERWACHTEINKOMST, new Account(), creditorAccount, true, 0 + "", "");
    }

    public Transaction getAverageExpenses(ArrayList<Transaction> allTransactions) {
        allTransactions = getExpense(allTransactions);
        double totalSpent = 0;
        int totalTransactions = 0;
        for (Transaction transaction : allTransactions) {
            String transactionType = transaction.getType().toLowerCase();
            if (transactionType.contains(OVERBOEKING.toLowerCase()) || transactionType.equals(OVERBOEKING.toLowerCase())) {
                totalSpent += Double.parseDouble(transaction.getAmount());
                totalTransactions++;
            }
        }
        Account creditorAccount = new Account();
        creditorAccount.setIban("Onbekend");
        creditorAccount.setName(VERWACHTEUITGAVE);
        double totalIncome = totalSpent / totalTransactions;
        if (totalIncome > 0) {
            return new Transaction(setDateToNextMonth(), VERWACHTEUITGAVE, new Account(), creditorAccount, true, totalIncome + "", "");
        } else return new Transaction(setDateToNextMonth(), VERWACHTEUITGAVE, new Account(), creditorAccount, true, 0 + "", "");
    }


    private String setDateToNextMonth() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy");
        dateFormat.setTimeZone(TimeZone.getTimeZone("CET"));
        Random random = new Random();

        calendar.add(Calendar.MONTH, 1);
        return dateFormat.format(calendar.getTime());
    }

    private ArrayList<Transaction> getIncome(ArrayList<Transaction> transactions) {
        return transactions.stream().filter(Transaction::getReceived).collect(Collectors.toCollection(ArrayList::new));
    }

    private ArrayList<Transaction> getExpense(ArrayList<Transaction> transactions) {
        return transactions.stream().filter(transaction -> !transaction.getReceived()).collect(Collectors.toCollection(ArrayList::new));
    }

}
