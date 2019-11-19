package API.Adapter;

import API.DTO.Account;
import API.DTO.AuthorizationCode;
import API.DTO.Balance;
import API.DTO.Transaction;
import API.RABO.Controller.RabobankController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import javax.ws.rs.core.Response;

@Component("RaboAdapter")
public class RaboAdapter extends BankAdapter {
    private RabobankController controller =  new RabobankController();
    @Override
    public Account getUserAccounts(String token, String id) {
        return controller.getUserAccounts(token);
    }

    @Override
    public Balance getAccountBalances(String token, String id) {
        return controller.getAccountBalances(token,id);
    }

    @Override
    public Transaction getAccountTransactions(String token, String id) {
        return controller.getAccountTransactions(token, id);
    }

    @Override
    public String authorize() {
        return controller.authorize();
    }

    @Override
    public ResponseEntity<String> token(String code) {
        return controller.token(code);
    }
}
