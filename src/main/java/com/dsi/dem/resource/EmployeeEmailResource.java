package com.dsi.dem.resource;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.EmployeeEmail;
import com.dsi.dem.util.Utility;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.util.List;

/**
 * Created by sabbir on 7/20/16.
 */

@Path("/v1/employee/{employee_id}/email")
@Api(value = "/Email", description = "Operations about Employees Email")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class EmployeeEmailResource {

    private static final Logger logger = Logger.getLogger(EmployeeEmailResource.class);

    @POST
    @ApiOperation(value = "Create Employees Email", notes = "Create Employees Email", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees email create success"),
            @ApiResponse(code = 500, message = "Employees email create failed, unauthorized.")
    })
    public Response createEmployeesEmail(@PathParam("employee_id") String employeeID,
                                         List<EmployeeEmail> employeeEmailList) throws CustomException {

        return null;
    }

    @PUT
    @Path("/{email_id}")
    @ApiOperation(value = "Update Employees Email", notes = "Update Employees Email", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees email update success"),
            @ApiResponse(code = 500, message = "Employees email update failed, unauthorized.")
    })
    public Response updateEmployeesEmail(@PathParam("employee_id") String employeeID,
                                   @PathParam("email_id") String emailID,
                                   EmployeeEmail employeeEmail) throws CustomException {

        return null;
    }

    @DELETE
    @Path("/{email_id}")
    @ApiOperation(value = "Delete Employees Email", notes = "Delete Employees Email", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees email delete success"),
            @ApiResponse(code = 500, message = "Employees email delete failed, unauthorized.")
    })
    public Response deleteEmployeesEmail(@PathParam("employee_id") String employeeID,
                                         @PathParam("email_id") String emailID) throws CustomException {

        return null;
    }

    @GET
    @Path("/{email_id}")
    @ApiOperation(value = "Read Employees An Email Or All Emails", notes = "Read Employees An Email Or All Emails", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read employees an email or all emails success"),
            @ApiResponse(code = 500, message = "Read employees an email or all emails failed, unauthorized.")
    })
    public Response readEmployeesEmailOrAllEmails(@PathParam("employee_id") String employeeID,
                                                  @PathParam("email_id") String emailID) throws CustomException {

        if(!Utility.isNullOrEmpty(emailID)){
            //TODO read employees an email info.

        } else{
            //TODO read employees all email info.
        }
        return null;
    }
}
