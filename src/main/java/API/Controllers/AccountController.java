package API.Controllers;

import API.Services.AccountService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class AccountController {
    private AccountService service = new AccountService();

//    @Inject
//    public void setService(AccountService service) {
//        this.service = service;
//    }

    @Path("{bank}/authorize")
    @GET
    public String authorize(@PathParam("bank") String bank) {
        return service.authorize(bank);
    }

    @Path("{bank}/token")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response token(@QueryParam("code") String code, @PathParam("bank") String bank) {
        return Response.ok().entity(service.token(bank, code)).build();
    }

    @Path("{bank}/accounts")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserAccounts(@PathParam("bank") String bank, @QueryParam("token") String token) {
        return Response.ok().entity(service.getUserAccounts(bank, token)).build();
    }
    @Path("{bank}/accounts/{id}/balances")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAccountBalances(@PathParam("id") String id, @QueryParam("token") String token, @PathParam("bank") String bank) {
        return Response.ok().entity(service.getAccountBalances(bank,token,id)).build();
    }

    @Path("{bank}/accounts/{id}/transactions")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAccountTransactions(@PathParam("id") String id, @QueryParam("token") String token, @PathParam("bank")String bank) {
        return Response.ok().entity(service.getAccountTransactions(bank,token,id)).build();
    }
}
