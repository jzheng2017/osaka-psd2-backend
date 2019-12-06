package API.Controllers;

import API.DTO.Transaction;
import API.DTO.TransactionCategory;
import API.Services.TransactionService;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/transactions/categories")
public class TransactionController {
    private TransactionService transactionService;

    @Inject
    public void setTransactionService(TransactionService transactionService) {
        this.transactionService = transactionService;
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
    public Response assignToCategory(@PathParam("category") int categoryId, Transaction transaction, @QueryParam("token") String token) {
        var response = transactionService.addToCategory(categoryId, transaction, token);

        return Response
                .ok(response)
                .build();
    }
}
