package com.dsi.dem.resource;

import com.dsi.dem.dto.AttendanceDto;
import com.dsi.dem.dto.transformer.AttendanceDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.service.AttendanceService;
import com.dsi.dem.service.impl.AttendanceServiceImpl;
import com.wordnik.swagger.annotations.*;
import com.wordnik.swagger.jaxrs.PATCH;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.InputStream;
import java.util.List;

/**
 * Created by sabbir on 10/19/16.
 */

@Path("/v1/attendance_schedule")
@Api(value = "/Attendance", description = "Operations about Employees Attendance")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.WILDCARD})
public class AttendanceResource {

    private static final Logger logger = Logger.getLogger(AttendanceResource.class);

    private static final AttendanceDtoTransformer DTO_TRANSFORMER = new AttendanceDtoTransformer();
    private static final AttendanceService attendanceService = new AttendanceServiceImpl();

    @Context
    HttpServletRequest request;

    @GET
    @Path("/is_available")
    @ApiOperation(value = "Check Attendance Schedule Availability", notes = "Check Attendance Schedule Availability", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Check attendance schedule availability create success"),
            @ApiResponse(code = 500, message = "Check attendance schedule availability failed, unauthorized.")
    })
    public Response checkAvailableAttendanceSchedule(@QueryParam("date") String date) throws CustomException {

        return Response.ok().entity(attendanceService.
                isAvailableEmployeeOrTempAttendanceSheet(date)).build();
    }

    @POST
    @Path("/upload")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @ApiOperation(value = "Create Employees Temporary Attendance", notes = "Create Employees Temporary Attendance", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees temporary attendance create success"),
            @ApiResponse(code = 500, message = "Employees temporary attendance create failed, unauthorized.")
    })
    public Response createEmployeesTemporaryAttendance(@FormDataParam("file") InputStream fileInputStream,
                                                       @FormDataParam("file") FormDataContentDisposition fileDetails) throws CustomException {

        String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        String userID = request.getAttribute("user_id") != null ?
                request.getAttribute("user_id").toString() : null;

        return Response.ok().entity(attendanceService.saveTempAttendance(fileInputStream, userID, tenantName)).build();
    }

    @PATCH
    @Path("/temporary")
    @ApiOperation(value = "Update Employees Temporary Attendances", notes = "Create Employees Temporary Attendances", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees temporary attendances update success"),
            @ApiResponse(code = 500, message = "Employees temporary attendances update failed, unauthorized.")
    })
    public Response updateTemporaryAttendance(@QueryParam("attendanceDate") String attendanceDate,
                                              @ApiParam(value = "Employees Attendance Info", required = true)
                                                      List<AttendanceDto> attendanceDtoList) throws CustomException {

        String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        String userID = request.getAttribute("user_id") != null ?
                request.getAttribute("user_id").toString() : null;
        logger.info("User id: " + userID);

        attendanceService.updateTempAttendance(attendanceDtoList, userID, attendanceDate, tenantName);

        return Response.ok().entity(DTO_TRANSFORMER.getTempAttendancesDto(
                attendanceService.getAllTempAttendances(attendanceDate))).build();
    }

    @GET
    @Path("/temporary")
    @ApiOperation(value = "Search or Read Temporary Attendance Schedule", notes = "Search or Read Temporary Attendance Schedule", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search or read temporary attendance schedule success"),
            @ApiResponse(code = 500, message = "Search or read temporary attendance schedule failed, unauthorized.")
    })
    public Response searchOrReadAttendanceSchedule(@QueryParam("attendanceDate") String attendanceDate) throws CustomException {

        return Response.ok().entity(DTO_TRANSFORMER.getTempAttendancesDto(attendanceService.
                getAllTempAttendances(attendanceDate))).build();
    }

    @GET
    @Path("/status")
    @ApiOperation(value = "Check Attendance Status", notes = "Check Attendance Status", position = 5)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Check attendance status success"),
            @ApiResponse(code = 500, message = "Check attendance status failed, unauthorized.")
    })
    public Response checkAttendanceStatus(@QueryParam("attendanceDate") String attendanceDate,
                                          @QueryParam("mode") String mode) throws CustomException {

        logger.info("Check attendance status for this date: " + attendanceDate + " & mode: " + mode);
        return Response.ok().entity(attendanceService.getAttendanceStatus(attendanceDate, mode)).build();
    }

    @POST
    @ApiOperation(value = "Create Employees Attendance", notes = "Create Employees Attendance", position = 6)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees attendance create success"),
            @ApiResponse(code = 500, message = "Employees attendance create failed, unauthorized.")
    })
    public Response createEmployeesAttendance(@QueryParam("attendanceDate") String attendanceDate,
                                              @ApiParam(value = "Employees Attendance Info", required = true)
                                                      List<AttendanceDto> attendanceDtoList) throws CustomException {

        String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        String userID = request.getAttribute("user_id") != null ?
                request.getAttribute("user_id").toString() : null;
        logger.info("User id: " + userID);

        attendanceService.saveAttendance(attendanceDtoList, userID, attendanceDate, tenantName);

        return Response.ok().entity(DTO_TRANSFORMER.getEmployeesAttendanceList(
                attendanceService.getAllAttendancesByDate(attendanceDate))).build();
    }

    @GET
    @ApiOperation(value = "Search or Read All Attendance Schedule", notes = "Search or Read All Attendance Schedule", position = 7)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search or read all attendance schedule success"),
            @ApiResponse(code = 500, message = "Search or read all attendance schedule failed, unauthorized.")
    })
    public Response searchOrReadAttendanceSchedule(@QueryParam("isAbsent") String isAbsent,
                                                   @QueryParam("employeeNo") String employeeNo,
                                                   @QueryParam("firstName") String firstName,
                                                   @QueryParam("lastName") String lastName,
                                                   @QueryParam("nickName") String nickName,
                                                   @QueryParam("teamName") String teamName,
                                                   @QueryParam("projectName") String projectName,
                                                   @QueryParam("attendanceDate") String attendanceDate,
                                                   @QueryParam("from") String from,
                                                   @QueryParam("range") String range,
                                                   @QueryParam("attendanceId") String attendanceID) throws CustomException {

        logger.info("Read or search all attendance schedule.");
        return Response.ok().entity(DTO_TRANSFORMER.getEmployeesAttendanceList(attendanceService.
                searchOrReadAttendances(null, employeeNo, isAbsent, firstName, lastName,
                        nickName, attendanceDate, teamName, projectName, from, range))).build();
    }

    @GET
    @Path("/draft")
    @ApiOperation(value = "Read Attendance Draft File", notes = "Read Attendance Draft File", position = 8)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read attendance draft files success"),
            @ApiResponse(code = 500, message = "Read attendance draft files failed, unauthorized.")
    })
    public Response readAttendanceDraftFile(@QueryParam("from") String from,
                                            @QueryParam("range") String range) throws CustomException {

        return Response.ok().entity(attendanceService.getDraftAttendanceFileDetails(from, range)).build();
    }

    @DELETE
    @ApiOperation(value = "Delete Attendances", notes = "Delete Attendances", position = 9)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Delete attendances success"),
            @ApiResponse(code = 500, message = "Delete attendances failed, unauthorized.")
    })
    public Response deleteAllAttendances(@QueryParam("attendanceDate") String attendanceDate) throws CustomException {

        String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        attendanceService.deleteAttendance(attendanceDate, tenantName);
        return Response.ok().entity(null).build();
    }
}
