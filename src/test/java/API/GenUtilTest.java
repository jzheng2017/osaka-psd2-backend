package API;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.time.ZoneOffset;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenUtilTest {

    private GenUtil genUtilUnderTest;

    @BeforeEach
    void setUp() {
        genUtilUnderTest = new GenUtil();
    }

    @Test
    void testGenerateDigestSha512() {
        // Setup
        final String expectedResult = "sha-512=" + new String(Base64.encodeBase64(DigestUtils.sha512("value")), StandardCharsets.UTF_8);

        // Run the test
        final String result = genUtilUnderTest.generateDigestSha512("value");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGenerateDigestSha256() {
        // Setup
        final String expectedResult = "SHA-256=" + new String(Base64.encodeBase64(DigestUtils.sha256("value")), StandardCharsets.UTF_8);


        // Run the test
        final String result = genUtilUnderTest.generateDigestSha256("value");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetServerTime() {
        // Setup
        final String expectedResult = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC));

        // Run the test
        final String result = genUtilUnderTest.getServerTime();

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
