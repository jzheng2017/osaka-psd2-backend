package API.ING;

import API.Adapter.INGAdapter;
import API.DTO.Account;
import API.DTO.ING.INGAccount;
import API.DTO.RABO.RaboAccount;

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
                    accountInArray.getCurrency());
            accounts.add(acc);
        }
        Account mappedAccount = new Account();
        mappedAccount.setAccounts(accounts);//Set accounts array to all accounts
        return mappedAccount;
    }
}
