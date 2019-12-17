package API.Banks.ABNAMRO;

import API.Banks.Requests.Headers;
import API.RSA;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.netty.handler.ssl.SslContextBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.SslProvider;

import javax.ws.rs.core.MediaType;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;

public class ABNAMROUtil {
    private static final String API_KEY = "ZVOCL4Il81X0OSefId9yAxTJVl5vGWnh";
    private static final String CERT = "MIIC9DCCAdygAwIBAgIGAWkqXD5SMA0GCSqGSIb3DQEBCwUAMDsxCzAJBgNVBAYTAk5MMQwwCgYDVQQKEwNBQkMxHjAcBgNVBAMTFVRwcFNhbmRib3hDZXJ0aWZpY2F0ZTAeFw0xOTAyMjYxNTExMjJaFw0yMDAyMjYxNTExMjJaMDsxCzAJBgNVBAYTAk5MMQwwCgYDVQQKEwNBQkMxHjAcBgNVBAMTFVRwcFNhbmRib3hDZXJ0aWZpY2F0ZTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAJkUcwLLs9dr3ngvR5ULGHoCvCpyaeVA9s/VBZ4q4ysbLAjdGux3mR18GKtGMkIeB1Dmw65vBdJWCBXxbPdeWA3ZRoC6yUCU6w5HMg0IMMq4Z5dbUs5cgvrUF1ZD12uUW/4zSQ6dw4DpyVzE2rDQ88dSGBGSC2U/Ql3aR8W2RaDL0Ii5MobKM1VtCrL2bjGKyPf4rViJZDrvFQBBH2WzlGJnDQVYxgQnINVQa1lIY+B/gNvm1iw/znAqeAN38FrNNXy6LpHXmi7viDh1/pBMbG2L6SRnuSOu79QrXaMPsaupklEHlyrY5s/SDvsjgEGC3IQNVduAL87zhjTLt+ElGPcCAwEAATANBgkqhkiG9w0BAQsFAAOCAQEAIJkFTRn6MnnQsfSJkNvzdHypH4ha43zURXypc8B5bY9VD8jzBuy5QEpuNRV8i3X22Sqldh2KZ4tp+X54f2LIfwwjd6t60eTTvPsH8MTpV376pUMB3pdCuA6EPlCij/qXJbi/UCeNA8jQAoOTd7oIwIrQrf6tyCa3d871WvFkS4GyyVuPa6QBIsbD040cT63EaB/QQc9z+1EJiAMDZ1oONotEPL2t6gQkrEmNlYgebDMx9NEvQYYMnQB6AMfYuxcieqDN4bngJS3IKnEuzn47PYrNLfdoWHuJffIv4MnTxl59WRV5pTOaZu776Qlgzm7RHdgbibfD5ASixT+VDebMoQ==";
    private static final String KEY = "MIIEvQIBADANBgkqhkiG9w0BAQEFAASCBKcwggSjAgEAAoIBAQCZFHMCy7PXa954L0eVCxh6ArwqcmnlQPbP1QWeKuMrGywI3Rrsd5kdfBirRjJCHgdQ5sOubwXSVggV8Wz3XlgN2UaAuslAlOsORzINCDDKuGeXW1LOXIL61BdWQ9drlFv+M0kOncOA6clcxNqw0PPHUhgRkgtlP0Jd2kfFtkWgy9CIuTKGyjNVbQqy9m4xisj3+K1YiWQ67xUAQR9ls5RiZw0FWMYEJyDVUGtZSGPgf4Db5tYsP85wKngDd/BazTV8ui6R15ou74g4df6QTGxti+kkZ7kjru/UK12jD7GrqZJRB5cq2ObP0g77I4BBgtyEDVXbgC/O84Y0y7fhJRj3AgMBAAECggEARGydlAxVkN8IjBQmHPrer/r0/MwzhWPqbq+7WR22eRgmMLgURsqWyFUl+bjg0ij2ADWGFjxOD9ygtJ47pL6pAVezaesT9igagUFVn/mfRZ3zv/X0J4W2jkOrQsYETnP8Qr3N1Bi0wLS/axYa4pojvV52n7P2IAWMtsLQ/hEhQmPm7IqInzsIEtZTOfOcTqOK1xe3nXaouZVl2jXhJlBAv4tsO7e9RmWSeanB5qSZ0SmNSurc/9ukFoi9s/SuW5yPxe/LmHMr6CCu5U5spzW5XccWSlnEZ2wxm+Cnipu6AXnaTAhPqDMJLGCYrxqOt3HJCCSIwESx9Nst2sS32ij+gQKBgQDOVVxRKwBzHwGhEPAGmdmFYKoxKE0QTmrxBlhPpAx1F1S0e9cxyRqIh1BIL/itdgbQgNyI5yKIKrgt+gFOKUP04Vzu5BBDYxhtCkdf184kqe2VweTLGVGMho/P2I026BHw2/VLO37yng71eP4RC+VLroZQBed/2z1hWrijBah6FwKBgQC97YIQM5v27/HjPgazE+jmp7Iw2RaJ7cGLrS70zt8T1Fec75ze9x9S3xDVJ1T8pP5E5Zq0pVwpJsPtQfvdkEsxhN+1X5fLuuYg1+Y1hh5SOGcB31a0NoJK2dBOuC6CjHfOJkaBjY5XQ8Q1fkXSm1DDan7+GiHnrPxUThafw8MEIQKBgG4gS0SbQgMvwmvYIXQ0e0/f9xaDnxYb9KIuM8ZWFbwNNs2Z55KP9pR2PFg7GmxiuWJh1NNRIjIxMtp/PGEeT0INYs+ydCezZV8VhGDYSxNwivlKYrYwDkGFtI5H059BoAnBLJv55ljSGcPUzy4D/l81iER/0j6AorMqe6+vHmwDAoGBAIbmbKw/S/cQJJnIU4/cg19ZGyqw9t5O/lrMTn7ZdP8ronM4ig6gLiJ5iAYuIqI0OtoKz2Ch1xzviNg7Nr7/nzjz7MVxuWqePJh1YPEBawXxQ9DDplzoHpE1tkxDa92UEgBdlVSti72Vx4ZLQyK86Jd0S/EF9LEOYEctE8q0jA6hAoGANPZaEQ0Fljrfg7gKJugKAOC2QXqCzKAejwW6MaZP4b4Mb+sQBBI6J8L8KW5c0U/kYE3tNNk29qeo0jV5/q9XeCzGzWS+tpHdePx9P9WevQeoNNPAKVmBs+8JO5IR126H5N+Qq3VHAD19V5AKHL4daI2epGY4MIV94xxhI6IvEzk=";

