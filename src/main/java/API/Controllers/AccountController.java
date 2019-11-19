package API.Controllers;

import API.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class AccountController {
    @Autowired
    private AccountService service;
    @GetMapping("/{bank}/authorize")
    public String authorize(@PathVariable String bank) {
        return service.authorize(bank);
    }

    @GetMapping("/{bank}/token")
    public ResponseEntity<String> token(@RequestParam(name = "code") String code, @PathVariable String bank) {
        return service.token(bank, code);
    }

    @GetMapping("/{bank}/accounts")
    public ResponseEntity<String> getUserAccounts(@PathVariable String bank, @RequestParam(name = "token") String token) {
        return service.getUserAccounts(bank, token);
    }

    @GetMapping("/{bank}/accounts/{id}/balances")
    public ResponseEntity<String> getAccountBalances(@PathVariable String id, @RequestParam(name = "token") String token, @PathVariable String bank) {
        return service.getAccountBalances(bank,token,id);
    }

    @GetMapping("/{bank}/accounts/{id}/transactions")
    public ResponseEntity<String> getAccountTransactions(@PathVariable String id, @RequestParam(name = "token") String token, @PathVariable String bank) {
        return service.getAccountTransactions(bank,token,id);
    }
}
