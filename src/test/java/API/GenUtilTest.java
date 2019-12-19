package API;

import API.DTO.Auth.LoginRequest;
import API.Utils.GenUtil;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static API.Utils.GenUtil.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class GenUtilTest {

    @BeforeEach
    void setup() {
    }

    @Test
    void testGenerateDigestSha512() {
        // Arrange
        var expected = "sha-512=" + new String(Base64.encodeBase64(DigestUtils.sha512("value")), StandardCharsets.UTF_8);

        // Act
        var result = generateDigestSha512("value");

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void testGenerateDigestSha256() {
        // Arrange
        var expected = "SHA-256=" + new String(Base64.encodeBase64(DigestUtils.sha256("value")), StandardCharsets.UTF_8);

        // Act
        var result = generateDigestSha256("value");

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void testGetServerTime() {
        // Arrange
        var calendar = Calendar.getInstance();
        var dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        var expected = dateFormat.format(calendar.getTime());

        // Act
        final String result = getServerTime();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void testGetErrors() {
        // Arrange
        ArrayList<String> expectedResult = new ArrayList<>(Arrays.asList(new String[]{"HELP"}));

        // Act
        ArrayList<String> result = getErrors(new String[]{""}, new String[]{"Help"});

        // Assert
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetErrorsV1() {
        // Arrange
        ArrayList<String> expectedResult = new ArrayList<>(Arrays.asList(new String[]{"MESSAGE"}));

        // Act
        ArrayList<String> result = getErrors(null, "message");

        // Assert
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetErrorsV2() {
        // Arrange
        ArrayList<String> expected = new ArrayList<>(Arrays.asList(new String[]{"INVALID_EMAIL"}));
        var request = new LoginRequest();
        request.setEmail("");

        // Act
        ArrayList<String> result = getErrors(request);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void testGetErrorsV2EmptyBody() {
        // Arrange
        ArrayList<String> expected = new ArrayList<>(Arrays.asList(new String[]{"EMPTY_BODY_SUBMITTED"}));
        var request = new LoginRequest();

        // Act
        ArrayList<String> result = getErrors(request);

        // Assert
        assertEquals(expected, result);
    }
}
