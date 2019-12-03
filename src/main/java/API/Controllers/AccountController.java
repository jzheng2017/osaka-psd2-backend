package API.Controllers;

import API.DTO.Account;
import API.DTO.ErrorMessage;
import API.DTO.PaymentRequest;
import API.DTO.Transaction;
import API.Errors.Error;
import API.Generator;
import API.Services.AccountService;
import org.bouncycastle.crypto.digests.GeneralDigest;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.lang.reflect.Array;

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
        String[] possibleErrors = {token};
        String[] messages = {Error.INVALID_TOKEN,};
        String errorMessages = Generator.getErrors(possibleErrors,messages);
        if (errorMessages.isEmpty()) {
            Account accounts = accountService.getUserAccounts(token);
            return Response.ok().entity(accounts).build();
        } else {
            Response.Status errorCode = Response.Status.BAD_REQUEST;
            ErrorMessage errorMessage = new ErrorMessage(errorCode, Generator.getErrors(possibleErrors, messages));
            return Response.status(errorCode).entity(errorMessage).build();
        }
    }

    @Path("/{id}/details")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountTransactions(@PathParam("id") String id, @QueryParam("token") String token, @QueryParam("tableid") String tableid) {
        String[] possibleErrors = {id, token, tableid};
        String[] messages = {Error.INVALID_ID, Error.INVALID_TOKEN, Error.INVALID_TABLEID};
        String errorMessages = Generator.getErrors(possibleErrors, messages);
        if (errorMessages.isEmpty()) {
            Transaction transactions = accountService.getAccountDetails(token, id, tableid);
            return Response.ok().entity(transactions).build();
        } else {
            Response.Status errorCode = Response.Status.BAD_REQUEST;
            ErrorMessage errorMessage = new ErrorMessage(errorCode, errorMessages);
            return Response.status(errorCode).entity(errorMessage).build();
        }
    }
}
