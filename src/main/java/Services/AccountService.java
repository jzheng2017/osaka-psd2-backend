package Services;

import Adapter.BankAdapter;
import dto.Account;
import org.springframework.beans.factory.annotation.Autowired;

public class AccountService {
    @Autowired
    private BankAdapter bankPicker;

    public Account getUserAccounts(String bank) {
        BankAdapter bankAdapter = bankPicker.getBankAdapter(bank);
        return bankAdapter.getUserAccounts();
    }

    public Object getAccountBalances(String id, String bank) {
        BankAdapter bankAdapter = bankPicker.getBankAdapter(bank);
        return bankAdapter.getAccountBalances(id);
    }

    public Object getAccountTransactions(String id, String bank) {
        BankAdapter bankAdapter = bankPicker.getBankAdapter(bank);
        return bankAdapter.getAccountTransactions(id);
    }
}
