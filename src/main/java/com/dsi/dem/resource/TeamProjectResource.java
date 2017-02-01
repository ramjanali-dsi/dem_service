package com.dsi.dem.resource;

import com.dsi.dem.dto.TeamProjectDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.service.TeamService;
import com.dsi.dem.service.impl.TeamServiceImpl;
import com.wordnik.swagger.annotations.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by sabbir on 8/5/16.
 */

@Path("/v1/team/{team_id}/project")
@Api(value = "/TeamProject", description = "Operations about Team Project")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class TeamProjectResource {

    private static final TeamService teamService = new TeamServiceImpl();

    @Context
    HttpServletRequest request;

    @POST
    @ApiOperation(value = "Create Team Project", notes = "Create Team Project", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Team project create success"),
            @ApiResponse(code = 500, message = "Team project create failed, unauthorized.")
    })
    public Response createTeamProject(@PathParam("team_id") String teamID,
                                      @ApiParam(value = "TeamMember Dto", required = true)
                                              List<TeamProjectDto> teamProjectDtoList) throws CustomException {

        String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        return Response.ok().entity(teamService.createTeamProjects(teamID, teamProjectDtoList, tenantName)).build();
    }

    @DELETE
    @Path("/{project_id}")
    @ApiOperation(value = "Delete Team Project", notes = "Delete Team Project", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Team project delete success"),
            @ApiResponse(code = 500, message = "Team project delete failed, unauthorized.")
    })
    public Response deleteTeamProject(@PathParam("team_id") String teamID, 
                                      @PathParam("project_id") String projectID) throws CustomException {

        teamService.deleteTeamProject(projectID);
        return Response.ok().entity(null).build();
    }
}
