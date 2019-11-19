package API.Controllers;

import API.Services.AccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.ComponentScan;
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
    @GetMapping("/authorize")
    public Response authorize(@QueryParam("bank") String bank) {
        return Response.ok().entity(service.authorize(bank)).build();
    }

    @GetMapping("/accounts")
    public Response getUserAccounts(@QueryParam("bank") String bank, @RequestParam(name = "token") String token, @PathVariable String id) {
        return Response.ok().entity(service.getUserAccounts(bank, token, id)).build();
    }

    @GetMapping("/accounts/{id}/balances")
    public Response getAccountBalances(@PathVariable String id, @RequestParam(name = "token") String token, @QueryParam("bank") String bank) {
        return Response.ok().entity(service.getAccountBalances(bank,token,id)).build();
    }

    @GetMapping("/accounts/{id}/transactions")
    public Response getAccountTransactions(@PathVariable String id,  @RequestParam(name = "token") String token,@QueryParam("bank") String bank) {
        return Response.ok().entity(service.getAccountTransactions(bank,token,id)).build();
    }
}
