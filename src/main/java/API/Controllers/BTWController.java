package API.Controllers;

import API.Services.BTWService;

import javax.inject.Inject;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.core.Response;

@Path("/btw")
public class BTWController {
    private BTWService btwService;

    @Inject
    public void setBtwService(BTWService btwService){
        this.btwService = btwService;
    }

    @Path("/getpercentages")
    @GET
    public Response getBTWPercentages(){
        var response = btwService.getBTWPercentages();
        return Response.status(200).entity(response).build();
    }
}
