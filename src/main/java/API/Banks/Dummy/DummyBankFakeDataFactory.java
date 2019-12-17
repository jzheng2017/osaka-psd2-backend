package API.Banks.Dummy;

import API.DTO.Account;
import API.DTO.AccountDetails;
import API.DTO.Balance;
import API.DTO.BalanceAmount;

import java.util.ArrayList;
import java.util.List;

public class DummyBankFakeDataFactory {
    private static List<Account> accounts = new ArrayList<>();

    public static List<Account> getAccounts() {
        accounts.clear();
        Account account1 = new Account("1", "NL61QHU123812391", "John Doe", "Euro", 500.50);
        Account account2 = new Account("2", "NL61QHU123815124", "Jane Doe", "Euro", 123.51);
        Account account3 = new Account("3", "NL61QHU122351491", "James Doe", "Euro", 61.68);
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        return accounts;
    }

    public static Balance getBalanceFromAccounts(String _account) {
        Balance balance = new Balance();
        Balance balance2 = new Balance();
        ArrayList<Balance> list = new ArrayList<>();
        balance2.setBalanceAmount(new BalanceAmount("EUR", accounts.stream().filter(account -> account.getId().equals(_account)).findFirst().orElse(null).getBalance().intValue()));
        list.add(balance2);
        balance.setBalances(list);
        return balance;
    }

}
