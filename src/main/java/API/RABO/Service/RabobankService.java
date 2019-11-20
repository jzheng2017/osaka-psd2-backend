package API.RABO.Service;

import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.RABO.RaboAccount;
import API.DTO.RABO.RaboBalance;
import API.DTO.RABO.RaboTransaction;
import API.DTO.Transaction;
import API.RSA;
import com.google.gson.Gson;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;

import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;

public class RabobankService {
    private static final String OAUTH_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/oauth2";
    private static final String API_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/payments/account-information/ais/v3";
    private static final String CLIENT_ID = "c451778c-db2c-451e-8f57-9bb62422329e";
    private static final String CLIENT_SECRET = "G3cJ8kF6qA1tR8iF7rS3hI2eD2yT6eA1bF7sF8uK0qP4lE6dQ1";
    private static final String REDIRECT_URL = "http://localhost:8080/rabo/token";
    private static final String SCOPES = "ais.balances.read%20ais.transactions.read-90days%20ais.transactions.read-history";
    private static final String CERT_PATH = "cert.pem";
    private static final String KEY_PATH = "key.pem";
    private static final String KEY_ID = "15451702564611395176";

    private HttpClient httpClient;
    private Gson gson = new Gson();

    public RabobankService() {
        httpClient = HttpClient.create();
    }

    public String authorize() {
        return OAUTH_BASE + "/authorize?client_id=" + CLIENT_ID + "&scope=" + SCOPES + "&redirect_uri=" + REDIRECT_URL + "&response_type=code";
    }

    public String token(String code) {
        String body = "grant_type=authorization_code&code=" + code;
        var authorization = Base64.encodeBase64String((CLIENT_ID + ":" + CLIENT_SECRET).getBytes());

        return httpClient
                .headers(h -> h.set("Authorization", "Basic " + authorization))
                .headers(h -> h.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED))
                .post()
                .uri(OAUTH_BASE + "/token")
                .send(ByteBufFlux.fromString(Mono.just(body)))
                .responseContent()
                .aggregate()
                .asString()
                .block();
    }

    public Account getUserAccounts(String token) {
        String endpoint = "/accounts/";
        String result = doGetRequest(token, endpoint);
        RaboAccount account = gson.fromJson(result, RaboAccount.class);
        Account mappedAccount = new Account();
        return mappedAccount;
    }

    public Balance getAccountBalances(String token, String id) {
        String endpoint = "/accounts/" + id + "/balances";
        String result = doGetRequest(token, endpoint);
        RaboBalance balance = gson.fromJson(result, RaboBalance.class);
        Balance mappedBalance = new Balance();
        return mappedBalance;
    }

    public Transaction getAccountTransactions(String token, String id) {
        String endpoint = "/accounts/" + id + "/transactions?bookingStatus=booked";
        String result = doGetRequest(token, endpoint);
        RaboTransaction transaction = gson.fromJson(result, RaboTransaction.class);
        Transaction mappedTransaction = new Transaction();
        return mappedTransaction;
    }

    private String doGetRequest(String token, String endpoint) {
        var date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC));
        var digest = generateDigest("");
        var requestId = UUID.randomUUID().toString();
        return httpClient
                .headers(h -> h.set("Authorization", "Basic " + token))
                .headers(h -> h.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED))
                .headers(h -> h.set("x-ibm-client-id", CLIENT_ID))
                .headers(h -> h.set("authorization", "Bearer " + token))
                .headers(h -> h.set("accept", "application/json"))
                .headers(h -> h.set("date", date))
                .headers(h -> h.set("digest", digest))
                .headers(h -> h.set("x-request-id", requestId))
                .headers(h -> h.set("tpp-signature-certificate", RSA.getCertificate(CERT_PATH)))
                .headers(h -> h.set("signature", generateSignatureHeader(date, digest, requestId)))
                .get()
                .uri(OAUTH_BASE + endpoint)
                .responseContent()
                .aggregate()
                .asString()
                .block();
    }

    private String generateSignatureHeader(String date, String digest, String requestId) {
        try {
            String string = "date: " + date + "\n" + "digest: " + digest + "\n" + "x-request-id: " + requestId;
            var privateKey = RSA.getPrivateKey(KEY_PATH);
            var signature = RSA.sign(privateKey, string.getBytes(StandardCharsets.UTF_8));
            return "keyId=\"" + KEY_ID + "\",algorithm=\"rsa-sha512\",headers=\"date digest x-request-id\",signature=\"" + signature + "\"";
        } catch (IOException | GeneralSecurityException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }

    private String generateDigest(String value) {
        byte[] sha = DigestUtils.sha512(value);
        return "sha-512=" + new String(Base64.encodeBase64(sha), StandardCharsets.UTF_8);
    }
}
