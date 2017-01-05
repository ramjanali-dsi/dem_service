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
 * Created by sabbir on 11/28/16.
 */
@Path("/v1/special_leave_requests")
@Api(value = "/SpecialLeaveRequest", description = "Operations about Special Leave Request")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class SpecialLeaveResource {

    private static final LeaveService leaveService = new LeaveServiceImpl();

    @Context
    HttpServletRequest request;

    @POST
    @ApiOperation(value = "Special Leave Request Create", notes = "Special Leave Request Create", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Special leave request create success"),
            @ApiResponse(code = 500, message = "Special leave request create failed, unauthorized.")
    })
    public Response createSpecialLeave(@ApiParam(value = "Employees Leave Request", required = true)
                                               LeaveRequestDto leaveRequestDto) throws CustomException {

        String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        return Response.ok().entity(leaveService.saveSpecialLeave(leaveRequestDto, tenantName)).build();
    }

    @PATCH
    @Path("/{leave_request_id}")
    @ApiOperation(value = "Special Leave Request Update", notes = "Special Leave Request Update", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Special leave request update success"),
            @ApiResponse(code = 500, message = "Special leave request update failed, unauthorized.")
    })
    public Response updateSpecialLeave(@PathParam("leave_request_id") String leaveRequestID,
                                       @ApiParam(value = "Employees Leave Request", required = true)
                                               LeaveRequestDto leaveRequestDto) throws CustomException {

        String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        return Response.ok().entity(leaveService.updateSpecialLeave(leaveRequestDto,
                leaveRequestID, tenantName)).build();
    }

    @DELETE
    @Path("/{leave_request_id}")
    @ApiOperation(value = "Special Leave Request Delete", notes = "Special Leave Request Delete", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Special leave request delete success"),
            @ApiResponse(code = 500, message = "Special leave request delete failed, unauthorized.")
    })
    public Response deleteSpecialLeave(@PathParam("leave_request_id") String leaveRequestID) throws CustomException {

        leaveService.deleteSpecialLeave(leaveRequestID);
        return Response.ok().entity(null).build();
    }

    @GET
    @ApiOperation(value = "Read or Search Special Leave Request", notes = "Read or Search Special Leave Request", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read or search special leave request success"),
            @ApiResponse(code = 500, message = "Read or search special leave request failed, unauthorized.")
    })
    public Response readOrSearchSpecialLeave(@QueryParam("employeeNo") String employeeNo,
                                             @QueryParam("firstName") String firstName,
                                             @QueryParam("lastName") String lastName,
                                             @QueryParam("nickName") String nickName,
                                             @QueryParam("leaveStatus") String leaveStatus,
                                             @QueryParam("leaveType") String leaveType,
                                             @QueryParam("requestType") String requestType,
                                             @QueryParam("leaveRequestId") String leaveRequestId,
                                             @QueryParam("from") String from,
                                             @QueryParam("range") String range) throws CustomException {

        return Response.ok().entity(leaveService.searchOrReadAllSpecialLeave(employeeNo, firstName, lastName,
                nickName, leaveStatus, leaveType, requestType, leaveRequestId, from, range)).build();
    }
}
