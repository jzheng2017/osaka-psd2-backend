package API.RABO.Controller;

import API.DTO.Account;
import API.DTO.Balance;
import API.DTO.Transaction;
import API.RABO.Service.RabobankService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import java.io.IOException;
import java.security.GeneralSecurityException;

@Controller
public class RabobankController {
    @Autowired
    private RabobankService rabobankService;

    @GetMapping("/authorize")
    public String authorize() {
        return rabobankService.authorize();
    }

    @GetMapping("/token")
    public ResponseEntity<String> token(@RequestParam(name = "code") String code) {
        return rabobankService.token(code);
    }

    @GetMapping("/accounts")
    public Account getUserAccounts(@RequestParam(name = "token") String token)  {
        return rabobankService.getUserAccounts(token);
    }

    @GetMapping("/accounts/{id}/balances")
    public Balance getAccountBalances(@RequestParam(name = "token") String token, @PathVariable String id) {
        return rabobankService.getAccountBalances(token, id);
    }

    @GetMapping("/accounts/{id}/transactions")
    public Transaction getAccountTransactions(@RequestParam(name = "token") String token, @PathVariable String id) {
        return rabobankService.getAccountTransactions(token, id);
    }
}
