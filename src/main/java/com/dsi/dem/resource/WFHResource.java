package com.dsi.dem.resource;

import com.dsi.dem.dto.WorkFromHomeDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.service.WorkFromHomeService;
import com.dsi.dem.service.impl.WorkFromHomeServiceImpl;
import com.wordnik.swagger.annotations.*;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sabbir on 1/11/17.
 */
@Path("/v1/work_from_home_requests")
@Api(value = "/EmployeesWorkFormHome", description = "Operations about Employees Work From Home")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class WFHResource {

    private static final WorkFromHomeService wfhService = new WorkFromHomeServiceImpl();

    @Context
    HttpServletRequest request;

    @GET
    @ApiOperation(value = "Search Or Read Employees Work Form Home Requests",
            notes = "Search Or Read Employees Work From Home Requests", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search or read employees work from home requests success"),
            @ApiResponse(code = 500, message = "Search or read employees work form home requests failed, unauthorized.")
    })
    public Response searchOrReadEmployeesWFHRequests(@QueryParam("date") String date,
                                                     @QueryParam("reason") String reason,
                                                     @QueryParam("statusId") String statusId,
                                                     @QueryParam("employeeNo") String employeeNo,
                                                     @QueryParam("firstName") String firstName,
                                                     @QueryParam("lastName") String lastName,
                                                     @QueryParam("nickName") String nickName,
                                                     @QueryParam("workFromHomeId") String wfhId,
                                                     @QueryParam("from") String from,
                                                     @QueryParam("range") String range) throws CustomException {

        return Response.ok().entity(wfhService.searchOrReadEmployeesWFHRequests(date, reason, statusId, employeeNo, firstName,
                lastName, nickName, wfhId, from, range)).build();
    }

    @POST
    @Path("/{work_from_home_id}/approval")
    @ApiOperation(value = "Work Form Home Request Approval", notes = "Work Form Home Request Approval", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Work from home request approval success"),
            @ApiResponse(code = 500, message = "Work from home request approval failed, unauthorized.")
    })
    public Response workFromHomeApproval(@PathParam("work_from_home_id") String wfhId,
                                         @ApiParam(value = "Employees Work From Home Request", required = true)
                                                     WorkFromHomeDto workFromHomeDto) throws CustomException {

        String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        String userID = request.getAttribute("user_id") != null ?
                request.getAttribute("user_id").toString() : null;

        workFromHomeDto.setWorkFromHomeId(wfhId);
        return Response.ok().entity(wfhService.approveWFHRequest(workFromHomeDto, userID, tenantName)).build();
    }
}
