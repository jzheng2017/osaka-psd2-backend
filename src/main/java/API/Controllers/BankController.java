package API.Controllers;

import API.DTO.Bank;
import API.DTO.ErrorMessage;
import API.Errors.Error;
import API.Services.BankService;
import API.Utils.GenUtil;
import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/")
public class BankController {
    private BankService bankService;
    private ErrorMessage errorMessage;

    public BankController() {
        errorMessage = new ErrorMessage();
    }

    @Inject
    public void setBankService(BankService bankService) {
        this.bankService = bankService;
    }

    @Path("connect/{bank}")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response connect(@PathParam("bank") Bank bank, @QueryParam("token") String token) {
        var errors = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        errorMessage.setErrorMessage(errors);

        if (errors.isEmpty()) {
            var url = bankService.connect(bank, token);
            return url == null ? errorMessage.buildResponse() : Response.temporaryRedirect(url).build();
        }

        return errorMessage.buildResponse();
    }

    @Path("connect/{bank}/finish")
    @Produces(MediaType.APPLICATION_JSON)
    @GET
    public Response finish(@PathParam("bank") Bank bank, @QueryParam("code") String code, @QueryParam("state") String state) {
        var errors = GenUtil.getErrors(state, Error.INVALID_TOKEN);
        errorMessage.setErrorMessage(errors);

        if (errors.isEmpty())
            return Response
                    .temporaryRedirect(bankService.finish(bank, code, state))
                    .build();

        return errorMessage.buildResponse();
    }

    @Path("disconnect")
    @DELETE
    @Produces(MediaType.APPLICATION_JSON)
    public Response delete(@QueryParam("token") String token, @QueryParam("tableid") String tableid) {
        String[] possibleErrors = {token, tableid};
        String[] possibleErrorMessages = {Error.INVALID_TOKEN, Error.INVALID_TABLEID};
        var errors = GenUtil.getErrors(possibleErrors, possibleErrorMessages);
        errorMessage.setErrorMessage(errors);

        if (errors.isEmpty()) {
            bankService.delete(token, tableid);

            return Response
                    .ok()
                    .build();
        }

        return errorMessage.buildResponse();
    }

    @Path("connections")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response connections(@QueryParam("token") String token) {
        var error = GenUtil.getErrors(token,Error.INVALID_TOKEN);
        errorMessage.setErrorMessage(error);

        if (error.isEmpty())
            return Response
                    .ok(bankService.getConnections(token))
                    .build();

        return errorMessage.buildResponse();
    }

    @Path("banks")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getBanks() {
        return Response
                .ok(bankService.all())
                .build();
    }
}
