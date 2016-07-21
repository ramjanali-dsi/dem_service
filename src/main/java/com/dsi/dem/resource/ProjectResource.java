package com.dsi.dem.resource;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Project;
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

@Path("/v1/project")
@Api(value = "/Project", description = "Operations about Project Management")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ProjectResource {

    private static final Logger logger = Logger.getLogger(ProjectResource.class);

    @POST
    @ApiOperation(value = "Create Project", notes = "Create Project", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project create success"),
            @ApiResponse(code = 500, message = "Project create failed, unauthorized.")
    })
    public Response createProject(Project project) throws CustomException {

        return null;
    }

    @PUT
    @Path("/{project_id}")
    @ApiOperation(value = "Update Project", notes = "Update Project", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project update success"),
            @ApiResponse(code = 500, message = "Project update failed, unauthorized.")
    })
    public Response updateProject(@PathParam("project_id") String projectID,
                                  Project project) throws CustomException {

        return null;
    }

    @DELETE
    @Path("/{project_id}")
    @ApiOperation(value = "Delete Project", notes = "Delete Project", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Project delete success"),
            @ApiResponse(code = 500, message = "Project delete failed, unauthorized.")
    })
    public Response deleteProject(@PathParam("project_id") String projectID) throws CustomException {

        return null;
    }

    @GET
    @Path("/{project_id}")
    @ApiOperation(value = "Read Project Or All Projects", notes = "Read Project Or All Projects", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read project or all projects success"),
            @ApiResponse(code = 500, message = "Read project or all projects failed, unauthorized.")
    })
    public Response readProjectOrAllProjects(@PathParam("project_id") String projectID) throws CustomException {

        if(!Utility.isNullOrEmpty(projectID)){
            //TODO read a project

        } else {
            //TODO read all projects
        }
        return null;
    }

    @GET
    @Path("/search")
    @ApiOperation(value = "Search Project Or All Projects", notes = "Search Project Or All Projects", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search project or all projects success"),
            @ApiResponse(code = 500, message = "Search project or all projects failed, unauthorized.")
    })
    public Response searchProjectOrAllProjects(@QueryParam("search_text") String searchText) throws CustomException {

        if(!Utility.isNullOrEmpty(searchText)){
            //TODO search a project

        }
        return null;
    }
}
