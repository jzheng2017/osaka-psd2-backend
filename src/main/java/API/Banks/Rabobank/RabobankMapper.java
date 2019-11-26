package API.Banks.Rabobank;

import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.Bank;
import API.DTO.RABO.RaboAccount;
import API.DTO.RABO.RaboBalance;
import API.DTO.RABO.RaboBooking;
import API.DTO.RABO.RaboTransaction;
import API.DTO.Transaction;
import API.Banks.Rabobank.Util.RaboMapUtil;

import java.util.ArrayList;

public class RabobankMapper {
    private RaboMapUtil util;

    public RabobankMapper() {
        this.util = new RaboMapUtil();
    }

    public Account mapToAccount(RaboAccount raboAccount) {
        ArrayList<Account> accounts = new ArrayList<>();
        ArrayList<RaboAccount> raboAccounts = raboAccount.getAccounts();
        for (RaboAccount accountInArray : raboAccounts) {
            Account acc = new Account(
                    accountInArray.getResourceId(),
                    accountInArray.getIban(),
                    accountInArray.getName(),
                    accountInArray.getCurrency(),
                    Bank.RABOBANK);
            accounts.add(acc);
        }
        Account mappedAccount = new Account();
        mappedAccount.setAccounts(accounts);//Set accounts array to all accounts
        return mappedAccount;
    }

    public Balance mapToBalance(RaboBalance raboBalance) {
        RaboAccount raboAccount = raboBalance.getAccount();
        Account account = new Account();
        account.setIban(raboAccount.getIban());
        account.setCurrency(raboAccount.getCurrency());
        ArrayList<RaboBalance> balances = raboBalance.getBalances();
        ArrayList<Balance> mappedBalances = util.getBalances(balances);
        Balance mappedBalance = new Balance();
        mappedBalance.setAccount(account);
        mappedBalance.setBalances(mappedBalances);
        return mappedBalance;
    }


    public Transaction mapToTransaction(RaboTransaction raboTransaction) {
        RaboAccount raboAccount = raboTransaction.getAccount();
        RaboTransaction transactionDetails = raboTransaction.getTransactions();
        ArrayList<RaboBooking> raboBookedTransactions = transactionDetails.getBooked();
        ArrayList<RaboBooking> raboPendingTransactions = transactionDetails.getPending();
        return util.getTransaction(raboAccount, raboBookedTransactions, raboPendingTransactions);
    }
}
