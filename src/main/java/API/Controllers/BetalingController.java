package API.Controllers;

import API.DTO.PaymentRequest;
import API.Services.BetalingService;

import javax.inject.Inject;
import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilderException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

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
        paymentRequest.setIp("156.114.161.8");

        return Response
                .ok(betalingService.initiateTransaction(token, paymentRequest, tableid))
                .build();
    }
}
