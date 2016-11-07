package com.dsi.dem.resource;

import com.dsi.dem.dto.AttendanceDto;
import com.dsi.dem.dto.TempAttendanceDto;
import com.dsi.dem.dto.transformer.AttendanceDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.TemporaryAttendance;
import com.dsi.dem.service.AttendanceService;
import com.dsi.dem.service.EmployeeService;
import com.dsi.dem.service.impl.APIProvider;
import com.dsi.dem.service.impl.AttendanceServiceImpl;
import com.dsi.dem.service.impl.EmployeeServiceImpl;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.Utility;
import com.wordnik.swagger.annotations.*;
import com.wordnik.swagger.jaxrs.PATCH;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

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

        logger.info("Check attendance date schedule exist or not!");
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

        String userID = request.getAttribute("user_id") != null ?
                request.getAttribute("user_id").toString() : null;
        logger.info("User id: " + userID);

        logger.info("Attendance sheet upload:: start");
        attendanceService.saveTempAttendance(fileInputStream, userID);
        logger.info("Attendance sheet upload:: end");

        return commonResponse();
    }

    @PATCH
    @Path("/temporary/{temp_attendance_id}")
    @ApiOperation(value = "Update Employees Temporary Attendance", notes = "Create Employees Temporary Attendance", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees temporary attendance update success"),
            @ApiResponse(code = 500, message = "Employees temporary attendance update failed, unauthorized.")
    })
    public Response updateTemporaryAttendance(@PathParam("temp_attendance_id") String tempAttendanceId,
                                              @ApiParam(value = "Employees Attendance Info", required = true)
                                                      AttendanceDto attendanceDto) throws CustomException {

        String userID = request.getAttribute("user_id") != null ?
                request.getAttribute("user_id").toString() : null;
        logger.info("User id: " + userID);

        logger.info("Update employees temporary attendance: start");
        attendanceService.updateTempAttendance(attendanceDto, userID, tempAttendanceId);
        logger.info("Update employees temporary attendance: end");

        return Response.ok().entity(DTO_TRANSFORMER.getTempAttendanceDto(attendanceService.
                getTemporaryAttendance(tempAttendanceId))).build();
    }

    @GET
    @Path("/temporary")
    @ApiOperation(value = "Search or Read Temporary Attendance Schedule", notes = "Search or Read Temporary Attendance Schedule", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search or read temporary attendance schedule success"),
            @ApiResponse(code = 500, message = "Search or read temporary attendance schedule failed, unauthorized.")
    })
    public Response searchOrReadAttendanceSchedule(@QueryParam("attendanceDate") String attendanceDate) throws CustomException {

        logger.info("Read all temporary attendances for this date: " + attendanceDate);
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
    public Response createEmployeesAttendance(@QueryParam("attendanceDate") String attendanceDate) throws CustomException {

        logger.info("Employees attendance schedule create: start");
        attendanceService.saveAttendance(attendanceDate);
        logger.info("Employees attendance schedule create: end");

        return commonResponse();
    }

    private Response commonResponse() throws CustomException {
        try{
            JSONObject resultObj = new JSONObject();
            resultObj.put(Constants.MESSAGE, Constants.SUCCESS);

            return Response.ok().entity(resultObj.toString()).build();

        } catch (JSONException je) {
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }
    }

    @GET
    @ApiOperation(value = "Search or Read All Attendance Schedule", notes = "Search or Read All Attendance Schedule", position = 5)
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
        return Response.ok().entity(DTO_TRANSFORMER.getEmployeesAttendanceList(attendanceService.searchOrReadAttendances(null, employeeNo, isAbsent, firstName,
                lastName, nickName, attendanceDate, teamName, projectName, from, range))).build();
    }
}
