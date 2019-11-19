package API.Controllers;

import API.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

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

    @GetMapping("/accounts")
    public Response getUserAccounts(@PathVariable String bank, @RequestParam(name = "token") String token, @PathVariable String id) {
        return Response.ok().entity(service.getUserAccounts(bank, token, id)).build();
    }

    @GetMapping("/{bank}/accounts/{id}/balances")
    public Response getAccountBalances(@PathVariable String id, @RequestParam(name = "token") String token, @PathVariable String bank) {
        return Response.ok().entity(service.getAccountBalances(bank,token,id)).build();
    }

    @GetMapping("/{bank}/accounts/{id}/transactions")
    public Response getAccountTransactions(@PathVariable String id,  @RequestParam(name = "token") String token,@PathVariable String bank) {
        return Response.ok().entity(service.getAccountTransactions(bank,token,id)).build();
    }
}
