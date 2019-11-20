package API.Services;

import API.Adapter.BankAdapter;
import API.Adapter.INGAdapter;
import API.Adapter.RaboAdapter;
import API.DTO.*;

public class AccountService {
    public Account getUserAccounts(String bank, String token) {
        BankAdapter bankAdapter = BankAdapter.getBankAdapter(bank);
        return bankAdapter.getUserAccounts(token);
    }

    public Balance getAccountBalances(String bank , String token, String id) {
        BankAdapter bankAdapter = BankAdapter.getBankAdapter(bank);
        return bankAdapter.getAccountBalances(token,id);
    }

    public Transaction getAccountTransactions(String bank, String token, String id) {
        BankAdapter bankAdapter = BankAdapter.getBankAdapter(bank);
        return bankAdapter.getAccountTransactions(token,id);
    }

    public String authorizeING() {
        INGAdapter bankAdapter = new INGAdapter();
        return bankAdapter.authorize();
    }

    public String authorizeRABO() {
        RaboAdapter bankAdapter = new RaboAdapter();
        return bankAdapter.authorize();
    }


    public String token(String bank, String code) {
        BankAdapter bankAdapter = BankAdapter.getBankAdapter(bank);
        return bankAdapter.token(code);
    }
}
