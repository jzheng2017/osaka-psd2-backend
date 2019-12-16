package API.Controllers;

import API.DTO.ErrorMessage;
import API.Errors.Error;
import API.GenUtil;
import API.Services.InsightsService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/insights")
public class InsightsController {
    private InsightsService insightsService;

    @Inject
    public void setInsightsService(InsightsService insightsService) {
        this.insightsService = insightsService;
    }

    @GET
    @Path("/income")
    @Produces(MediaType.APPLICATION_JSON)
    public Response futureIncome(@QueryParam("token") String token) {
        var errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        var errorCode = Response.Status.BAD_REQUEST;
        var errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            var futureIncome = insightsService.getFutureIncome(token);

            if (futureIncome != null) {
                return Response.ok().entity(futureIncome).build();
            }
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }

    @GET
    @Path("/expenses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response futureExpenses(@QueryParam("token") String token) {
        var errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        var errorCode = Response.Status.BAD_REQUEST;
        var errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            var futureExpenses = insightsService.getFutureExpenses(token);

            if (futureExpenses != null) {
                return Response.ok().entity(futureExpenses).build();
            }
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response futureTransactions(@QueryParam("token") String token) {
        var errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        var errorCode = Response.Status.BAD_REQUEST;
        var errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            var futureInsights = insightsService.getFutureInsights(token);
            if (futureInsights != null && !futureInsights.isEmpty()) {
                return Response.ok().entity(futureInsights).build();
            }
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }

    @GET
    @Path("/{accountid}/income")
    @Produces(MediaType.APPLICATION_JSON)
    public Response futureIncomeForAccount(@QueryParam("token") String token, @PathParam("accountid") String accountId, @QueryParam("tableid") String tableId) {
        var errorMessages = GenUtil.getErrors(new String[]{token, accountId, tableId}, new String[]{Error.INVALID_TOKEN, Error.INVALID_ID, Error.INVALID_TABLEID});
        var errorCode = Response.Status.BAD_REQUEST;
        var errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            var futureIncome = insightsService.getFutureIncomeForAccount(token, accountId,tableId);

            if (futureIncome != null) {
                return Response.ok().entity(futureIncome).build();
            }
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }

    @GET
    @Path("/{accountid}/expenses")
    @Produces(MediaType.APPLICATION_JSON)
    public Response futureExpensesForAccount(@QueryParam("token") String token, @PathParam("accountid") String accountId, @QueryParam("tableid") String tableId) {
        var errorMessages = GenUtil.getErrors(new String[]{token, accountId, tableId}, new String[]{Error.INVALID_TOKEN, Error.INVALID_ID, Error.INVALID_TABLEID});
        var errorCode = Response.Status.BAD_REQUEST;
        var errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            var futureExpenses = insightsService.getFutureExpensesForAccount(token, accountId,tableId);

            if (futureExpenses != null) {
                return Response.ok().entity(futureExpenses).build();
            }
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }

    @GET
    @Path("/{accountid}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response futureTransactionsForAccount(@QueryParam("token") String token, @PathParam("accountid") String accountId, @QueryParam("tableid") String tableId) {
        var errorMessages = GenUtil.getErrors(new String[]{token, accountId, tableId}, new String[]{Error.INVALID_TOKEN, Error.INVALID_ID, Error.INVALID_TABLEID});
        var errorCode = Response.Status.BAD_REQUEST;
        var errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            var insights = insightsService.getFutureInsightsForAccount(token, accountId,tableId);
            if (insights != null) {
                return Response.ok().entity(insights).build();
            }
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }
}
