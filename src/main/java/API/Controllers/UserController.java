package API.Controllers;

import API.DTO.Auth.LoginRequest;
import API.DTO.Auth.RegisterRequest;
import API.Services.UserService;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("/users")
public class UserController {
    private UserService userService = new UserService();

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
