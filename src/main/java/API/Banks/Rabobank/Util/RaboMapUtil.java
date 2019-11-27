package API.Banks.Rabobank.Util;

import API.DTO.Account;
import API.DTO.RABO.RaboBooking;
import API.DTO.Transaction;
import java.util.ArrayList;

public class RaboMapUtil {

    public Transaction getTransaction(Account account, ArrayList<RaboBooking> bookings) {
        var transactions = addTransactionToArray(bookings, account.getIban());

        Transaction mappedTransaction = new Transaction();

        mappedTransaction.setAccount(account);
        mappedTransaction.setTransactions(transactions);

        return mappedTransaction;
    }

    private ArrayList<Transaction> addTransactionToArray(ArrayList<RaboBooking> bookings, String iban) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        if (bookings != null) {
            for (RaboBooking t : bookings) {
                transactions.add(addTransaction(t, iban));
            }
        }
        return transactions;
    }

    private Transaction addTransaction(RaboBooking booking, String iban) {
        Account creditorAccount = booking.getCreditorAccount();
        Account account = new Account();
        account.setIban(creditorAccount.getIban());
        account.setName(booking.getCreditorName());

        Account debtorAccount = new Account();
        Account raboDebtorAccount = booking.getDebtorAccount();
        debtorAccount.setIban(raboDebtorAccount.getIban());
        debtorAccount.setName(booking.getDebtorName());
        String amount = booking.getTransactionAmount().getAmount();

        String isAfschrift;
        if (iban.equals(raboDebtorAccount.getIban())) {
            isAfschrift = "Afschrift";
        } else if (iban.equals(creditorAccount.getIban())) {
            isAfschrift = "Inkomst";
        } else {
            isAfschrift = "Doorgifte";
        }
        return new Transaction(
                booking.getBookingDate(),
                booking.getRaboTransactionTypeName(),
                account, debtorAccount, isAfschrift, amount);
    }
}
