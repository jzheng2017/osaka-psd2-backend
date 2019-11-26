package API.RABO.Util;

import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.RABO.RaboAccount;
import API.DTO.RABO.RaboBalance;
import API.DTO.RABO.RaboBooking;
import API.DTO.Transaction;

import java.util.ArrayList;

public class RaboMapUtil {
    public ArrayList<Balance> getBalances(ArrayList<RaboBalance> balances) {
        ArrayList<Balance> mappedBalances = new ArrayList<>();
        for (RaboBalance balanceInArray : balances) {
            mappedBalances.add(new Balance(
                    balanceInArray.getBalanceAmount(),
                    balanceInArray.getBalanceType(),
                    balanceInArray.getLastChangeDateTime()
            ));
        }
        return mappedBalances;
    }

    public Transaction getTransaction(RaboAccount raboAccount, ArrayList<RaboBooking> raboBookedTransactions, ArrayList<RaboBooking> raboPendingTransactions) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        String iban = raboAccount.getIban();
        transactions.addAll(addTransactionToArray(raboBookedTransactions, iban));
        transactions.addAll(addTransactionToArray(raboPendingTransactions, iban));
        Account account = new Account();
        account.setIban(iban);
        account.setCurrency(raboAccount.getCurrency());
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
        RaboAccount creditorAccount = booking.getCreditorAccount();
        Account account = new Account();
        account.setIban(creditorAccount.getIban());
        account.setName(booking.getCreditorName());

        Account debtorAccount = new Account();
        RaboAccount raboDebtorAccount = booking.getDebtorAccount();
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
