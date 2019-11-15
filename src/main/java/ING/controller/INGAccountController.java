package ING.controller;

import ING.service.INGAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class INGAccountController {
    @Autowired
    private INGAccountService ingAccountService;

    @GetMapping()
    public static ResponseEntity<String> getUserAccounts() {
        return null;
    }

    @GetMapping()
    public static ResponseEntity<String> getAccountBalances() {
        return null;
    }

    @GetMapping()
    public static ResponseEntity<String> getAccountTransactions() {
        return null;
    }
}
