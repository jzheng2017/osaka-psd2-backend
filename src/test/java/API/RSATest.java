package API;

import org.junit.jupiter.api.Test;

import java.io.UnsupportedEncodingException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RSATest {

    @Test
    void testGetPrivateKeyFromStringThrowsError() {
        // Setup
        final RSAPrivateKey expectedResult = null;

        // Run the test
        final RSAPrivateKey result = RSA.getPrivateKeyFromString("key");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetCertificateFromStringThrowsError() {
        // Setup
        final X509Certificate expectedResult = null;

        // Run the test
        final X509Certificate result = RSA.getCertificateFromString("cert");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testSignThrowsError256() {
        // Setup
        final PrivateKey expectedResult = null;
        // Verify the results
        assertThrows(InvalidKeyException.class, () -> {
            RSA.sign256(expectedResult, new byte[]{});
        });
    }

    @Test
    void testSignThrowsError() {
        // Setup
        final PrivateKey expectedResult = null;
        // Verify the results
        assertThrows(InvalidKeyException.class, () -> {
            RSA.sign(expectedResult, new byte[]{});
        });
    }
}
