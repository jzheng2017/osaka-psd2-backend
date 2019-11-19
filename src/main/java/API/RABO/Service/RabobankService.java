package API.RABO.Service;

import API.DTO.Account;
import API.DTO.AuthorizationCode;
import API.DTO.Balance;
import API.DTO.Transaction;
import API.RSA;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.stereotype.Component;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import org.springframework.web.client.RestTemplate;

import java.util.UUID;
import java.io.IOException;
import java.net.URI;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

@Component
public class RabobankService {
    private static final String OAUTH_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/oauth2";
    private static final String API_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/payments/account-information/ais/v3";
    private static final String CLIENT_ID = "c451778c-db2c-451e-8f57-9bb62422329e";
    private static final String CLIENT_SECRET = "G3cJ8kF6qA1tR8iF7rS3hI2eD2yT6eA1bF7sF8uK0qP4lE6dQ1";
    private static final String REDIRECT_URL = "http://localhost:8080/rabo/token";
    private static final String SCOPES = "ais.balances.read ais.transactions.read-90days ais.transactions.read-history";
    private static final String CERT_PATH = "src/main/resources/certs/RABO/cert.pem";
    private static final String KEY_PATH = "src/main/resources/certs/RABO/key.pem";
    private static final String KEY_ID = "15451702564611395176";

    @Autowired
    private RestTemplate template = new RestTemplate();

    public String authorize() {
       return  "redirect:" + OAUTH_BASE + "/authorize?client_id=" + CLIENT_ID + "&scope=" + SCOPES + "&redirect_uri=" + REDIRECT_URL + "&response_type=code";
    }

    public ResponseEntity<String> token(String code) {
        MultiValueMap<String, String> body = new LinkedMultiValueMap<>();
        body.add("grant_type", "authorization_code");
        body.add("code", code);

        var headers = new HttpHeaders();
        var authorization = Base64.encodeBase64String((CLIENT_ID + ":" + CLIENT_SECRET).getBytes());
        headers.set("authorization", "Basic " + authorization);
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity req = new HttpEntity<>(body, headers);
        return template.postForEntity(URI.create(OAUTH_BASE + "/token"), req, String.class);
    }

    public Account getUserAccounts(String token) {
        try {
            var headers = getHeaders(token);
            var request = new RequestEntity(headers, HttpMethod.GET, URI.create(API_BASE + "/accounts"));
            return template.exchange(request, Account.class).getBody();
        } catch (IOException | GeneralSecurityException excep) {
            System.out.println(excep.getMessage());
        }
        return null;
    }

    public Balance getAccountBalances(String token, String id) {
        try {
            var headers = getHeaders(token);
            var request = new RequestEntity(headers, HttpMethod.GET, URI.create(API_BASE + "/accounts/" + id + "/balances"));
            return template.exchange(request, Balance.class).getBody();
        } catch (IOException | GeneralSecurityException excep) {
            System.out.println(excep.getMessage());
        }
        return null;
    }

    public Transaction getAccountTransactions(String token, String id) {
        try {
            var headers = getHeaders(token);
            var request = new RequestEntity(headers, HttpMethod.GET, URI.create(API_BASE + "/accounts/" + id + "/transactions?bookingStatus=booked"));
            return template.exchange(request, Transaction.class).getBody();
        } catch (IOException | GeneralSecurityException excep) {
            System.out.println(excep.getMessage());
        }
        return null;
    }

    private HttpHeaders getHeaders(String token) throws IOException, GeneralSecurityException {
        var headers = new HttpHeaders();
        var date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC));
        var digest = generateDigest("");
        var requestId = UUID.randomUUID().toString();

        headers.set("x-ibm-client-id", CLIENT_ID);
        headers.set("authorization", "Bearer " + token);
        headers.set("accept", "application/json");
        headers.set("date", date);
        headers.set("digest", digest);
        headers.set("x-request-id", requestId);
        headers.set("tpp-signature-certificate", RSA.getCertificate(CERT_PATH));
        headers.set("signature", generateSignatureHeader(date, digest, requestId));
        return headers;
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
