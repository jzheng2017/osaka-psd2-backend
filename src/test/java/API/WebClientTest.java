package API;

import API.Banks.Requests.Headers;
import API.Utils.WebClient;
import com.google.gson.Gson;
import com.google.gson.JsonObject;
import io.netty.handler.codec.http.HttpMethod;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import javax.ws.rs.core.MediaType;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;
import java.util.HashMap;

public class WebClientTest {
    private static final String KEY = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCDS/VFSaWsUKxw+lBFH/7xfpRQQNtDiqpn4dS1cpRxZgvjNnikFn9ROqjlILCYIC8FvkclN0kqzjNSihQKzUyyrbxoXTxw/dHfDIEOddGzJIO0X0Pi3R3hi4ruGeU+as+Dx3z2+TGZTpnYWlWwIVi2OKkyFoXPGTGYzXRXCFnCwnQN8Rr6W9lBVq4eEyxYAGcE84UumakPz/OS3FoEzz6ZGenGrvhJO+cA1ly1eySL7egsD8rnHButKAnNszfozlp+/+VAuMmZtv6VIlrfyQHL/WQkaUeKqU5YukAOgyaryZfiiQEGk4Yr35FTguMjS7FsAsmVprEn79755NraSfN7AgMBAAECggEAG0Qo+Wyj9TcDuKqjBNfyL+CjmE7/ufUQEma5r7zNywbwLQ1g3GK3qfzOmlbBlbYJTd4IFFh432TXD6sRInUkGm8uE1ZZePWdIf8Mxh39oIBSwaCPDovw6qf6ABxsmRvBYJKLBxcVD8tc86s+5Eboj18Q9A/tVebbf+oa4QAg8+r1KGUio2zUwal3lWeULZ2K8Xri9ltfiyqriTodd9nkV2TiSsjn1m5djSzI8DCTwdf10cbjgJ9XP9tMbPSp53aE8PEpp9vQUyL6uuS/5CFLXDbYK/BiqBzLdKlXKt/92qaXaDDDagUFQ8IyLdI6zwjW8f7DXyTSVqqjH1Jw+3BKUQKBgQDSEQ8x7lXc3MVFfJ/H24iN/PALA8Yl0adP/mvXZH9zM/OfW/hr6tTUU/F2utSx6QT6zWUKczskbXaENKH5v0BlZ9tsF68c+yd0zbPoDhhuM4iR+sdGUuZXjM6u3JBqCb8I0pxvpW5GqZbAn05Ktk0BRwMZpBOTT9Ho5AuRGd5PbQKBgQCgAZRR2BS6sznYTmci7kWnKpbJVS3p+KJTE4Y9vuiGkKb0dncl3k4EzW23zUCwNh6xEiLFbXe1ivWlMYSuOmZpa3ISI6FtcEOpkd8HUu19753bQHUTdDBLTMoNL0FioqKAo6b6vJcPaqtqt6ijsBOuGO2t85o8g34e0/KjSb61hwKBgBSG3jk+1N0UJaK4ntRku19EjCBHaiFf7z192wPdKicTuIal8gx5kfp9iWbUstv/rSDk2S7AO9M/bwlUK0/ARIakM2jIl6/5Ss27HA1c8z4xgvLg0oAosaF0fO3RV7tE4In4KpkuTSxSfgyshHYAgl3Rlpf21ILcleJwBkFTicmxAoGAEwidEC9YJ+1yEB0jf7BAcOZMEZ8kWxTMmn1UFrxDBN7oPWRqQAL13PRi/N5Zt5x4gi/aGwoul1X0arY9RkyEKj4xz56VcWNNaTqFAWYIAlcivBYq1ymXJR35WyAn8wfNtOfC0Ujl31udEJDQashjTu6AN5Um39P0iM5Fqs729LkCgYBszT9OlaE6xgzMvZPeS79OboYBP4bFWcT5rbZhfTacY9pF+Xa67L3HC+8vaYOGu3iK/KSAGKEZ4/BUW2l36PjFq7QMprY6NGgYz9vSPahBB+baZXWYJTN5KlL/IrhAPBsdHo1qsUY3POMZboY4LPPDvk4nT1DA641MdbFJGWHaBA==";
    private static final String CERT = "MIID8zCCAtugAwIBAgIESZYC0jANBgkqhkiG9w0BAQsFADByMR8wHQYDVQQDDBZBcHBDZXJ0aWZpY2F0ZU1lYW5zQVBJMQwwCgYDVQQLDANJTkcxDDAKBgNVBAoMA0lORzESMBAGA1UEBwwJQW1zdGVyZGFtMRIwEAYDVQQIDAlBbXN0ZXJkYW0xCzAJBgNVBAYTAk5MMB4XDTE5MDMwNDEzNTkwOFoXDTIwMDMwNDE0NTkwOFowPDEbMBkGA1UECwwSc2FuZGJveF9laWRhc19xd2FjMR0wGwYDVQRhDBRQU0ROTC1TQlgtMTIzNDUxMjM0NTCCASIwDQYJKoZIhvcNAQEBBQADggEPADCCAQoCggEBAINL9UVJpaxQrHD6UEUf/vF+lFBA20OKqmfh1LVylHFmC+M2eKQWf1E6qOUgsJggLwW+RyU3SSrOM1KKFArNTLKtvGhdPHD90d8MgQ510bMkg7RfQ+LdHeGLiu4Z5T5qz4PHfPb5MZlOmdhaVbAhWLY4qTIWhc8ZMZjNdFcIWcLCdA3xGvpb2UFWrh4TLFgAZwTzhS6ZqQ/P85LcWgTPPpkZ6cau+Ek75wDWXLV7JIvt6CwPyuccG60oCc2zN+jOWn7/5UC4yZm2/pUiWt/JAcv9ZCRpR4qpTli6QA6DJqvJl+KJAQaThivfkVOC4yNLsWwCyZWmsSfv3vnk2tpJ83sCAwEAAaOBxjCBwzAVBgNVHR8EDjAMMAqgCKAGhwR/AAABMCEGA1UdIwQaMBigFgQUcEi7XgDA9Cb4xHTReNLETt+0clkwgYYGCCsGAQUFBwEDBHoweDAKBgYEAI5GAQEMADATBgYEAI5GAQYwCQYHBACORgEGAzBVBgYEAIGYJwIwSzA5MBEGBwQAgZgnAQMMBlBTUF9BSTARBgcEAIGYJwEBDAZQU1BfQVMwEQYHBACBmCcBAgwGUFNQX1BJDAZYLVdJTkcMBk5MLVhXRzANBgkqhkiG9w0BAQsFAAOCAQEAX2cd5VV48bkc9o1i//f0LG8IhvQiaqFYJFsBEyaWjJYrTVKcY1w6aL9hEnBfIY+6cXiHADkoI9cJGSeOFRw9c86Wyqe1cQhMri43os2svQCuMr/p9HFFs6FvdVJa+uNXB4U2wEBY++N0DPe65XiW7oJYkntZxpa3AfIpDS2+yo5rueWMsChBxC5KbNpKDt11UN14Bmxnwu9yzGUQkdfOE0xWyw9Vmy2X5oJVsBsEUa+UZ5GVOLq36bQNzCScqO1JCK7ZQfHaK89VfHrCx/zNx+INBPriTqpD0czQC28lsntB2JNRMksZZSio1tiwqwCSxCtD1BPiX5KUevQsfOxTAQ==";
    private static final String BASE_EXAMPLE_API = "https://jsonplaceholder.typicode.com";

