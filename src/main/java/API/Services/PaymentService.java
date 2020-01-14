package API.Services;

import API.Banks.ClientFactory;
import API.DTO.PaymentRequest;
import API.DTO.TransactionResponse;
import API.DataSource.BankTokenDao;

import javax.inject.Inject;

public class PaymentService {
    private BankTokenDao bankTokenDao;

    @Inject
    public void setBankTokenDao(BankTokenDao bankTokenDao) {
        this.bankTokenDao = bankTokenDao;
    }

    public TransactionResponse initiateTransaction(String token, PaymentRequest paymentRequest, String tableid) {
        var bankToken = bankTokenDao.getBankTokensForUser(token, tableid);
        var client = ClientFactory.getClient(bankToken.getBank());
        return client.initiateTransaction(bankToken.getAccessToken(), paymentRequest);
    }
}
