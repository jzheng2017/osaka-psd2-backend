package API.Banks.ABNAMRO;

import API.Banks.Client;
import API.Banks.Requests.Headers;
import API.DTO.*;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.ws.rs.core.MediaType;
import java.net.URI;
import java.util.ArrayList;
import java.util.HashMap;

public class ABNAMROClient extends Client {
    private static final String CERT = "MIIC9DCCAdygAwIBAgIGAWkqXD5SMA0GCSqGSIb3DQEBCwUAMDsxCzAJBgNVBAYTAk5MMQwwCgYDVQQKEwNBQkMxHjAcBgNVBAMTFVRwcFNhbmRib3hDZXJ0aWZpY2F0ZTAeFw0xOTAyMjYxNTExMjJaFw0yMDAyMjYxNTExMjJaMDsxCzAJBgNVBAYTAk5MMQwwCgYDVQQKEwNBQkMxHjAcBgNVBAMTFVRwcFNhbmRib3hDZXJ0aWZpY2F0ZTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAJkUcwLLs9dr3ngvR5ULGHoCvCpyaeVA9s/VBZ4q4ysbLAjdGux3mR18GKtGMkIeB1Dmw65vBdJWCBXxbPdeWA3ZRoC6yUCU6w5HMg0IMMq4Z5dbUs5cgvrUF1ZD12uUW/4zSQ6dw4DpyVzE2rDQ88dSGBGSC2U/Ql3aR8W2RaDL0Ii5MobKM1VtCrL2bjGKyPf4rViJZDrvFQBBH2WzlGJnDQVYxgQnINVQa1lIY+B/gNvm1iw/znAqeAN38FrNNXy6LpHXmi7viDh1/pBMbG2L6SRnuSOu79QrXaMPsaupklEHlyrY5s/SDvsjgEGC3IQNVduAL87zhjTLt+ElGPcCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAIJkFTRn6MnnQsfSJkNvzdHypH4ha43zURXypc8B5bY9VD8jzBuy5QEpuNRV8i3X22Sqldh2KZ4tp+X54f2LIfwwjd6t60eTTvPsH8MTpV376pUMB3pdCuA6EPlCij/qXJbi/UCeNA8jQAoOTd7oIwIrQrf6tyCa3d871WvFkS4GyyVuPa6QBIsbD040cT63EaB/QQc9z+1EJiAMDZ1oONotEPL2t6gQkrEmNlYgebDMx9NEvQYYMnQB6AMfYuxcieqDN4bngJS3IKnEuzn47PYrNLfdoWHuJffIv4MnTxl59WRV5pTOaZu776Qlgzm7RHdgbibfD5ASixT+VDebMoQ==";
    private static final String KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCZFHMCy7PXa954L0eVCxh6ArwqcmnlQPbP1QWeKuMrGywI3Rrsd5kdfBirRjJCHgdQ5sOubwXSVggV8Wz3XlgN2UaAuslAlOsORzINCDDKuGeXW1LOXIL61BdWQ9drlFv+M0kOncOA6clcxNqw0PPHUhgRkgtlP0Jd2kfFtkWgy9CIuTKGyjNVbQqy9m4xisj3+K1YiWQ67xUAQR9ls5RiZw0FWMYEJyDVUGtZSGPgf4Db5tYsP85wKngDd/BazTV8ui6R15ou74g4df6QTGxti+kkZ7kjru/UK12jD7GrqZJRB5cq2ObP0g77I4BBgtyEDVXbgC/O84Y0y7fhJRj3AgMBAAECggEARGydlAxVkN8IjBQmHPrer/r0/MwzhWPqbq+7WR22eRgmMLgURsqWyFUl+bjg0ij2ADWGFjxOD9ygtJ47pL6pAVezaesT9igagUFVn/mfRZ3zv/X0J4W2jkOrQsYETnP8Qr3N1Bi0wLS/axYa4pojvV52n7P2IAWMtsLQ/hEhQmPm7IqInzsIEtZTOfOcTqOK1xe3nXaouZVl2jXhJlBAv4tsO7e9RmWSeanB5qSZ0SmNSurc/9ukFoi9s/SuW5yPxe/LmHMr6CCu5U5spzW5XccWSlnEZ2wxm+Cnipu6AXnaTAhPqDMJLGCYrxqOt3HJCCSIwESx9Nst2sS32ij+gQKBgQDOVVxRKwBzHwGhEPAGmdmFYKoxKE0QTmrxBlhPpAx1F1S0e9cxyRqIh1BIL/itdgbQgNyI5yKIKrgt+gFOKUP04Vzu5BBDYxhtCkdf184kqe2VweTLGVGMho/P2I026BHw2/VLO37yng71eP4RC+VLroZQBed/2z1hWrijBah6FwKBgQC97YIQM5v27/HjPgazE+jmp7Iw2RaJ7cGLrS70zt8T1Fec75ze9x9S3xDVJ1T8pP5E5Zq0pVwpJsPtQfvdkEsxhN+1X5fLuuYg1+Y1hh5SOGcB31a0NoJK2dBOuC6CjHfOJkaBjY5XQ8Q1fkXSm1DDan7+GiHnrPxUThafw8MEIQKBgG4gS0SbQgMvwmvYIXQ0e0/f9xaDnxYb9KIuM8ZWFbwNNs2Z55KP9pR2PFg7GmxiuWJh1NNRIjIxMtp/PGEeT0INYs+ydCezZV8VhGDYSxNwivlKYrYwDkGFtI5H059BoAnBLJv55ljSGcPUzy4D/l81iER/0j6AorMqe6+vHmwDAoGBAIbmbKw/S/cQJJnIU4/cg19ZGyqw9t5O/lrMTn7ZdP8ronM4ig6gLiJ5iAYuIqI0OtoKz2Ch1xzviNg7Nr7/nzjz7MVxuWqePJh1YPEBawXxQ9DDplzoHpE1tkxDa92UEgBdlVSti72Vx4ZLQyK86Jd0S/EF9LEOYEctE8q0jA6hAoGANPZaEQ0Fljrfg7gKJugKAOC2QXqCzKAejwW6MaZP4b4Mb+sQBBI6J8L8KW5c0U/kYE3tNNk29qeo0jV5/q9XeCzGzWS+tpHdePx9P9WevQeoNNPAKVmBs+8JO5IR126H5N+Qq3VHAD19V5AKHL4daI2epGY4MIV94xxhI6IvEzk=";

