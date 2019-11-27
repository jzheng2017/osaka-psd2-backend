package API.Banks.ING;

import API.Banks.ING.Util.INGMapUtil;
import API.DTO.Account;
import API.DTO.ING.INGBooking;
import API.DTO.ING.INGTransaction;
import API.DTO.Transaction;

import java.util.ArrayList;

public class INGMapper {
    private INGMapUtil util;

    public INGMapper() {
        this.util = new INGMapUtil();
    }

    public Transaction mapToTransaction(INGTransaction ingTransaction) {
        if (ingTransaction != null) {
            Account ingAccount = ingTransaction.getAccount();
            INGTransaction transactionDetails = ingTransaction.getTransactions();
            ArrayList<INGBooking> ingBookedTransactions = transactionDetails.getBooked();
            ArrayList<INGBooking> ingPendingTransactions = transactionDetails.getPending();
            return util.getTransaction(ingAccount, ingBookedTransactions, ingPendingTransactions);
        } else return null;
    }
}
