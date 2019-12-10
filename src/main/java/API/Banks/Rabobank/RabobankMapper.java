package API.Banks.Rabobank;

import API.DTO.Account;
import API.DTO.AccountDetails;
import API.DTO.Transaction;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import java.util.ArrayList;

public class RabobankMapper {
    private Gson gson;

    public RabobankMapper() {
        gson = new Gson();
    }

    public AccountDetails mapToAccountDetails(JsonObject response) {
        var account = gson.fromJson(response.getAsJsonObject("account").toString(), Account.class);
        var bookedTransactions = response.getAsJsonObject("transactions").getAsJsonArray("booked");
        var transactions = new ArrayList<Transaction>();
        for(JsonElement element : bookedTransactions) {
            var object = element.getAsJsonObject();
            transactions.add(getTransaction(account, object));
        }
        var details = new AccountDetails();
        details.setTransactions(transactions);
        details.setAccount(account);
        return details;
    }

    private Transaction getTransaction(Account account, JsonObject object) {
        var transaction = new Transaction();
        transaction.setId(object.get("entryReference").getAsString());
        transaction.setDate(object.get("bookingDate").getAsString());
        var receiver = object.get("creditorAccount").getAsJsonObject();
        Account receiverAccount = getAccount(object, receiver, "creditorName");
        transaction.setReceiver(receiverAccount);
        var sender = object.get("debtorAccount").getAsJsonObject();
        transaction.setSender(getAccount(object, sender, "debtorName"));
        var transactionAmount = object.get("transactionAmount").getAsJsonObject();
        var amount = transactionAmount.get("amount").getAsString();
        transaction.setAmount(amount);
        transaction.setType("Doorgifte");
        transaction.setBooked(true);
        transaction.setReceived(receiverAccount.getIban().equals(account.getIban()));
        return transaction;
    }

    private Account getAccount(JsonObject object, JsonObject receiver, String creditorName) {
        var receiverAccount = new Account();
        receiverAccount.setType(null);
        receiverAccount.setIban(receiver.get("iban").getAsString());
        if (object.has(creditorName)) {
            receiverAccount.setName(object.get(creditorName).getAsString());
        }
        return receiverAccount;
    }
}
