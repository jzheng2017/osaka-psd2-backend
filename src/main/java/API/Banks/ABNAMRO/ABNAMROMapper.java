package API.Banks.ABNAMRO;

import API.DTO.*;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;

import javax.json.Json;
import java.net.URI;
import java.util.ArrayList;

public class ABNAMROMapper {
    private static final String TRANSACTION_ID = "transactionId";
    private static final String AMOUNT = "amount";

    public Account mapToUser(JsonObject json) {
        var iban = json.get("accountNumber").getAsString();
        var name = json.get("accountHolderName").getAsString();
        var currency = json.get("currency").getAsString();

        var account = new Account();
        account.setId(iban);
        account.setIban(iban);
        account.setName(name);
        account.setCurrency(currency);

        return account;
    }

    public ArrayList<Transaction> mapToTransactions(JsonObject json, Account account) {
        var transactionsJson = json.get("transactions").getAsJsonArray();

        var transactions = new ArrayList<Transaction>();
        for (JsonElement transactionElement : transactionsJson) {
            var transactionJson = transactionElement.getAsJsonObject();

            var transaction = mapToTransaction(transactionJson);

            transaction.setReceived(false);
            transaction.setSender(account);
            transaction.setBooked(true);

            transaction.setReceiver(mapReceiverToAccount(transactionJson));

            transactions.add(transaction);
        }
        return transactions;
    }

    private Transaction mapToTransaction(JsonObject transactionJson) {
        Transaction transaction = new Transaction();
        if (transactionJson.has(TRANSACTION_ID))
            transaction.setId(transactionJson.get(TRANSACTION_ID).getAsString());

        if (transactionJson.has("bookDate"))
            transaction.setDate(transactionJson.get("bookDate").getAsString());

        if (transactionJson.has(AMOUNT))
            transaction.setAmount(transactionJson.get(AMOUNT).getAsString());

        if (transactionJson.has("descriptionLines")) {
            var descriptionLines = transactionJson.get("descriptionLines").getAsJsonArray();
            if (descriptionLines.size() > 0) {
                var type = descriptionLines.get(0).getAsString();
                transaction.setType(type);
            }
            transaction.setAmount(transactionJson.get(AMOUNT).getAsString());
        }
        return transaction;
    }

    private Account mapReceiverToAccount(JsonObject transactionJson) {
        var receiver = new Account();

        if (transactionJson.has("counterPartyAccountNumber"))
            receiver.setIban(transactionJson.get("counterPartyAccountNumber").getAsString());

        if (transactionJson.has("counterPartyName"))
            receiver.setName(transactionJson.get("counterPartyName").getAsString());

        return receiver;
    }

    public Number mapToBalance(JsonObject json) {
        return json.get(AMOUNT).getAsNumber();
    }

    public String mapToTransactionId(JsonObject json) {
        return json.get(TRANSACTION_ID).getAsString();
    }

    public TransactionResponse mapToTransactionResponse(URI url) {
        var response = new TransactionResponse();
        response.setUrl(url);
        return response;
    }

    public Payment mapToPayment(JsonObject json) {
        var response = new Payment();

        if (json.has("status"))
            response.setPaid("EXECUTED".equals(json.get("status").getAsString()));

        if (json.has(TRANSACTION_ID))
            response.setId(TRANSACTION_ID);

        return response;
    }

    public JsonObject parsePaymentRequest(PaymentRequest paymentRequest) {
        var object = new JsonObject();
        var receiver = paymentRequest.getReceiver();
        var sender = paymentRequest.getSender();

        object.addProperty("initiatingpartyAccountNumber", sender.getIban());
        object.addProperty("counterpartyAccountNumber", receiver.getIban());
        object.addProperty(AMOUNT, paymentRequest.getAmount());
        object.addProperty("counterpartyName", receiver.getName());
        object.addProperty("remittanceInfo", paymentRequest.getInformation());

        return object;
    }
}
