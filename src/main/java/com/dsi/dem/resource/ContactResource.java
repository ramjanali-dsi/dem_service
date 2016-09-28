package com.dsi.dem.resource;

import com.dsi.dem.dto.transformer.EmployeeDtoTransformer;
import com.dsi.dem.dto.EmployeeContactDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.EmployeeContact;
import com.dsi.dem.service.ContactService;
import com.dsi.dem.service.impl.ContactServiceImpl;
import com.dsi.dem.util.Utility;
import com.wordnik.swagger.annotations.*;
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
public class ContactResource {

    private static final Logger logger = Logger.getLogger(ContactResource.class);

    private static final EmployeeDtoTransformer EMPLOYEE_DTO_TRANSFORMER = new EmployeeDtoTransformer();
    private static final ContactService contactService = new ContactServiceImpl();

    @POST
    @ApiOperation(value = "Create Employees Contact", notes = "Create Employees Contact", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees contact create success"),
            @ApiResponse(code = 500, message = "Employees contact create failed, unauthorized.")
    })
    public Response createEmployeesContact(@PathParam("employee_id") String employeeID,
                                           @ApiParam(value = "EmployeeContact Dto", required = true)
                                                   List<EmployeeContactDto> employeeContactDtoList) throws CustomException {

        logger.info("Convert Dto to Object:: Start");
        List<EmployeeContact> employeeContactList = EMPLOYEE_DTO_TRANSFORMER.getContactInfoList(employeeContactDtoList);
        logger.info("Convert Dto to Object:: End");

        logger.info("Employees Contacts info Create:: Start");
        contactService.saveEmployeeContactInfo(employeeContactList, employeeID);
        logger.info("Employee Contacts info Create:: End");

        return Response.ok().entity(EMPLOYEE_DTO_TRANSFORMER.getContactInfoDtoList(
                contactService.getEmployeesContactInfoByEmployeeID(employeeID))).build();
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
                                           @ApiParam(value = "EmployeeContact Dto", required = true)
                                                       EmployeeContactDto employeeContactDto) throws CustomException {

        logger.info("Convert Dto to Object:: Start");
        EmployeeContact employeeContact = EMPLOYEE_DTO_TRANSFORMER.getEmployeeContactInfo(employeeContactDto);
        logger.info("Convert Dto to Object:: End");

        logger.info("Employees contact info update:: Start");
        employeeContact.setContactNumberId(contactID);
        contactService.updateEmployeeContactInfo(employeeContact, employeeID);
        logger.info("Employees contact info update:: End");

        return Response.ok().entity(EMPLOYEE_DTO_TRANSFORMER.getEmployeeContactInfoDto(
                contactService.getEmployeeContactInfo(contactID, employeeID))).build();
    }

    @DELETE
    @Path("/{contact_id}")
    @ApiOperation(value = "Delete Employees Contact", notes = "Delete Employees Contact", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Employees contact delete success"),
            @ApiResponse(code = 500, message = "Employees contact delete failed, unauthorized.")
    })
    public Response deleteEmployeesContact(@PathParam("employee_id") String employeeID,
                                           @PathParam("contact_id") String contactID) throws CustomException {

        logger.info("Employees contact info delete:: Start");
        contactService.deleteEmployeeContactInfo(contactID);
        logger.info("Employees contact info delete:: End");

        return null;
    }

    @GET
    @Path("/{contact_id}")
    @ApiOperation(value = "Read Employees Contact", notes = "Read Employees Contact", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read employees contact success"),
            @ApiResponse(code = 500, message = "Read employees contact failed, unauthorized.")
    })
    public Response readEmployeesContactOrAllContacts(@PathParam("employee_id") String employeeID,
                                                      @PathParam("contact_id") String contactID) throws CustomException {

        logger.info("Read an employees contact info");
        return Response.ok().entity(EMPLOYEE_DTO_TRANSFORMER.getEmployeeContactInfoDto(
                contactService.getEmployeeContactInfo(contactID, employeeID))).build();
    }

    @GET
    @ApiOperation(value = "Search Or Read Employees All Contacts", notes = "Search Or Read Employees All Contacts", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search or read employees contacts success"),
            @ApiResponse(code = 500, message = "Search or read employees contacts failed, unauthorized.")
    })
    public Response searchOrReadEmployeesAllContact(@PathParam("employee_id") String employeeID,
                                                    @QueryParam("search") String searchText) throws CustomException {

        if(!Utility.isNullOrEmpty(searchText)){
            //TODO search employees contact info

        } else {
            logger.info("Read employees all contact info");
            return Response.ok().entity(EMPLOYEE_DTO_TRANSFORMER.getContactInfoDtoList(
                    contactService.getEmployeesContactInfoByEmployeeID(employeeID))).build();
        }
        return null;
    }
}
