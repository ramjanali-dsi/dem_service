package com.dsi.dem.resource;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Team;
import com.dsi.dem.util.Utility;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
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

    @POST
    @ApiOperation(value = "Create Team", notes = "Create Team", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Team create success"),
            @ApiResponse(code = 500, message = "Team create failed, unauthorized.")
    })
    public Response createTeam(Team team) throws CustomException {

        return null;
    }

    @PUT
    @Path("/{team_id}")
    @ApiOperation(value = "Update Team", notes = "Update Team", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Team update success"),
            @ApiResponse(code = 500, message = "Team update failed, unauthorized.")
    })
    public Response updateTeam(@PathParam("team_id") String teamID, Team team) throws CustomException {

        return null;
    }

    @DELETE
    @Path("/{team_id}")
    @ApiOperation(value = "Delete Team", notes = "Delete Team", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Team delete success"),
            @ApiResponse(code = 500, message = "Team delete failed, unauthorized.")
    })
    public Response deleteTeam(@PathParam("team_id") String teamID) throws CustomException {

        return null;
    }

    @GET
    @Path("/{team_id}")
    @ApiOperation(value = "Read Team Or All Teams", notes = "Read Team Or All Teams", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read team or all teams success"),
            @ApiResponse(code = 500, message = "read team or all teams failed, unauthorized.")
    })
    public Response readTeamOrAllTeams(@PathParam("team_id") String teamID) throws CustomException {

        if(!Utility.isNullOrEmpty(teamID)){
            //TODO read team info

        } else {
            //TODO read all team info
        }
        return null;
    }

    @GET
    @Path("/search")
    @ApiOperation(value = "Search Team Or All Teams", notes = "Search Team Or All Teams", position = 5)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search team or all teams success"),
            @ApiResponse(code = 500, message = "Search team or all teams failed, unauthorized.")
    })
    public Response searchTeamOrAllTeams(@QueryParam("search_text") String searchText) throws CustomException {

        if(!Utility.isNullOrEmpty(searchText)){
            //TODO search team info

        }
        return null;
    }
}