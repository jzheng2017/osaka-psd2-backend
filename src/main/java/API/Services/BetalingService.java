package API.Services;

import API.Adapters.BankAdapter;
import API.DTO.PaymentRequest;
import API.DataSource.BankTokenDao;
import API.DataSource.UserDAO;

public class BetalingService {
    private UserDAO userDAO = new UserDAO();
    private BankTokenDao bankTokenDao = new BankTokenDao();
    public String initializePayment(String token, PaymentRequest paymentRequest, String tableid) {
        var user = userDAO.getUserByToken(token);
        var bankToken = bankTokenDao.getBankTokensForUser(user,tableid);
        var adapter = new BankAdapter(bankToken.getBank());
        return adapter.doPaymentRequest(bankToken.getAccessToken(), paymentRequest);
    }
}
