package API;

import org.apache.commons.codec.digest.DigestUtils;

public class HashedPassword {
    public static String generate(String password) {
        return DigestUtils.sha512Hex(password);
    }

    public static boolean verify(String hashedPassword, String password) {
        return generate(password).equals(hashedPassword);
    }
}
