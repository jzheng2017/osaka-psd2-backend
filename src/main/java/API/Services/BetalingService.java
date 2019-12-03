package API.Services;

import API.Adapters.BankAdapter;
import API.DTO.PaymentRequest;
import API.DTO.TransactionResponse;
import API.DataSource.BankTokenDao;
import API.DataSource.UserDAO;

public class BetalingService {
    private UserDAO userDAO = new UserDAO();
    private BankTokenDao bankTokenDao = new BankTokenDao();

    public TransactionResponse initiateTransaction(String token, PaymentRequest paymentRequest, String tableid) {
        var user = userDAO.getUserByToken(token);
        var bankToken = bankTokenDao.getBankTokensForUser(user,tableid);
        var adapter = new BankAdapter(bankToken.getBank());
        return adapter.initiateTransaction(bankToken.getAccessToken(), paymentRequest);
    }
}
