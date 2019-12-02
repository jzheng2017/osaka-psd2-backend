package API.Controllers;

import API.DTO.Account;
import API.DTO.PaymentRequest;
import API.Services.AccountService;
import API.Services.BetalingService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/payment")
public class BetalingController {
    private BetalingService betalingService = new BetalingService();

    @Inject
    public void setAccountService(BetalingService betalingService) {
        this.betalingService = betalingService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getUserAccounts(@QueryParam("token") String token, PaymentRequest paymentRequest, @QueryParam("tableid") String tableid) {
        String response = betalingService.initializePayment(token,paymentRequest, tableid);
        if (response == null || response.isEmpty())
            return Response.status(Response.Status.NOT_FOUND).build();
        return Response.ok().entity(response).build();
    }
}
