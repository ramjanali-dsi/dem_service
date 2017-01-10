package com.dsi.dem.resource;

import com.dsi.dem.dto.*;
import com.dsi.dem.dto.transformer.EmployeeDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.*;
import com.dsi.dem.service.*;
import com.dsi.dem.service.impl.*;
import com.dsi.dem.util.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordnik.swagger.annotations.*;
import org.apache.commons.io.FilenameUtils;
import org.apache.log4j.Logger;
import org.glassfish.jersey.media.multipart.FormDataContentDisposition;
import org.glassfish.jersey.media.multipart.FormDataParam;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.io.InputStream;
import java.util.List;

/**
 * Created by sabbir on 7/20/16.
 */

@Path("/v1/employee")
@Api(value = "/Employee", description = "Operations about Employee Management")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.WILDCARD})
public class EmployeeResource {

    private static final Logger logger = Logger.getLogger(EmployeeResource.class);

    private static final EmployeeService employeeService = new EmployeeServiceImpl();
    private static final TeamService teamService = new TeamServiceImpl();
    private static final EmailService emailService = new EmailServiceImpl();
    private static final ContactService contactService = new ContactServiceImpl();
    private static final DesignationService designationService = new DesignationServiceImpl();
    private static final CallAnotherResource callAnotherService = new CallAnotherResource();

    private static final EmployeeDtoTransformer EMPLOYEE_DTO_TRANSFORMER = new EmployeeDtoTransformer();

    @Context
    HttpServletRequest request;

