package API.Banks.ABNAMRO;

import API.Banks.Client;
import API.DTO.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.net.URI;
import java.util.ArrayList;

public class ABNAMROClient implements Client {
    private static final String BASE_URL = "https://api-sandbox.abnamro.com/v1";
    private static final String OAUTH_URL = "https://auth-sandbox.connect.abnamro.com:8443/as/token.oauth2";
    private static final String REDIRECT_URL = "https://localhost/auth";
    private ABNAMROUtil util;

    public ABNAMROClient() {
        util = new ABNAMROUtil();
    }

    @Override
    public URI getAuthorizationUrl(String redirectUrl, String state) {
        return URI.create("https://auth-sandbox.connect.abnamro.com/as/authorization.oauth2?scope=psd2:account:balance:read+psd2:account:transaction:read+psd2:account:details:read&client_id=TPP_test&response_type=code&flow=code&redirect_uri="+REDIRECT_URL+"&bank=NLAA01&state="+state);
    }

    private BankToken responseToBankToken(JsonObject response) {
        var bankToken = new BankToken();

        if(response.has("access_token"))
            bankToken.setAccessToken(response.get("access_token").getAsString());

        if(response.has("refresh_token"))
            bankToken.setRefreshToken(response.get("refresh_token").getAsString());

        return bankToken;
    }

    @Override
    public BankToken token(String code) {
        var payload = "grant_type=authorization_code&client_id=TPP_test&code="+code+"&redirect_uri=https://localhost/auth";
        var output = util.post(OAUTH_URL, payload);
        return responseToBankToken(output);
    }

    @Override
    public BankToken refresh(String code) {
        var payload = "grant_type=refresh_token&client_id=TPP_test&refresh_token="+code;
        var output = util.post(OAUTH_URL, payload);
        return responseToBankToken(output);
    }

    private Account getUserAccount(String token) {
        var accountJson = util.get(token, BASE_URL+"/consentinfo");
        var iban = accountJson.get("iban").getAsString();

        var detailsJson = util.get(token, BASE_URL+"/accounts/"+iban+"/details");
        var balanceJson = util.get(token, BASE_URL+"/accounts/"+iban+"/balances");

        var name = detailsJson.get("accountHolderName").getAsString();
        var currency = detailsJson.get("currency").getAsString();
        var amount = balanceJson.get("amount").getAsNumber();

        var account = new Account();
        account.setId(iban);
        account.setIban(iban);
        account.setName(name);
        account.setBalance(amount.doubleValue());
        account.setType("Betaalrekening");
        account.setCurrency(currency);

        return account;
    }

    private ArrayList<Transaction> getAccountTransactions(String token, Account account) {
        var transactions = new ArrayList<Transaction>();
        var transactionsRespose = util.get(token, BASE_URL+"/accounts/"+account.getIban()+"/transactions?bookDateFrom=2019-02-22&bookDateTo=2019-12-16");
        var transactionsJson = transactionsRespose.get("transactions").getAsJsonArray();

        for(JsonElement transactionElement : transactionsJson) {
            var transaction = new Transaction();
            var transactionJson = transactionElement.getAsJsonObject();

            if(transactionJson.has("transactionId"))
                transaction.setId(transactionJson.get("transactionId").getAsString());

            if(transactionJson.has("bookDate"))
                transaction.setDate(transactionJson.get("bookDate").getAsString());

            if(transactionJson.has("amount"))
                transaction.setAmount(transactionJson.get("amount").getAsString());

            if(transactionJson.has("descriptionLines")) {
                var descriptionLines = transactionJson.get("descriptionLines").getAsJsonArray();
                if(descriptionLines.size() > 0) {
                    var type = descriptionLines.get(0).getAsString();
                    transaction.setType(type);
                }
                transaction.setAmount(transactionJson.get("amount").getAsString());
            }

            transaction.setReceived(true);
            transaction.setReceiver(account);

            var sender = new Account();

            if(transactionJson.has("counterPartyAccountNumber"))
                sender.setIban(transactionJson.get("counterPartyAccountNumber").getAsString());

            if(transactionJson.has("counterPartyName"))
                sender.setName(transactionJson.get("counterPartyName").getAsString());

            transaction.setSender(sender);

            transactions.add(transaction);
        }

        return transactions;
    }

    @Override
    public ArrayList<Account> getUserAccounts(String token) {
        var accounts = new ArrayList<Account>();
        accounts.add(getUserAccount(token));
        return accounts;
    }

    @Override
    public Balance getAccountBalances(String token, String id) {
        var balance = new Balance();
        var balanceAmount = new BalanceAmount();
        balanceAmount.setAmount(500);
        balance.setBalanceAmount(balanceAmount);

        var balanceGroot = new Balance();
        var balances = new ArrayList<Balance>();
        balances.add(balance);
        balanceGroot.setBalances(balances);

        return balanceGroot;
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
        return null;
    }

    @Override
    public void revoke(String refreshToken) {

    }
}
