package com.dsi.dem.resource;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.service.ReferenceService;
import com.dsi.dem.service.impl.ReferenceServiceImpl;
import com.dsi.dem.util.Constants;
import com.wordnik.swagger.annotations.Api;
import org.apache.log4j.Logger;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sabbir on 7/21/16.
 */

@Path("/v1/reference")
@Api(value = "/Reference", description = "Operations about Reference")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ReferenceResource {

    private static final Logger logger = Logger.getLogger(ReferenceResource.class);

    private static final ReferenceService referenceService = new ReferenceServiceImpl();

    @GET
    @Path("/role")
    public Response getAllRoles() throws CustomException {
        logger.info("Read all employee roles type");
        return Response.ok().entity(referenceService.getAllRoles()).build();
    }

    @GET
    @Path("/email_type")
    public Response getAllEmailTypes() throws CustomException {
        logger.info("Read all employees email types");
        return Response.ok().entity(referenceService.getAllEmailTypes()).build();
    }

    @GET
    @Path("/contact_number_type")
    public Response getAllContactNumberTypes() throws CustomException {
        logger.info("Read all employees contact number types");
        return Response.ok().entity(referenceService.getAllContactInfoTypes()).build();
    }

    @GET
    @Path("/employee_status")
    public Response getAllEmployeeStatus() throws CustomException {
        logger.info("Read all employee status names");
        return Response.ok().entity(referenceService.getAllEmployeeStatusNames()).build();
    }

    @GET
    @Path("/project_status")
    public Response getAllProjectStatusNames() throws CustomException {
        logger.info("Read all project status names");
        return Response.ok().entity(referenceService.getAllProjectStatusNames()).build();
    }

    @GET
    @Path("/leave_status")
    public Response getAllLeaveStatusNames() throws CustomException {
        logger.info("Read all leave status names");
        return Response.ok().entity(referenceService.getAllLeaveStatusNames()).build();
    }

    @GET
    @Path("/leave_type")
    public Response getAllLeaveTypes() throws CustomException {
        logger.info("Read all general leave types");
        return Response.ok().entity(referenceService.getAllLeaveTypes(
                Constants.GENERAL_LEAVE_TYPE)).build();
    }

    @GET
    @Path("/special_leave_type")
    public Response getAllSpecialLeaveTypes() throws CustomException {
        logger.info("Read all special leave types");
        return Response.ok().entity(referenceService.getAllLeaveTypes(
                Constants.SPECIAL_LEAVE_TYPE)).build();
    }

    @GET
    @Path("/leave_request")
    public Response getAllLeaveRequestTypes() throws CustomException {
        logger.info("Read all leave request types");
        return Response.ok().entity(referenceService.getAllLeaveRequestTypes()).build();
    }

    @GET
    @Path("/holiday_type")
    public Response getAllHolidayTypes() throws CustomException {
        logger.info("Read all holiday types");
        return Response.ok().entity(referenceService.getAllHolidayTypes()).build();
    }
}
