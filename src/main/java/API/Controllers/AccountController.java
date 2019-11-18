package API.Controllers;

import API.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Controller
@ComponentScan({"API.Services.AccountService"})
public class AccountController {
    @Autowired
    private AccountService service;

    @GetMapping("/accounts")
    public Response getUserAccounts(@QueryParam("bank") String bank) {
        return Response.ok().entity(service.getUserAccounts(bank)).build();
    }

    @GetMapping("/accounts/{id}/balances")
    public Response getAccountBalances(@PathVariable String id, @QueryParam("bank") String bank) {
        return Response.ok().entity(service.getAccountBalances(id, bank)).build();
    }

    @GetMapping("/accounts/{id}/transactions")
    public Response getAccountTransactions(@PathVariable String id, @QueryParam("bank") String bank) {
        return Response.ok().entity(service.getAccountTransactions(id, bank)).build();
    }
}