    @POST
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @ApiOperation(value = "Create Employee", notes = "Create Employee", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee create success"),
            @ApiResponse(code = 500, message = "Employee create failed, unauthorized.")
    })
    public Response createEmployee(@FormDataParam("file") InputStream fileInputStream,
                                   @FormDataParam("file") FormDataContentDisposition fileDetails,
                                   @FormDataParam("employee") String employeeDtoData)
            throws CustomException {

        String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        String currentUserID = request.getAttribute("user_id") != null ?
                request.getAttribute("user_id").toString() : null;

        ObjectMapper mapper = new ObjectMapper();
        EmployeeDto employeeDto;
        try {
            logger.info("Request Body of Employee: " + employeeDtoData);
            employeeDto = mapper.readValue(employeeDtoData, EmployeeDto.class);

            logger.info("Convert Dto to Object:: Start");
            Employee employee = EMPLOYEE_DTO_TRANSFORMER.getEmployee(employeeDto);
            logger.info("Convert Dto to Object:: End");

            if (fileInputStream != null) {
                employee.getInfo().setPhotoUrl(savePhoto(fileInputStream, fileDetails.getFileName()));
            }

            return Response.ok().entity(EMPLOYEE_DTO_TRANSFORMER.getEmployeeDto(
                    employeeService.saveEmployee(employee, currentUserID, tenantName))).build();

        } catch (IOException io) {
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0008,
                    Constants.DEM_SERVICE_0008_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_008);
            throw new CustomException(errorMessage);
        }
    }

    @PUT
    @Path("/{employee_id}")
    @Consumes(MediaType.MULTIPART_FORM_DATA)
    @ApiOperation(value = "Update Employee", notes = "Update Employee", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee update success"),
            @ApiResponse(code = 500, message = "Employee update failed, unauthorized.")
    })
    public Response updateEmployee(@PathParam("employee_id") String employeeID,
                                   @FormDataParam("file") InputStream fileInputStream,
                                   @FormDataParam("file") FormDataContentDisposition fileDetails,
                                   @FormDataParam("employee") String employeeDtoData) throws CustomException {

        String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        String currentUserID = request.getAttribute("user_id") != null ?
                request.getAttribute("user_id").toString() : null;

        ObjectMapper mapper = new ObjectMapper();
        EmployeeDto employeeDto;

        try {
            logger.info("Request Body of Employee: " + employeeDtoData);
            employeeDto = mapper.readValue(employeeDtoData, EmployeeDto.class);

            logger.info("Convert Dto to Object:: Start");
            Employee employee = EMPLOYEE_DTO_TRANSFORMER.getEmployeeWithInfo(employeeDto);
            logger.info("Convert Dto to Object:: End");

            if (fileInputStream != null) {
                employee.getInfo().setPhotoUrl(savePhoto(fileInputStream, fileDetails.getFileName()));
            }

            employee.setEmployeeId(employeeID);

            return Response.ok().entity(EMPLOYEE_DTO_TRANSFORMER.getEmployeeDto
                    (employeeService.updateEmployee(employee, currentUserID, tenantName))).build();

        } catch (IOException io) {
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0008,
                    Constants.DEM_SERVICE_0008_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_008);
            throw new CustomException(errorMessage);
        }
    }

    private String savePhoto(InputStream fileInputStream, String fileName) throws CustomException {
        String photoName = Utility.generateRandomString() + "." +
                FilenameUtils.getExtension(fileName);
        logger.info("---- Photo file name: " + photoName);

        String uploadFileLocation = APIProvider.PHOTO_DIRECTORY + photoName;
        Utility.saveToFile(fileInputStream, uploadFileLocation);
        logger.info("Photo uploaded successfully.");

        return photoName;
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
        Employee employee = employeeService.getEmployeeByID(employeeID);
        String userID = employee.getUserId();

        List<TeamMember> memberList = teamService.getTeamMembers(null, employeeID);
        if(!Utility.isNullOrEmpty(memberList)){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0003);
            throw new CustomException(errorMessage);
        }

        logger.info("User info delete:: start");
        callAnotherService.sendDelete(APIProvider.API_USER + userID);
        logger.info("User info delete:: end");

        logger.info("Login info delete:: start");
        callAnotherService.sendDelete(APIProvider.API_LOGIN_SESSION_DELETE + userID);
        logger.info("Login info delete:: end");

        employeeService.deleteEmployee(employeeID);
        logger.info("Employee delete:: End");

        return Response.ok().entity(null).build();
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
        EmployeeDto employeeDto = EMPLOYEE_DTO_TRANSFORMER.getEmployeeDto
                (employeeService.getEmployeeByID(employeeID));

        return Response.ok().entity(employeeDto).build();
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
                                             @QueryParam("projectName") String projectName,
                                             @QueryParam("userId") String userID,
                                             @QueryParam("from") String from,
                                             @QueryParam("range") String range) throws CustomException {

        if(!Utility.isNullOrEmpty(userID)){
            userID = request.getAttribute("user_id") != null ?
                    request.getAttribute("user_id").toString() : null;

            logger.info("User id: " + userID);

        } else {
            logger.info("Read all employees info");
        }

        return Response.ok().entity(EMPLOYEE_DTO_TRANSFORMER.getEmployeesDto(
                employeeService.searchEmployees(employeeNo, firstName, lastName, nickName, bankAccountId,
                        ipAddress, nationalId, tinId, phone, email, isActive, joiningDate, teamName,
                        projectName, userID, from, range))).build();
    }

    @POST
    @Path("{employee_id}/additional")
    @ApiOperation(value = "Create Employees Additional Info", notes = "Create Employees Additional Info", position = 6)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees additional info create success"),
            @ApiResponse(code = 500, message = "Employees additional info create failed, unauthorized.")
    })
    public Response createEmployeesAdditionalInfo(@PathParam("employee_id") String employeeID,
                                                  @ApiParam(value = "Employees Additional Info", required = true)
                                                          AdditionalInfo additionalInfo)
            throws CustomException {

        switch (additionalInfo.getAction()) {
            case "Email":
                for(EmployeeEmailDto emailDto : additionalInfo.getEmailList()){
                    EmployeeEmail employeeEmail;
                    switch (emailDto.getActivity()) {
                        case 1:
                            logger.info("Convert EmployeeEmail Dto to Object:: Start");
                            employeeEmail = EMPLOYEE_DTO_TRANSFORMER.getEmployeeEmail(emailDto);
                            logger.info("Convert EmployeeEmail Dto to Object:: End");

                            logger.info("Employee Emails Create:: Start");
                            emailService.saveEmployeeEmail(employeeEmail, employeeID);
                            logger.info("Employee Emails Create:: End");

                            break;

                        case 2:
                            logger.info("Convert EmployeeEmail Dto to Object:: Start");
                            employeeEmail = EMPLOYEE_DTO_TRANSFORMER.getEmployeeEmail(emailDto);
                            logger.info("Convert EmployeeEmail Dto to Object:: End");

                            logger.info("Employees email update:: Start");
                            emailService.updateEmployeeEmail(employeeEmail, employeeID);
                            logger.info("Employees email update:: End");

                            break;

                        case 3:
                            logger.info("Employees email delete:: Start");
                            emailService.deleteEmployeeEmail(emailDto.getEmailId());
                            logger.info("Employees email delete:: End");

                            break;
                    }
                }
                logger.info("Read employees all email info");
                return Response.ok().entity(emailService.getEmployeesEmailByEmployeeID(employeeID)).build();

            case "Contact":
                for(EmployeeContactDto contactDto : additionalInfo.getContactList()){
                    EmployeeContact  employeeContact;
                    switch (contactDto.getActivity()) {
                        case 1:
                            logger.info("Convert EmployeeContact Dto to Object:: Start");
                            employeeContact = EMPLOYEE_DTO_TRANSFORMER.getEmployeeContactInfo(contactDto);
                            logger.info("Convert EmployeeContact Dto to Object:: End");

                            logger.info("Employees Contacts info Create:: Start");
                            contactService.saveEmployeeContactInfo(employeeContact, employeeID);
                            logger.info("Employee Contacts info Create:: End");

                            break;

                        case 2:
                            logger.info("Convert EmployeeContact Dto to Object:: Start");
                            employeeContact = EMPLOYEE_DTO_TRANSFORMER.getEmployeeContactInfo(contactDto);
                            logger.info("Convert EmployeeContact Dto to Object:: End");

                            logger.info("Employees contact info update:: Start");
                            contactService.updateEmployeeContactInfo(employeeContact, employeeID);
                            logger.info("Employees contact info update:: End");

                            break;

                        case 3:
                            logger.info("Employees contact info delete:: Start");
                            contactService.deleteEmployeeContactInfo(contactDto.getContactNumberId());
                            logger.info("Employees contact info delete:: End");

                            break;
                    }
                }
                logger.info("Read employees all contact info");
                return Response.ok().entity(contactService.getEmployeesContactInfoByEmployeeID(employeeID)).build();

            case "Designation":
                for(EmployeeDesignationDto designationDto : additionalInfo.getDesignationList()){
                    EmployeeDesignation employeeDesignation;
                    switch (designationDto.getActivity()) {
                        case 1:
                            logger.info("Convert EmployeeDesignation Dto to Object:: Start");
                            employeeDesignation = EMPLOYEE_DTO_TRANSFORMER.getEmployeeDesignation(designationDto);
                            logger.info("Convert EmployeeDesignation Dto to Object:: End");

                            logger.info("Employees Designations info Create:: Start");
                            designationService.saveEmployeeDesignation(employeeDesignation, employeeID);
                            logger.info("Employee Designations info Create:: End");

                            break;

                        case 2:
                            logger.info("Convert EmployeeDesignation Dto to Object:: Start");
                            employeeDesignation = EMPLOYEE_DTO_TRANSFORMER.getEmployeeDesignation(designationDto);
                            logger.info("Convert EmployeeDesignation Dto to Object:: End");

                            logger.info("Employees designation info update:: Start");
                            designationService.updateEmployeeDesignation(employeeDesignation, employeeID);
                            logger.info("Employees designation info update:: End");

                            break;

                        case 3:
                            logger.info("Employees designation info delete:: Start");
                            designationService.deleteEmployeeDesignation(designationDto.getDesignationId());
                            logger.info("Employees designation info delete:: End");

                            break;
                    }
                }
                logger.info("Read employees all designation info");
                return Response.ok().entity(designationService.getEmployeesDesignationByEmployeeID(employeeID)).build();
        }

        ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
        throw new CustomException(errorMessage);
    }
}
