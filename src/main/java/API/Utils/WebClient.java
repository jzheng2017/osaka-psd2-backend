package API.Utils;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.ssl.SslContextBuilder;
import reactor.core.publisher.Mono;
import reactor.netty.ByteBufFlux;
import reactor.netty.http.client.HttpClient;
import reactor.netty.tcp.SslProvider;

import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.util.HashMap;

public class WebClient {
    private HttpClient httpClient;
    private Gson gson;

    public WebClient(String key, String cert) {
        gson = new Gson();
        httpClient = HttpClient.create().secure(sslContextSpec -> {
            SslContextBuilder sslContextBuilder = SslContextBuilder.forClient();
            RSAPrivateKey privateKey = RSA.getPrivateKeyFromString(key);
            X509Certificate certificate = RSA.getCertificateFromString(cert);
            sslContextBuilder.keyManager(privateKey, certificate);
            sslContextSpec.sslContext(sslContextBuilder).defaultConfiguration(SslProvider.DefaultConfigurationType.TCP);
        });
    }

    public JsonObject post(String url, HashMap<String, String> headers, String payload) {
        return request(HttpMethod.POST, headers, url, payload);
    }

    public JsonObject get(String url, HashMap<String, String> headers) {
        return request(HttpMethod.GET, headers, url, "");
    }

    public JsonObject put(String url, HashMap<String, String> headers) {
        return request(HttpMethod.PUT, headers, url, "");
    }

    public JsonObject request(HttpMethod method, HashMap<String, String> headers, String url, String payload) {
        var response = httpClient
                .headers(h -> headers.forEach(h::set))
                .request(method)
                .uri(url)
                .send(ByteBufFlux.fromString(Mono.just(payload)))
                .responseContent()
                .aggregate()
                .asString()
                .block();

        return gson.fromJson(response, JsonObject.class);
    }
}
