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

class GeneratorTest {

    private Generator generatorUnderTest;

    @BeforeEach
    void setUp() {
        generatorUnderTest = new Generator();
    }

    @Test
    void testGenerateDigestSha512() {
        // Setup
        final String expectedResult = "sha-512=" + new String(Base64.encodeBase64(DigestUtils.sha512("value")), StandardCharsets.UTF_8);

        // Run the test
        final String result = generatorUnderTest.generateDigestSha512("value");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGenerateDigestSha256() {
        // Setup
        final String expectedResult = "SHA-256=" + new String(Base64.encodeBase64(DigestUtils.sha256("value")), StandardCharsets.UTF_8);


        // Run the test
        final String result = generatorUnderTest.generateDigestSha256("value");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetServerTime() {
        // Setup
        final String expectedResult = DateTimeFormatter.RFC_1123_DATE_TIME.format(ZonedDateTime.now(ZoneOffset.UTC));

        // Run the test
        final String result = generatorUnderTest.getServerTime();

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
