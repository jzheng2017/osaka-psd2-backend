package API.Controllers;

import API.DTO.Bank;
import API.Services.DummyService;
import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Response;

@Path("/dummy")
public class DummyController {
    private DummyService dummyService;

    @Inject
    public void setDummyService(DummyService dummyService) {
        this.dummyService = dummyService;
    }

    @Path("/{bank}")
    @GET
    public Response authorize(@PathParam("bank") Bank bank, @QueryParam("redirect_uri") String uri, @QueryParam("state") String state) {
        return Response
                .temporaryRedirect(dummyService.authorize(bank, uri, state))
                .build();
    }
}
