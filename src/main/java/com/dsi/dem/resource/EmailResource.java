package com.dsi.dem.resource;

import com.dsi.dem.dto.EmployeeEmailDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.service.EmailService;
import com.dsi.dem.service.impl.EmailServiceImpl;
import com.dsi.dem.util.Utility;
import com.wordnik.swagger.annotations.*;

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
public class EmailResource {

    private static final EmailService emailService = new EmailServiceImpl();

    @POST
    @ApiOperation(value = "Create Employees Email", notes = "Create Employees Email", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees email create success"),
            @ApiResponse(code = 500, message = "Employees email create failed, unauthorized.")
    })
    public Response createEmployeesEmail(@PathParam("employee_id") String employeeID,
                                         @ApiParam(value = "EmployeeEmail Dto", required = true)
                                                 List<EmployeeEmailDto> employeeEmailDtoList) throws CustomException {

        return Response.ok().entity(emailService.saveEmployeeEmail(employeeEmailDtoList, employeeID)).build();
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
                                         @ApiParam(value = "EmployeeEmail Dto", required = true)
                                                     EmployeeEmailDto emailDto) throws CustomException {

        return Response.ok().entity(emailService.updateEmployeeEmail(emailDto, employeeID, emailID)).build();
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

        emailService.deleteEmployeeEmail(emailID);
        return Response.ok().entity(null).build();
    }

    @GET
    @Path("/{email_id}")
    @ApiOperation(value = "Read Employees An Email", notes = "Read Employees An Email", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read employees an email success"),
            @ApiResponse(code = 500, message = "Read employees an email failed, unauthorized.")
    })
    public Response readEmployeesEmail(@PathParam("employee_id") String employeeID,
                                       @PathParam("email_id") String emailID) throws CustomException {

        return Response.ok().entity(emailService.getEmployeeEmail(emailID, employeeID)).build();
    }

    @GET
    @ApiOperation(value = "Search Or Read Employees All Emails", notes = "Search Or Read Employees All Emails", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search or read employees emails success"),
            @ApiResponse(code = 500, message = "Search or read employees emails failed, unauthorized.")
    })
    public Response searchOrReadEmployeesAllEmail(@PathParam("employee_id") String employeeID,
                                                  @QueryParam("search") String searchText) throws CustomException {

        if(!Utility.isNullOrEmpty(searchText)){
            //TODO search employees email info

        } else {
            return Response.ok().entity(emailService.getEmployeesEmailByEmployeeID(employeeID)).build();
        }

        return Response.ok().entity(null).build();
    }
}
