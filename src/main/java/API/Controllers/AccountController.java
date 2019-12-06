package API.Controllers;

import API.DTO.*;
import API.DTO.Responses.AccountsResponse;
import API.Errors.Error;
import API.GenUtil;
import API.Services.AccountService;
import org.apache.commons.lang.ArrayUtils;

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
            AccountsResponse userAccounts = accountService.getUserAccounts(token);
            if (userAccounts.getAccounts() != null && !userAccounts.getAccounts().isEmpty()) {
                return Response.ok().entity(userAccounts).build();
            } else {
                errorMessages.add(Error.INVALID_TOKEN);
                errorMessage.setErrorMessage(errorMessages);
            }
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }


    @Path("/{id}/details")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountDetails(@PathParam("id") String id, @QueryParam("token") String token, @QueryParam("tableid") String tableid) {
        String[] possibleErrors = {id, token, tableid};
        String[] messages = {Error.INVALID_ID, Error.INVALID_TOKEN, Error.INVALID_TABLEID};
        ArrayList<String> errorMessages = GenUtil.getErrors(possibleErrors, messages);
        Response.Status errorCode = Response.Status.BAD_REQUEST;
        ErrorMessage errorMessage = new ErrorMessage(errorCode, errorMessages);

        if (errorMessages.isEmpty()) {
            AccountDetails accountDetails = accountService.getAccountDetails(token, id, tableid);
            if (accountDetails != null) {
                return Response.ok().entity(accountDetails).build();
            } else {
                errorMessages.add(Error.INVALID_TOKEN);
                errorMessages.add(Error.INVALID_TABLEID);
                errorMessage.setErrorMessage(errorMessages);
            }
        }

        return Response.status(errorCode).entity(errorMessage).build();
    }

    @Path("/categorize")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignAccountToType(CreateAccountCategoryRequest request, @QueryParam("token") String token) {
        ArrayList<String> errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        Response.Status errorCode = Response.Status.BAD_REQUEST;
        ErrorMessage errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            AccountCategory category = accountService.assignAccountToCategory(token, request);
            if(category != null) {
                return Response.status(Response.Status.CREATED).entity(category).build();
            }
        } else {
            errorMessages.add(Error.INVALID_TOKEN);
            errorMessage.setErrorMessage(errorMessages);
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }

    @Path("/categories")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccountCategories(@QueryParam("token") String token) {
        ArrayList<String> errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        Response.Status errorCode = Response.Status.BAD_REQUEST;
        ErrorMessage errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            ArrayList<AccountCategory> categories = accountService.getAllCategories(token);
            if (categories != null && !categories.isEmpty()) {
                return Response.status(Response.Status.OK).entity(categories).build();
            } else {
                errorMessages.add(Error.INVALID_TOKEN);
                errorMessage.setErrorMessage(errorMessages);
            }
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }

    @Path("/categories")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCategory(@QueryParam("token") String token, CreateAccountCategoryRequest request) {
        ArrayList<String> errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        Response.Status errorCode = Response.Status.BAD_REQUEST;
        ErrorMessage errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            AccountCategory category = accountService.addNewCategory(token, request);
            if(category != null) {
                return Response.status(Response.Status.CREATED).entity(category).build();
            }
        } else {
            errorMessages.add(Error.INVALID_TOKEN);
            errorMessage.setErrorMessage(errorMessages);
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }
}
