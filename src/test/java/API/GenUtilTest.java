package API;

import API.DTO.Auth.LoginRequest;
import API.DTO.Balance;
import API.DTO.BalanceAmount;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

import static org.junit.jupiter.api.Assertions.assertEquals;

class GenUtilTest {
    private GenUtil genUtil;

    @BeforeEach
    void setup() {
        genUtil = new GenUtil();
    }

    @Test
    void testGenerateDigestSha512() {
        // Arrange
        var expected = "sha-512=" + new String(Base64.encodeBase64(DigestUtils.sha512("value")), StandardCharsets.UTF_8);

        // Act
        var result = genUtil.generateDigestSha512("value");

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void testGenerateDigestSha256() {
        // Arrange
        var expected = "SHA-256=" + new String(Base64.encodeBase64(DigestUtils.sha256("value")), StandardCharsets.UTF_8);

        // Act
        var result = genUtil.generateDigestSha256("value");

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
        final String result = genUtil.getServerTime();

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void testGetBalanceFromBalances() {
        // Arrange
        var balance = new Balance();
        var balances = new ArrayList<Balance>();
        var newBalance = new Balance();
        var amount = new BalanceAmount("EUR",100);
        newBalance.setBalanceAmount(amount);
        balances.add(newBalance);
        balance.setBalances(balances);
        double expectedResult = 100.0;

        // Act
        float result = genUtil.getBalanceFromBalances(balance);

        // Assert
        assertEquals(expectedResult, result, 0.0001);
    }

    @Test
    void testGetErrors() {
        // Arrange
        ArrayList<String> expectedResult = new ArrayList<>(Arrays.asList(new String[]{"HELP"}));

        // Act
        ArrayList<String> result = GenUtil.getErrors(new String[]{""}, new String[]{"Help"});

        // Assert
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetErrorsV1() {
        // Arrange
        ArrayList<String> expectedResult = new ArrayList<>(Arrays.asList(new String[]{"MESSAGE"}));

        // Act
        ArrayList<String> result = GenUtil.getErrors(null, "message");

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
        ArrayList<String> result = GenUtil.getErrors(request);

        // Assert
        assertEquals(expected, result);
    }

    @Test
    void testGetErrorsV2EmptyBody() {
        // Arrange
        ArrayList<String> expected = new ArrayList<>(Arrays.asList(new String[]{"EMPTY_BODY_SUBMITTED"}));
        var request = new LoginRequest();

        // Act
        ArrayList<String> result = GenUtil.getErrors(request);

        // Assert
        assertEquals(expected, result);
    }
}
