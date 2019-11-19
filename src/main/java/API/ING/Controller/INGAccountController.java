package API.ING.Controller;

import API.ING.Service.INGAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;

import javax.ws.rs.core.Response;

@Component
public class INGAccountController {
    private INGAccountService ingAccountService = new INGAccountService();

    @GetMapping()
    public Response getUserAccounts() {
        return Response.ok().entity(ingAccountService.getUserAccounts()).build();
    }

    @GetMapping()
    public Response getAccountBalances(String accountID) {
        return Response.ok().entity(ingAccountService.getAccountBalances(accountID)).build();
    }

    @GetMapping()
    public Response getAccountTransactions(String accountID) {
        return Response.ok().entity(ingAccountService.getAccountTransactions(accountID)).build();
    }
}
