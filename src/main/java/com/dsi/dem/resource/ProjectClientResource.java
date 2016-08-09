package com.dsi.dem.resource;

import com.dsi.dem.dto.ProjectDto;
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

@Path("/v1/project/{project_id}/client")
@Api(value = "/ProjectClient", description = "Operations about Project Client")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProjectClientResource {

    private static final Logger logger = Logger.getLogger(ProjectClientResource.class);

    private static final ProjectDtoTransformer TRANSFORMER = new ProjectDtoTransformer();
    private static final ProjectService projectService = new ProjectServiceImpl();

    @POST
    @ApiOperation(value = "Create Project Client", notes = "Create Project Client", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project client create success"),
            @ApiResponse(code = 500, message = "Project client create failed, unauthorized.")
    })
    public Response createProjectClient(@PathParam("project_id") String projectID,
                                        @ApiParam(value = "Project Dto", required = true) ProjectDto projectDto)
            throws CustomException {

        logger.info("Project client create:: start");
        if(Utility.isNullOrEmpty(projectDto.getClientIds())){
            ErrorContext errorContext = new ErrorContext(null, "ProjectClient", "Project client list not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        projectService.saveProjectClient(projectDto.getClientIds(), projectService.getProjectByID(projectID));
        logger.info("Project client create:: end");

        return Response.ok().entity(TRANSFORMER.getProjectClientsDto(projectService.getProjectClients(projectID))).build();
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

        logger.info("Project client delete:: start");
        projectService.deleteProjectClient(clientID);
        logger.info("Project client delete:: end");

        return Response.ok().entity("Success").build();
    }
}
