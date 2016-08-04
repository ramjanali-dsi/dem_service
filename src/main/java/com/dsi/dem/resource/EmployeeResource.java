package com.dsi.dem.resource;

import com.dsi.dem.dto.transformer.EmployeeDtoTransformer;
import com.dsi.dem.dto.EmployeeDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Employee;
import com.dsi.dem.service.EmployeeService;
import com.dsi.dem.service.impl.EmployeeServiceImpl;
import com.dsi.dem.util.Utility;
import com.wordnik.swagger.annotations.*;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sabbir on 7/20/16.
 */

@Path("/v1/employee")
@Api(value = "/Employee", description = "Operations about Employee Management")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class EmployeeResource {

    private static final Logger logger = Logger.getLogger(EmployeeResource.class);

    private static final EmployeeDtoTransformer EMPLOYEE_DTO_TRANSFORMER = new EmployeeDtoTransformer();
    private static final EmployeeService employeeService = new EmployeeServiceImpl();

    @POST
    @ApiOperation(value = "Create Employee", notes = "Create Employee", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee create success"),
            @ApiResponse(code = 500, message = "Employee create failed, unauthorized.")
    })
    public Response createEmployee(@ApiParam(value = "Employee Dto", required = true) EmployeeDto employeeDto)
            throws CustomException {

        logger.info("Convert Dto to Object:: Start");
        Employee employee = EMPLOYEE_DTO_TRANSFORMER.getEmployee(employeeDto);
        logger.info("Convert Dto to Object:: End");

        logger.info("Employee Create:: Start");
        employeeService.saveEmployee(employee);
        logger.info("Employee Create:: End");

        return Response.ok().entity(EMPLOYEE_DTO_TRANSFORMER.getEmployeeDto
                (employeeService.getEmployeeByID(employee.getEmployeeId()))).build();
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
    public Response searchOrReadAllEmployees(@QueryParam("search") String searchText) throws CustomException {

        if(!Utility.isNullOrEmpty(searchText)){
            //TODO search an employee info.

        } else {
            logger.info("Read all employees info");
            return Response.ok().entity(EMPLOYEE_DTO_TRANSFORMER.getEmployeesDto(
                    employeeService.getAllEmployees())).build();
        }
        return null;
    }
}
