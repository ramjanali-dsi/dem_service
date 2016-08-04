package com.dsi.dem.resource;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.LeaveRequest;
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

@Path("/v1/employee/{employee_id}/leave_request")
@Api(value = "/Leave_Request", description = "Operations about Employees Leave Request")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class LeaveResource {

    private static final Logger logger = Logger.getLogger(LeaveResource.class);

    @POST
    //@Path("/leave_request")
    @ApiOperation(value = "Create Employees Leave Request", notes = "Create Employees Leave Request", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees leave request create success"),
            @ApiResponse(code = 500, message = "Employees leave request create failed, unauthorized.")
    })
    public Response createEmployeesLeaveRequest(@PathParam("employee_id") String employeeID,
                                                LeaveRequest leaveRequest) throws CustomException {

        return null;
    }

    @PUT
    //@Path("/leave_request/{leave_request_id}")
    @Path("/{leave_request_id}")
    @ApiOperation(value = "Update Employees Leave Request", notes = "Update Employees Leave Request", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees leave request update success"),
            @ApiResponse(code = 500, message = "Employees leave request update failed, unauthorized.")
    })
    public Response updateEmployeesLeaveRequest(@PathParam("employee_id") String employeeID,
                                                @PathParam("leave_request_id") String leaveRequestID,
                                                LeaveRequest leaveRequest) throws CustomException {

        return null;
    }

    @DELETE
    //@Path("/leave_request/{leave_request_id}")
    @Path("/{leave_request_id}")
    @ApiOperation(value = "Delete Employees Leave Request", notes = "Delete Employees Leave Request", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees leave request delete success"),
            @ApiResponse(code = 500, message = "Employees leave request delete failed, unauthorized.")
    })
    public Response deleteEmployeesLeaveRequest(@PathParam("employee_id") String employeeID,
                                                @PathParam("leave_request_id") String leaveRequestID) throws CustomException {

        return null;
    }

    @GET
    //@Path("/leave_request/{leave_request_id}")
    @Path("/{leave_request_id}")
    @ApiOperation(value = "Read Employees Leave Request Or All Leave Requests",
            notes = "Read Employees Leave Request Or All Leave Requests", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read employees leave request or all leave requests success"),
            @ApiResponse(code = 500, message = "Read employees leave request or all leave requests failed, unauthorized.")
    })
    public Response readEmployeesLeaveRequestOrAllLeaveRequests(@PathParam("employee_id") String employeeID,
                                                                @PathParam("leave_request_id") String leaveRequestID) throws CustomException {

        if(!Utility.isNullOrEmpty(leaveRequestID)){
            //TODO read employees leave request info.

        } else{
            //TODO read employees all leave request info.
        }
        return null;
    }

    /*@GET
    @Path("/leave_status")
    @ApiOperation(value = "Read Employees Leave Status",
            notes = "Read Employees Leave Status", position = 5)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read employees leave status success"),
            @ApiResponse(code = 500, message = "Read employees leave status failed, unauthorized.")
    })
    public Response readEmployeesLeaveStatus(@PathParam("employee_id") String employeeID) throws CustomException {

        return null;
    }*/
}
