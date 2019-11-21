package API.RABO.Service;

import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.RABO.RaboAccount;
import API.DTO.RABO.RaboBalance;
import API.DTO.RABO.RaboTransaction;
import API.DTO.Transaction;
import API.RABO.RaboMapper;
import API.RSA;
import com.google.gson.Gson;
import io.netty.handler.ssl.SslContextBuilder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.IOUtils;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.SslProvider;

import javax.ws.rs.core.MediaType;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.PrivateKey;
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
    private RaboMapper mapper = new RaboMapper();

    public RabobankService() {
        try {
            InputStream certificate = getClass().getClassLoader().getResourceAsStream(CERT_PATH);
            InputStream key = getClass().getClassLoader().getResourceAsStream(KEY_PATH);
            File certificateFile = File.createTempFile("certificate", ".pem");
            File keyFile = File.createTempFile("key", ".pem");
            FileOutputStream out = new FileOutputStream(certificateFile);
            FileOutputStream out2 = new FileOutputStream(keyFile);
            IOUtils.copy(certificate, out);
            IOUtils.copy(key, out2);

            httpClient = HttpClient.create().secure(sslContextSpec -> {
                SslContextBuilder sslContextBuilder = SslContextBuilder.forClient();
                sslContextBuilder.keyManager(certificateFile, keyFile);
                sslContextSpec.sslContext(sslContextBuilder).defaultConfiguration(SslProvider.DefaultConfigurationType.TCP);
            });

            certificateFile.deleteOnExit();
            keyFile.deleteOnExit();
        } catch (IOException excep) {
            System.out.println(excep.getMessage());
        }
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
        System.out.println(result);
        return mapper.mapToAccount(account);
    }

    public Balance getAccountBalances(String token, String id) {
        String endpoint = "/accounts/" + id + "/balances";
        String result = doGetRequest(token, endpoint);
        System.out.println(result);
        RaboBalance balance = gson.fromJson(result, RaboBalance.class);
        Balance mappedBalance = new Balance();
        return mappedBalance;
    }

    public Transaction getAccountTransactions(String token, String id) {
        String endpoint = "/accounts/" + id + "/transactions?bookingStatus=booked";
        String result = doGetRequest(token, endpoint);
        System.out.println(result);
        RaboTransaction transaction = gson.fromJson(result, RaboTransaction.class);
        Transaction mappedTransaction = new Transaction();
        return mappedTransaction;
    }

    private String doGetRequest(String token, String endpoint) {
        try {
            var date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC));
            var digest = generateDigest("");
            var requestId = UUID.randomUUID().toString();
            InputStream inputStream = getClass().getClassLoader().getResourceAsStream(CERT_PATH);
            String input = IOUtils.toString(inputStream, StandardCharsets.UTF_8.name());
            return httpClient
                    .headers(h -> h.set("Authorization", "Bearer " + token))
                    .headers(h -> h.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED))
                    .headers(h -> h.set("x-ibm-client-id", CLIENT_ID))
                    .headers(h -> h.set("Accept", MediaType.APPLICATION_JSON))
                    .headers(h -> h.set("date", date))
                    .headers(h -> h.set("digest", digest))
                    .headers(h -> h.set("x-request-id", requestId))
                    .headers(h -> h.set("tpp-signature-certificate", RSA.getCertificateFromString(input)))
                    .headers(h -> h.set("signature", generateSignatureHeader(date, digest, requestId)))
                    .get()
                    .uri(API_BASE + endpoint)
                    .responseContent()
                    .aggregate()
                    .asString()
                    .block();

        } catch (IOException excep) {
            System.out.println(excep.getMessage());
        }
        return null;
    }

    private String generateSignatureHeader(String date, String digest, String requestId) {
        try {
            String string = "date: " + date + "\n" + "digest: " + digest + "\n" + "x-request-id: " + requestId;
            InputStream input = getClass().getClassLoader().getResourceAsStream(KEY_PATH);
            var privateKey = RSA.getPrivateKeyFromString(IOUtils.toString(input, StandardCharsets.UTF_8.name()));
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