    private Gson gson;
    private HttpClient httpClient;

    public ABNAMROUtil() {
        gson = new Gson();
        httpClient = HttpClient.create().secure(sslContextSpec -> {
            SslContextBuilder sslContextBuilder = SslContextBuilder.forClient();
            RSAPrivateKey privateKey = RSA.getPrivateKeyFromString(KEY);
            X509Certificate certificate = RSA.getCertificateFromString(CERT);
            sslContextBuilder.keyManager(privateKey, certificate);

            sslContextSpec.sslContext(sslContextBuilder).defaultConfiguration(SslProvider.DefaultConfigurationType.TCP);
        });
    }

    public JsonObject post(String url, String payload) {
        var response = httpClient
                .headers(h -> h.set(Headers.CONTENT_TYPE, MediaType.APPLICATION_FORM_URLENCODED))
                .post()
                .uri(url)
                .send(ByteBufFlux.fromString(Mono.just(payload)))
                .responseContent()
                .aggregate()
                .asString()
                .block();

        return gson.fromJson(response, JsonObject.class);
    }

    public JsonObject get(String token, String url) {
        var response = httpClient
                .headers(h -> h.set("API-Key", API_KEY))
                .headers(h -> h.set(Headers.AUTHORIZATION, "Bearer "+token))
                .get()
                .uri(url)
                .responseContent()
                .aggregate()
                .asString()
                .block();

        return gson.fromJson(response, JsonObject.class);
    }
}
