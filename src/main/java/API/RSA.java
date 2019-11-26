package API;

import org.apache.commons.codec.binary.Base64;

import java.io.ByteArrayInputStream;
import java.io.UnsupportedEncodingException;
import java.nio.charset.StandardCharsets;
import java.security.*;
import java.security.cert.Certificate;
import java.security.cert.CertificateException;
import java.security.cert.CertificateFactory;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;
import java.security.spec.InvalidKeySpecException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;

public class RSA {
    private static Logger log = Logger.getLogger(RSA.class.getName());

    private RSA() {

    }

    public static RSAPrivateKey getPrivateKeyFromString(String key) {
        RSAPrivateKey privKey = null;
        byte[] encoded = Base64.decodeBase64(key);
        try {
            KeyFactory kf = KeyFactory.getInstance("RSA");
            PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);
            privKey = (RSAPrivateKey) kf.generatePrivate(keySpec);
        } catch (NoSuchAlgorithmException | InvalidKeySpecException e) {
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
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
            log.log(Level.SEVERE, Arrays.toString(e.getStackTrace()));
        }
        return (X509Certificate) certificate;
    }

    public static String sign(PrivateKey privateKey, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
        Signature sign = Signature.getInstance("SHA512withRSA");
        sign.initSign(privateKey);
        sign.update(message);
        return new String(Base64.encodeBase64(sign.sign()), "UTF-8");
    }

    public static String sign256(PrivateKey privateKey, byte[] message) throws NoSuchAlgorithmException, InvalidKeyException, SignatureException, UnsupportedEncodingException {
        Signature sign = Signature.getInstance("SHA256withRSA");
        sign.initSign(privateKey);
        sign.update(message);
        return new String(Base64.encodeBase64(sign.sign()), StandardCharsets.UTF_8);
    }
}
