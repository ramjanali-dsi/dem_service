package com.dsi.dem.resource;

import com.dsi.dem.dto.ProjectDto;
import com.dsi.dem.dto.transformer.ProjectDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Project;
import com.dsi.dem.service.ProjectService;
import com.dsi.dem.service.impl.ProjectServiceImpl;
import com.dsi.dem.util.Utility;
import com.wordnik.swagger.annotations.*;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sabbir on 7/21/16.
 */

@Path("/v1/project")
@Api(value = "/Project", description = "Operations about Project Management")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProjectResource {

    private static final Logger logger = Logger.getLogger(ProjectResource.class);

    private static final ProjectDtoTransformer TRANSFORMER = new ProjectDtoTransformer();
    private static final ProjectService projectService = new ProjectServiceImpl();

    @POST
    @ApiOperation(value = "Create Project", notes = "Create Project", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project create success"),
            @ApiResponse(code = 500, message = "Project create failed, unauthorized.")
    })
    public Response createProject(@ApiParam(value = "Project Dto", required = true)ProjectDto  projectDto)
            throws CustomException {

        logger.info("Convert Dto to Object:: Start");
        Project project = TRANSFORMER.getProject(projectDto);
        logger.info("Convert Dto to Object:: End");

        if(!Utility.isNullOrEmpty(projectDto.getTeamIds())){
            logger.info("Project Create:: Start");
            projectService.saveProject(project);
            projectService.saveProjectTeam(projectDto.getTeamIds(), project);

            if(!Utility.isNullOrEmpty(projectDto.getClientIds())){
                projectService.saveProjectClient(projectDto.getClientIds(), project);
            }
        }
        logger.info("Project Create:: End");

        return Response.ok().entity(TRANSFORMER.getProjectDto(
                projectService.getProjectByID(project.getProjectId()))).build();
    }

    @PUT
    @Path("/{project_id}")
    @ApiOperation(value = "Update Project", notes = "Update Project", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project update success"),
            @ApiResponse(code = 500, message = "Project update failed, unauthorized.")
    })
    public Response updateProject(@PathParam("project_id") String projectID,
                                  @ApiParam(value = "Project Dto", required = true) ProjectDto projectDto)
            throws CustomException {

        logger.info("Convert Dto to Object:: Start");
        Project project = TRANSFORMER.getProject(projectDto);
        logger.info("Convert Dto to Object:: End");

        logger.info("Project Update:: Start");
        project.setProjectId(projectID);
        projectService.updateProject(project);
        logger.info("Project Update:: End");

        return Response.ok().entity(TRANSFORMER.getProjectDto(
                projectService.getProjectByID(projectID))).build();
    }

    @DELETE
    @Path("/{project_id}")
    @ApiOperation(value = "Delete Project", notes = "Delete Project", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project delete success"),
            @ApiResponse(code = 500, message = "Project delete failed, unauthorized.")
    })
    public Response deleteProject(@PathParam("project_id") String projectID) throws CustomException {

        logger.info("Project delete:: Start");
        projectService.deleteProject(projectID);
        logger.info("Project delete:: End");

        return Response.ok().entity("Success").build();
    }

    @GET
    @Path("/{project_id}")
    @ApiOperation(value = "Read Project", notes = "Read Project", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read project success"),
            @ApiResponse(code = 500, message = "Read project failed, unauthorized.")
    })
    public Response readProjectOrAllProjects(@PathParam("project_id") String projectID) throws CustomException {

        logger.info("Read a project");
        return Response.ok().entity(TRANSFORMER.getProjectDto(
                projectService.getProjectByID(projectID))).build();
    }

    @GET
    @ApiOperation(value = "Search Or Read All Projects", notes = "Search Or Read All Projects", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search or read all projects success"),
            @ApiResponse(code = 500, message = "Search or read all projects failed, unauthorized.")
    })
    public Response searchProjectOrAllProjects(@QueryParam("search") String searchText) throws CustomException {

        if(!Utility.isNullOrEmpty(searchText)){
            //TODO search a project
            return null;

        } else {
            logger.info("Read all projects");
            return Response.ok().entity(TRANSFORMER.getProjectsDto(
                    projectService.getAllProjects())).build();
        }
    }
}
