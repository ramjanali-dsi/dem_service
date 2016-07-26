package com.dsi.dem.resource;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.EmployeeContact;
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

@Path("/v1/employee/{employee_id}/contact")
@Api(value = "/Contact", description = "Operations about Employees Contact")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class EmployeeContactResource {

    private static final Logger logger = Logger.getLogger(EmployeeContactResource.class);

    @POST
    @ApiOperation(value = "Create Employees Contact", notes = "Create Employees Contact", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees contact create success"),
            @ApiResponse(code = 500, message = "Employees contact create failed, unauthorized.")
    })
    public Response createEmployeesContact(@PathParam("employee_id") String employeeID,
                                           List<EmployeeContact> employeeContactList) throws CustomException {

        return null;
    }

    @PUT
    @Path("/{contact_id}")
    @ApiOperation(value = "Update Employees Contact", notes = "Update Employees Contact", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees email update success"),
            @ApiResponse(code = 500, message = "Employees email update failed, unauthorized.")
    })
    public Response updateEmployeesContact(@PathParam("employee_id") String employeeID,
                                           @PathParam("contact_id") String contactID,
                                           EmployeeContact employeeContact) throws CustomException {

        return null;
    }

    @DELETE
    @Path("/{contact_id}")
    @ApiOperation(value = "Delete Employees Contact", notes = "Delete Employees Contact", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees email delete success"),
            @ApiResponse(code = 500, message = "Employees email delete failed, unauthorized.")
    })
    public Response deleteEmployeesContact(@PathParam("employee_id") String employeeID,
                                           @PathParam("contact_id") String contactID) throws CustomException {

        return null;
    }

    @GET
    @Path("/{contact_id}")
    @ApiOperation(value = "Read Employees Contact Or All Contacts", notes = "Read Employees Contact Or All Contacts", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read employees contact or all contact success"),
            @ApiResponse(code = 500, message = "Read employees contact or all contact failed, unauthorized.")
    })
    public Response readEmployeesContactOrAllContacts(@PathParam("employee_id") String employeeID,
                                                      @PathParam("contact_id") String contactID) throws CustomException {

        if(!Utility.isNullOrEmpty(contactID)){
            //TODO read employees contact info.

        } else{
            //TODO read employees all contact info.
        }
        return null;
    }
}
