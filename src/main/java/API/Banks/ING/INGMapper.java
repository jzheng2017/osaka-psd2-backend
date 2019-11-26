package API.Banks.ING;

import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.Bank;
import API.DTO.ING.INGAccount;
import API.DTO.ING.INGBalance;
import API.DTO.ING.INGBooking;
import API.DTO.ING.INGTransaction;
import API.DTO.Transaction;
import API.Banks.ING.Util.INGMapUtil;

import java.util.ArrayList;

public class INGMapper {
    private INGMapUtil util;

    public INGMapper() {
        this.util = new INGMapUtil();
    }

    public Account mapToAccount(INGAccount ingAccount) {
        ArrayList<Account> accounts = new ArrayList<>();
        ArrayList<INGAccount> ingAccounts = ingAccount.getAccounts();
        for (INGAccount accountInArray : ingAccounts) {
            Account acc = new Account(
                    accountInArray.getResourceId(),
                    accountInArray.getIban(),
                    accountInArray.getName(),
                    accountInArray.getCurrency(), Bank.ING);
            accounts.add(acc);
        }
        Account mappedAccount = new Account();
        mappedAccount.setAccounts(accounts);
        return mappedAccount;
    }

    public Balance mapToBalance(INGBalance ingBalance) {
        if (ingBalance != null) {
            INGAccount ingAccount = ingBalance.getAccount();
            Account account = new Account();
            account.setIban(ingAccount.getIban());
            account.setCurrency(ingAccount.getCurrency());
            ArrayList<INGBalance> balances = ingBalance.getBalances();
            ArrayList<Balance> mappedBalances = util.getBalances(balances);
            Balance mappedBalance = new Balance();
            mappedBalance.setAccount(account);
            mappedBalance.setBalances(mappedBalances);
            return mappedBalance;
        } else return null;
    }

    public Transaction mapToTransaction(INGTransaction ingTransaction) {
        if (ingTransaction != null) {
            INGAccount ingAccount = ingTransaction.getAccount();
            INGTransaction transactionDetails = ingTransaction.getTransactions();
            ArrayList<INGBooking> ingBookedTransactions = transactionDetails.getBooked();
            ArrayList<INGBooking> ingPendingTransactions = transactionDetails.getPending();
            return util.getTransaction(ingAccount, ingBookedTransactions, ingPendingTransactions);
        } else return null;
    }
}
