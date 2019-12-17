package API.Banks.Dummy;

import API.DTO.*;

import java.util.ArrayList;
import java.util.List;

public class DummyBankFakeDataFactory {
    private ArrayList<Account> accounts;

    public DummyBankFakeDataFactory() {
        accounts = getAccounts();
    }

    public Account getAccount(String id) {
        return accounts.stream().filter(account -> account.getId().equals(id)).findFirst().orElse(null);
    }

    public ArrayList<Account> getAccounts() {
        ArrayList<Account> accounts = new ArrayList<>();
        Account account1 = new Account("1", "NL61QHU123812391", "John Doe", "Euro", 500.50);
        Account account2 = new Account("2", "NL61QHU123815124", "Jane Doe", "Euro", 123.51);
        Account account3 = new Account("3", "NL61QHU122351491", "James Doe", "Euro", 61.68);
        accounts.add(account1);
        accounts.add(account2);
        accounts.add(account3);
        return accounts;
    }

    public ArrayList<Transaction> getTransactions(Account account) {
        ArrayList<Transaction> transactions = new ArrayList<>();
        switch (account.getId()) {
            case "1":
                Account account2 = new Account("7 ", "NL61QHU123812391", "John Doe",  "Euro", 500.50);
                Transaction transaction1 = new Transaction("10-10-2000", TransactionTypes.INCASSO, account2, account, true, "250");
                Transaction transaction2 = new Transaction("10-10-2000", TransactionTypes.INCOME, account2, account, false, "500");
                Transaction transaction6 = new Transaction("10-10-2000", TransactionTypes.TAX, account2, account, true, "250");
                transactions.add(transaction1);
                transactions.add(transaction2);
                transactions.add(transaction6);
                break;
            case "2":
                Account account3 = new Account("7 ", "NL61QHU123812391", "Belasting Dienst", "Euro", 500.50);
                Account account5 = new Account("7 ", "NL61QHU173212391", "Frank van Dam", "Euro", 500.50);
                Account account6 = new Account("7 ", "NL67HHU123812391", "Jan Dijkman", "Euro", 500.50);
                Transaction transaction3 = new Transaction("10-10-2000", TransactionTypes.INCASSO, account3, account, true, "130");
                Transaction transaction8 = new Transaction("10-10-2000", TransactionTypes.INCOME, account6, account, false, "521");
                Transaction transaction9 = new Transaction("10-10-2000", TransactionTypes.INCOME, account5, account, false, "22");
                Transaction transaction10 = new Transaction("10-10-2000", TransactionTypes.TAX, account3, account, true, "130");
                transactions.add(transaction3);
                transactions.add(transaction8);
                transactions.add(transaction9);
                transactions.add(transaction10);
                break;
            case "3":
                Account account4 = new Account("7 ", "NL61QHU123812391", "John Doe", "Euro", 500.50);
                Account account11 = new Account("7 ", "NL44QHU53812391", "Dick Maas", "Euro", 500.50);
                Transaction transaction4 = new Transaction("10-10-2000", TransactionTypes.INCASSO, account4, account, true, "440");
                Transaction transaction11 = new Transaction("10-10-2000", "Overboeking", account11, account, true, "500");
                Transaction transaction12 = new Transaction("10-10-2000", TransactionTypes.TAX, account4, account, true, "440");
                Transaction transaction13 = new Transaction("10-10-2000", TransactionTypes.TAX, account11, account, true, "500");
                transactions.add(transaction4);
                transactions.add(transaction11);
                transactions.add(transaction12);
                transactions.add(transaction13);
                break;
        }

        return transactions;
    }


    public Balance getBalanceFromAccounts(String _account) {
        Balance balance = new Balance();
        Balance balance2 = new Balance();
        ArrayList<Balance> list = new ArrayList<>();
        balance2.setBalanceAmount(new BalanceAmount("EUR", accounts.stream().filter(account -> account.getId().equals(_account)).findFirst().orElse(null).getBalance().intValue()));
        list.add(balance2);
        balance.setBalances(list);
        return balance;
    }

    public void setAccounts(ArrayList<Account> accounts) {
        this.accounts = accounts;
    }
}
