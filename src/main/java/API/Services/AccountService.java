package API.Services;

import API.Adapter.BankAdapter;
import API.DTO.Account;
import org.springframework.stereotype.Service;

import javax.ws.rs.core.Response;

@Service
public class AccountService {

    public Response getUserAccounts(String bank) {
        BankAdapter bankAdapter = BankAdapter.getBankAdapter(bank);
        return bankAdapter.getUserAccounts();
    }

    public Response getAccountBalances(String id, String bank) {
        BankAdapter bankAdapter = BankAdapter.getBankAdapter(bank);
        return bankAdapter.getAccountBalances(id);
    }

    public Response getAccountTransactions(String id, String bank) {
        BankAdapter bankAdapter = BankAdapter.getBankAdapter(bank);
        return bankAdapter.getAccountTransactions(id);
    }
}
