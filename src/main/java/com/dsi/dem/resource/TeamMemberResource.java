package com.dsi.dem.resource;

import com.dsi.dem.dto.TeamMemberDto;
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

@Path("/v1/team/{team_id}/member")
@Api(value = "/TeamMember", description = "Operations about Team Member")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class TeamMemberResource {

    private static final TeamService teamService = new TeamServiceImpl();

    @Context
    HttpServletRequest request;

    @POST
    @ApiOperation(value = "Create Team Member", notes = "Create Team Member", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Team member create success"),
            @ApiResponse(code = 500, message = "Team member create failed, unauthorized.")
    })
    public Response createTeamMember(@PathParam("team_id") String teamID,
                                     @ApiParam(value = "TeamMember Dto", required = true)
                                             List<TeamMemberDto> teamMemberDtoList) throws CustomException {

        String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        return Response.ok().entity(teamService.createTeamMembers(teamID, teamMemberDtoList, tenantName)).build();
    }

    @DELETE
    @Path("/{member_id}")
    @ApiOperation(value = "Delete Team Member", notes = "Delete Team Member", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Team member delete success"),
            @ApiResponse(code = 500, message = "Team member delete failed, unauthorized.")
    })
    public Response deleteTeamMember(@PathParam("team_id") String teamID,
                                           @PathParam("member_id") String memberID) throws CustomException {

        teamService.deleteTeamMember(memberID);
        return Response.ok().entity(null).build();
    }
}
