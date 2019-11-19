package API.Services;

import API.Adapter.BankAdapter;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

@Service
public class AccountService {
    public ResponseEntity<String> getUserAccounts(String bank, String token) {
        BankAdapter bankAdapter = BankAdapter.getBankAdapter(bank);
        return bankAdapter.getUserAccounts(token);
    }

    public ResponseEntity<String> getAccountBalances(String bank , String token, String id) {
        BankAdapter bankAdapter = BankAdapter.getBankAdapter(bank);
        return bankAdapter.getAccountBalances(token,id);
    }

    public ResponseEntity<String> getAccountTransactions(String bank, String token, String id) {
        BankAdapter bankAdapter = BankAdapter.getBankAdapter(bank);
        return bankAdapter.getAccountTransactions(token,id);
    }

    public String authorize(String bank) {
        BankAdapter bankAdapter = BankAdapter.getBankAdapter(bank);
        return bankAdapter.authorize();
    }

    public ResponseEntity<String> token(String bank, String code) {
        BankAdapter bankAdapter = BankAdapter.getBankAdapter(bank);
        return bankAdapter.token(code);
    }
}
