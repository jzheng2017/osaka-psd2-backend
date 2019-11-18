package controllers;

import Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;

import javax.ws.rs.core.Response;

public class AccountController {
    @Autowired
    private AccountService service;

    @GetMapping("/accounts")
    public Response getUserAccounts() {
//        return service.getUserAccounts();
        return null;
    }
}
