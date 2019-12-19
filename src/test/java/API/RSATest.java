package API;

import API.Utils.RSA;
import org.junit.jupiter.api.Test;

import java.security.InvalidKeyException;
import java.security.PrivateKey;
import java.security.cert.X509Certificate;
import java.security.interfaces.RSAPrivateKey;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class RSATest {

    @Test
    void testGetPrivateKeyFromStringThrowsError() {
        // Arrange
        RSAPrivateKey expectedResult = null;

        // Act
        var result = RSA.getPrivateKeyFromString("key");

        // Assert
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetCertificateFromStringThrowsError() {
        // Arrange
        X509Certificate expectedResult = null;

        // Act
        var result = RSA.getCertificateFromString("cert");

        // Assert
        assertEquals(expectedResult, result);
    }

    @Test
    void testSignThrowsError256() {
        // Arrange
        PrivateKey expectedResult = null;

        // Assert
        assertThrows(InvalidKeyException.class, () -> {

            // Act
            RSA.sign256(expectedResult, new byte[]{});
        });
    }

    @Test
    void testSignThrowsError() {
        // Arrange
        PrivateKey expectedResult = null;

        // Assert
        assertThrows(InvalidKeyException.class, () -> {

            // Act
            RSA.sign(expectedResult, new byte[]{});
        });
    }
}
