package API.Banks.ING.Util;

import API.DTO.Account;
import API.DTO.ING.INGBooking;
import API.DTO.Transaction;

import java.util.ArrayList;

public class INGMapUtil {
    public Transaction getTransaction(Account ingAccount, ArrayList<INGBooking> ingBookedTransactions, ArrayList<INGBooking> ingPendingTransactions) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        String iban = ingAccount.getIban();
        transactions.addAll(addTransactionToArray(ingBookedTransactions, iban));
        transactions.addAll(addTransactionToArray(ingPendingTransactions, iban));
        Account account = new Account();
        account.setIban(iban);
        account.setCurrency(ingAccount.getCurrency());
        Transaction mappedTransaction = new Transaction();
        mappedTransaction.setAccount(account);
        mappedTransaction.setTransactions(transactions);
        return mappedTransaction;
    }

    private ArrayList<Transaction> addTransactionToArray(ArrayList<INGBooking> bookings, String iban) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        if (bookings != null) {
            for (INGBooking t : bookings) {
                transactions.add(addTransaction(t, iban));
            }
        }
        return transactions;
    }

    private Transaction addTransaction(INGBooking booking, String iban) {
        String isAfschrift;
        String transactionAmount = booking.getTransactionAmount().getAmount();
        Account debtorAccount = new Account();
        Account account = new Account();
        if (transactionAmount.contains("-")) {
            isAfschrift = "Afschrift";
            String ibanFromString = getIbanFromString(booking.getRemittanceInformationUnstructured());
            account.setIban(ibanFromString);
            debtorAccount.setIban(iban);
        } else {
            isAfschrift = "Inkomst";
            String ibanFromString = getIbanFromString(booking.getRemittanceInformationUnstructured());
            debtorAccount.setIban(ibanFromString);
            account.setIban(iban);
        }
        return new Transaction(
                booking.getBookingDate(),
                booking.getTransactionType(),
                account, debtorAccount, isAfschrift, transactionAmount);
    }

    private String getIbanFromString(String info) {
        if (info.contains("IBAN")) {
            int startIndex = info.indexOf("NL");
            int stopIndex = info.indexOf("<");
            return info.substring(startIndex, stopIndex);
        } else {
            return null;
        }
    }
}
