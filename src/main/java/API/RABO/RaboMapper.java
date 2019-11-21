package API.RABO;

import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.BalanceAmount;
import API.DTO.RABO.RaboAccount;
import API.DTO.RABO.RaboBalance;

import API.DTO.Links;

import java.util.ArrayList;

public class RaboMapper {
    public Account mapToAccount(RaboAccount raboAccount) {
        ArrayList<Account> accounts = new ArrayList<>();
        ArrayList<RaboAccount> raboAccounts = raboAccount.getAccounts();
        for (RaboAccount accountInArray : raboAccounts) {
            Links links = accountInArray.getLinks();
            Account acc = new Account(
                    accountInArray.getResourceID(),
                    accountInArray.getIban(),
                    accountInArray.getName(),
                    accountInArray.getCurrency(),
                    links.getBalances(),
                    links.getTransaction(),
                    links.getAccount());
            accounts.add(acc);
        }
        Account mappedAccount = new Account();
        mappedAccount.setAccounts(accounts);//Set accounts array to all accounts
        return mappedAccount;
    }

    public Balance mapToBalance(RaboBalance raboBalance) {
        //Mapping account
        RaboAccount raboAccount = raboBalance.getAccount();
        Account account = new Account();
        account.setIban(raboAccount.getIban());
        account.setCurrency(raboAccount.getCurrency());
        //Mapping balances
        ArrayList<RaboBalance> balances = raboBalance.getBalances();
        ArrayList<Balance> mappedBalances = new ArrayList<>();
        for (RaboBalance balanceInArray : balances) {
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
}
