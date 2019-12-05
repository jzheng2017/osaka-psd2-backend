package API.Controllers;

import API.DTO.AccountDetails;
import API.DTO.ErrorMessage;
import API.DTO.PaymentRequest;
import API.DTO.TransactionResponse;
import API.Errors.Error;
import API.GenUtil;
import API.Services.PaymentService;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/payment")
public class PaymentController {
    private PaymentService paymentService;

    @Inject
    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response doPayment(@QueryParam("token") String token, PaymentRequest paymentRequest, @QueryParam("tableid") String tableid) {
        paymentRequest.setIp("156.114.161.8");
        String[] possibleErrors = {token, tableid};
        String[] messages = {Error.INVALID_TOKEN, Error.INVALID_TABLEID};
        ArrayList<String> errorMessages = GenUtil.getErrors(possibleErrors, messages);
        Response.Status errorCode = Response.Status.BAD_REQUEST;
        ErrorMessage errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            TransactionResponse response = paymentService.initiateTransaction(token, paymentRequest, tableid);
            if (response != null) {
                return Response.ok().entity(response).build();
            } else {
                errorMessages.add(Error.INVALID_TOKEN);
                errorMessages.add(Error.INVALID_TABLEID);
                errorMessage.setErrorMessage(errorMessages);
            }
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }
}
