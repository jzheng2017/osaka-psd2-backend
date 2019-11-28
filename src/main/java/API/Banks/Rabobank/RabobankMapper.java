package API.Banks.Rabobank;

import API.Banks.Rabobank.Util.RaboMapUtil;
import API.DTO.Account;
import API.DTO.RABO.RaboBooking;
import API.DTO.RABO.RaboTransaction;
import API.DTO.Transaction;
import java.util.ArrayList;
import java.util.Objects;

public class RabobankMapper {
    private RaboMapUtil util;

    public RabobankMapper() {
        this.util = new RaboMapUtil();
    }

    public Transaction mapToTransaction(RaboTransaction raboTransaction) {
        var transaction = raboTransaction.getTransactions();
        ArrayList<RaboBooking> bookings = new ArrayList<>();

        var booked = transaction.getBooked();
        var pending = transaction.getPending();

        if(booked != null)
            bookings.addAll(transaction.getBooked());

        if(pending != null)
            bookings.addAll(transaction.getPending());

        Account account = raboTransaction.getAccount();

        return util.getTransaction(account, bookings);
    }
}
