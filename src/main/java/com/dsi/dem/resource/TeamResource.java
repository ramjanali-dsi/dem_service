package com.dsi.dem.resource;

import com.dsi.dem.dto.TeamDto;
import com.dsi.dem.dto.transformer.TeamDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Team;
import com.dsi.dem.service.TeamService;
import com.dsi.dem.service.impl.TeamServiceImpl;
import com.dsi.dem.util.Utility;
import com.wordnik.swagger.annotations.*;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sabbir on 7/21/16.
 */

@Path("/v1/team")
@Api(value = "/Team", description = "Operations about Team Management")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class TeamResource {

    private static final Logger logger = Logger.getLogger(TeamResource.class);

    private static final TeamDtoTransformer TRANSFORMER = new TeamDtoTransformer();
    private static final TeamService teamService = new TeamServiceImpl();

    @POST
    @ApiOperation(value = "Create Team", notes = "Create Team", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Team create success"),
            @ApiResponse(code = 500, message = "Team create failed, unauthorized.")
    })
    public Response createTeam(@ApiParam(value = "Team Dto", required = true) TeamDto teamDto) throws CustomException {

        logger.info("Convert Dto to Object:: Start");
        Team team = TRANSFORMER.getTeam(teamDto);
        logger.info("Convert Dto to Object:: End");

        logger.info("Team Create:: Start");
        teamService.saveTeam(team);

        if(!Utility.isNullOrEmpty(teamDto.getProjectIds())) {
            teamService.saveTeamProjects(teamDto.getProjectIds(), team);
        }
        logger.info("Team Create:: End");

        return Response.ok().entity(TRANSFORMER.getTeamDto(teamService.getTeamByID(team.getTeamId()))).build();
    }

    @PUT
    @Path("/{team_id}")
    @ApiOperation(value = "Update Team", notes = "Update Team", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Team update success"),
            @ApiResponse(code = 500, message = "Team update failed, unauthorized.")
    })
    public Response updateTeam(@PathParam("team_id") String teamID,
                               @ApiParam(value = "Team Dto", required = true) TeamDto teamDto) throws CustomException {

        logger.info("Convert Dto to Object:: Start");
        Team team = TRANSFORMER.getTeam(teamDto);
        logger.info("Convert Dto to Object:: End");

        logger.info("Team Update:: Start");
        team.setTeamId(teamID);
        teamService.updateTeam(team);
        logger.info("Team Update:: End");

        return Response.ok().entity(TRANSFORMER.getTeamDto(teamService.getTeamByID(teamID))).build();
    }

    @DELETE
    @Path("/{team_id}")
    @ApiOperation(value = "Delete Team", notes = "Delete Team", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Team delete success"),
            @ApiResponse(code = 500, message = "Team delete failed, unauthorized.")
    })
    public Response deleteTeam(@PathParam("team_id") String teamID) throws CustomException {

        logger.info("Team delete:: Start");
        teamService.deleteTeam(teamID);
        logger.info("Team delete:: End");

        return Response.ok().entity(null).build();
    }

    @GET
    @Path("/{team_id}")
    @ApiOperation(value = "Read Team", notes = "Read Team", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read team success"),
            @ApiResponse(code = 500, message = "read team failed, unauthorized.")
    })
    public Response readTeamOrAllTeams(@PathParam("team_id") String teamID) throws CustomException {

        logger.info("Read a team info");
        return Response.ok().entity(TRANSFORMER.getTeamDto(teamService.getTeamByID(teamID))).build();
    }

    @GET
    @ApiOperation(value = "Search Or Read All Teams", notes = "Search Or Read All Teams", position = 5)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search or read all teams success"),
            @ApiResponse(code = 500, message = "Search or read all teams failed, unauthorized.")
    })
    public Response searchTeamOrAllTeams(@QueryParam("teamName") String teamName,
                                         @QueryParam("status") String status,
                                         @QueryParam("floor") String floor,
                                         @QueryParam("room") String room,
                                         @QueryParam("memberName") String memberName,
                                         @QueryParam("projectName") String projectName,
                                         @QueryParam("clientName") String clientName,
                                         @QueryParam("from") String from,
                                         @QueryParam("range") String range) throws CustomException {

        logger.info("Read all team info");
        return Response.ok().entity(TRANSFORMER.getTeamsDto(teamService.searchTeams(teamName, status, floor, room,
                memberName, projectName, clientName, from, range))).build();
    }
}
