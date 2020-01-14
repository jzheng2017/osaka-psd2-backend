package API.Banks.ABNAMRO;

import API.Banks.Client;
import API.DTO.*;
import java.net.URI;
import java.util.ArrayList;

public class ABNAMROClient extends Client {
    public static final String REDIRECT_URL = "https://localhost/auth";
    public static final String AUTHORIZATION_SCOPE = "psd2:account:balance:read+psd2:account:transaction:read+psd2:account:details:read";
    public static final String PAYMENT_REQUEST_SCOPE = "psd2:payment:sepa:write";
    public static final String PAYMENT_SCOPE = "psd2:payment:sepa:write+psd2:payment:sepa:read";
    public static final String ACCOUNTS = "/accounts/";
    public static final String TRANSACTIONS = "/transactions";
    public static final String CLIENT_ID = "TPP_test";

    private ABNAMROUtil abnamroUtil;
    private ABNAMROMapper abnamroMapper;

    public ABNAMROClient() {
        this.abnamroUtil = new ABNAMROUtil();
        this.abnamroMapper = new ABNAMROMapper();
    }

    public void setAbnamroUtil(ABNAMROUtil abnamroUtil) {
        this.abnamroUtil = abnamroUtil;
    }

    @Override
    public URI getAuthorizationUrl(String redirectUrl, String state) {
        return abnamroUtil.getAuthorizationUrl("scope="+AUTHORIZATION_SCOPE+"&state=" + state);
    }

    @Override
    public BankToken token(String code) {
        var payload = "grant_type=authorization_code&client_id=" + CLIENT_ID + "&code=" + code + "&redirect_uri=" + REDIRECT_URL;
        var response = abnamroUtil.getBankToken(payload);
        return responseToBankToken(response);
    }

    @Override
    public BankToken refresh(String code) {
        var payload = "grant_type=refresh_token&client_id=" + CLIENT_ID + "&refresh_token=" + code;
        return responseToBankToken(abnamroUtil.getBankToken(payload));
    }

    private Account getUserAccount(String token) {
        var iban = abnamroUtil.get("/consentinfo", token).get("iban").getAsString();
        var json = abnamroUtil.get(ACCOUNTS + iban + "/details", token);
        return abnamroMapper.mapToUser(json);
    }

    private ArrayList<Transaction> getAccountTransactions(String token, Account account) {
        var response = abnamroUtil.get(ACCOUNTS + account.getIban() + TRANSACTIONS, token);
        return abnamroMapper.mapToTransactions(response, account);
    }

    @Override
    public ArrayList<Account> getUserAccounts(String token) {
        var accounts = new ArrayList<Account>();
        accounts.add(getUserAccount(token));
        return accounts;
    }

    @Override
    public Number getBalance(String token, String id) {
        var balanceJson = abnamroUtil.get(ACCOUNTS + id + "/balances", token);
        return abnamroMapper.mapToBalance(balanceJson);
    }

    @Override
    public AccountDetails getAccountDetails(String token, String id) {
        var details = new AccountDetails();

        var account = getUserAccount(token);
        details.setAccount(account);

        var transactions = getAccountTransactions(token, account);
        details.setTransactions(transactions);

        return details;
    }

    @Override
    public TransactionResponse initiateTransaction(String token, PaymentRequest paymentRequest) {
        var payload = "grant_type=client_credentials&client_id=" + CLIENT_ID + "&scope="+PAYMENT_REQUEST_SCOPE;
        var bankToken = responseToBankToken(abnamroUtil.getBankToken(payload));

        var object = abnamroMapper.parsePaymentRequest(paymentRequest);

        var transaction = abnamroUtil.post("/payments", gson.toJson(object), bankToken.getAccessToken());
        var id = abnamroMapper.mapToTransactionId(transaction);
        var url = abnamroUtil.getAuthorizationUrl("scope="+PAYMENT_SCOPE+"&transactionId="+id+"&state="+id);
        return abnamroMapper.mapToTransactionResponse(url);
    }

    @Override
    public void revoke(String refreshToken) {
        // Niet mogelijk binnen de ABN AMRO
    }

    @Override
    public boolean isPaymentToken(String token) {
        var consentJson = abnamroUtil.get("/consentinfo", token);

        if (consentJson.has("scopes")) {
            var scopes = consentJson.get("scopes").getAsString();
            return scopes.contains("psd2:payment:sepa:write psd2:payment:sepa:read");
        }

        return false;
    }

    @Override
    public Payment pay(String token, String id) {
        var json = abnamroUtil.put("/payments/"+id, token);
        return abnamroMapper.mapToPayment(json);
    }
}