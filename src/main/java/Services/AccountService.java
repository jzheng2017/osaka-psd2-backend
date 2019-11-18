package Services;

import Adapter.BankAdapter;
import Adapter.INGAdapter;
import Adapter.RaboAdapter;
import dto.Account;

public class AccountService {
    public Account getUserAccounts(String bank) {
        BankAdapter bankAdapter = getBankAdapter(bank);
        return bankAdapter.getUserAccounts();
    }

    private BankAdapter getBankAdapter(String bank) {
        BankAdapter adapter;
        if ("RABO".equals(bank)) {
            adapter = new RaboAdapter();
        } else {
            adapter = new INGAdapter();
        }
        return adapter;
    }

    public Object getAccountBalances(String id, String bank) {
        BankAdapter bankAdapter = getBankAdapter(bank);
        return bankAdapter.getAccountBalances(id);
    }

    public Object getAccountTransactions(String id, String bank) {
        BankAdapter bankAdapter = getBankAdapter(bank);
        return bankAdapter.getAccountTransactions(id);
    }
}
