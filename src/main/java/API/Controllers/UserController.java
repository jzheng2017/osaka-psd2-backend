package API.Controllers;

import API.DTO.Auth.LoginRequest;
import API.DTO.Auth.RegisterRequest;
import API.Services.UserService;
import javax.inject.Inject;
import javax.ws.rs.*;
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
        var response = userService.register(request.getName(), request.getEmail(), request.getPassword());

        if (response == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();

        return Response.ok(response).build();
    }

    @Path("/login")
    @POST
    @Produces(MediaType.APPLICATION_JSON)
    @Consumes(MediaType.APPLICATION_JSON)
    public Response login(LoginRequest request) {
        var response = userService.login(request.getEmail(), request.getPassword());

        if (response == null)
            return Response.status(Response.Status.UNAUTHORIZED).build();

        return Response.ok(response).build();
    }
}
