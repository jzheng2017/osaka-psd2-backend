package API.RABO.Controller;

import API.RABO.Service.RabobankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

@Component
public class RabobankController {
    @Autowired
    private RabobankService rabobankService = new RabobankService();

    public String authorize() {
        return rabobankService.authorize();
    }

    public ResponseEntity<String> token(String code) {
        return rabobankService.token(code);
    }

    public ResponseEntity<String> getUserAccounts(String token)  {
        return rabobankService.getUserAccounts(token);
    }

    public ResponseEntity<String> getAccountBalances(String token, String id) {
        return rabobankService.getAccountBalances(token, id);
    }

    public ResponseEntity<String> getAccountTransactions(String token, String id) {
        return rabobankService.getAccountTransactions(token, id);
    }
}
