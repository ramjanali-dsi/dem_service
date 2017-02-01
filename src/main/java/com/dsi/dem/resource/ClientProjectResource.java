package com.dsi.dem.resource;

import com.dsi.dem.dto.ClientDto;
import com.dsi.dem.dto.ClientProjectDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.service.ClientService;
import com.dsi.dem.service.impl.ClientServiceImpl;
import com.wordnik.swagger.annotations.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by sabbir on 8/9/16.
 */

@Path("/v1/client/{client_id}/project")
@Api(value = "/ClientProject", description = "Operations about Client Project")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ClientProjectResource {

    private static final ClientService clientService = new ClientServiceImpl();

    @Context
    HttpServletRequest request;

    @POST
    @ApiOperation(value = "Create Client Project", notes = "Create Client Project", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Client project create success"),
            @ApiResponse(code = 500, message = "Client project create failed, unauthorized.")
    })
    public Response createProjectClient(@PathParam("client_id") String clientID,
                                        @ApiParam(value = "Client Dto", required = true)
                                                List<ClientProjectDto> projectDtoList) throws CustomException {

        String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        return Response.ok().entity(clientService.createClientProjects(clientID, projectDtoList, tenantName)).build();
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
