package API.Controllers;

import API.Banks.ClientFactory;
import API.DTO.Bank;
import API.DTO.BankToken;
import API.DTO.ErrorMessage;
import API.Errors.Error;
import API.Services.UserService;
import API.Utils.GenUtil;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.util.ArrayList;

@Path("/")
public class BankController {
    private static final String FINAL_REDIRECT_URL = "http://localhost:4200/overzicht/rekeningen";
    private static final String FINAL_PAYMENT_URL = "http://localhost:4200/overmaken";

    private UserService userService;

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Path("connect/{bank}")
    @GET
    public Response connect(@PathParam("bank") Bank bank, @QueryParam("token") String token) {
        ArrayList<String> errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        if (errorMessages.isEmpty()) {
            boolean limitReached = userService.checkIfAvailable(token).isLimitReached();
            if (!limitReached) {
                return Response.temporaryRedirect(userService.connect(bank, token)).build();
            }
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(errorMessages)).build();
    }


    @Path("connect/{bank}/finish")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response finish(@PathParam("bank") Bank bank, @QueryParam("code") String code, @QueryParam("state") String state) {
        ArrayList<String> errorMessages = GenUtil.getErrors(state, Error.INVALID_TOKEN);
        if (errorMessages.isEmpty()) {
            var adapter = ClientFactory.getClient(bank);

            BankToken bankToken = adapter.token(code);
            bankToken.setBank(bank);

            if (adapter.isPaymentToken(bankToken.getAccessToken())) {
                var payment = adapter.pay(bankToken.getAccessToken(), state);
                return Response.temporaryRedirect(URI.create(FINAL_PAYMENT_URL + "?success=" + payment.isPaid())).build();
            }

            userService.attachBankAccount(state, bankToken);
            return Response.temporaryRedirect(URI.create(FINAL_REDIRECT_URL)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(errorMessages)).build();
    }

    @Path("disconnect")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response deleteBankAccount(@QueryParam("token") String token, @QueryParam("tableid") String tableid) {
        ArrayList<String> errorMessages = GenUtil.getErrors(new String[]{token, tableid}, new String[] {Error.INVALID_TOKEN, Error.INVALID_TABLEID});
        if (errorMessages.isEmpty()) {
            userService.deleteBankAccount(token, tableid);
            return Response.ok().build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(errorMessages)).build();
    }

    @Path("connections")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getConnections(@QueryParam("token") String token) {
        ArrayList<String> errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        if (errorMessages.isEmpty()) {
            return Response.ok().entity(userService.checkIfAvailable(token)).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(errorMessages)).build();
    }

    @Path("banks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBanks() {
        return Response
                .ok(Bank.values())
                .build();
    }
}
