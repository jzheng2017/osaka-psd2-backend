package API.Controllers;

import API.DTO.Account;
import API.DTO.ErrorMessage;
import API.DTO.Transaction;
import API.Errors.Error;
import API.GenUtil;
import API.Services.AccountService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/accounts")
public class AccountController {
    private AccountService accountService;

    @Inject
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserAccounts(@QueryParam("token") String token) {
        ArrayList<String> errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        Response.Status errorCode = Response.Status.BAD_REQUEST;
        ErrorMessage errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            Account userAccounts = accountService.getUserAccounts(token);
            if (userAccounts != null) {
                return Response.ok().entity(userAccounts).build();
            } else {
                errorMessages.add(Error.INVALID_TOKEN + " OR " + Error.INVALID_TABLEID);
                errorMessage.setErrorMessage(errorMessages);
            }
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }

    @Path("/{id}/details")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountTransactions(@PathParam("id") String id, @QueryParam("token") String token, @QueryParam("tableid") String tableid) {
        String[] possibleErrors = {id, token, tableid};
        String[] messages = {Error.INVALID_ID, Error.INVALID_TOKEN, Error.INVALID_TABLEID};
        ArrayList<String> errorMessages = GenUtil.getErrors(possibleErrors, messages);
        Response.Status errorCode = Response.Status.BAD_REQUEST;
        ErrorMessage errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            Transaction accountDetails = accountService.getAccountDetails(token, id, tableid);
            if (accountDetails != null) {
                return Response.ok().entity(accountDetails).build();
            } else {
                errorMessages.add(Error.INVALID_TOKEN + " OR " + Error.INVALID_TABLEID);
                errorMessage.setErrorMessage(errorMessages);
            }
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }
}
