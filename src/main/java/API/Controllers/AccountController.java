package API.Controllers;

import API.DTO.Account;
import API.DTO.BankToken;
import API.DTO.Transaction;
import API.Services.AccountService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/")
public class AccountController {
    private AccountService service;

    @Inject
    public void setService(AccountService service) {
        this.service = service;
    }

    @Path("rabo/authorize")
    @GET
    public Response authorizeRABO() {
        Logger log = Logger.getLogger(getClass().getName());
        try {
            URI url = new URI(service.authorizeRABO());
            return Response.temporaryRedirect(url).build();
        } catch (URISyntaxException | NullPointerException excep) {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("ing/authorize")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response authorizeING() {
        BankToken token = service.authorizeING();
        if (token != null) {
            return Response.ok().entity(token).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("{bank}/token")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response token(@QueryParam("code") String code, @PathParam("bank") String bank) {
        BankToken token = service.token(bank, code);
        if (token != null) {
            return Response.ok().entity(token).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("{bank}/refresh")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response refresh(@QueryParam("token") String code, @PathParam("bank") String bank) {
        BankToken token = service.refresh(bank, code);
        if (token != null) {
            return Response.ok().entity(token).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("accounts")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserAccounts(@QueryParam("token") String token) {
        Account accounts = service.getUserAccounts(token);
        if (accounts != null) {
            return Response.ok().entity(accounts).build();
        } else {
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }

    @Path("accounts/{id}/details")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAccountTransactions(@PathParam("id") String id, @QueryParam("token") String token, @QueryParam("tableid") String tableid) {
        Transaction transactions = service.getAccountDetails(token, id, tableid);
        if (transactions != null) {
            return Response.ok().entity(transactions).build();
        } else return Response.status(Response.Status.NOT_FOUND).build();
    }

    @Path("{bank}/check-balance")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response checkBalance(@QueryParam("token") String token, @PathParam("bank") String bank) {
        String checkEnoughBalance = service.checkEnoughBalance(bank, token);
        if (checkEnoughBalance != null) {
            return Response.ok().entity(checkEnoughBalance).build();
        } else return Response.status(Response.Status.NOT_FOUND).build();
    }
}
