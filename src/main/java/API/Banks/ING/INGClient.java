package API.Banks.ING;

import API.Banks.ING.Util.INGUtil;
import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.BankToken;
import API.DTO.ING.INGAccount;
import API.DTO.ING.INGBalance;
import API.DTO.ING.INGTransaction;
import API.DTO.Transaction;
import com.google.gson.Gson;

public class INGClient {
    private Gson gson;
    private INGMapper mapper;
    private INGUtil util;

    public INGClient() {
        this.util = new INGUtil();
        this.gson = new Gson();
        this.mapper = new INGMapper();
    }

    private BankToken authorize() {
        var body = "grant_type=client_credentials";
        var url = "/oauth2/token";
        var output = util.getAccessToken(body, url);
        return gson.fromJson(output, BankToken.class);
    }

    public String getAuthorizationUrl(String redirectUrl, String state) {
        return "http://localhost:8080/dummy/ing?redirect_uri="+redirectUrl+"&state="+state;
    }

    public BankToken token(String code) {
        BankToken application = authorize();

        var body = "grant_type=authorization_code&code="+code;
        var url = "/oauth2/token";
        var request = util.getCustomerAccessToken(body, application.getAccessToken(), url);
        return gson.fromJson(request, BankToken.class);
    }

    public BankToken refresh(String refreshToken) {
        return token("2c1c404c-c960-49aa-8777-19c805713edf");
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
}
