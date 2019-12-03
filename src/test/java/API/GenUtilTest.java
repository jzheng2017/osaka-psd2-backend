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
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        final String expectedResult = dateFormat.format(calendar.getTime());
        // Run the test
        final String result = genUtilUnderTest.getServerTime();

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetBalanceFromBalances() {
        // Setup
        final Balance balance = new Balance();
        ArrayList<Balance> balances = new ArrayList<>();
        Balance newBalance = new Balance();
        BalanceAmount amount = new BalanceAmount("EUR",100);
        newBalance.setBalanceAmount(amount);
        balances.add(newBalance);
        balance.setBalances(balances);
        final double expectedResult = 100.0;

        // Run the test
        final float result = genUtilUnderTest.getBalanceFromBalances(balance);

        // Verify the results
        assertEquals(expectedResult, result, 0.0001);
    }

    @Test
    void testGetErrors() {
        // Setup
        final ArrayList<String> expectedResult = new ArrayList<>(Arrays.asList(new String[]{"HELP"}));

        // Run the test
        final ArrayList<String> result = GenUtil.getErrors(new String[]{""}, new String[]{"Help"});

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetErrorsV1() {
        // Setup
        final ArrayList<String> expectedResult = new ArrayList<>(Arrays.asList(new String[]{"MESSAGE"}));

        // Run the test
        final ArrayList<String> result = GenUtil.getErrors(null, "message");

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetErrorsV2() {
        // Setup
        final ArrayList<String> expectedResult = new ArrayList<>(Arrays.asList(new String[]{"INVALID_EMAIL"}));
        LoginRequest request = new LoginRequest();
        request.setEmail("");
        // Run the test
        final ArrayList<String> result = GenUtil.getErrors(request);

        // Verify the results
        assertEquals(expectedResult, result);
    }

    @Test
    void testGetErrorsV2EmptyBody() {
        // Setup
        final ArrayList<String> expectedResult = new ArrayList<>(Arrays.asList(new String[]{"EMPTY_BODY_SUBMITTED"}));
        LoginRequest request = new LoginRequest();
        // Run the test
        final ArrayList<String> result = GenUtil.getErrors(request);

        // Verify the results
        assertEquals(expectedResult, result);
    }
}
