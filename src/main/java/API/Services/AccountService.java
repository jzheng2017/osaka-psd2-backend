package API.Services;

import API.Adapter.BankAdapter;
import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.Transaction;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    //TODO: deze functies returnen allemaal strings, dit moeten DTO's worden!
    public Account getUserAccounts(String bank, String token, String id) {
        BankAdapter bankAdapter = BankAdapter.getBankAdapter(bank);
        return bankAdapter.getUserAccounts(token, id);
    }

    public Balance getAccountBalances(String bank , String token, String id) {
        BankAdapter bankAdapter = BankAdapter.getBankAdapter(bank);
        return bankAdapter.getAccountBalances(token,id);
    }

    public Transaction getAccountTransactions(String bank, String token, String id) {
        BankAdapter bankAdapter = BankAdapter.getBankAdapter(bank);
        return bankAdapter.getAccountTransactions(token,id);
    }

    public String authorize(String bank) {
        BankAdapter bankAdapter = BankAdapter.getBankAdapter(bank);
        return bankAdapter.authorize();
    }
}
