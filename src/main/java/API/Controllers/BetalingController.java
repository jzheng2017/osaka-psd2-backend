package API.Controllers;

import API.DTO.PaymentRequest;
import API.Services.BetalingService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilderException;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

public class BetalingController {
    private BetalingService betalingService = new BetalingService();
    private static Logger log = Logger.getLogger(BetalingService.class.getName());

    @Inject
    public void setAccountService(BetalingService betalingService) {
        this.betalingService = betalingService;
    }

    @Path("/payment")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response getUserAccounts(@QueryParam("token") String token, PaymentRequest paymentRequest, @QueryParam("tableid") String tableid) {
        String response = betalingService.initializePayment(token, paymentRequest, tableid);
        try {
            return Response.temporaryRedirect(new URI(response)).build();
        } catch (UriBuilderException | URISyntaxException ex) {
            log.log(Level.SEVERE, ex.getMessage());
            return Response.status(Response.Status.NOT_FOUND).build();
        }
    }
}
