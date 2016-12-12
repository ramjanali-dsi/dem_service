package com.dsi.dem.resource;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.service.LeaveService;
import com.dsi.dem.service.impl.LeaveServiceImpl;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sabbir on 7/21/16.
 */

@Path("/v1/employees/{employee_id}")
@Api(value = "/EmployeesLeave", description = "Operations about Employees Leave")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class LeaveResource {

    private static final LeaveService leaveService = new LeaveServiceImpl();

    @GET
    @Path("/leave_summaries")
    @ApiOperation(value = "Read Employees Leave Summaries",
            notes = "Read Employees Leave Summaries", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read employees leave summary success"),
            @ApiResponse(code = 500, message = "Read employees leave summary failed, unauthorized.")
    })
    public Response readEmployeesLeaveSummaries(@PathParam("employee_id") String employeeID) throws CustomException {

        return Response.ok().entity(leaveService.getEmployeeLeaveSummary(employeeID)).build();
    }

    @GET
    @Path("/leave_details/{leave_request_id}")
    @ApiOperation(value = "Read Employees Leave Details",
            notes = "Read Employees Leave Details", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read employees leave details success"),
            @ApiResponse(code = 500, message = "Read employees leave details failed, unauthorized.")
    })
    public Response readEmployeesLeaveDetails(@PathParam("employee_id") String employeeID,
                                              @PathParam("leave_request_id") String leaveRequestId) throws CustomException {

        return Response.ok().entity(leaveService.getEmployeesAllLeaveDetails(employeeID, leaveRequestId)).build();
    }

    @GET
    @Path("/leave_details")
    @ApiOperation(value = "Read Employees Leave Details",
            notes = "Read Employees Leave Details", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read employees leave details success"),
            @ApiResponse(code = 500, message = "Read employees leave details failed, unauthorized.")
    })
    public Response readEmployeesLeaveDetails(@PathParam("employee_id") String employeeID) throws CustomException {

        return Response.ok().entity(leaveService.getEmployeesAllLeaveDetails(employeeID, null)).build();
    }
}
