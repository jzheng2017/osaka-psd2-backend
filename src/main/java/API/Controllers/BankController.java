package API.Controllers;

import API.Banks.BankClient;
import API.DTO.Bank;
import API.DTO.BankToken;
import API.DTO.ErrorMessage;
import API.Errors.Error;
import API.GenUtil;
import API.Services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;

@Path("/")
public class BankController {
    private static final String BANK_TOKEN = "{{BANK}}";
    private static final String REDIRECT_URI = "http://steinmilder:8080/connect/" + BANK_TOKEN + "/finish";
    private static final URI FINAL_REDIRECT_URL = URI.create("http://localhost:4200/overzicht/rekeningen");

    private UserService userService;

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Path("connect/{bank}")
    @GET
    public Response connect(@PathParam("bank") Bank bank, @QueryParam("token") String token) {
        Response.Status errorCode = Response.Status.BAD_REQUEST;
        ArrayList<String> errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        ErrorMessage errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            boolean limitReached = userService.checkIfAvailable(token).isLimitReached();
            if(!limitReached) {
                var client = new BankClient(bank);
                var redirectUrl = REDIRECT_URI.replace(BANK_TOKEN, bank.toString());
                var url = client.getAuthorizationUrl(redirectUrl, token);
                return Response.temporaryRedirect(url).build();
            }
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }



    @Path("connect/{bank}/finish")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response finish(@PathParam("bank") Bank bank, @QueryParam("code") String code, @QueryParam("state") String token) {
        ArrayList<String> errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        Response.Status errorCode = Response.Status.BAD_REQUEST;
        ErrorMessage errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            var adapter = new BankClient(bank);
            BankToken bankToken = adapter.token(code);
            bankToken.setBank(bank);
            userService.attachBankAccount(token, bankToken);
            return Response.temporaryRedirect(FINAL_REDIRECT_URL).build();
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }

    @Path("disconnect")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBankAccount(@QueryParam("token") String token, @QueryParam("tableid") String tableid) {
        Response.Status errorCode = Response.Status.BAD_REQUEST;
        String[] possibleErrors = {token, tableid};
        String[] possibleErrorMessages = {Error.INVALID_TOKEN, Error.INVALID_TABLEID};
        ArrayList<String> errorMessages = GenUtil.getErrors(possibleErrors,possibleErrorMessages);
        ErrorMessage errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            userService.deleteBankAccount(token,tableid);
            return Response.ok().build();
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }

    @Path("connections")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConnections(@QueryParam("token") String token) {
        Response.Status errorCode = Response.Status.BAD_REQUEST;
        ArrayList<String> errorMessages = GenUtil.getErrors(token,Error.INVALID_TOKEN);
        ErrorMessage errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            return Response.ok().entity(userService.checkIfAvailable(token)).build();
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }
}
