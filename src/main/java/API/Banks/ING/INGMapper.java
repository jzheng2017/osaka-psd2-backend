package API.Banks.ING;

import API.DTO.Account;
import API.DTO.AccountDetails;
import API.DTO.Transaction;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

public class INGMapper {
    private Gson gson;
    private static final Logger LOGGER = Logger.getLogger(INGMapper.class.getName());

    public INGMapper() {
        gson = new Gson();
    }

    public String getIbanFromString(String info) {
        if (info.contains("IBAN")) {
            int startIndex = info.indexOf("NL");
            int stopIndex = info.indexOf("<");
            return info.substring(startIndex, stopIndex);
        } else {
            return null;
        }
    }

    private void parseTransactionToList(ArrayList<Transaction> transactions, JsonElement element, Boolean booked, Account account) {
        var object = element.getAsJsonObject();

        var information = object.get("remittanceInformationUnstructured").getAsString();

        var transaction = new Transaction();
        transaction.setId(object.get("transactionId").getAsString());

        if (booked)
            transaction.setDate(object.get("bookingDate").getAsString());

        transaction.setType(object.get("transactionType").getAsString());

        var transactionAmount = object.get("transactionAmount").getAsJsonObject();
        var amount = transactionAmount.get("amount").getAsString();
        transaction.setAmount(amount.replace("-", ""));
        transaction.setBooked(booked);

        var otherAccount = new Account();
        otherAccount.setIban(getIbanFromString(information));

        if (amount.contains("-")) {
            transaction.setSender(account);
            transaction.setReceiver(otherAccount);
            transaction.setReceived(false);
        } else {
            transaction.setSender(otherAccount);
            transaction.setReceiver(account);
            transaction.setReceived(true);
        }

        transactions.add(transaction);
    }

    public AccountDetails mapToAccountDetails(JsonObject object) {
        try {
            var account = gson.fromJson(object.getAsJsonObject("account").toString(), Account.class);
            var bookedTransactions = object.getAsJsonObject("transactions").getAsJsonArray("booked");
            var pendingTransactions = object.getAsJsonObject("transactions").getAsJsonArray("pending");

            var transactions = new ArrayList<Transaction>();

            for (JsonElement element : bookedTransactions) {
                parseTransactionToList(transactions, element, true, account);
            }

            for (JsonElement element : pendingTransactions) {
                parseTransactionToList(transactions, element, false, account);
            }

            var details = new AccountDetails();
            details.setAccount(account);
            details.setTransactions(transactions);

            return details;
        } catch (NullPointerException e) {
            LOGGER.log(Level.INFO,e.toString());
            return null;
        }
    }
}
