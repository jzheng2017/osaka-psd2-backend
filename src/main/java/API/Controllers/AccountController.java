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
    private ErrorMessage errorMessage;

    public AccountController() {
        this.errorMessage = new ErrorMessage();
    }

    @Inject
    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserAccounts(@QueryParam("token") String token) {
        var errors = GenUtil.getErrors(token, Error.INVALID_TOKEN);

        if (errors.isEmpty()) {
            var accounts = accountService.getUserAccounts(token);

            if (accounts != null)
                return Response
                        .ok()
                        .entity(accounts)
                        .build();
        } else {
            errors.add(Error.INVALID_TOKEN);
            errorMessage.setErrorMessage(errors);
        }

        return errorMessage.buildResponse();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    @Path("/{category}")
    public Response getAccountsCategorized(@PathParam("category") String categoryId, @QueryParam("token") String token) {
        var errors = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        errorMessage.setErrorMessage(errors);

        if (errors.isEmpty()) {
            var userAccounts = accountService.getUserAccountsCategorized(token, categoryId);
            if (userAccounts.getAccounts() != null) {
                return Response.ok().entity(userAccounts).build();
            }
        }
        return errorMessage.buildResponse();
    }

    @Path("/{id}/details")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAccountDetails(@PathParam("id") String id, @QueryParam("token") String token, @QueryParam("tableid") String tableid) {
        var errors = GenUtil.getErrors(new String[]{token, id, tableid}, new String[]{ Error.INVALID_TOKEN, Error.INVALID_ID,Error.INVALID_TABLEID});
        errorMessage.setErrorMessage(errors);

        if (errors.isEmpty()) {
            var accountDetails = accountService.getAccountDetails(token, id, tableid);
            if (accountDetails != null) {
                return Response.ok().entity(accountDetails).build();
            }
        }
        return errorMessage.buildResponse();
    }

    @Path("/categorize")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response assignAccountToType(CreateAccountCategoryRequest request, @QueryParam("token") String token) {
        var errors = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        errorMessage.setErrorMessage(errors);

        if (errors.isEmpty()) {
            var category = accountService.assignAccountToCategory(token, request);
            if (category != null) {
                return Response.status(Response.Status.CREATED).entity(category).build();
            }
        }
        return errorMessage.buildResponse();
    }

    @Path("/categories")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAllAccountCategories(@QueryParam("token") String token) {
        var errors = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        errorMessage.setErrorMessage(errors);

        if (errors.isEmpty()) {
            var categories = accountService.getAllCategories(token);
            if (categories != null && !categories.isEmpty()) {
                return Response.status(Response.Status.OK).entity(categories).build();
            }
        }
        return errorMessage.buildResponse();
    }

    @Path("/categories")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response addCategory(@QueryParam("token") String token, CreateAccountCategoryRequest request) {
        var errors = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        errorMessage.setErrorMessage(errors);

        if (errors.isEmpty()) {
            var category = accountService.addNewCategory(token, request);
            if (category != null)
                return Response.status(Response.Status.CREATED).entity(category).build();
        }

        return errorMessage.buildResponse();
    }

    @Path("/{accountid}/{transactionid}/details")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTransactionDetails(@PathParam("accountid") String accountId, @PathParam("transactionid") String transactionId, @QueryParam("token") String token, @QueryParam("tableid") String tableId){
        var errors = GenUtil.getErrors(new String[]{accountId, token, tableId, transactionId}, new String[]{Error.INVALID_ID, Error.INVALID_TOKEN, Error.INVALID_TABLEID, Error.INVALID_TRANSACTION_ID});
        errorMessage.setErrorMessage(errors);

        if (errors.isEmpty()) {
            var transactionDetails = accountService.getTransactionDetails(token, accountId, transactionId, tableId);

            if (transactionDetails != null) {
                return Response.ok().entity(transactionDetails).build();
            }
        }

        return errorMessage.buildResponse();
    }
}
