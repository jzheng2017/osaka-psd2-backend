package API.RABO.Util;

import API.DTO.BankToken;
import API.Generator;
import API.RSA;
import com.google.gson.Gson;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.ssl.SslContextBuilder;
import org.apache.commons.codec.binary.Base64;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.SslProvider;

import javax.inject.Inject;
import javax.ws.rs.core.MediaType;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.UUID;


public class RaboUtil {
    private static final String OAUTH_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/oauth2";
    private static final String AIS_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/payments/account-information/ais/v3";
    private static final String AF_BASE = "https://api-sandbox.rabobank.nl/openapi/sandbox/payments/confirmation-availability-funds/caf/v1/funds-confirmations";
    private static final String CLIENT_ID = "c451778c-db2c-451e-8f57-9bb62422329e";
    private static final String CLIENT_SECRET = "G3cJ8kF6qA1tR8iF7rS3hI2eD2yT6eA1bF7sF8uK0qP4lE6dQ1";
    private static final String REDIRECT_URL = "http://localhost:8080/rabo/token";
    private static final String SCOPES = "ais.balances.read%20ais.transactions.read-90days%20ais.transactions.read-history%20caf.fundsconfirmation.read";
    private static final String CERT = "MIIFhDCCA2wCCQDWb3mdqqeOaDANBgkqhkiG9w0BAQsFADCBgzELMAkGA1UEBhMCTkwxEzARBgNVBAgMCkdlbGRlcmxhbmQxDzANBgNVBAcMBkFybmhlbTEMMAoGA1UECgwDSEFOMQwwCgYDVQQLDANJQ0ExDjAMBgNVBAMMBVN0ZWluMSIwIAYJKoZIhvcNAQkBFhNpbmZvQHN0ZWlubWlsZGVyLm5sMB4XDTE5MTExNDIwMzIwOFoXDTIwMTExMzIwMzIwOFowgYMxCzAJBgNVBAYTAk5MMRMwEQYDVQQIDApHZWxkZXJsYW5kMQ8wDQYDVQQHDAZBcm5oZW0xDDAKBgNVBAoMA0hBTjEMMAoGA1UECwwDSUNBMQ4wDAYDVQQDDAVTdGVpbjEiMCAGCSqGSIb3DQEJARYTaW5mb0BzdGVpbm1pbGRlci5ubDCCAiIwDQYJKoZIhvcNAQEBBQADggIPADCCAgoCggIBAM0dxjiB2QKCZHyh1aN56Q3NvVxC4/BIIIDidX84WEDMjSwfbvfmxdSmzpQfADTq5Q1ubMKkTC1/wbfXtFUUmbR2Pcq9eFs4awDlRJai0FQyWQUWYZgPdiKcKw4bQ/EHvBdUE0k6RqaGJYJ/phSmzksGOAqqC6IL7nsf9QpsnhyCVR773QILpWtKBB0TLcfiAeU9M+HvXAZg7f+hoonFk+Krb1Bboq5Y4oUCORKVm1j8jwUyu6izihLowUyvRhunbJRQ6qNKmKZ0/GoVQ63Lj0NCdqZheLPpNhrOkcWb4hiuqROvhrG4ikXYIJEacpoWsGfk0VXGYXKPu+UXqcnfyxajGglxRuQc1XMzOkE9Yc+EdmlK0Ku8sGxPr0td5P97y5WEz12EFzQbU1Db3ek4hbul6dvZx9xLP6A0EuasgsiomSlDSknp6ZixANx7hPFMtn5Y9f9GKm/GxU6RFeWZoZZM1o6rwS5wGXmMxOGeUKr7VxbTKggNuDj37l9ixiKycv26HTnOlXnZZTYeVDMi6PGd83e/XTHe4qZiXe0GHAoMjADgnzOGimaviUneKu9PZQWLJuLv0voW942XXLdUxOZokZYXo3/vW+X8mzM2+vc+fX/pYlWw4IDlCRsV4cN0376PVsHW5m+t37dCWTxMTk5ACL6tUjyw/QEqotR3wAlXAgMBAAEwDQYJKoZIhvcNAQELBQADggIBAGgYRCSzGRb8KdZj0hv56V7zlWBwTa76QJCqztmMctLFnhLJRMA75rV+I4irQtFaBJXMwSw0/InMXCEmmUsQyLjdx+EU8qBuj56NXzSCVI66cDyxDFn6vNptIRU6JE26zNyKgVK/LXbZplwYDqrFbvfGzlQ0x+4exvIvbl8xVYGP82itLJ1ZFFok/gZa1gkgIi0lF2Ib5Sxy2gUc/LGJHzaQq/TkLAzKDGlIm2U5UQEEOCqZzxXdZBsFoRb5sHMTtye/erMwGQCK8In4POwHy3zo7Vflcvgyq4qe46epVxgMC6QMMCVm2hADr2YUf+L09KL392Ug1Yxxppoz12Hjb1bAJ/o5/ndf+m2uPq47IP6DJe7x/FuIv77aArFWHUVRkxLZxSVYS+VrPfCmLhuHlP8nS4dJfsTEIxyUChwj36ioOpRNo0ioK/9HSbm6zDieuf1XxnzNPQZAL3bGyJrnRuWuzh+VONVTQ7lS1DXjqE2xRudZu5/8GkHhdnh6dxbP1I3R6zrGoyYugtO1fsM6YTR0PilvvAcaUNY26hsDHEPxzPHgqSaW1VT16Ja1cH0fbDQVTXrSNUFBDSsIlONKaYnMWTaZozud5TaeiS16w3QZ1Jk2dHuXAaWl+k2Ib5bISoc9uFzVjqQd7Ub4o212I9fGBJd6hiaNuYy4PAKQQCRK";
    private static final String KEY = "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";
    private static final String KEY_ID = "15451702564611395176";
    private HttpClient httpClient;
    private Generator gen;
    private Gson gson;

