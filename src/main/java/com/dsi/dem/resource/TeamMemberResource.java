package com.dsi.dem.resource;

import com.dsi.dem.dto.TeamMemberDto;
import com.dsi.dem.dto.transformer.TeamDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Team;
import com.dsi.dem.model.TeamMember;
import com.dsi.dem.service.TeamService;
import com.dsi.dem.service.impl.TeamServiceImpl;
import com.wordnik.swagger.annotations.*;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
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

    private static final Logger logger = Logger.getLogger(TeamMemberResource.class);

    private static final TeamDtoTransformer TRANSFORMER = new TeamDtoTransformer();
    private static final TeamService teamService = new TeamServiceImpl();

    @POST
    @ApiOperation(value = "Create Team Member", notes = "Create Team Member", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Team member create success"),
            @ApiResponse(code = 500, message = "Team member create failed, unauthorized.")
    })
    public Response createTeamMember(@PathParam("team_id") String teamID,
                                     @ApiParam(value = "TeamMember Dto", required = true)
                                             List<TeamMemberDto> teamMemberDtoList) throws CustomException {

        logger.info("Convert Dto to Object:: Start");
        List<TeamMember> teamMemberList = TRANSFORMER.getTeamMembers(teamMemberDtoList);
        logger.info("Convert Dto to Object:: End");

        logger.info("Team member create:: start");
        teamService.saveTeamMembers(teamMemberList, teamID);
        logger.info("Team member create:: end");

        return Response.ok().entity(TRANSFORMER.getTeamMembersDto(teamService.getTeamMembers(teamID))).build();
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

        logger.info("Team member delete:: start");
        teamService.deleteTeamMember(memberID);
        logger.info("Team member delete:: end");

        return Response.ok().entity("Success").build();
    }
}
