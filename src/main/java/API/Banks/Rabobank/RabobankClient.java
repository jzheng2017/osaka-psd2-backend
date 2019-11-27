package API.Banks.Rabobank;

import API.Banks.Rabobank.Util.RaboUtil;
import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.BankToken;
import API.DTO.RABO.RaboAccount;
import API.DTO.RABO.RaboBalance;
import API.DTO.RABO.RaboTransaction;
import API.DTO.Transaction;
import com.google.gson.Gson;

public class RabobankClient {
    private static final String SCOPES = "ais.balances.read%20ais.transactions.read-90days%20ais.transactions.read-history%20caf.fundsconfirmation.read";
    private static final String CLIENT_ID = "c451778c-db2c-451e-8f57-9bb62422329e";
    private static final String OAUTH_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/oauth2";
    private static final String AIS_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/payments/account-information/ais/v3";
    private Gson gson;
    private RabobankMapper mapper;
    private RaboUtil util;

    public RabobankClient() {
        this.gson = new Gson();
        this.mapper = new RabobankMapper();
        this.util = new RaboUtil();
    }

    public String getAuthorizationUrl(String redirectUrl, String state) {
        return OAUTH_BASE + "/authorize?client_id=" + CLIENT_ID + "&scope=" + SCOPES + "&redirect_uri=" + redirectUrl + "&response_type=code&state="+state;
    }

    public BankToken token(String code) {
        String body = "grant_type=authorization_code&code=" + code;
        return util.getBankToken(code, body);
    }

    public BankToken refresh(String code) {
        String body = "grant_type=refresh_token&refresh_token=" + code;
        return util.getBankToken(code, body);
    }

    public Account getUserAccounts(String token) {
        String endpoint = "/accounts";
        String result = util.doGetRequest(AIS_BASE, endpoint, token);
        RaboAccount account = gson.fromJson(result, RaboAccount.class);
        return mapper.mapToAccount(account);
    }

    public Balance getAccountBalances(String token, String id) {
        String endpoint = "/accounts/" + id + "/balances";
        String result = util.doGetRequest(AIS_BASE, endpoint, token);
        RaboBalance balance = gson.fromJson(result, RaboBalance.class);
        return mapper.mapToBalance(balance);
    }

    public Transaction getAccountTransactions(String token, String id) {
        String endpoint = "/accounts/" + id + "/transactions?bookingStatus=booked";
        String result = util.doGetRequest(AIS_BASE, endpoint, token);
        RaboTransaction transaction = gson.fromJson(result, RaboTransaction.class);
        return mapper.mapToTransaction(transaction);
    }
}
