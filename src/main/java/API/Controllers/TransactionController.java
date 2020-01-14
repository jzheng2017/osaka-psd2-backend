package API.Controllers;

import API.DTO.AddToCategoryRequest;
import API.DTO.ErrorMessage;
import API.DTO.TransactionCategory;
import API.Errors.Error;
import API.Services.TransactionService;
import API.Utils.GenUtil;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Response;

@Path("/transactions/categories")
public class TransactionController {
    private TransactionService transactionService;

    @Inject
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
    }

    @Path("/")
    @GET
    public Response getCategories(@QueryParam("token") String token) {
        var errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        if (errorMessages.isEmpty()) {
            var response = transactionService.getCategories(token);
            if (response != null) {
                return Response.ok().entity(response).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(errorMessages)).build();
    }

    @Path("/create")
    @POST
    public Response createCategory(TransactionCategory category, @QueryParam("token") String token) {
        var errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        if (errorMessages.isEmpty()) {
            var response = transactionService.createCategory(category, token);
            if (response != null) {
                return Response.ok().entity(response).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(errorMessages)).build();
    }

    @Path("/{category}/assign")
    @POST
    public Response assignToCategory(@PathParam("category") int categoryId, AddToCategoryRequest request, @QueryParam("token") String token) {
        var errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        errorMessages.addAll(GenUtil.getErrors(request));
        if (errorMessages.isEmpty()) {
            transactionService.addToCategory(categoryId, request, token);
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(errorMessages)).build();
    }
}
