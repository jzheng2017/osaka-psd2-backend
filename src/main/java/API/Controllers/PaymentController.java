package API.Controllers;

import API.DTO.PaymentRequest;
import API.Services.PaymentService;

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
    public Response getUserAccounts(@QueryParam("token") String token, PaymentRequest paymentRequest, @QueryParam("tableid") String tableid) {
        paymentRequest.setIp("156.114.161.8");

        return Response
                .ok(paymentService.initiateTransaction(token, paymentRequest, tableid))
                .build();
    }
}
