package com.dsi.dem.resource;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Employee;
import com.dsi.dem.util.Utility;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * Created by sabbir on 7/20/16.
 */

@Path("/v1/employee")
@Api(value = "/Employee", description = "Operations about Employee Management")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class EmployeeResource {

    private static final Logger logger = Logger.getLogger(EmployeeResource.class);

    @POST
    @ApiOperation(value = "Create Employee", notes = "Create Employee", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee create success"),
            @ApiResponse(code = 500, message = "Employee create failed, unauthorized.")
    })
    public Response createEmployee(String body) throws CustomException {

        ObjectMapper mapper = new ObjectMapper();
        Map<String, Object> map = new HashMap<>();

        try {
            map = mapper.readValue(body, new TypeReference<Map<String, Object>>(){});



        } catch (IOException e) {
            e.printStackTrace();
        }

        logger.info("Request body map: " + map);
        return null;
    }

    @PUT
    @Path("/{employee_id}")
    @ApiOperation(value = "Update Employee", notes = "Update Employee", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee update success"),
            @ApiResponse(code = 500, message = "Employee update failed, unauthorized.")
    })
    public Response updateEmployee(@PathParam("employee_id") String employeeID,
                                   Employee employee) throws CustomException {

        return null;
    }

    @DELETE
    @Path("/{employee_id}")
    @ApiOperation(value = "Delete Employee", notes = "Delete Employee", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employee delete success"),
            @ApiResponse(code = 500, message = "Employee delete failed, unauthorized.")
    })
    public Response deleteEmployee(@PathParam("employee_id") String employeeID) throws CustomException {

        return null;
    }

    @GET
    @Path("/{employee_id}")
    @ApiOperation(value = "Read An Employee Or All Employees", notes = "Read An Employee Or All Employees", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read an employee or all employees success"),
            @ApiResponse(code = 500, message = "Read an employee or all employees failed, unauthorized.")
    })
    public Response readEmployeeOrAllEmployees(@PathParam("employee_id") String employeeID) throws CustomException {

        if(!Utility.isNullOrEmpty(employeeID)){
            //TODO read an employee info.

        } else{
            //TODO read all employees info.
        }
        return null;
    }

    @GET
    @Path("/search")
    @ApiOperation(value = "Search An Employee Or All Employees", notes = "Search An Employee Or All Employees", position = 5)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search an employee or all employees success"),
            @ApiResponse(code = 500, message = "Search an employee or all employees failed, unauthorized.")
    })
    public Response searchEmployeeOrAllEmployees(@QueryParam("search_text") String searchText) throws CustomException {

        if(!Utility.isNullOrEmpty(searchText)){
            //TODO search an employee info.
        }
        return null;
    }
}
