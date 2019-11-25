package API.Services;

import API.Adapter.BankAdapter;
import API.Adapter.INGAdapter;
import API.Adapter.RaboAdapter;
import API.DTO.*;
import API.DTO.RABO.RaboAccount;

import java.util.ArrayList;

public class AccountService {
    private final String raboName = "rabo";
    private final String ingName = "ing";
    private INGAdapter ingAdapter = new INGAdapter();
    private RaboAdapter raboAdapter = new RaboAdapter();

    public Account getUserAccounts(String tokenRabo, String tokenING) {
        Account rabobankAccounts = raboAdapter.getUserAccounts(tokenRabo);
        Account ingAccounts = ingAdapter.getUserAccounts(tokenING);
        ArrayList<Account> accounts = new ArrayList<>();
        accounts.addAll(rabobankAccounts.getAccounts());
        accounts.addAll(ingAccounts.getAccounts());
        Account accountToReturn = new Account();
        accountToReturn.setAccounts(addBalancesToAccounts(accounts, tokenRabo, tokenING));
        return accountToReturn;
    }

    private ArrayList<Account> addBalancesToAccounts(ArrayList<Account> bankAccounts, String tokenRabo, String tokenING) {
        for (Account account : bankAccounts) {
            Balance tempBalance;
            if (account.getBank().equals(raboName)) {
                tempBalance = raboAdapter.getAccountBalances(tokenRabo, account.getID());
            } else {
                tempBalance = ingAdapter.getAccountBalances(tokenING, account.getID());
            }
            if (tempBalance != null) {
                account.setBalance(getBalanceFromBalances(tempBalance));
            }
        }
        return bankAccounts;
    }

    private float getBalanceFromBalances(Balance balance) {
        Balance tempBalance = balance.getBalances().get(0);
        return tempBalance.getBalanceAmount().getAmount();
    }

    public Transaction getAccountDetails(String bank, String token, String id) {
        BankAdapter bankAdapter = getBankAdapter(bank);
        Balance currentBalance = bankAdapter.getAccountBalances(token, id);
        Transaction tempTransaction = bankAdapter.getAccountTransactions(token, id);
        Account tempAccount = tempTransaction.getAccount();
        tempAccount.setBalance(getBalanceFromBalances(currentBalance));
        tempTransaction.setAccount(tempAccount);
        return tempTransaction;
    }

    public AccessToken authorizeING() {
        AccessToken application = ingAdapter.authorize();
        return ingAdapter.getCustomerAuthorizationToken(application.getAccess_token());
    }

    public String authorizeRABO() {
        return raboAdapter.authorize();
    }

    public BankToken token(String bank, String code) {
        BankAdapter bankAdapter = getBankAdapter(bank);
        return bankAdapter.token(code);
    }

    public BankToken refresh(String bank, String code) {
        BankAdapter bankAdapter = getBankAdapter(bank);
        return bankAdapter.refresh(code);
    }

    private BankAdapter getBankAdapter(String bank) {
        /*
        if (raboName.equals(bank)) {
            return raboAdapter;
        } else {
            return ingAdapter;
        }

         */
        return null;
    }
}
