package com.dsi.dem.resource;

import com.dsi.dem.dto.ProjectDto;
import com.dsi.dem.dto.ProjectTeamDto;
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

@Path("/v1/project/{project_id}/team")
@Api(value = "/ProjectTeam", description = "Operations about Project Team")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProjectTeamResource {

    private static final ProjectService projectService = new ProjectServiceImpl();

    @Context
    HttpServletRequest request;

    @POST
    @ApiOperation(value = "Create Project Team", notes = "Create Project Team", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project team create success"),
            @ApiResponse(code = 500, message = "Project team create failed, unauthorized.")
    })
    public Response createProjectTeam(@PathParam("project_id") String projectID,
                                      @ApiParam(value = "ProjectTeam Dto", required = true)
                                              List<ProjectTeamDto> teamDtoList) throws CustomException {

        String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        return Response.ok().entity(projectService.createProjectTeams(teamDtoList, projectID, tenantName)).build();
    }

    @DELETE
    @Path("/{team_id}")
    @ApiOperation(value = "Delete Project Team", notes = "Delete Project Team", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project team delete success"),
            @ApiResponse(code = 500, message = "Project team delete failed, unauthorized.")
    })
    public Response deleteProjectTeam(@PathParam("project_id") String projectID,
                                      @PathParam("team_id") String teamID) throws CustomException {

        projectService.deleteProjectTeam(teamID);
        return Response.ok().entity(null).build();
    }
}
