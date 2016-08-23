package com.dsi.dem.resource;

import com.dsi.dem.dto.transformer.EmployeeDtoTransformer;
import com.dsi.dem.dto.EmployeeDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Employee;
import com.dsi.dem.service.EmployeeService;
import com.dsi.dem.service.impl.APIProvider;
import com.dsi.dem.service.impl.EmployeeServiceImpl;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.Utility;
import com.dsi.httpclient.HttpClient;
import com.wordnik.swagger.annotations.*;
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
import java.io.InputStream;

/**
 * Created by sabbir on 7/20/16.
 */

@Path("/v1/employee")
@Api(value = "/Employee", description = "Operations about Employee Management")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.WILDCARD})
public class EmployeeResource {

    private static final Logger logger = Logger.getLogger(EmployeeResource.class);

    private static final EmployeeDtoTransformer EMPLOYEE_DTO_TRANSFORMER = new EmployeeDtoTransformer();
    private static final EmployeeService employeeService = new EmployeeServiceImpl();
    private static final HttpClient httpClient = new HttpClient();

    @Context
    HttpServletRequest request;

    /*@POST
    @ApiOperation(value = "Create Employee", notes = "Create Employee", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee create success"),
            @ApiResponse(code = 500, message = "Employee create failed, unauthorized.")
    })
    public Response createEmployee(@ApiParam(value = "Employee Dto", required = true) EmployeeDto employeeDto)
            throws CustomException {

        String currentUserID = request.getAttribute("user_id") != null ?
                request.getAttribute("user_id").toString() : null;
        try {
            logger.info("Convert Dto to Object:: Start");
            Employee employee = EMPLOYEE_DTO_TRANSFORMER.getEmployee(employeeDto);
            logger.info("Convert Dto to Object:: End");

            employeeService.validateInputForCreation(employee);

            logger.info("Employee Create:: Start");
            String result = httpClient.sendPost(APIProvider.API_USER, Utility.getUserObject(employee, currentUserID),
                    Constants.SYSTEM, Constants.SYSTEM_ID);
            logger.info("v1/user api call result: " + result);

            JSONObject resultObj = new JSONObject(result);
            if(!resultObj.has(Constants.MESSAGE)){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result).build();
            }

            employee.setUserId(resultObj.getString("user_id"));
            employeeService.saveEmployee(employee);
            logger.info("Employee Create:: End");

            return Response.ok().entity(EMPLOYEE_DTO_TRANSFORMER.getEmployeeDto
                    (employeeService.getEmployeeByID(employee.getEmployeeId()))).build();

        } catch (JSONException je) {
            ErrorContext errorContext = new ErrorContext(null, null, je.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0009,
                    Constants.DEM_SERVICE_0009_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }*/

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @ApiOperation(value = "Create Employee", notes = "Create Employee", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee create success"),
            @ApiResponse(code = 500, message = "Employee create failed, unauthorized.")
    })
    public Response createEmployee(@FormDataParam("file") InputStream fileInputStream,
                                   @FormDataParam("file") FormDataContentDisposition fileDetails,
                                   @FormDataParam("employee") EmployeeDto employeeDto)
            throws CustomException {

        String currentUserID = request.getAttribute("user_id") != null ?
                request.getAttribute("user_id").toString() : null;
        try {
            logger.info("Convert Dto to Object:: Start");
            Employee employee = EMPLOYEE_DTO_TRANSFORMER.getEmployee(employeeDto);
            logger.info("Convert Dto to Object:: End");

            employeeService.validateInputForCreation(employee);

            logger.info("Employee Create:: Start");

            String result = httpClient.sendPost(APIProvider.API_LOGIN_SESSION_CREATE, Utility.getLoginObject(employee, currentUserID),
                    Constants.SYSTEM, Constants.SYSTEM_ID);
            logger.info("v1/login_session/create api call result: " + result);

            JSONObject resultObj = new JSONObject(result);
            if (!resultObj.has(Constants.MESSAGE)) {
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result).build();
            }

            if (fileInputStream != null) {
                String photoName = Utility.generateRandomString() + "." + FilenameUtils.getExtension(fileDetails.getFileName());
                logger.info("---- Photo file name: " + photoName);

                String uploadFileLocation = APIProvider.PHOTO_DIRECTORY + photoName;
                Utility.saveToFile(fileInputStream, uploadFileLocation);
                logger.info("Photo uploaded successfully.");

                employee.getInfo().setPhotoUrl(photoName);
            }

            employee.setUserId(resultObj.getString("user_id"));
            employeeService.saveEmployee(employee);
            logger.info("Employee Create:: End");

            return Response.ok().entity(EMPLOYEE_DTO_TRANSFORMER.getEmployeeDto
                    (employeeService.getEmployeeByID(employee.getEmployeeId()))).build();

        } catch (JSONException je) {
            ErrorContext errorContext = new ErrorContext(null, null, je.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0009,
                    Constants.DEM_SERVICE_0009_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @PUT
    @Path("/{employee_id}")
    @ApiOperation(value = "Update Employee", notes = "Update Employee", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee update success"),
            @ApiResponse(code = 500, message = "Employee update failed, unauthorized.")
    })
    public Response updateEmployee(@PathParam("employee_id") String employeeID,
                                   @ApiParam(value = "Employee Dto", required = true)
                                           EmployeeDto employeeDto) throws CustomException {

        logger.info("Convert Dto to Object:: Start");
        Employee employee = EMPLOYEE_DTO_TRANSFORMER.getEmployee(employeeDto);
        logger.info("Convert Dto to Object:: End");

        logger.info("Employee update:: Start");
        employee.setEmployeeId(employeeID);
        employeeService.updateEmployee(employee);
        logger.info("Employee update:: End");

        return Response.ok().entity(EMPLOYEE_DTO_TRANSFORMER.getEmployeeDto
                (employeeService.getEmployeeByID(employeeID))).build();
    }

    @DELETE
    @Path("/{employee_id}")
    @ApiOperation(value = "Delete Employee", notes = "Delete Employee", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee delete success"),
            @ApiResponse(code = 500, message = "Employee delete failed, unauthorized.")
    })
    public Response deleteEmployee(@PathParam("employee_id") String employeeID) throws CustomException {

        logger.info("Employee delete:: Start");
        employeeService.deleteEmployee(employeeID);
        logger.info("Employee delete:: End");

        return Response.ok().entity("Success").build();
    }

    @GET
    @Path("/{employee_id}")
    @ApiOperation(value = "Read An Employee", notes = "Read An Employee", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read an employee success"),
            @ApiResponse(code = 500, message = "Read an employee failed, unauthorized.")
    })
    public Response readEmployee(@PathParam("employee_id") String employeeID) throws CustomException {

        logger.info("Read an employee info");
        return Response.ok().entity(EMPLOYEE_DTO_TRANSFORMER.getEmployeeDto
                (employeeService.getEmployeeByID(employeeID))).build();
    }

    @GET
    @ApiOperation(value = "Search Or Read All Employees", notes = "Search Or Read All Employees", position = 5)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search or read all employees success"),
            @ApiResponse(code = 500, message = "Search or rea all employees failed, unauthorized.")
    })
    public Response searchOrReadAllEmployees(@QueryParam("employeeNo") String employeeNo,
                                             @QueryParam("firstName") String firstName,
                                             @QueryParam("lastName") String lastName,
                                             @QueryParam("nickName") String nickName,
                                             @QueryParam("bankAccountId") String bankAccountId,
                                             @QueryParam("ipAddress") String ipAddress,
                                             @QueryParam("nationalId") String nationalId,
                                             @QueryParam("tinId") String tinId,
                                             @QueryParam("joiningDate") String joiningDate,
                                             @QueryParam("phone") String phone,
                                             @QueryParam("email") String email,
                                             @QueryParam("isActive") String isActive,
                                             @QueryParam("teamName") String teamName,
                                             @QueryParam("projectName") String projectName) throws CustomException {

        logger.info("Read all employees info");
        return Response.ok().entity(EMPLOYEE_DTO_TRANSFORMER.getEmployeesDto(
                employeeService.searchEmployees(employeeNo, firstName, lastName, nickName, bankAccountId,
                        ipAddress, nationalId, tinId, phone, email, isActive, joiningDate, teamName, projectName))).build();
    }
}
