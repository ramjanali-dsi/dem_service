package com.dsi.dem.resource;

import com.dsi.dem.dto.LeaveRequestDto;
import com.dsi.dem.dto.transformer.LeaveDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.LeaveRequest;
import com.dsi.dem.service.LeaveService;
import com.dsi.dem.service.impl.LeaveServiceImpl;
import com.wordnik.swagger.annotations.*;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sabbir on 9/26/16.
 */

@Path("/v1")
@Api(value = "/LeaveRequest", description = "Operations about Leave Request")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class LeaveRequestResource {

    private static final Logger logger = Logger.getLogger(LeaveRequestResource.class);

    private static final LeaveService leaveService = new LeaveServiceImpl();
    private static final LeaveDtoTransformer LEAVE_DTO_TRANSFORMER = new LeaveDtoTransformer();

    @GET
    @Path("/leave_summaries")
    @ApiOperation(value = "Search Or Read Leave Summaries", notes = "Search Or Read Leave Summaries", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search or read all leave summaries success"),
            @ApiResponse(code = 500, message = "Search or read all leave summaries failed, unauthorized.")
    })
    public Response searchOrReadLeaveSummaries(@QueryParam("employeeNo") String employeeNo,
                                               @QueryParam("firstName") String firstName,
                                               @QueryParam("lastName") String lastName,
                                               @QueryParam("nickName") String nickName,
                                               @QueryParam("email") String email,
                                               @QueryParam("phone") String phone,
                                               @QueryParam("teamName") String teamName,
                                               @QueryParam("projectName") String projectName,
                                               @QueryParam("employeeId") String employeeId,
                                               @QueryParam("from") String from,
                                               @QueryParam("range") String range) throws CustomException {

        logger.info("Search or read employees leave summaries");
        return Response.ok().entity(LEAVE_DTO_TRANSFORMER.getAllEmployeesLeaveDto(leaveService.
                searchOrReadEmployeesLeaveSummary(employeeNo, firstName, lastName, nickName, email, phone, teamName,
                        projectName, employeeId, from, range))).build();
    }

    @GET
    @Path("/leave_details")
    @ApiOperation(value = "Search Or Read Leave Details", notes = "Search Or Read Leave Details", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search or read all leave details success"),
            @ApiResponse(code = 500, message = "Search or read all leave details failed, unauthorized.")
    })
    public Response searchOrReadLeaveDetails(@QueryParam("employeeNo") String employeeNo,
                                             @QueryParam("firstName") String firstName,
                                             @QueryParam("lastName") String lastName,
                                             @QueryParam("nickName") String nickName,
                                             @QueryParam("email") String email,
                                             @QueryParam("phone") String phone,
                                             @QueryParam("teamName") String teamName,
                                             @QueryParam("projectName") String projectName,
                                             @QueryParam("employeeId") String employeeId,
                                             @QueryParam("leaveType") String leaveType,
                                             @QueryParam("requestType") String requestType,
                                             @QueryParam("approvedStartDate") String approvedStartDate,
                                             @QueryParam("approvedEndDate") String approvedEndDate,
                                             @QueryParam("approvedFirstName") String approvedFirstName,
                                             @QueryParam("approvedLastName") String approvedLastName,
                                             @QueryParam("approvedNickName") String approvedNickName,
                                             @QueryParam("appliedStartDate") String appliedStartDate,
                                             @QueryParam("appliedEndDate") String appliedEndDate,
                                             @QueryParam("leaveStatus") String leaveStatus,
                                             @QueryParam("from") String from,
                                             @QueryParam("range") String range) throws CustomException {

        logger.info("Search or read employees leave details");
        return Response.ok().entity(LEAVE_DTO_TRANSFORMER.getAllEmployeesLeaveDetails(
                leaveService.searchOrReadLeaveDetails(employeeNo, firstName, lastName, nickName, email, phone, teamName,
                        projectName, employeeId, leaveType, requestType, approvedStartDate, approvedEndDate, approvedFirstName,
                        approvedLastName, approvedNickName, appliedStartDate, appliedEndDate, leaveStatus, from, range))).build();
    }

    @POST
    @Path("/leave_request/{leave_request_id}/approval")
    @ApiOperation(value = "Leave Request Approval", notes = "Leave Request Approval", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Leave request approval success"),
            @ApiResponse(code = 500, message = "Leave request approval failed, unauthorized.")
    })
    public Response approvalLeaveRequest(@PathParam("leave_request_id") String leaveRequestId,
                                         @ApiParam(value = "Employees Leave Request", required = true)
                                                 LeaveRequestDto leaveRequestDto) throws CustomException {

        logger.info("Employees leave request approval:: Start");
        LeaveRequest leaveRequest = LEAVE_DTO_TRANSFORMER.getLeaveRequest(leaveRequestDto);

        leaveRequest.setLeaveRequestId(leaveRequestId);
        leaveService.approveLeaveRequest(leaveRequest);
        logger.info("Employees leave request approval:: End");

        return Response.ok().entity(LEAVE_DTO_TRANSFORMER.getLeaveRequestDto(
                leaveService.getLeaveRequestById(leaveRequestId))).build();
    }
}
