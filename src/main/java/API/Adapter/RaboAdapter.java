package API.Adapter;

import API.RABO.Controller.RabobankController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component("RaboAdapter")
public class RaboAdapter extends BankAdapter {
    private RabobankController controller =  new RabobankController();
    @Override
    public ResponseEntity<String> getUserAccounts(String token) {
        return controller.getUserAccounts(token);
    }

    @Override
    public ResponseEntity<String> getAccountBalances(String token, String id) {
        return controller.getAccountBalances(token,id);
    }

    @Override
    public ResponseEntity<String> getAccountTransactions(String token, String id) {
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
