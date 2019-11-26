package API.Controllers;

import API.DTO.Account;
import API.DTO.Transaction;
import API.Services.AccountService;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/accounts")
public class AccountController {
    private AccountService service;

    @Inject
    public void setService(AccountService service) {
        this.service = service;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserAccounts(@QueryParam("token") String token) {
        Account accounts = service.getUserAccounts(token);

        if (accounts == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok().entity(accounts).build();
    }

    @Path("/{id}/details")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountTransactions(@PathParam("id") String id, @QueryParam("token") String token, @QueryParam("tableid") String tableid) {
        Transaction transactions = service.getAccountDetails(token, id, tableid);

        if (transactions == null)
            return Response.status(Response.Status.NOT_FOUND).build();

        return Response.ok().entity(transactions).build();
    }
}
