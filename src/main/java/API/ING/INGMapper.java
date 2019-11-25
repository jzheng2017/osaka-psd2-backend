package API.ING;

import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.ING.INGAccount;
import API.DTO.ING.INGBalance;
import API.DTO.ING.INGBooking;
import API.DTO.ING.INGTransaction;
import API.DTO.RABO.RaboAccount;
import API.DTO.RABO.RaboBalance;
import API.DTO.RABO.RaboBooking;
import API.DTO.RABO.RaboTransaction;
import API.DTO.Transaction;

import java.util.ArrayList;

public class INGMapper {
    public Account mapToAccount(INGAccount ingAccount) {
        ArrayList<Account> accounts = new ArrayList<>();
        ArrayList<INGAccount> ingAccounts = ingAccount.getAccounts();
        for (INGAccount accountInArray : ingAccounts) {
            Account acc = new Account(
                    accountInArray.getResourceId(),
                    accountInArray.getIban(),
                    accountInArray.getName(),
                    accountInArray.getCurrency(), "ing");
            accounts.add(acc);
        }
        Account mappedAccount = new Account();
        mappedAccount.setAccounts(accounts);//Set accounts array to all accounts
        return mappedAccount;
    }

    public Balance mapToBalance(INGBalance ingBalance) {
        //Mapping account
        INGAccount ingAccount = ingBalance.getAccount();
        Account account = new Account();
        account.setIban(ingAccount.getIban());
        account.setCurrency(ingAccount.getCurrency());
        //Mapping balances
        ArrayList<INGBalance> balances = ingBalance.getBalances();
        ArrayList<Balance> mappedBalances = new ArrayList<>();
        for (INGBalance balanceInArray : balances) {
            mappedBalances.add(new Balance(
                    balanceInArray.getBalanceAmount(),
                    balanceInArray.getBalanceType(),
                    balanceInArray.getLastChangeDateTime()
            ));
        }
        Balance mappedBalance = new Balance();
        mappedBalance.setAccount(account);
        mappedBalance.setBalances(mappedBalances);
        return mappedBalance;
    }

    public Transaction mapToTransaction(INGTransaction ingTransaction) {
        if (ingTransaction != null) {
            INGAccount ingAccount = ingTransaction.getAccount();
            INGTransaction transactionDetails = ingTransaction.getTransactions();
            ArrayList<INGBooking> ingBookedTransactions = transactionDetails.getBooked();
            ArrayList<INGBooking> ingPendingTransactions = transactionDetails.getPending();
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
        } else return null;
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
