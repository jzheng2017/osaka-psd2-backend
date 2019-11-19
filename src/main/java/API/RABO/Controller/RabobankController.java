package API.RABO.Controller;

import API.DTO.Account;
import API.DTO.AuthorizationCode;
import API.DTO.Balance;
import API.DTO.Transaction;
import API.RABO.Service.RabobankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Component
public class RabobankController {
    @Autowired
    private RabobankService rabobankService = new RabobankService();

    public AuthorizationCode authorize() {
        return rabobankService.authorize();
    }

    public ResponseEntity<String> token( String code) {
        return rabobankService.token(code);
    }

    public Account getUserAccounts(String token)  {
        return rabobankService.getUserAccounts(token);
    }

    public Balance getAccountBalances(String token, String id) {
        return rabobankService.getAccountBalances(token, id);
    }

    public Transaction getAccountTransactions(String token, String id) {
        return rabobankService.getAccountTransactions(token, id);
    }
}
