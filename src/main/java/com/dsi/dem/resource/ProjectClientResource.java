package com.dsi.dem.resource;

import com.dsi.dem.dto.ProjectClientDto;
import com.dsi.dem.dto.ProjectDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.service.ProjectService;
import com.dsi.dem.service.impl.ProjectServiceImpl;
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

@Path("/v1/project/{project_id}/client")
@Api(value = "/ProjectClient", description = "Operations about Project Client")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProjectClientResource {

    private static final ProjectService projectService = new ProjectServiceImpl();

    @Context
    HttpServletRequest request;

    @POST
    @ApiOperation(value = "Create Project Client", notes = "Create Project Client", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project client create success"),
            @ApiResponse(code = 500, message = "Project client create failed, unauthorized.")
    })
    public Response createProjectClient(@PathParam("project_id") String projectID,
                                        @ApiParam(value = "Project Dto", required = true)
                                                List<ProjectClientDto> clientDtoList) throws CustomException {

        String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        return Response.ok().entity(projectService.createProjectClients(clientDtoList, projectID ,tenantName)).build();
    }

    @DELETE
    @Path("/{client_id}")
    @ApiOperation(value = "Delete Project Client", notes = "Delete Project Client", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project client delete success"),
            @ApiResponse(code = 500, message = "Project client delete failed, unauthorized.")
    })
    public Response deleteProjectClient(@PathParam("project_id") String projectID,
                                        @PathParam("client_id") String clientID) throws CustomException {


        projectService.deleteProjectClient(clientID);
        return Response.ok().entity(null).build();
    }
}
