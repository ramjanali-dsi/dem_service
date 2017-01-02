package com.dsi.dem.resource;

import com.dsi.dem.dto.ProjectDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.service.ProjectService;
import com.dsi.dem.service.impl.ProjectServiceImpl;
import com.wordnik.swagger.annotations.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
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

    private static final ProjectService projectService = new ProjectServiceImpl();

    @Context
    HttpServletRequest request;

    @POST
    @ApiOperation(value = "Create Project", notes = "Create Project", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project create success"),
            @ApiResponse(code = 500, message = "Project create failed, unauthorized.")
    })
    public Response createProject(@ApiParam(value = "Project Dto", required = true)ProjectDto  projectDto)
            throws CustomException {

        String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        return Response.ok().entity(projectService.saveProject(projectDto, tenantName)).build();
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

        String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        return Response.ok().entity(projectService.updateProject(projectID, projectDto, tenantName)).build();
    }

    @DELETE
    @Path("/{project_id}")
    @ApiOperation(value = "Delete Project", notes = "Delete Project", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project delete success"),
            @ApiResponse(code = 500, message = "Project delete failed, unauthorized.")
    })
    public Response deleteProject(@PathParam("project_id") String projectID) throws CustomException {

        String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        return Response.ok().entity(projectService.deleteProject(projectID, tenantName)).build();
    }

    @GET
    @Path("/{project_id}")
    @ApiOperation(value = "Read Project", notes = "Read Project", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read project success"),
            @ApiResponse(code = 500, message = "Read project failed, unauthorized.")
    })
    public Response readProjectOrAllProjects(@PathParam("project_id") String projectID) throws CustomException {

        return Response.ok().entity(projectService.getProjectByID(projectID)).build();
    }

    @GET
    @ApiOperation(value = "Search Or Read All Projects", notes = "Search Or Read All Projects", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search or read all projects success"),
            @ApiResponse(code = 500, message = "Search or read all projects failed, unauthorized.")
    })
    public Response searchProjectOrAllProjects(@QueryParam("projectName") String projectName,
                                               @QueryParam("status") String status,
                                               @QueryParam("clientName") String clientName,
                                               @QueryParam("teamName") String teamName,
                                               @QueryParam("memberName") String memberName,
                                               @QueryParam("from") String from,
                                               @QueryParam("range") String range) throws CustomException {


        return Response.ok().entity(projectService.searchProjects(projectName, status, clientName,
                teamName, memberName, from, range)).build();
    }
}