    private WebClient webClient;
    private HashMap<String, String> headers;
    private Gson gson;

    @BeforeEach
    void setup() {
        gson = new Gson();
        webClient = new WebClient(KEY, CERT);
        headers = new HashMap<>();
        headers.put(Headers.CONTENT_TYPE, MediaType.APPLICATION_JSON);
    }

    @Test
    void testGetJsonItem() {
        // Arrange

        // Act
        var response = webClient.get(BASE_EXAMPLE_API+"/todos/1", headers);

        // Assert
        assertNotNull(response);
    }

    @Test
    void testPostJson() {
        // Arrange
        var payload = new JsonObject();
        payload.addProperty("title", "test");
        payload.addProperty("body", "beschrijving");
        payload.addProperty("userId", 2);

        var json = gson.toJson(payload);

        // Act
        var response = webClient.post(BASE_EXAMPLE_API+"/todos", headers, json);
        payload.addProperty("id", response.get("id").getAsNumber());

        // Assert
        assertEquals(payload, response);
    }

    @Test
    void testPutJson() {
        // Arrange
        var expected = new JsonObject();
        expected.addProperty("id", 1);

        // Act
        var response = webClient.put(BASE_EXAMPLE_API+"/posts/1", headers);

        // Assert
        assertEquals(expected, response);
    }

    @Test
    void testRequestJson() {
        // Arrange
        var payload = new JsonObject();
        payload.addProperty("id", 1);
        payload.addProperty("title", "test");
        payload.addProperty("body", "beschrijving");
        payload.addProperty("userId", 1);

        var json = gson.toJson(payload);

        // Act
        var response = webClient.request(HttpMethod.PUT, headers, BASE_EXAMPLE_API+"/posts/1", json);

        // Assert
        assertEquals(payload, response);
    }
}
