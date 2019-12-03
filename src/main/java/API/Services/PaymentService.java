package API.Services;

import API.Adapters.BankAdapter;
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
        var adapter = new BankAdapter(bankToken.getBank());
        return adapter.initiateTransaction(bankToken.getAccessToken(), paymentRequest);
    }
}
