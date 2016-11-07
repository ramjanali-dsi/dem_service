package com.dsi.dem.resource;

import com.dsi.dem.dto.ClientDto;
import com.dsi.dem.dto.ProjectDto;
import com.dsi.dem.dto.transformer.ClientDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.service.ClientService;
import com.dsi.dem.service.impl.ClientServiceImpl;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.Utility;
import com.wordnik.swagger.annotations.*;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sabbir on 8/9/16.
 */

@Path("/v1/client/{client_id}/project")
@Api(value = "/ClientProject", description = "Operations about Client Project")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ClientProjectResource {

    private static final Logger logger = Logger.getLogger(ClientProjectResource.class);

    private static final ClientDtoTransformer TRANSFORMER = new ClientDtoTransformer();
    private static final ClientService clientService = new ClientServiceImpl();

    @POST
    @ApiOperation(value = "Create Client Project", notes = "Create Client Project", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Client project create success"),
            @ApiResponse(code = 500, message = "Client project create failed, unauthorized.")
    })
    public Response createProjectClient(@PathParam("client_id") String clientID,
                                        @ApiParam(value = "Client Dto", required = true) ClientDto clientDto)
            throws CustomException {

        logger.info("Client project create:: start");
        if(Utility.isNullOrEmpty(clientDto.getProjectIds())){
            //ErrorContext errorContext = new ErrorContext(null, "ClientProject", "Client project list not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }

        clientService.saveClientProject(clientDto.getProjectIds(), clientService.getClientByID(clientID));
        logger.info("Client project create:: end");

        return Response.ok().entity(TRANSFORMER.getClientProjectsDto(clientService.getClientProjects(clientID))).build();
    }

    @DELETE
    @Path("/{project_id}")
    @ApiOperation(value = "Delete Client Project", notes = "Delete Client Project", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Client project delete success"),
            @ApiResponse(code = 500, message = "Client project delete failed, unauthorized.")
    })
    public Response deleteProjectClient(@PathParam("client_id") String clientID,
                                        @PathParam("project_id") String projectID) throws CustomException {

        logger.info("Client project delete:: start");
        clientService.deleteClientProject(clientID);
        logger.info("Project client delete:: end");

        return null;
    }
}
