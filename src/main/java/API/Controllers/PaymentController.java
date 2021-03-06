package API.Controllers;

import API.Banks.Requests.Headers;
import API.DTO.ErrorMessage;
import API.DTO.PaymentRequest;
import API.DTO.TransactionResponse;
import API.Errors.Error;
import API.Services.PaymentService;
import API.Utils.GenUtil;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
        paymentRequest.setIp(Headers.HARDCODED_IP_PAYMENT_REQUEST);
        var errorMessages = GenUtil.getErrors(new String[]{token,  tableid}, new String[]{ Error.INVALID_TOKEN,Error.INVALID_TABLEID});
        if (errorMessages.isEmpty()) {
            TransactionResponse response = paymentService.initiateTransaction(token, paymentRequest, tableid);
            if (response != null) {
                return Response.ok().entity(response).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(errorMessages)).build();
    }
}
