package API.Services;

import API.Adapter.BankAdapter;
import API.DTO.Account;
import org.springframework.stereotype.Service;

@Service
public class AccountService {

    public Account getUserAccounts(String bank) {
        BankAdapter bankAdapter = BankAdapter.getBankAdapter(bank);
        return bankAdapter.getUserAccounts();
    }

    public Object getAccountBalances(String id, String bank) {
        BankAdapter bankAdapter = BankAdapter.getBankAdapter(bank);
        return bankAdapter.getAccountBalances(id);
    }

    public Object getAccountTransactions(String id, String bank) {
        BankAdapter bankAdapter = BankAdapter.getBankAdapter(bank);
        return bankAdapter.getAccountTransactions(id);
    }
}
