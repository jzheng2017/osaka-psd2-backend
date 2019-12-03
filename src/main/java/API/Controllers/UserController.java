package API.Controllers;

import API.DTO.Auth.LoginRequest;
import API.DTO.Auth.RegisterRequest;
import API.DTO.ErrorMessage;
import API.GenUtil;
import API.Services.UserService;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

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
        Response.Status errorCode = Response.Status.BAD_REQUEST;
        String errorMessages = GenUtil.getErrors(request);
        ErrorMessage errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            return Response.ok(userService.register(request)).build();
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }

    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest request) {
        Response.Status errorCode = Response.Status.BAD_REQUEST;
        String errorMessages = GenUtil.getErrors(request);
        ErrorMessage errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            return Response.ok(userService.login(request.getEmail(), request.getPassword())).build();
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }
}
