package API.Controllers;

import API.Adapters.BankAdapter;
import API.Adapters.BaseAdapter;
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

@Path("/connect")
public class BankController {
    private static final String BANK_TOKEN = "{{BANK}}";
    private static final String REDIRECT_URI = "http://localhost:8080/connect/" + BANK_TOKEN + "/finish";
    private static final URI FINAL_REDIRECT_URL = URI.create("http://localhost:4200/overzicht/rekeningen");
    private UserService userService;

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Path("/{bank}")
    @GET
    public Response connect(@PathParam("bank") Bank bank, @QueryParam("token") String token) {
        Response.Status errorCode = Response.Status.BAD_REQUEST;
        ArrayList<String> errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        ErrorMessage errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            BaseAdapter adapter = new BankAdapter(bank);
            var redirectUrl = REDIRECT_URI.replace(BANK_TOKEN, bank.toString());
            var url = adapter.getAuthorizationUrl(redirectUrl, token);
            return Response.temporaryRedirect(url).build();
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }

    @Path("/{bank}/finish")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response finish(@PathParam("bank") Bank bank, @QueryParam("code") String code, @QueryParam("state") String token) {
        ArrayList<String> errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        Response.Status errorCode = Response.Status.BAD_REQUEST;
        ErrorMessage errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            BaseAdapter adapter = new BankAdapter(bank);
            BankToken bankToken = adapter.token(code);
            bankToken.setBank(bank);
            userService.attachBankAccount(token, bankToken);
            return Response.temporaryRedirect(FINAL_REDIRECT_URL).build();
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }
}
