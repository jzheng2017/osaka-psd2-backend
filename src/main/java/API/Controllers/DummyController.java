package API.Controllers;

import API.DTO.Bank;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.net.URI;

@Path("/dummy")
public class DummyController {
    private static final String DEFAULT_ING_AUTHORIZATION_CODE = "2c1c404c-c960-49aa-8777-19c805713edf";
    private static final String DEFAULT_DUMMY_AUTHORIZATION_CODE = "dummy";

    @Path("/{bank}")
    @GET
    public Response authorize(@PathParam("bank") String bank, @QueryParam("redirect_uri") String uri, @QueryParam("state") String state) {
        var code = Bank.valueOf(bank).equals(Bank.ING) ? DEFAULT_ING_AUTHORIZATION_CODE : DEFAULT_DUMMY_AUTHORIZATION_CODE;
        var url = URI.create(uri+"?code="+code+"&state="+state);
        return Response.temporaryRedirect(url).build();
    }
}
