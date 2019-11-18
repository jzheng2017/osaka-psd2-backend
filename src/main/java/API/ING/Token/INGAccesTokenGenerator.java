package API.ING.Token;


import API.ING.RSA;
import API.DTO.AccessToken;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

@Component
public class INGAccesTokenGenerator {
    private String base = "https://api.sandbox.ing.com";
    private String endpoint = "/oauth2/token";
    private String privateKeyLocation = "src/main/resources/certs/ING/example_eidas_client_signing.key";
    private final String clientID = "5ca1ab1e-c0ca-c01a-cafe-154deadbea75";

    @Autowired
    private RestTemplate rest  = new RestTemplate();

    public String getDigest(String body) {
        try {
            byte[] sha = DigestUtils.sha256(body);
            return "SHA-256=" + new String(Base64.encodeBase64(sha), "UTF-8");
        } catch (UnsupportedEncodingException exc) {
            System.out.println(exc.getMessage());
        }
        return "SHA-256= ";
    }

    public String getServerTime() {
        return DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC));
    }

    public String getSignature(String digest, String date, String requestID, String clientID, String httpMethod) {
        try {
            String string = "(request-target): " + httpMethod + " " + endpoint + "\n" +
                    "date: " + date + "\n" +
                    "digest: " + digest + "\n" +
                    "X-ING-ReqID" + requestID;
            var signature = RSA.sign(RSA.getPrivateKey(privateKeyLocation), string.getBytes(StandardCharsets.UTF_8));
            return "keyId=\"" + clientID + "\",algorithm=\"rsa-sha256\",headers=\"(request-target) date digest X-ING-ReqID\",signature=\"" + signature + "\"";

        } catch (IOException | GeneralSecurityException exception) {
            System.out.println(exception.getMessage());
            //replace with logger
        }
        return null;
    }

    public String getAccesTokenFromObject() {
        AccessToken accessToken = getAccessToken();
        return accessToken.getAccesToken();
    }

    public AccessToken getAccessToken() {
        String body ="grant_type=client_credentials";
        String url = "/oauth2/token";
        String certificateKeyId = "SN=499602D2,CA=C=NL,ST=Amsterdam,L=Amsterdam,O=ING,OU=ING,CN=AppCertificateMeansAPI";
        String digest = getDigest(body);
        String date = getServerTime();
        String requestId = UUID.randomUUID().toString();
        String signature = getSignature(digest, date, requestId, certificateKeyId, "post");
        String certificate = getSingleLineCertificate();
        HttpHeaders headers = new HttpHeaders();
        headers.set("digest", digest);
        headers.set("X-ING-ReqID", requestId);
        headers.set("Authorization", "Signature "+ signature);
        headers.set("Content-Type", "application/x-www-form-urlencoded");
        headers.set("TPP-Signature-Certificate", certificate);
        HttpEntity<String> requestEntity = new HttpEntity<>(body, headers);
        ResponseEntity<AccessToken> responseEntity = rest.exchange(base + url, HttpMethod.POST, requestEntity, AccessToken.class);
        return responseEntity.getBody();
    }

    public String getCustomerAccessToken() {
        //TODO: customer redirect, get token from redirect with access token etc.
        return "2c1c404c-c960-49aa-8777-19c805713edf";
    }

    private String getSingleLineCertificate() {
        return "-----BEGIN CERTIFICATE-----MIID9TCCAt2gAwIBAgIESZYC0jANBgkqhkiG9w0BAQsFADByMR8wHQYDVQQDDBZBcHBDZXJ0aWZpY2F0ZU1lYW5zQVBJMQwwCgYDVQQLDANJTkcxDDAKBgNVBAoMA0lORzESMBAGA1UEBwwJQW1zdGVyZGFtMRIwEAYDVQQIDAlBbXN0ZXJkYW0xCzAJBgNVBAYTAk5MMB4XDTE5MDMwNDEzNTkwN1oXDTIwMDMwNDE0NTkwN1owPjEdMBsGA1UECwwUc2FuZGJveF9laWRhc19xc2VhbGMxHTAbBgNVBGEMFFBTRE5MLVNCWC0xMjM0NTEyMzQ1MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxWVOA7gAntPONQAfmLCEpQUcdi2oNRkQ7HioxD1cIxsy9QRFNFhbl8bSW++oSh/Gdo2tds9Oe7i/54cxp7svQitBDvOLLqC5/4+xtNXOYLFVjQF2EyJWlFBq9ZEqmD/5uk8UpJHt9lqJZfuxUeF0ZA/NAADR3nEL1mSSbEqRpxRvdJ+rn+9DaquRBthZSlPJkOTKyQ9tzbTgmsrrzD1GLA8UMt6GqpYZnFvuJapa9yDHxEe1laazwgTmmcD0su/K5D9hqSWlbxEDp0Bud5GeEYVhV6Zqf2J8vMbTVD9UZHI9Bb0W99u1+NUyPKqV+jwgbmA37ehDaB17i4ABbItxAwIDAQABo4HGMIHDMBUGA1UdHwQOMAwwCqAIoAaHBH8AAAEwIQYDVR0jBBowGKAWBBRwSLteAMD0JvjEdNF40sRO37RyWTCBhgYIKwYBBQUHAQMEejB4MAoGBgQAjkYBAQwAMBMGBgQAjkYBBjAJBgcEAI5GAQYCMFUGBgQAgZgnAjBLMDkwEQYHBACBmCcBAwwGUFNQX0FJMBEGBwQAgZgnAQEMBlBTUF9BUzARBgcEAIGYJwECDAZQU1BfUEkMBlgtV0lORwwGTkwtWFdHMA0GCSqGSIb3DQEBCwUAA4IBAQB3TXQgvS+vm0CuFoILkAwXc/FKL9MNHb1JYc/TZKbHwPDsYJT3KNCMDs/HWnBD/VSNPMteH8Pk5Eh+PIvQyOhY3LeqvmTwDZ6lMUYk5yRRXXh/zYbiilZAATrOBCo02ymm6TqcYfPHF3kh4FHIVLsSe4m/XuGoBO2ru5sMUCxncrWtw4UXZ38ncms2zHbkH6TB5cSh2LXY2aZSX8NvYyHPCCw0jrkVm1/kAs69xM2JfIh8fJtycI9NvcoSd8HGSe/D5SjUwIFKTWXq2eCMsNEAG51qS0jWXQqPtqBRRTdu+AEAJ3DeIY6Qqg2mjk+i6rTMqSwFVqo7Cq7zHty4E7qK-----END CERTIFICATE-----";
    }

    public HttpHeaders getHeaders() {
        String accessToken = getCustomerAccessToken();
        HttpHeaders headers = new HttpHeaders();
        String date = getServerTime();
        String digest = getDigest("");
        String requestId = UUID.randomUUID().toString();
        headers.set("accept", "application/json");
        headers.set("X-ING-ReqID", requestId);
        headers.set("Authorization", "Bearer " + accessToken);
        headers.set("date", date);
        headers.set("digest", digest);
        headers.set("Signature", getSignature(date, digest, requestId, clientID, "get"));
        return headers;
    }
}
