package API.Controllers;

import API.Services.AccountService;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/")
public class AccountController {
    private AccountService service = new AccountService();

//    @Inject
//    public void setService(AccountService service) {
//        this.service = service;
//    }

    @Path("rabo/authorize")
    @GET
    public Response authorizeRABO() {
        try {
            URI url = new URI(service.authorizeRABO());
            return Response.temporaryRedirect(url).build();
        } catch (URISyntaxException excep) {
            System.out.println(excep.getMessage());
        }
        return Response.status(Response.Status.BAD_GATEWAY).build();
    }

    @Path("ing/authorize")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response authorizeING() {
      return Response.ok().entity(service.authorizeING()).build();
    }

    @Path("{bank}/token")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response token(@QueryParam("code") String code, @PathParam("bank") String bank) {
        return Response.ok().entity(service.token(bank, code)).build();
    }

    @Path("{bank}/refresh")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response refresh(@QueryParam("code") String code, @PathParam("bank") String bank) {
        return Response.ok().entity(service.refresh(bank, code)).build();
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

    @Path("{bank}/check-balance")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String checkBalance(@QueryParam("token") String token, @PathParam("bank") String bank) {
        return service.checkEnoughBalance(bank, token);
    }
}
