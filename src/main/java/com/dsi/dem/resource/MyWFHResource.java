package com.dsi.dem.resource;

import com.dsi.dem.dto.WorkFromHomeDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.service.WorkFromHomeService;
import com.dsi.dem.service.impl.WorkFromHomeServiceImpl;
import com.wordnik.swagger.annotations.*;
import com.wordnik.swagger.jaxrs.PATCH;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sabbir on 1/11/17.
 */
@Path("/v1/my_work_from_home_requests")
@Api(value = "/EmployeesOwnWorkFormHome", description = "Operations about Employees Own Work From Home")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class MyWFHResource {

    private static final WorkFromHomeService wfhService = new WorkFromHomeServiceImpl();

    @Context
    HttpServletRequest request;

    @GET
    @ApiOperation(value = "Search Or Read Employees Own Work Form Home Requests",
            notes = "Search Or Read Employees Own Work From Home Requests", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search or read employees own work from home requests success"),
            @ApiResponse(code = 500, message = "Search or read employees own work form home requests failed, unauthorized.")
    })
    public Response searchOrReadEmployeesWFHRequests(@QueryParam("date") String date,
                                                     @QueryParam("reason") String reason,
                                                     @QueryParam("statusId") String statusId,
                                                     @QueryParam("from") String from,
                                                     @QueryParam("range") String range) throws CustomException {

        String userID = request.getAttribute("user_id") != null ?
                request.getAttribute("user_id").toString() : null;

        return Response.ok().entity(wfhService.searchOrReadWorkFormHomeRequests
                (userID, date, reason, statusId, from, range)).build();
    }

    @PATCH
    @Path("/{work_from_home_request_id}")
    @ApiOperation(value = "Update Employees Own Work Form Home Requests",
            notes = "Update Employees Own Work From Home Requests", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Update employees own work from home requests success"),
            @ApiResponse(code = 500, message = "Update employees own work form home requests failed, unauthorized.")
    })
    public Response updateWorkFormHomeRequest(@PathParam("work_from_home_request_id") String wfhId,
                                              @ApiParam(value = "Employees Work From Home Request", required = true)
                                                      WorkFromHomeDto workFromHomeDto) throws CustomException {

        String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        String userID = request.getAttribute("user_id") != null ?
                request.getAttribute("user_id").toString() : null;

        workFromHomeDto.setWorkFromHomeId(wfhId);
        return Response.ok().entity(wfhService.updateWorkFromHomeRequest(workFromHomeDto, userID, tenantName)).build();
    }

    @POST
    @ApiOperation(value = "Create Employees Own Work Form Home Requests",
            notes = "Create Employees Own Work From Home Requests", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Create employees own work from home requests success"),
            @ApiResponse(code = 500, message = "Create employees own work form home requests failed, unauthorized.")
    })
    public Response createWorkFromHomeRequest(@ApiParam(value = "Employees Work From Home Request", required = true)
                                                          WorkFromHomeDto workFromHomeDto) throws CustomException {

        String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        String userID = request.getAttribute("user_id") != null ?
                request.getAttribute("user_id").toString() : null;

        return Response.ok().entity(wfhService.saveWorkFromHomeRequest(workFromHomeDto, userID, tenantName)).build();
    }
}
