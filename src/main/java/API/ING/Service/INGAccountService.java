package API.ING.Service;

import API.DTO.*;
import API.DTO.ING.INGAccount;
import API.DTO.ING.INGBalance;
import API.DTO.ING.INGTransaction;
import API.Generator;
import API.ING.INGMapper;
import API.ING.Util.INGUtil;
import API.RSA;
import com.google.gson.Gson;

import javax.ws.rs.HttpMethod;
import java.io.IOException;
import java.security.GeneralSecurityException;
import java.util.UUID;


public class INGAccountService {
    private Gson gson;
    private INGMapper mapper;
    private Generator gen;
    private INGUtil util;

    public INGAccountService() {
        this.gen = new Generator();
        this.util = new INGUtil();
        this.gson = new Gson();
        this.mapper = new INGMapper();
    }

    public BankToken authorize() {
        var body = "grant_type=client_credentials";
        var url = "/oauth2/token";
        var output = util.getAccessToken(body, url);
        return gson.fromJson(output, BankToken.class);
    }

    public BankToken getAuthorizationCode(String code) {
        var body = "grant_type=authorization_code&code=2c1c404c-c960-49aa-8777-19c805713edf&redirect_uri=xxx";
        var url = "/oauth2/token";
        var request = util.getCustomerAccessToken(body, code, url);
        return gson.fromJson(request, BankToken.class);
    }

    public Account getUserAccounts(String code) {
        var url = "/v3/accounts";
        INGAccount account = gson.fromJson(util.doApiRequest(code, url), INGAccount.class);
        return mapper.mapToAccount(account);
    }

    public Balance getAccountBalances(String code, String accountID) {
        var url = "/v3/accounts/" + accountID + "/balances?balanceTypes=expected";
        INGBalance balance = gson.fromJson(util.doApiRequest(code, url), INGBalance.class);
        return mapper.mapToBalance(balance);
    }

    public Transaction getAccountTransactions(String code, String accountID) {
        var url = "/v2/accounts/" + accountID + "/transactions?dateFrom=2016-10-01&dateTo=2016-11-21&limit=10";
        INGTransaction transactions = gson.fromJson(util.doApiRequest(code, url), INGTransaction.class);
        return mapper.mapToTransaction(transactions);
    }

    public BankToken refresh(String refreshToken) {
        BankToken application = authorize();
        return getAuthorizationCode(application.getAccessToken());
    }

}
