package API.RABO.Service;

import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.Transaction;
import API.RSA;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;

import javax.ws.rs.core.HttpHeaders;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;

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

    public RabobankService() {
        httpClient = HttpClient.create();
    }

    public String authorize() {
        return  OAUTH_BASE + "/authorize?client_id=" + CLIENT_ID + "&scope=" + SCOPES + "&redirect_uri=" + REDIRECT_URL + "&response_type=code";
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
//        try {
//            var headers = getHeaders(token);
//            var request = new RequestEntity(headers, HttpMethod.GET, URI.create(API_BASE + "/accounts"));
//            return template.exchange(request, String.class);
//        } catch (IOException | GeneralSecurityException excep) {
//            System.out.println(excep.getMessage());
//        }
        return null;
    }

    public Balance getAccountBalances(String token, String id) {
//        try {
//            var headers = getHeaders(token);
//            var request = new RequestEntity(headers, HttpMethod.GET, URI.create(API_BASE + "/accounts/" + id + "/balances"));
//            return template.exchange(request, String.class);
//        } catch (IOException | GeneralSecurityException excep) {
//            System.out.println(excep.getMessage());
//        }
        return null;
    }

    public Transaction getAccountTransactions(String token, String id) {
//        try {
//            var headers = getHeaders(token);
//            var request = new RequestEntity(headers, HttpMethod.GET, URI.create(API_BASE + "/accounts/" + id + "/transactions?bookingStatus=booked"));
//            return template.exchange(request, String.class);
//        } catch (IOException | GeneralSecurityException excep) {
//            System.out.println(excep.getMessage());
//        }
        return null;
    }

    private HttpHeaders getHeaders(String token) throws IOException, GeneralSecurityException {
//        var headers = new HttpHeaders();
//        var date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC));
//        var digest = generateDigest("");
//        var requestId = UUID.randomUUID().toString();
//
//        headers.set("x-ibm-client-id", CLIENT_ID);
//        headers.set("authorization", "Bearer " + token);
//        headers.set("accept", "application/json");
//        headers.set("date", date);
//        headers.set("digest", digest);
//        headers.set("x-request-id", requestId);
//        headers.set("tpp-signature-certificate", RSA.getCertificate(CERT_PATH));
//        headers.set("signature", generateSignatureHeader(date, digest, requestId));
//        return headers;
        return null;
    }

    private String generateSignatureHeader(String date, String digest, String requestId) throws IOException, GeneralSecurityException {
        String string = "date: " + date + "\n" + "digest: " + digest + "\n" + "x-request-id: " + requestId;
        var privateKey = RSA.getPrivateKey(KEY_PATH);
        var signature = RSA.sign(privateKey, string.getBytes(StandardCharsets.UTF_8));
        return "keyId=\"" + KEY_ID + "\",algorithm=\"rsa-sha512\",headers=\"date digest x-request-id\",signature=\"" + signature + "\"";
    }

    private String generateDigest(String value) {
        byte[] sha = DigestUtils.sha512(value);
        return "sha-512=" + new String(Base64.encodeBase64(sha), StandardCharsets.UTF_8);
    }
}
