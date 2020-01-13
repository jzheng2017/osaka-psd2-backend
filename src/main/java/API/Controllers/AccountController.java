package API.Controllers;

import API.DTO.CreateAccountCategoryRequest;
import API.DTO.ErrorMessage;
import API.Errors.Error;
import API.Services.AccountService;
import API.Utils.GenUtil;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
        var errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        var errorMessage = new ErrorMessage(errorMessages);
        if (errorMessages.isEmpty()) {
            var userAccounts = accountService.getUserAccounts(token);
            if (userAccounts.getAccounts() != null && !userAccounts.getAccounts().isEmpty())
                return Response.ok().entity(userAccounts).build();
        } else {
            errorMessages.add(Error.INVALID_TOKEN);
            errorMessage.setErrorMessage(errorMessages);
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(errorMessage).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{categoryid}")
    public Response getAccountsCategorized(@PathParam("categoryid") String categoryId, @QueryParam("token") String token) {
        var errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        if (errorMessages.isEmpty()) {
            var userAccounts = accountService.getUserAccountsCategorized(token, categoryId);
            if (userAccounts.getAccounts() != null) {
                return Response.ok().entity(userAccounts).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(errorMessages)).build();
    }

    @Path("/{id}/details")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountDetails(@PathParam("id") String id, @QueryParam("token") String token, @QueryParam("tableid") String tableid) {
        var errorMessages = GenUtil.getErrors(new String[]{token, id, tableid}, new String[]{ Error.INVALID_TOKEN, Error.INVALID_ID,Error.INVALID_TABLEID});
        if (errorMessages.isEmpty()) {
            var accountDetails = accountService.getAccountDetails(token, id, tableid);
            if (accountDetails != null) {
                return Response.ok().entity(accountDetails).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(errorMessages)).build();
    }

    @Path("/categorize")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignAccountToType(CreateAccountCategoryRequest request, @QueryParam("token") String token) {
        var errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        if (errorMessages.isEmpty()) {
            var category = accountService.assignAccountToCategory(token, request);
            if (category != null) {
                return Response.status(Response.Status.CREATED).entity(category).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(errorMessages)).build();
    }

    @Path("/categories")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccountCategories(@QueryParam("token") String token) {
        var errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        if (errorMessages.isEmpty()) {
            var categories = accountService.getAllCategories(token);
            if (categories != null && !categories.isEmpty()) {
                return Response.status(Response.Status.OK).entity(categories).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(errorMessages)).build();
    }

    @Path("/categories")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCategory(@QueryParam("token") String token, CreateAccountCategoryRequest request) {
        var errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        if (errorMessages.isEmpty()) {
            var category = accountService.addNewCategory(token, request);
            if (category != null)
                return Response.status(Response.Status.CREATED).entity(category).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage( errorMessages)).build();
    }

    @Path("/{accountid}/{transactionid}/details")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactionDetails(@PathParam("accountid") String accountId, @PathParam("transactionid") String transactionId, @QueryParam("token") String token, @QueryParam("tableid") String tableId){
        var errorMessages = GenUtil.getErrors(new String[]{accountId, token, tableId, transactionId}, new String[]{Error.INVALID_ID, Error.INVALID_TOKEN, Error.INVALID_TABLEID, Error.INVALID_TRANSACTION_ID});
        if (errorMessages.isEmpty()) {
            var transactionDetails = accountService.getTransactionDetails(token, accountId, transactionId, tableId);

            if (transactionDetails != null) {
                return Response.ok().entity(transactionDetails).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(errorMessages)).build();
    }
}
