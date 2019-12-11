package API.Services;

import API.Banks.ClientFactory;
import API.DTO.PaymentRequest;
import API.DTO.TransactionResponse;
import API.DataSource.BankTokenDao;
import API.DataSource.UserDAO;

import javax.inject.Inject;

public class PaymentService {
    private UserDAO userDAO;
    private BankTokenDao bankTokenDao;

    @Inject
    public void setUserDAO(UserDAO userDAO) {
        this.userDAO = userDAO;
    }

    @Inject
    public void setBankTokenDao(BankTokenDao bankTokenDao) {
        this.bankTokenDao = bankTokenDao;
    }

    public TransactionResponse initiateTransaction(String token, PaymentRequest paymentRequest, String tableid) {
        var user = userDAO.getUserByToken(token);
        var bankToken = bankTokenDao.getBankTokensForUser(user, tableid);
        var client = ClientFactory.getClient(bankToken.getBank());
        return client.initiateTransaction(bankToken.getAccessToken(), paymentRequest);
    }
}
