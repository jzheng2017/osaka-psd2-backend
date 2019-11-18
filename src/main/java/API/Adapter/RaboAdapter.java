package API.Adapter;

import API.DTO.Balance;
import API.DataSource.Transaction;
import API.DTO.Account;
import org.springframework.stereotype.Component;

@Component("RaboAdapter")
public class RaboAdapter extends BankAdapter {
    @Override
    public Account getUserAccounts() {
        return null;
    }

    @Override
    public Balance getAccountBalances(String id) {
        return null;
    }

    @Override
    public Transaction getAccountTransactions(String id) {
        return null;
    }
}
