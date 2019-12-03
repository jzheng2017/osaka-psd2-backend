package API;

import API.DTO.Balance;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.digest.DigestUtils;

import java.lang.reflect.Array;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.*;

public class GenUtil {
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

    public static ArrayList<String> getErrors(String[] errors, String[] messages) {
        ArrayList<String> errorMessage = new ArrayList<>();
        int index = 0;
        for (String error : errors) {
            if (error == null || error.isEmpty()) {
                errorMessage.add(messages[index].toUpperCase());
            }
            index++;
        }
        return errorMessage;
    }

    public static ArrayList<String> getErrors(String error, String message) {
        ArrayList<String> errorMessage = new ArrayList<>();
        if (error == null || error.isEmpty()) {
            errorMessage.add(message.toUpperCase());
        }
        return errorMessage;
    }

    public static ArrayList<String> getErrors(Object error) {
        ArrayList<String> errorMessage = new ArrayList<>();
        Gson gson = new Gson();
        String object = gson.toJson(error);
        if (object.isEmpty() || object.equals("{}")) {
            errorMessage.add("EMPTY_BODY_SUBMITTED");
        } else {
            JsonObject jsonObject = gson.fromJson(object, JsonObject.class);
            Map<String, Object> attributes = new HashMap<>();
            Set<Map.Entry<String, JsonElement>> entrySet = jsonObject.entrySet();
            for (Map.Entry<String, JsonElement> entry : entrySet) {
                attributes.put(entry.getKey(), jsonObject.get(entry.getKey()).getAsString());
            }
            for (Map.Entry<String, Object> entry : attributes.entrySet()) {
                if (entry.getValue().toString().isEmpty()) {
                    errorMessage.add("INVALID_" + entry.getKey().toUpperCase());
                }
            }
        }
        return errorMessage;
    }
}
