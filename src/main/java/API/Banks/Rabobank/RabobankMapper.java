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

            var transaction = new Transaction();
            transaction.setId(object.get("entryReference").getAsString());
            transaction.setDate(object.get("bookingDate").getAsString());

            var receiver = object.get("creditorAccount").getAsJsonObject();
            var receiverAccount = new Account();
            receiverAccount.setType(null);
            receiverAccount.setIban(receiver.get("iban").getAsString());
            if(object.has("creditorName")) {
                receiverAccount.setName(object.get("creditorName").getAsString());
            }
            transaction.setReceiver(receiverAccount);

            var sender = object.get("debtorAccount").getAsJsonObject();
            var senderAccount = new Account();
            senderAccount.setType(null);
            senderAccount.setIban(sender.get("iban").getAsString());

            if(object.has("debtorName")) {
                senderAccount.setName(object.get("debtorName").getAsString());
            }

            transaction.setSender(senderAccount);

            var transactionAmount = object.get("transactionAmount").getAsJsonObject();
            var amount = transactionAmount.get("amount").getAsString();
            transaction.setAmount(amount);

            transaction.setType("Doorgifte");

            transaction.setBooked(true);
            transaction.setReceived(receiverAccount.getIban().equals(account.getIban()));

            transactions.add(transaction);
        }


        var details = new AccountDetails();
        details.setTransactions(transactions);
        details.setAccount(account);

        return details;
    }
}
