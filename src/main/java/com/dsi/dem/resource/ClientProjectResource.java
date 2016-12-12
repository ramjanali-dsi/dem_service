package com.dsi.dem.resource;

import com.dsi.dem.dto.ClientDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.service.ClientService;
import com.dsi.dem.service.impl.ClientServiceImpl;
import com.wordnik.swagger.annotations.*;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sabbir on 8/9/16.
 */

@Path("/v1/client/{client_id}/project")
@Api(value = "/ClientProject", description = "Operations about Client Project")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ClientProjectResource {

    private static final ClientService clientService = new ClientServiceImpl();

    @POST
    @ApiOperation(value = "Create Client Project", notes = "Create Client Project", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Client project create success"),
            @ApiResponse(code = 500, message = "Client project create failed, unauthorized.")
    })
    public Response createProjectClient(@PathParam("client_id") String clientID,
                                        @ApiParam(value = "Client Dto", required = true) ClientDto clientDto)
            throws CustomException {

        return Response.ok().entity(clientService.createClientProjects(clientID, clientDto)).build();
    }

    @DELETE
    @Path("/{project_id}")
    @ApiOperation(value = "Delete Client Project", notes = "Delete Client Project", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Client project delete success"),
            @ApiResponse(code = 500, message = "Client project delete failed, unauthorized.")
    })
    public Response deleteProjectClient(@PathParam("client_id") String clientID,
                                        @PathParam("project_id") String projectID) throws CustomException {

        clientService.deleteClientProject(clientID);
        return Response.ok().entity(null).build();
    }
}
