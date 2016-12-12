package com.dsi.dem.resource;

import com.dsi.dem.dto.LeaveRequestDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.service.LeaveService;
import com.dsi.dem.service.impl.LeaveServiceImpl;
import com.wordnik.swagger.annotations.*;
import com.wordnik.swagger.jaxrs.PATCH;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sabbir on 9/26/16.
 */

@Path("/v1/my_leave_requests")
@Api(value = "/EmployeesOwnLeave", description = "Operations about Employees Own Leave")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class MyLeaveResource {

    private static final LeaveService leaveService = new LeaveServiceImpl();

    @Context
    HttpServletRequest request;

    @GET
    @Path("is_available")
    @ApiOperation(value = "Check Leave Requests Availability", notes = "Check Leave Requests Availability", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Check leave requests availability success"),
            @ApiResponse(code = 500, message = "Check leave requests availability failed, unauthorized.")
    })
    public Response checkAvailableLeave(@QueryParam("type") String typeID) throws CustomException {

        String userID = request.getAttribute("user_id") != null ?
                request.getAttribute("user_id").toString() : null;

        return Response.ok().entity(leaveService.isAvailableLeaveTypes(typeID, userID)).build();
    }

    @GET
    @ApiOperation(value = "Search Or Read Employees Leave Requests", notes = "Search Or Read Employees Leave Requests", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search or read employees leave requests success"),
            @ApiResponse(code = 500, message = "Search or read employees leave requests failed, unauthorized.")
    })
    public Response searchOrReadEmployeesLeaveRequests(@QueryParam("teamName") String teamName,
                                                       @QueryParam("projectName") String projectName,
                                                       @QueryParam("leaveCnt") String leaveCnt,
                                                       @QueryParam("leaveReason") String leaveReason,
                                                       @QueryParam("leaveType") String leaveType,
                                                       @QueryParam("leaveStatus") String leaveStatus,
                                                       @QueryParam("requestType") String requestType,
                                                       @QueryParam("appliedStartDate") String appliedStartDate,
                                                       @QueryParam("appliedEndDate") String appliedEndDate,
                                                       @QueryParam("deniedReason") String deniedReason,
                                                       @QueryParam("deniedBy") String deniedBy,
                                                       @QueryParam("leaveRequestId") String leaveRequestId,
                                                       @QueryParam("from") String from,
                                                       @QueryParam("range") String range) throws CustomException {

        String userID = request.getAttribute("user_id") != null ?
                request.getAttribute("user_id").toString() : null;

        return Response.ok().entity(leaveService.searchOrReadLeaveRequests(userID, teamName, projectName, leaveCnt,
                leaveReason, leaveType, leaveStatus, requestType, appliedStartDate, appliedEndDate, deniedReason,
                deniedBy, leaveRequestId, from, range)).build();
    }

    @PATCH
    @Path("/{leave_request_id}")
    @ApiOperation(value = "Update Employees Leave Request", notes = "Update Employees Leave Request", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees leave request update success"),
            @ApiResponse(code = 500, message = "Employees leave request update failed, unauthorized.")
    })
    public Response updateEmployeesLeaveRequest(@PathParam("leave_request_id") String leaveRequestID,
                                                @ApiParam(value = "Employees Leave Request", required = true)
                                                        LeaveRequestDto leaveRequestDto) throws CustomException {

        String userID = request.getAttribute("user_id") != null ?
                request.getAttribute("user_id").toString() : null;

        return Response.ok().entity(leaveService.updateLeaveRequest(leaveRequestDto, userID, leaveRequestID)).build();
    }

    @POST
    @ApiOperation(value = "Create Employees Leave Request", notes = "Create Employees Leave Request", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees leave request create success"),
            @ApiResponse(code = 500, message = "Employees leave request create failed, unauthorized.")
    })
    public Response createEmployeesLeaveRequest(@ApiParam(value = "Employees Leave Request", required = true)
                                                            LeaveRequestDto leaveRequestDto) throws CustomException {

        String userID = request.getAttribute("user_id") != null ?
                request.getAttribute("user_id").toString() : null;

        return Response.ok().entity(leaveService.saveLeaveRequest(leaveRequestDto, userID)).build();
    }

    @DELETE
    @Path("/{leave_request_id}")
    @ApiOperation(value = "Delete Employees Leave Request", notes = "Delete Employees Leave Request", position = 5)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees leave request delete success"),
            @ApiResponse(code = 500, message = "Employees leave request delete failed, unauthorized.")
    })
    public Response deleteEmployeesLeaveRequest(@PathParam("leave_request_id") String leaveRequestID) throws CustomException {

        String userID = request.getAttribute("user_id") != null ?
                request.getAttribute("user_id").toString() : null;

        leaveService.deleteLeaveRequest(leaveRequestID, userID);
        return Response.ok().entity(null).build();
    }
}
