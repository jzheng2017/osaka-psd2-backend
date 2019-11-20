package API.ING.Service;

import API.DTO.AccessToken;
import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.RABO.RaboAccessToken;
import API.DTO.Transaction;
import API.RSA;
import com.google.gson.Gson;
import io.netty.handler.ssl.SslContextBuilder;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.apache.commons.io.FileUtils;
import org.apache.commons.io.IOUtils;
import org.json.JSONObject;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.SslProvider;

import javax.ws.rs.HttpMethod;
import java.io.*;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;
import java.util.UUID;


public class INGAccountService {
    private static final String BASE_URL = "https://api.sandbox.ing.com";
    private static final String PAYLOAD = "grant_type=client_credentials";
    private static final String KEY_PATH = "example_eidas_client_tls.key";
    private static final String CERT_PATH = "example_eidas_client_tls.cer";
    private static final String SIGNING_KEY_PATH = "example_eidas_client_signing.key";
    private static final String KEY_ID = "SN=499602D2,CA=C=NL,ST=Amsterdam,L=Amsterdam,O=ING,OU=ING,CN=AppCertificateMeansAPI";

    private HttpClient httpClient;

    public INGAccountService()  {
        try {
            //getting certificates for tomcat
            InputStream certificate = getClass().getClassLoader().getResourceAsStream(CERT_PATH);
            InputStream key = getClass().getClassLoader().getResourceAsStream(KEY_PATH);
            File certificateFile = File.createTempFile("certificate", ".cer");
            File keyFile = File.createTempFile("key", ".key");
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
        try {
            var digest = generateDigest(PAYLOAD);
            var date = getServerTime();
            var requestId = UUID.randomUUID().toString();
            var body = "grant_type=client_credentials";
            var method = HttpMethod.POST;
            var url = "/oauth2/token";
            var signature = generateSignatureHeader(digest, date, requestId, url, method, KEY_ID);
            var certificate = "-----BEGIN CERTIFICATE-----MIID9TCCAt2gAwIBAgIESZYC0jANBgkqhkiG9w0BAQsFADByMR8wHQYDVQQDDBZBcHBDZXJ0aWZpY2F0ZU1lYW5zQVBJMQwwCgYDVQQLDANJTkcxDDAKBgNVBAoMA0lORzESMBAGA1UEBwwJQW1zdGVyZGFtMRIwEAYDVQQIDAlBbXN0ZXJkYW0xCzAJBgNVBAYTAk5MMB4XDTE5MDMwNDEzNTkwN1oXDTIwMDMwNDE0NTkwN1owPjEdMBsGA1UECwwUc2FuZGJveF9laWRhc19xc2VhbGMxHTAbBgNVBGEMFFBTRE5MLVNCWC0xMjM0NTEyMzQ1MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxWVOA7gAntPONQAfmLCEpQUcdi2oNRkQ7HioxD1cIxsy9QRFNFhbl8bSW++oSh/Gdo2tds9Oe7i/54cxp7svQitBDvOLLqC5/4+xtNXOYLFVjQF2EyJWlFBq9ZEqmD/5uk8UpJHt9lqJZfuxUeF0ZA/NAADR3nEL1mSSbEqRpxRvdJ+rn+9DaquRBthZSlPJkOTKyQ9tzbTgmsrrzD1GLA8UMt6GqpYZnFvuJapa9yDHxEe1laazwgTmmcD0su/K5D9hqSWlbxEDp0Bud5GeEYVhV6Zqf2J8vMbTVD9UZHI9Bb0W99u1+NUyPKqV+jwgbmA37ehDaB17i4ABbItxAwIDAQABo4HGMIHDMBUGA1UdHwQOMAwwCqAIoAaHBH8AAAEwIQYDVR0jBBowGKAWBBRwSLteAMD0JvjEdNF40sRO37RyWTCBhgYIKwYBBQUHAQMEejB4MAoGBgQAjkYBAQwAMBMGBgQAjkYBBjAJBgcEAI5GAQYCMFUGBgQAgZgnAjBLMDkwEQYHBACBmCcBAwwGUFNQX0FJMBEGBwQAgZgnAQEMBlBTUF9BUzARBgcEAIGYJwECDAZQU1BfUEkMBlgtV0lORwwGTkwtWFdHMA0GCSqGSIb3DQEBCwUAA4IBAQB3TXQgvS+vm0CuFoILkAwXc/FKL9MNHb1JYc/TZKbHwPDsYJT3KNCMDs/HWnBD/VSNPMteH8Pk5Eh+PIvQyOhY3LeqvmTwDZ6lMUYk5yRRXXh/zYbiilZAATrOBCo02ymm6TqcYfPHF3kh4FHIVLsSe4m/XuGoBO2ru5sMUCxncrWtw4UXZ38ncms2zHbkH6TB5cSh2LXY2aZSX8NvYyHPCCw0jrkVm1/kAs69xM2JfIh8fJtycI9NvcoSd8HGSe/D5SjUwIFKTWXq2eCMsNEAG51qS0jWXQqPtqBRRTdu+AEAJ3DeIY6Qqg2mjk+i6rTMqSwFVqo7Cq7zHty4E7qK-----END CERTIFICATE-----";

            String accessTokenString = httpClient
                    .headers(h -> h.set("Content-Type", "application/x-www-form-urlencoded"))
                    .headers(h -> h.set("Digest", digest))
                    .headers(h -> h.set("Date", date))
                    .headers(h -> h.set("X-ING-ReqID", requestId))
                    .headers(h -> h.set("TPP-Signature-Certificate", certificate))
                    .headers(h -> h.set("Authorization", "Signature " + signature))
                    .post()
                    .uri(BASE_URL + url)
                    .send(ByteBufFlux.fromString(Mono.just(body)))
                    .responseContent()
                    .aggregate()
                    .asString()
                    .block();
            System.out.println(accessTokenToDTO(accessTokenString).getAccesToken());
            return accessTokenString;
        } catch (IOException | GeneralSecurityException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    private AccessToken accessTokenToDTO(String token) {
        Gson gson = new Gson();
        RaboAccessToken accessToken = gson.fromJson(token, RaboAccessToken.class);
        AccessToken mappedAccesstoken = new AccessToken();
        mappedAccesstoken.setAccesToken(accessToken.getAccess_token());
        return mappedAccesstoken;
    }
    public String redirect(String code) {
        try {
            var digest = generateDigest("");
            var date = getServerTime();
            var requestId = UUID.randomUUID().toString();
            var method = HttpMethod.GET;
            var url = "/oauth2/authorization-server-url?scope=payment-accounts:balances:view&country_code=nl";
            var keyId = "5ca1ab1e-c0ca-c01a-cafe-154deadbea75";
            var signature = generateSignatureHeader(digest, date, requestId, url, method, keyId);
            return null;
/*
        return webClient
                .method(method)
                .uri(url)
                .header("Content-Type", "application/json")
                .header("Digest", digest)
                .header("Date", date)
                .header("X-ING-ReqID", requestId)
                .header("Authorization", "Bearer "+code)
                .header("Signature", signature)
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();

 */
        } catch (IOException | GeneralSecurityException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public String access(String code) {
        try {
            var body = "grant_type=authorization_code&code=3a1d7c04-9e87-433d-817c-86dccb77f11f&redirect_uri=xxx";

            var digest = generateDigest(body);
            var date = getServerTime();
            var requestId = UUID.randomUUID().toString();
            var method = HttpMethod.POST;
            var url = "/oauth2/token";
            var keyId = "5ca1ab1e-c0ca-c01a-cafe-154deadbea75";
            var signature = generateSignatureHeader(digest, date, requestId, url, method, keyId);
            return null;

        /*
        return webClient
                .method(method)
                .uri(url)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Digest", digest)
                .header("Date", date)
                .header("X-ING-ReqID", requestId)
                .header("Authorization", "Bearer "+code)
                .header("Signature", signature)
                .body(BodyInserters.fromObject(body))
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();
*/
        } catch (IOException | GeneralSecurityException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
    }

    public String accounts(String code) {
        try {
            var body = "";
            var digest = generateDigest(body);
            var date = getServerTime();
            var requestId = UUID.randomUUID().toString();
            var method = HttpMethod.GET;
            var url = "/v2/accounts/";
            var keyId = "5ca1ab1e-c0ca-c01a-cafe-154deadbea75";
            var signature = generateSignatureHeader(digest, date, requestId, url, method, keyId);

            return null;
/*
        return webClient
                .method(method)
                .uri("https://api.ing.com/v2/accounts/")
                .header("Content-Type", "application/x-www-form-urlencoded")
                .header("Digest", digest)
                .header("Date", date)
                .header("X-ING-ReqID", requestId)
                .header("Authorization", "Bearer "+code)
                .header("Signature", signature)
                .body(BodyInserters.fromObject(body))
                .exchange()
                .block()
                .bodyToMono(String.class)
                .block();

 */
        } catch (IOException | GeneralSecurityException exception) {
            System.out.println(exception.getMessage());
        }
        return null;

    }

    private String generateSignatureHeader(String digest, String date, String requestId, String url, String method, String keyId) throws IOException, GeneralSecurityException {
        InputStream input = getClass().getClassLoader().getResourceAsStream(SIGNING_KEY_PATH);
        String string = getSigningString(date, digest, requestId, method.toLowerCase(), url);
        var privateKey = RSA.getPrivateKeyFromString(IOUtils.toString(input, StandardCharsets.UTF_8.name()));
        var signature = RSA.sign256(privateKey, string.getBytes());
        return "keyId=\"" + keyId + "\",algorithm=\"rsa-sha256\",headers=\"(request-target) date digest x-ing-reqid\",signature=\"" + signature + "\"";
    }

    private String generateDigest(String value) {
        byte[] sha = DigestUtils.sha256(value);
        return "SHA-256=" + new String(Base64.encodeBase64(sha), StandardCharsets.UTF_8);
    }

    private String getServerTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }

    private String getSigningString(String date, String digest, String requestId, String method, String url) {
        return "(request-target): " + method + " " + url + "\n" +
                "date: " + date + "\n" +
                "digest: " + digest + "\n" +
                "x-ing-reqid: " + requestId;
    }

    public Account getUserAccounts() {
        return null;
    }

    public Balance getAccountBalances(String accountID) {
        return null;
    }

    public Transaction getAccountTransactions(String accountID) {
        return null;
    }
}
