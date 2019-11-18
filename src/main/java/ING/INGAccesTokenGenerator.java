package ING;


import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.springframework.http.HttpHeaders;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.security.GeneralSecurityException;
import java.text.SimpleDateFormat;
import java.util.*;

public class INGAccesTokenGenerator {
    private String base = "https://api.sandbox.ing.com";
    private String payload = "grant_type=client_credentials";
    private String httpMethod = "post";
    private String endpoint = "/oauth2/token";
    private String privateKeyLocation = "src/main/resources/certs/example_eidas_client_signing.key";

    private final HttpClient httpClient = HttpClient.newBuilder().version(HttpClient.Version.HTTP_2).build();

    public String getDigest() throws UnsupportedEncodingException {
        byte[] sha = DigestUtils.sha256(payload);
        return "SHA-256=" + new String(Base64.encodeBase64(sha), "UTF-8");
    }

    public String getServerTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat(
                "EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }

    public String getSignature(String digest, String date, String requestID) {
        try {
            String string = "(request-target): " + httpMethod + " " + endpoint + "\n" +
                    "date: " + date + "\n" +
                    "digest: " + digest + "\n" +
                    "X-ING-ReqID" + requestID;
            var signature = RSA.sign(RSA.getPrivateKey(privateKeyLocation), string.getBytes(StandardCharsets.UTF_8));
            return "Signature: keyId=\"SN=499602D2,CA=C=NL,ST=Amsterdam,L=Amsterdam,O=ING,OU=ING,CN=AppCertificateMeansAPI\",algorithm=\"rsa-sha256\",headers=\"(request-target) date digest X-ING-ReqID\",signature=\"" + signature + "\"";

        } catch (IOException | GeneralSecurityException exception) {
            System.out.println(exception.getMessage());
            //replace with logger
        }
        return null;
    }

    public String getAccessToken() {
        try {
            String digest = getDigest();
            String date = getServerTime();
            String requestId = UUID.randomUUID().toString();
            String signature = getSignature(digest, date, requestId);
            String certificate = getCertificate();
            System.out.println(digest);
            System.out.println(date);
            System.out.println(signature);
            System.out.println(requestId);
            Map<Object, Object> data = new HashMap<>();
            data.put("grant_type", "client_credentials");
            HttpRequest request = HttpRequest.newBuilder().POST(buildFormDataFromMap(data))
                    .uri(URI.create(base + endpoint))
                    .setHeader("digest", digest)
                    .setHeader("X-ING-ReqID", requestId)
                    .setHeader("Authorization", signature)
                    .setHeader("Content-Type", "application/x-www-form-urlencoded")
                    .setHeader("TPP-Signature-Certificate", certificate)
                    .build();

            HttpResponse<String> response = httpClient.send(request, HttpResponse.BodyHandlers.ofString());
            return response.body();
        } catch (IOException | InterruptedException exception) {
            System.out.println(exception);
            //replace with logger
        }
        return null;
    }

    private static HttpRequest.BodyPublisher buildFormDataFromMap(Map<Object, Object> data) {
        var builder = new StringBuilder();
        for (Map.Entry<Object, Object> entry : data.entrySet()) {
            if (builder.length() > 0) {
                builder.append("&");
            }
            builder.append(URLEncoder.encode(entry.getKey().toString(), StandardCharsets.UTF_8));
            builder.append("=");
            builder.append(URLEncoder.encode(entry.getValue().toString(), StandardCharsets.UTF_8));
        }
        return HttpRequest.BodyPublishers.ofString(builder.toString());
    }

    private String getCertificate() {
        return "-----BEGIN CERTIFICATE-----MIID9TCCAt2gAwIBAgIESZYC0jANBgkqhkiG9w0BAQsFADByMR8wHQYDVQQDDBZBcHBDZXJ0aWZpY2F0ZU1lYW5zQVBJMQwwCgYDVQQLDANJTkcxDDAKBgNVBAoMA0lORzESMBAGA1UEBwwJQW1zdGVyZGFtMRIwEAYDVQQIDAlBbXN0ZXJkYW0xCzAJBgNVBAYTAk5MMB4XDTE5MDMwNDEzNTkwN1oXDTIwMDMwNDE0NTkwN1owPjEdMBsGA1UECwwUc2FuZGJveF9laWRhc19xc2VhbGMxHTAbBgNVBGEMFFBTRE5MLVNCWC0xMjM0NTEyMzQ1MIIBIjANBgkqhkiG9w0BAQEFAAOCAQ8AMIIBCgKCAQEAxWVOA7gAntPONQAfmLCEpQUcdi2oNRkQ7HioxD1cIxsy9QRFNFhbl8bSW++oSh/Gdo2tds9Oe7i/54cxp7svQitBDvOLLqC5/4+xtNXOYLFVjQF2EyJWlFBq9ZEqmD/5uk8UpJHt9lqJZfuxUeF0ZA/NAADR3nEL1mSSbEqRpxRvdJ+rn+9DaquRBthZSlPJkOTKyQ9tzbTgmsrrzD1GLA8UMt6GqpYZnFvuJapa9yDHxEe1laazwgTmmcD0su/K5D9hqSWlbxEDp0Bud5GeEYVhV6Zqf2J8vMbTVD9UZHI9Bb0W99u1+NUyPKqV+jwgbmA37ehDaB17i4ABbItxAwIDAQABo4HGMIHDMBUGA1UdHwQOMAwwCqAIoAaHBH8AAAEwIQYDVR0jBBowGKAWBBRwSLteAMD0JvjEdNF40sRO37RyWTCBhgYIKwYBBQUHAQMEejB4MAoGBgQAjkYBAQwAMBMGBgQAjkYBBjAJBgcEAI5GAQYCMFUGBgQAgZgnAjBLMDkwEQYHBACBmCcBAwwGUFNQX0FJMBEGBwQAgZgnAQEMBlBTUF9BUzARBgcEAIGYJwECDAZQU1BfUEkMBlgtV0lORwwGTkwtWFdHMA0GCSqGSIb3DQEBCwUAA4IBAQB3TXQgvS+vm0CuFoILkAwXc/FKL9MNHb1JYc/TZKbHwPDsYJT3KNCMDs/HWnBD/VSNPMteH8Pk5Eh+PIvQyOhY3LeqvmTwDZ6lMUYk5yRRXXh/zYbiilZAATrOBCo02ymm6TqcYfPHF3kh4FHIVLsSe4m/XuGoBO2ru5sMUCxncrWtw4UXZ38ncms2zHbkH6TB5cSh2LXY2aZSX8NvYyHPCCw0jrkVm1/kAs69xM2JfIh8fJtycI9NvcoSd8HGSe/D5SjUwIFKTWXq2eCMsNEAG51qS0jWXQqPtqBRRTdu+AEAJ3DeIY6Qqg2mjk+i6rTMqSwFVqo7Cq7zHty4E7qK-----END CERTIFICATE-----";
    }

    public HttpHeaders getHeaders(String accessToken) {
        try {
            HttpHeaders headers = new HttpHeaders();
            String date = getServerTime();
            String digest = getDigest();
            String requestId = UUID.randomUUID().toString();
            headers.set("accept", "application/json");
            headers.set("date", date);
            headers.set("digest", digest);
            headers.set("tpp-signature-certificate", getCertificate());
            headers.set("signature", getSignature(date, digest,requestId));
            return headers;
        } catch (IOException exception) {
            System.out.println(exception.getMessage());
        }
        return null;
        //TODO:return headers for account information
    }
}
