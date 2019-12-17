package API.Services.Util;

import API.DTO.Insight;
import API.DTO.Transaction;
import API.DTO.TransactionTypes;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class InsightUtil {
    public ArrayList<Insight> getRecurringExpenses(ArrayList<Transaction> allTransactions) {
        ArrayList<Insight> recurringPayments = new ArrayList<>();
        for (Transaction transaction : allTransactions) {
            if (transaction.getType().equals(TransactionTypes.INCASSO)) {
                recurringPayments.add(new Insight(transaction.getAmount(), TransactionTypes.INCASSO));
            }
        }
        return recurringPayments;
    }

    public ArrayList<Insight> getRecurringIncome(ArrayList<Transaction> allTransactions) {
        ArrayList<Transaction> allIncome = getIncome(allTransactions);
        return new ArrayList<Insight>();
    }

    private ArrayList<Transaction> getIncome(ArrayList<Transaction> transactions) {
        return transactions.stream().filter(Transaction::getReceived).collect(Collectors.toCollection(ArrayList::new));
    }
}
