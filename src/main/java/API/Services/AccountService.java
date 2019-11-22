package API.Services;

import API.Adapter.BankAdapter;
import API.Adapter.INGAdapter;
import API.Adapter.RaboAdapter;
import API.DTO.*;
import API.DTO.ING.AccessTokenING;

import java.util.ArrayList;

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

    public AccessTokenING authorizeING() {
        INGAdapter bankAdapter = new INGAdapter();
        AccessTokenING application = bankAdapter.authorize();
        AccessTokenING customer = bankAdapter.getCustomerAuthorizationToken(application.getAccess_token());
        AccessTokenING accessTokensING = new AccessTokenING();
        ArrayList<AccessTokenING> accessTokensArrayING = new ArrayList<>();
        accessTokensArrayING.add(application);
        accessTokensArrayING.add(customer);
        accessTokensING.setAccessTokenINGS(accessTokensArrayING);
        return accessTokensING;
    }

    public String authorizeRABO() {
        RaboAdapter bankAdapter = new RaboAdapter();
        return bankAdapter.authorize();
    }

    public String token(String bank, String code) {
        BankAdapter bankAdapter = BankAdapter.getBankAdapter(bank);
        return bankAdapter.token(code);
    }

    public String checkEnoughBalance(String bank, String code) {
        BankAdapter bankAdapter = BankAdapter.getBankAdapter(bank);
        return bankAdapter.checkEnoughBalance(code);
    }

    public String refresh(String bank, String code)
    {
        BankAdapter bankAdapter = BankAdapter.getBankAdapter(bank);
        return bankAdapter.refresh(code);
    }
}
