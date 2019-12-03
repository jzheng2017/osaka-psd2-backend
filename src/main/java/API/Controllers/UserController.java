package API.Controllers;

import API.DTO.Auth.LoginRequest;
import API.DTO.Auth.LoginResponse;
import API.DTO.Auth.RegisterRequest;
import API.DTO.ErrorMessage;
import API.GenUtil;
import API.Services.UserService;
import com.mysql.cj.log.Log;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
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
        Response.Status errorCode = Response.Status.BAD_REQUEST;
        ArrayList<String> errorMessages = GenUtil.getErrors(request);
        ErrorMessage errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            LoginResponse response = userService.register(request);
            if (response != null)
                return Response.ok().build();
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }

    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest request) {
        Response.Status errorCode = Response.Status.BAD_REQUEST;
        ArrayList<String> errorMessages = GenUtil.getErrors(request);
        ErrorMessage errorMessage = new ErrorMessage(errorCode, errorMessages);
        if (errorMessages.isEmpty()) {
            LoginResponse response = userService.login(request.getEmail(), request.getPassword());
            if (response != null)
                return Response.ok(response).build();
        }
        return Response.status(errorCode).entity(errorMessage).build();
    }
}
