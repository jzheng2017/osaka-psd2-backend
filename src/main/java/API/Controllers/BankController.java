package API.Controllers;

import API.Adapters.BankAdapter;
import API.Adapters.BaseAdapter;
import API.DTO.Bank;
import API.DTO.BankToken;
import API.DTO.ErrorMessage;
import API.DTO.Transaction;
import API.Errors.Error;
import API.Generator;
import API.Services.UserService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/connect")
public class BankController {
    private static final String BANK_TOKEN = "{{BANK}}";
    private static final String REDIRECT_URI = "http://localhost:8080/connect/"+BANK_TOKEN+"/finish";
    private static final URI FINAL_REDIRECT_URL = URI.create("http://localhost:4200/overzicht/rekeningen");

    private UserService userService;

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Path("/{bank}")
    @GET
    public Response connect(@PathParam("bank") Bank bank, @QueryParam("token") String token) {
        String[] possibleErrors = {token};
        String[] messages = {Error.INVALID_TOKEN,};
        Response.Status errorCode = Response.Status.BAD_REQUEST;
        String errorMessages = Generator.getErrors(possibleErrors, messages);
        ErrorMessage errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            try {
                BaseAdapter adapter = new BankAdapter(bank);
                var redirectUrl = REDIRECT_URI.replace(BANK_TOKEN, bank.toString());
                var url = adapter.getAuthorizationUrl(redirectUrl, token);
                return Response.temporaryRedirect(url).build();
            } catch (NullPointerException ex) {
                errorMessage.setErrorBody(ex.getMessage());
                return Response.status(errorCode).entity(errorMessage).build();
            }
        } else {
            return Response.status(errorCode).entity(errorMessage).build();
        }
    }

    @Path("/{bank}/finish")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response finish(@PathParam("bank") Bank bank, @QueryParam("code") String code, @QueryParam("state") String token) {
        String[] possibleErrors = {String.valueOf(bank), token};
        String[] messages = {Error.INVALID_BANK, Error.INVALID_TOKEN,};
        String errorMessages = Generator.getErrors(possibleErrors, messages);
        if (errorMessages.isEmpty()) {
            BaseAdapter adapter = new BankAdapter(bank);
            BankToken bankToken = adapter.token(code);
            bankToken.setBank(bank);
            userService.attachBankAccount(token, bankToken);
            return Response.temporaryRedirect(FINAL_REDIRECT_URL).build();
        } else {
            Response.Status errorCode = Response.Status.BAD_REQUEST;
            ErrorMessage errorMessage = new ErrorMessage(errorCode, errorMessages);
            return Response.status(errorCode).entity(errorMessage).build();
        }
    }
}
