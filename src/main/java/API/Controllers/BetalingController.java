package API.Controllers;

import API.DTO.ErrorMessage;
import API.DTO.PaymentRequest;
import API.Errors.Error;
import API.GenUtil;
import API.Services.BetalingService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.UriBuilderException;
import java.net.URI;
import java.net.URISyntaxException;

@Path("/payment")
public class BetalingController {
    private BetalingService betalingService = new BetalingService();

    @Inject
    public void setBetalingService(BetalingService betalingService) {
        this.betalingService = betalingService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response initializePayment(@QueryParam("token") String token, PaymentRequest paymentRequest, @QueryParam("tableid") String tableid) {
        String[] possibleErrors = {token, tableid};
        String[] messages = {Error.INVALID_TOKEN, Error.INVALID_TABLEID};
        String errorMessages = GenUtil.getErrors(possibleErrors, messages);
        errorMessages += GenUtil.getErrors(paymentRequest);
        Response.Status errorCode = Response.Status.BAD_REQUEST;
        ErrorMessage errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            try {
                String response = betalingService.initializePayment(token, paymentRequest, tableid);
                return Response.status(Response.Status.OK).location(new URI(response)).build();
            } catch (URISyntaxException | UriBuilderException | IllegalStateException ex) {
                errorMessage.setErrorBody(ex.getMessage());
            }
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }
}
