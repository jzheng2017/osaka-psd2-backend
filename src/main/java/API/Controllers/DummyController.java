package API.Controllers;

import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.logging.Level;
import java.util.logging.Logger;

@Path("/dummy")
public class DummyController {
    private static final String DEFAULT_ING_AUTHORIZATION_CODE = "2c1c404c-c960-49aa-8777-19c805713edf";
    private static final String DEFAULT_DUMMY_AUTHORIZATION_CODE = "dummy";
    private static final Logger LOGGER = Logger.getLogger(DummyController.class.getName());

    @Path("/{bank}")
    @GET
    public Response authorizeIng(@PathParam("bank") String bank, @QueryParam("redirect_uri") String uri, @QueryParam("state") String state) {
        try {
            URI url;
            if ("ing".equals(bank)) {
                url = new URI(uri + "?code=" + DEFAULT_ING_AUTHORIZATION_CODE + "&state=" + state);
            } else {
                url = new URI(uri + "?code=" + DEFAULT_DUMMY_AUTHORIZATION_CODE + "&state=" + state);
            }
            return Response.temporaryRedirect(url).build();
        } catch (URISyntaxException e) {
            LOGGER.log(Level.INFO,e.toString());
        }
        return null;
    }
}
