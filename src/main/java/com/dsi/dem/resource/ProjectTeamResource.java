package com.dsi.dem.resource;

import com.dsi.dem.dto.ProjectDto;
import com.dsi.dem.dto.TeamDto;
import com.dsi.dem.dto.transformer.ProjectDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.service.ProjectService;
import com.dsi.dem.service.impl.ProjectServiceImpl;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.Utility;
import com.wordnik.swagger.annotations.*;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sabbir on 8/9/16.
 */

@Path("/v1/project/{project_id}/team")
@Api(value = "/ProjectTeam", description = "Operations about Project Team")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProjectTeamResource {

    private static final Logger logger = Logger.getLogger(ProjectTeamResource.class);

    private static final ProjectDtoTransformer TRANSFORMER = new ProjectDtoTransformer();
    private static final ProjectService projectService = new ProjectServiceImpl();

    @POST
    @ApiOperation(value = "Create Project Team", notes = "Create Project Team", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project team create success"),
            @ApiResponse(code = 500, message = "Project team create failed, unauthorized.")
    })
    public Response createProjectTeam(@PathParam("project_id") String projectID,
                                      @ApiParam(value = "Project Dto", required = true) ProjectDto projectDto)
            throws CustomException {

        logger.info("Project team create:: start");
        if(Utility.isNullOrEmpty(projectDto.getTeamIds())){
            ErrorContext errorContext = new ErrorContext(null, "ProjectTeam", "Project team list not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        projectService.saveProjectTeam(projectDto.getTeamIds(), projectService.getProjectByID(projectID));
        logger.info("Project team create:: end");

        return Response.ok().entity(TRANSFORMER.getProjectTeamsDto(projectService.getProjectTeams(projectID))).build();
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

        logger.info("Project team delete:: start");
        projectService.deleteProjectTeam(teamID);
        logger.info("Project team delete:: end");

        return null;
    }
}
