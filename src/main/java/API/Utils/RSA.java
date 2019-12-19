package API.Utils;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.logging.Logger;

public class RSA {
    private RSA() {

    }
    private static final Logger LOGGER = Logger.getLogger(RSA.class.getName());

    public static RSAPrivateKey getPrivateKeyFromString(String key) {
        RSAPrivateKey privKey = null;
        byte[] encoded = Base64.decodeBase64(key);
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            privKey = (RSAPrivateKey) kf.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            LOGGER.severe(e.toString());
        }
        return privKey;
    }

    public static X509Certificate getCertificateFromString(String cert) {
        Certificate certificate = null;
        byte[] encoded = Base64.decodeBase64(cert);
        try {
            CertificateFactory cf = CertificateFactory.getInstance("X.509");
            certificate = cf.generateCertificate(new ByteArrayInputStream(encoded));
        } catch (CertificateException e) {
            LOGGER.severe(e.toString());
        }
        return (X509Certificate) certificate;
    }

    public static String sign(PrivateKey privateKey, byte[] message) throws GeneralSecurityException {
        Signature sign = Signature.getInstance("SHA512withRSA");
        sign.initSign(privateKey);
        sign.update(message);
        return new String(Base64.encodeBase64(sign.sign()), StandardCharsets.UTF_8);
    }

    public static String sign256(PrivateKey privateKey, byte[] message) throws GeneralSecurityException {
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(privateKey);
        sign.update(message);
        return new String(Base64.encodeBase64(sign.sign()), StandardCharsets.UTF_8);
    }
}
