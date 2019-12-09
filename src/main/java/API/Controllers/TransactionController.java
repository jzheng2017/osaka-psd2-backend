package API.Controllers;

import API.DTO.AddToCategoryRequest;
import API.DTO.TransactionCategory;
import API.Services.TransactionService;
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
        var response = transactionService.getCategories(token);

        return Response
                .ok(response)
                .build();
    }

    @Path("/create")
    @POST
    public Response createCategory(TransactionCategory category, @QueryParam("token") String token) {
        var response = transactionService.createCategory(category, token);

        return Response
                .ok(response)
                .build();
    }

    @Path("/{category}/assign")
    @POST
    public Response assignToCategory(@PathParam("category") int categoryId, AddToCategoryRequest request, @QueryParam("token") String token) {
        transactionService.addToCategory(categoryId, request, token);

        return Response
                .ok()
                .build();
    }
}
