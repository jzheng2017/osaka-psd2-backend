package API.Adapter;

import API.ING.Controller.INGAccountController;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class INGAdapter extends BankAdapter {
    public final static String baseUrl = "https://api.sandbox.ing.com";

    private INGAccountController controller = new INGAccountController();

    @Override
    public ResponseEntity<String> getUserAccounts(String token) {
        return controller.getUserAccounts();
    }

    @Override
    public ResponseEntity<String> getAccountBalances(String token, String id)  {
        return controller.getAccountBalances(id);
    }

    @Override
    public ResponseEntity<String> getAccountTransactions(String token, String id)  {
        return controller.getAccountTransactions(id);
    }

    @Override
    public String authorize() {
        return null;
    }

    @Override
    public ResponseEntity<String> token(String code) {
        return null;
    }
}

