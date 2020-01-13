package API.Controllers;

import API.DTO.Auth.LoginRequest;
import API.DTO.Auth.LoginResponse;
import API.DTO.Auth.RegisterRequest;
import API.DTO.ErrorMessage;
import API.Errors.Error;
import API.Services.UserService;
import API.Utils.GenUtil;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.ArrayList;

@Path("/users")
public class UserController {
    private UserService userService;

    @Inject
    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    @Path("/register")
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response register(RegisterRequest request) {
        ArrayList<String> errorMessages = GenUtil.getErrors(request);
        if (errorMessages.isEmpty()) {
            LoginResponse response = userService.register(request);
            if (response != null)
                return Response.ok().entity(response).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(errorMessages)).build();
    }

    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest request) {
        ArrayList<String> errorMessages = GenUtil.getErrors(request);
        if (errorMessages.isEmpty()) {
            LoginResponse response = userService.login(request.getEmail(), request.getPassword());
            if (response != null)
                return Response.ok().entity(response).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(errorMessages)).build();
    }

    @Path("/user/details")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getUserDetails(@QueryParam("token") String token) {
        ArrayList<String> errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        if (errorMessages.isEmpty()) {
            var response = userService.getUserByToken(token);
            if (response != null)
                return Response.ok().entity(response).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(errorMessages)).build();
    }

    @Path("/user/attachedaccounts")
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response getAttachedAccounts(@QueryParam("token") String token){
        ArrayList<String> errorMessages = GenUtil.getErrors(token, Error.INVALID_TOKEN);
        if (errorMessages.isEmpty()) {
            var response = userService.getAttachedAccounts(token);
            if (response != null)
                return Response.ok(response).build();
        }
        return Response.status(Response.Status.BAD_REQUEST).entity(new ErrorMessage(errorMessages)).build();
    }
}
