package API;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;

public class Generator {
    private String generateDigest(String value, String shaMethode) {
        byte[] sha = DigestUtils.sha512(value);
        return shaMethode + "=" + new String(Base64.encodeBase64(sha), StandardCharsets.UTF_8);
    }
}