    private static final String API_KEY = "ZVOCL4Il81X0OSefId9yAxTJVl5vGWnh";
    private static final String BANK_ID = "NLAA01";
    private static final String CLIENT_ID = "TPP_test";
    private static final String BASE_URL = "https://api-sandbox.abnamro.com/v1";
    private static final String OAUTH_BASE = "https://auth-sandbox.connect.abnamro.com:8443/as";
    private static final String OAUTH_URL = OAUTH_BASE + "/token.oauth2";
    private static final String AUTHORIZATION_URL = "https://auth-sandbox.connect.abnamro.com/as/authorization.oauth2";
    private static final String REDIRECT_URL = "https://localhost/auth";
    private static final String ACCOUNTS = "/accounts/";
    private static final String TRANSACTION_ID = "transactionId";
    private static final String AMOUNT = "amount";
    //Some of these should be moved to an external class, like BankJsonAttributes

    @Override
    protected String getPrivateKey() {
        return KEY;
    }

    @Override
    protected String getCertificate() {
        return CERT;
    }

    @Override
    public URI getAuthorizationUrl(String redirectUrl, String state) {
        return URI.create(AUTHORIZATION_URL + "?scope=psd2:account:balance:read+psd2:account:transaction:read+psd2:account:details:read&client_id=" + CLIENT_ID + "&response_type=code&flow=code&redirect_uri=" + REDIRECT_URL + "&bank=" + BANK_ID + "&state=" + state);
    }

    @Override
    public BankToken token(String code) {
        var payload = "grant_type=authorization_code&client_id=" + CLIENT_ID + "&code=" + code + "&redirect_uri=" + REDIRECT_URL;
        var output = webClient.post(OAUTH_URL, getAuthorizationHeaders(), payload);
        return responseToBankToken(output);
    }

    @Override
    public BankToken refresh(String code) {
        var payload = "grant_type=refresh_token&client_id=" + CLIENT_ID + "&refresh_token=" + code;
        var output = webClient.post(OAUTH_URL, getAuthorizationHeaders(), payload);
        return responseToBankToken(output);
    }

    private Account getUserAccount(String token) {
        var accountJson = webClient.get(BASE_URL + "/consentinfo", getDefaultHeaders(token));
        var iban = accountJson.get("iban").getAsString();

        var detailsJson = webClient.get(BASE_URL + ACCOUNTS + iban + "/details", getDefaultHeaders(token));

        var name = detailsJson.get("accountHolderName").getAsString();
        var currency = detailsJson.get("currency").getAsString();

        var account = new Account();
        account.setId(iban);
        account.setIban(iban);
        account.setName(name);
        account.setCurrency(currency);

        return account;
    }

