package API.Banks.Dummy;

import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.BalanceAmount;
import API.DTO.Transaction;

import java.util.ArrayList;
import java.util.List;

public class DummyBankFakeDataFactory {
    private static List<Account> accounts = new ArrayList<>();
    private static List<Transaction> transactions = new ArrayList<>();

    public static Account getAccount(String id){
        return accounts.stream().filter(account -> account.getId().equals(id)).findFirst().orElse(null);
    }

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

    public static List<Transaction> getTransactions(Account account){
        transactions.clear();

        switch (account.getId()){
            case "1":
                Account account2 = new Account("7 ", "NL61QHU123812391", "John Doe", "Euro", 500.50);
                Transaction transaction1 = new Transaction("10-10-2000", "inkomen", account2, account, "ja", "250");
                Transaction transaction2 = new Transaction("10-10-2000", "inkomen", account2, account, "nee", "500");
                Transaction transaction6 = new Transaction("10-10-2000", "inkomen", account2, account, "ja", "250");
                transactions.add(transaction1);
                transactions.add(transaction2);
                transactions.add(transaction6);
                break;
            case "2":
                Account account3 = new Account("7 ", "NL61QHU123812391", "Belasting Dienst", "Euro", 500.50);
                Account account5 = new Account("7 ", "NL61QHU173212391", "Frank van Dam", "Euro", 500.50);
                Account account6 = new Account("7 ", "NL67HHU123812391", "Jan Dijkman", "Euro", 500.50);
                Transaction transaction3 = new Transaction("10-10-2000", "uitgave", account3, account, "ja", "130");
                Transaction transaction8 = new Transaction("10-10-2000", "inkomen", account6, account, "nee", "521");
                Transaction transaction9 = new Transaction("10-10-2000", "inkomen", account5, account, "nee", "22");
                Transaction transaction10 = new Transaction("10-10-2000", "uitgave", account3, account, "ja", "130");
                transactions.add(transaction3);
                transactions.add(transaction8);
                transactions.add(transaction9);
                transactions.add(transaction10);
                break;
            case "3":
                Account account4 = new Account("7 ", "NL61QHU123812391", "John Doe", "Euro", 500.50);
                Account account11 = new Account("7 ", "NL44QHU53812391", "Dick Maas", "Euro", 500.50);
                Transaction transaction4 = new Transaction("10-10-2000", "inkomen", account4, account, "ja", "440");
                Transaction transaction11 = new Transaction("10-10-2000", "inkomen", account11, account, "ja", "500");
                Transaction transaction12 = new Transaction("10-10-2000", "inkomen", account4, account, "ja", "440");
                Transaction transaction13 = new Transaction("10-10-2000", "inkomen", account11, account, "ja", "500");
                transactions.add(transaction4);
                transactions.add(transaction11);
                transactions.add(transaction12);
                transactions.add(transaction13);
                break;
        }

        return transactions;
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
