package API.Adapter;

import API.DTO.Account;
import API.DTO.AuthorizationCode;
import API.DTO.Balance;
import API.DTO.Transaction;
import API.ING.Controller.INGAccountController;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;

@Component
public class INGAdapter extends BankAdapter {
    public final static String baseUrl = "https://api.sandbox.ing.com";

    private INGAccountController controller = new INGAccountController();

    @Override
    public Account getUserAccounts(String token, String id) {
        return controller.getUserAccounts();
    }

    @Override
    public Balance getAccountBalances(String token, String id)  {
        return controller.getAccountBalances(id);
    }

    @Override
    public Transaction getAccountTransactions(String token, String id)  {
        return controller.getAccountTransactions(id);
    }

    @Override
    public AuthorizationCode authorize() {
        return null;
    }
}

