package com.dsi.dem.resource;

import com.dsi.dem.dto.TeamDto;
import com.dsi.dem.dto.transformer.TeamDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.service.TeamService;
import com.dsi.dem.service.impl.TeamServiceImpl;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.Utility;
import com.wordnik.swagger.annotations.*;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sabbir on 8/5/16.
 */

@Path("/v1/team/{team_id}/project")
@Api(value = "/TeamProject", description = "Operations about Team Project")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class TeamProjectResource {
    
    private static final Logger logger = Logger.getLogger(TeamProjectResource.class);
    
    private static final TeamDtoTransformer TRANSFORMER = new TeamDtoTransformer();
    private static final TeamService teamService = new TeamServiceImpl();

    @POST
    @ApiOperation(value = "Create Team Project", notes = "Create Team Project", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Team project create success"),
            @ApiResponse(code = 500, message = "Team project create failed, unauthorized.")
    })
    public Response createTeamProject(@PathParam("team_id") String teamID, TeamDto teamDto) throws CustomException {

        logger.info("Team project create:: start");
        if(Utility.isNullOrEmpty(teamDto.getProjectIds())){
            ErrorContext errorContext = new ErrorContext(null, "TeamProject", "Team project list not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        teamService.saveTeamProjects(teamDto.getProjectIds(), teamService.getTeamByID(teamID));
        logger.info("Team project create:: end");

        return Response.ok().entity(TRANSFORMER.getProjectTeamsDto(teamService.getTeamProjects(teamID))).build();
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

        logger.info("Team project delete:: start");
        teamService.deleteTeamProject(projectID);
        logger.info("Team project delete:: end");

        return Response.ok().entity("Success").build();
    }
}
