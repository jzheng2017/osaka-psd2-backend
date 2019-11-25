package API.Controllers;

import API.Services.AccountService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/")
//TODO foutafhandeling
public class AccountController {
    private AccountService service;

    @Inject
    public void setService(AccountService service) {
        this.service = service;
    }

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

    @Path("accounts")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getUserAccounts(@QueryParam("tokenrabo") String tokenRabo,@QueryParam("tokening") String tokenING) {
        return Response.ok().entity(service.getUserAccounts(tokenRabo, tokenING)).build();
    }

    @Path("{bank}/accounts/{id}/details")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public Response getAccountTransactions(@PathParam("id") String id, @QueryParam("token") String token, @PathParam("bank")String bank) {
        return Response.ok().entity(service.getAccountDetails(bank,token,id)).build();
    }

    @Path("{bank}/check-balance")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    public String checkBalance(@QueryParam("token") String token, @PathParam("bank") String bank) {
        return service.checkEnoughBalance(bank, token);
    }
}