    @Inject
    public void setGen(Generator gen) {
        this.gen = gen;
    }

    public RaboUtil() {
        this.gson = new Gson();
        httpClient = HttpClient.create().secure(sslContextSpec -> {
            SslContextBuilder sslContextBuilder = SslContextBuilder.forClient();
            RSAPrivateKey privateKey = RSA.getPrivateKeyFromString(KEY);
            X509Certificate certificate = RSA.getCertificateFromString(CERT);
            sslContextBuilder.keyManager(privateKey, certificate);
            sslContextSpec.sslContext(sslContextBuilder).defaultConfiguration(SslProvider.DefaultConfigurationType.TCP);
        });
    }

    public BankToken getBankToken(String code, String body) {
        var authorization = Base64.encodeBase64String((CLIENT_ID + ":" + CLIENT_SECRET).getBytes());
        String endpoint = "/token";
        var output = doPostRequest(OAUTH_BASE, endpoint, code, body, authorization);
        return gson.fromJson(output, BankToken.class);
    }

    public String doGetRequest(String base, String endpoint, String token) {
        String payload = "";
        var date = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC));
        var digest = gen.generateDigestSha512(payload);
        var requestId = UUID.randomUUID().toString();
        var body = ByteBufFlux.fromString(Mono.just(payload));
        return httpClient
                .headers(h -> h.set("Authorization", "Basic " + token))
                .headers(h -> h.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED))
                .headers(h -> h.set("x-ibm-client-id", CLIENT_ID))
                .headers(h -> h.set("authorization", "Bearer " + token))
                .headers(h -> h.set("accept", "application/json"))
                .headers(h -> h.set("date", date))
                .headers(h -> h.set("digest", digest))
                .headers(h -> h.set("x-request-id", requestId))
                .headers(h -> h.set("tpp-signature-certificate", CERT))
                .headers(h -> h.set("signature", generateSignatureHeader(date, digest, requestId)))
                .request(HttpMethod.GET)
                .uri(base + endpoint)
                .send(body)
                .responseContent()
                .aggregate()
                .asString()
                .block();
    }

    public String doPostRequest(String base, String endpoint, String token, String payload, String authorization) {
        return httpClient
                .headers(h -> h.set("Authorization", "Basic " + authorization))
                .headers(h -> h.set("Content-Type", MediaType.APPLICATION_FORM_URLENCODED))
                .post()
                .uri(base + endpoint)
                .send(ByteBufFlux.fromString(Mono.just(payload)))
                .responseContent()
                .aggregate()
                .asString()
                .block();
    }

    public String generateSignatureHeader(String date, String digest, String requestId) {
        try {
            String string = "date: " + date + "\n" + "digest: " + digest + "\n" + "x-request-id: " + requestId;
            var privateKey = RSA.getPrivateKeyFromString(KEY);
            var signature = RSA.sign(privateKey, string.getBytes(StandardCharsets.UTF_8));
            return "keyId=\"" + KEY_ID + "\",algorithm=\"rsa-sha512\",headers=\"date digest x-request-id\",signature=\"" + signature + "\"";
        } catch (IOException | GeneralSecurityException ex) {
            System.out.println(ex.getMessage());
        }
        return null;
    }
}
