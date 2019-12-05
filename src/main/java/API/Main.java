package API;

import API.DTO.Account;
import API.DTO.Transaction;
import API.DTO.TransactionCategory;
import API.DataSource.TransactionDAO;
import API.DataSource.UserDAO;

public class Main {
    public static void main(String[] args) {

        var transactionDAO = new TransactionDAO();
        var userDAO = new UserDAO();


        var token = "a9bc5b49-cf2b-45d5-8c3e-3f1db718733d";
        var user = userDAO.getUserByToken(token);

        /*
        var transactionCategory = new TransactionCategory();
        transactionCategory.setUser(user);
        transactionCategory.setColor("blauw");
        transactionCategory.setName("Belastinguitgaven");

        transactionDAO.createTransactionCategory(transactionCategory);
        */

        var transactionCategory = new TransactionCategory();
        transactionCategory.setId(1);

        var transaction = new Transaction();
        var account = new Account();
        account.setIban("NLRABOXXX");
        transaction.setSender(account);

        //transactionDAO.addTransactionToCategory(transactionCategory, transaction);


        var tc = transactionDAO.getCategoryForTransaction(user, transaction);
        System.out.println("KLEUR: "+tc.getColor());
    }
}
