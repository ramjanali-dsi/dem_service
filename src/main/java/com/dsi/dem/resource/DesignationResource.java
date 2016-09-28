package com.dsi.dem.resource;

import com.dsi.dem.dto.transformer.EmployeeDtoTransformer;
import com.dsi.dem.dto.EmployeeDesignationDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.EmployeeDesignation;
import com.dsi.dem.service.DesignationService;
import com.dsi.dem.service.impl.DesignationServiceImpl;
import com.dsi.dem.util.Utility;
import com.wordnik.swagger.annotations.*;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by sabbir on 8/4/16.
 */

@Path("/v1/employee/{employee_id}/designation")
@Api(value = "/Designation", description = "Operations about Employees Designation")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class DesignationResource {
    
    private static final Logger logger = Logger.getLogger(DesignationResource.class);
    
    private static final EmployeeDtoTransformer EMPLOYEE_DTO_TRANSFORMER = new EmployeeDtoTransformer();
    private static final DesignationService designationService = new DesignationServiceImpl();

    @POST
    @ApiOperation(value = "Create Employees Designation", notes = "Create Employees Designation", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees designation create success"),
            @ApiResponse(code = 500, message = "Employees designation create failed, unauthorized.")
    })
    public Response createEmployeesDesignation(@PathParam("employee_id") String employeeID, 
                                               @ApiParam(value = "EmployeeDesignation Dto", required = true) 
                                                       List<EmployeeDesignationDto> employeeDesignationDtoList) throws CustomException {

        logger.info("Convert Dto to Object:: Start");
        List<EmployeeDesignation> employeeDesignationList = EMPLOYEE_DTO_TRANSFORMER.getDesignationList(employeeDesignationDtoList);
        logger.info("Convert Dto to Object:: End");

        logger.info("Employees Designations info Create:: Start");
        designationService.saveEmployeeDesignation(employeeDesignationList, employeeID);
        logger.info("Employee Designations info Create:: End");

        return Response.ok().entity(EMPLOYEE_DTO_TRANSFORMER.getDesignationDtoList(
                designationService.getEmployeesDesignationByEmployeeID(employeeID))).build();
    }

    @PUT
    @Path("/{designation_id}")
    @ApiOperation(value = "Update Employees Designation", notes = "Update Employees Designation", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees email update success"),
            @ApiResponse(code = 500, message = "Employees email update failed, unauthorized.")
    })
    public Response updateEmployeesDesignation(@PathParam("employee_id") String employeeID,
                                               @PathParam("designation_id") String designationID,
                                               @ApiParam(value = "EmployeeDesignation Dto", required = true)
                                                           EmployeeDesignationDto employeeDesignationDto) throws CustomException {

        logger.info("Convert Dto to Object:: Start");
        EmployeeDesignation employeeDesignation = EMPLOYEE_DTO_TRANSFORMER.getEmployeeDesignation(employeeDesignationDto);
        logger.info("Convert Dto to Object:: End");

        logger.info("Employees designation info update:: Start");
        employeeDesignation.setDesignationId(designationID);
        designationService.updateEmployeeDesignation(employeeDesignation, employeeID);
        logger.info("Employees designation info update:: End");

        return Response.ok().entity(EMPLOYEE_DTO_TRANSFORMER.getEmployeeDesignationDto(
                designationService.getEmployeeDesignation(designationID, employeeID))).build();
    }

    @DELETE
    @Path("/{designation_id}")
    @ApiOperation(value = "Delete Employees Designation", notes = "Delete Employees Designation", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees designation delete success"),
            @ApiResponse(code = 500, message = "Employees designation delete failed, unauthorized.")
    })
    public Response deleteEmployeesDesignation(@PathParam("employee_id") String employeeID,
                                               @PathParam("designation_id") String designationID) throws CustomException {

        logger.info("Employees designation info delete:: Start");
        designationService.deleteEmployeeDesignation(designationID);
        logger.info("Employees designation info delete:: End");

        return null;
    }

    @GET
    @Path("/{designation_id}")
    @ApiOperation(value = "Read Employees Designation", notes = "Read Employees Designation", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read employees designation success"),
            @ApiResponse(code = 500, message = "Read employees designation failed, unauthorized.")
    })
    public Response readEmployeesDesignationOrAllDesignations(@PathParam("employee_id") String employeeID,
                                                              @PathParam("designation_id") String designationID) throws CustomException {

        logger.info("Read an employees designation info");
        return Response.ok().entity(EMPLOYEE_DTO_TRANSFORMER.getEmployeeDesignationDto(
                designationService.getEmployeeDesignation(designationID, employeeID))).build();
    }
}
