package API;

import API.DTO.Balance;
import API.DTO.PaymentRequest;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Locale;
import java.util.TimeZone;

public class Generator {
    public String generateDigestSha512(String value) {
        byte[] sha = DigestUtils.sha512(value);
        return "sha-512=" + new String(Base64.encodeBase64(sha), StandardCharsets.UTF_8);
    }

    public String generateDigestSha256(String value) {
        byte[] sha = DigestUtils.sha256(value);
        return "SHA-256=" + new String(Base64.encodeBase64(sha), StandardCharsets.UTF_8);
    }

    public String getServerTime() {
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy HH:mm:ss z", Locale.US);
        dateFormat.setTimeZone(TimeZone.getTimeZone("GMT"));
        return dateFormat.format(calendar.getTime());
    }

    public float getBalanceFromBalances(Balance balance) {
        Balance tempBalance = balance.getBalances().get(0);
        return tempBalance.getBalanceAmount().getAmount();
    }

    public static String getErrors(String[] errors, String[] messages) {
        StringBuilder errorMessage = new StringBuilder();
        int index = 0;
        for (String error : errors) {
            if (error == null || error.isEmpty()) {
                errorMessage.append(messages[index].toUpperCase()).append(", ");
            }
            index++;
        }
        return errorMessage.toString();
    }
}
