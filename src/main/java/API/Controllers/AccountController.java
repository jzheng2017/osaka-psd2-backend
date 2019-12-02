package API.Controllers;

import API.DTO.Account;
import API.DTO.PaymentRequest;
import API.DTO.Transaction;
import API.Services.AccountService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/accounts")
public class AccountController {
    private AccountService accountService = new AccountService();

    @Inject
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserAccounts(@QueryParam("token") String token) {
        Account accounts = accountService.getUserAccounts(token);

        if (accounts == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok().entity(accounts).build();
    }

    @Path("/{id}/details")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountTransactions(@PathParam("id") String id, @QueryParam("token") String token, @QueryParam("tableid") String tableid) {
        Transaction transactions = accountService.getAccountDetails(token, id, tableid);

        if (transactions == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok().entity(transactions).build();
    }

    @Path("/{id}/initiate")
    @POST
    public Response initTransaction(@PathParam("id") String id, @QueryParam("token") String token, @QueryParam("tableid") String tableid, PaymentRequest paymentRequest) {

        return Response
                .ok(accountService.initTransaction(token, id, tableid, paymentRequest))
                .build();
    }
}
