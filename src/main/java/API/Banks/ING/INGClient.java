package API.Banks.ING;

import API.Banks.ING.Util.INGUtil;
import API.DTO.*;
import API.DTO.ING.INGTransaction;
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
        return "http://localhost:8080/dummy/ing?redirect_uri=" + redirectUrl + "&state=" + state;
    }

    public BankToken token(String code) {
        BankToken application = authorize();

        var body = "grant_type=authorization_code&code=" + code;
        var url = "/oauth2/token";
        var request = util.getCustomerAccessToken(body, application.getAccessToken(), url);
        return gson.fromJson(request, BankToken.class);
    }

    public BankToken refresh(String refreshToken) {
        return token("2c1c404c-c960-49aa-8777-19c805713edf");
    }

    public Account getUserAccounts(String code) {
        var url = "/v3/accounts";
        var response = util.doApiRequest(code, url);
        return gson.fromJson(response, Account.class);
    }

    public Balance getAccountBalances(String code, String accountID) {
        var url = "/v3/accounts/" + accountID + "/balances?balanceTypes=expected";
        return gson.fromJson(util.doApiRequest(code, url), Balance.class);
    }

    public Transaction getAccountTransactions(String code, String accountID) {
        var url = "/v2/accounts/" + accountID + "/transactions?dateFrom=2016-10-01&dateTo=2016-11-21&limit=10";
        var result = util.doApiRequest(code, url);
        INGTransaction transactions = gson.fromJson(result, INGTransaction.class);
        return mapper.mapToTransaction(transactions);
    }

    //------Payment Initiation--------
    public boolean isRequestedAmountAvailable(String token, PaymentRequest paymentRequest) {
        Account accountToCheckFunds = getAccountByIban(token, paymentRequest.getIban());
        if (accountToCheckFunds != null) {
            Balance balance = getAccountBalances(token,accountToCheckFunds.getId());
            return util.getBalanceFromBalances(balance) >= paymentRequest.getBedrag();
        } else
            return false;
    }

    private Account getAccountByIban(String token, String iban) {
        Account accountsToSearch = getUserAccounts(token);
        return util.getAccountByIban(accountsToSearch, iban);
    }

    public String paymentRequest(String token, PaymentRequest paymentRequest) {
        var url = "/v1/payments/sepa-credit-transfers";
        var body = util.buildPaymentRequest(paymentRequest);
        return util.doAPIPostRequest(authorize().getAccessToken(), url,body,"payment", "156.114.161.8");
    }

}