    private ArrayList<Transaction> getAccountTransactions(String token, Account account) {
        var transactions = new ArrayList<Transaction>();
        var transactionsRespose = webClient.get(BASE_URL + ACCOUNTS + account.getIban() + "/transactions?bookDateFrom=2019-02-22&bookDateTo=2019-12-17", getDefaultHeaders(token));
        var transactionsJson = transactionsRespose.get("transactions").getAsJsonArray();

        for (JsonElement transactionElement : transactionsJson) {
            var transactionJson = transactionElement.getAsJsonObject();

            var transaction = createTransaction(transactionJson);

            transaction.setReceived(false);
            transaction.setSender(account);
            transaction.setBooked(true);

            transaction.setReceiver(getReceiver(transactionJson));

            transactions.add(transaction);
        }

        return transactions;
    }

    private Transaction createTransaction(JsonObject transactionJson) {
        Transaction transaction = new Transaction();
        if (transactionJson.has(TRANSACTION_ID))
            transaction.setId(transactionJson.get(TRANSACTION_ID).getAsString());

        if (transactionJson.has("bookDate"))
            transaction.setDate(transactionJson.get("bookDate").getAsString());

        if (transactionJson.has(AMOUNT))
            transaction.setAmount(transactionJson.get(AMOUNT).getAsString());

        if (transactionJson.has("descriptionLines")) {
            var descriptionLines = transactionJson.get("descriptionLines").getAsJsonArray();
            if (descriptionLines.size() > 0) {
                var type = descriptionLines.get(0).getAsString();
                transaction.setType(type);
            }
            transaction.setAmount(transactionJson.get(AMOUNT).getAsString());
        }
        return transaction;
    }

    private Account getReceiver(JsonObject transactionJson) {
        var receiver = new Account();

        if (transactionJson.has("counterPartyAccountNumber"))
            receiver.setIban(transactionJson.get("counterPartyAccountNumber").getAsString());

        if (transactionJson.has("counterPartyName"))
            receiver.setName(transactionJson.get("counterPartyName").getAsString());
        return receiver;
    }

    @Override
    public ArrayList<Account> getUserAccounts(String token) {
        var accounts = new ArrayList<Account>();
        accounts.add(getUserAccount(token));
        return accounts;
    }

    @Override
    public Number getBalance(String token, String id) {
        var balanceJson = webClient.get(BASE_URL + ACCOUNTS + id + "/balances", getDefaultHeaders(token));
        return balanceJson.get(AMOUNT).getAsNumber();
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
        var payload = "grant_type=client_credentials&client_id=" + CLIENT_ID + "&scope=psd2:payment:sepa:write";
        var bankToken = responseToBankToken(webClient.post(OAUTH_URL, getAuthorizationHeaders(), payload));

        var object = new JsonObject();
        var receiver = paymentRequest.getReceiver();
        var sender = paymentRequest.getSender();

        object.addProperty("initiatingpartyAccountNumber", sender.getIban());
        object.addProperty("counterpartyAccountNumber", receiver.getIban());
        object.addProperty(AMOUNT, paymentRequest.getAmount());
        object.addProperty("counterpartyName", receiver.getName());
        object.addProperty("remittanceInfo", paymentRequest.getInformation());

        var transaction = webClient.post(BASE_URL + "/payments", getDefaultHeaders(bankToken.getAccessToken()), gson.toJson(object));
        var id = transaction.get(TRANSACTION_ID).getAsString();

        var url = AUTHORIZATION_URL + "?scope=psd2:payment:sepa:write+psd2:payment:sepa:read&client_id=" + CLIENT_ID + "&transactionId=" + id + "&response_type=code&flow=code&redirect_uri=" + REDIRECT_URL + "&bank=" + BANK_ID + "&state=" + id;

        var response = new TransactionResponse();
        response.setUrl(URI.create(url));

        return response;
    }

    private HashMap<String, String> getDefaultHeaders(String token) {
        var headers = new HashMap<String, String>();

        headers.put(Headers.AUTHORIZATION, Headers.BEARER + token);
        headers.put(Headers.CONTENT_TYPE, MediaType.APPLICATION_JSON);
        headers.put("API-Key", API_KEY);

        return headers;
    }

    private HashMap<String, String> getAuthorizationHeaders() {
        var headers = new HashMap<String, String>();
        headers.put(Headers.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED);
        return headers;
    }

    @Override
    public boolean isPaymentToken(String token) {
        var consentJson = webClient.get(BASE_URL + "/consentinfo", getDefaultHeaders(token));

        if (consentJson.has("scopes")) {
            var scopes = consentJson.get("scopes").getAsString();
            return scopes.contains("psd2:payment:sepa:write psd2:payment:sepa:read");
        }

        return false;
    }

    @Override
    public Payment pay(String token, String id) {
        var responseJson = webClient.put(BASE_URL + "/payments/" + id, getDefaultHeaders(token));

        var response = new Payment();

        if (responseJson.has("status"))
            response.setPaid("EXECUTED".equals(responseJson.get("status").getAsString()));

        if (responseJson.has(TRANSACTION_ID))
            response.setId(TRANSACTION_ID);

        return response;
    }
}