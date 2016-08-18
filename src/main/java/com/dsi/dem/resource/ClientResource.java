package com.dsi.dem.resource;

import com.dsi.dem.dto.ClientDto;
import com.dsi.dem.dto.transformer.ClientDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Client;
import com.dsi.dem.service.ClientService;
import com.dsi.dem.service.impl.ClientServiceImpl;
import com.dsi.dem.util.Utility;
import com.google.gson.Gson;
import com.wordnik.swagger.annotations.*;
import org.apache.log4j.Logger;

import javax.ws.rs.*;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 * Created by sabbir on 7/21/16.
 */

@Path("/v1/client")
@Api(value = "/Client", description = "Operations about Client Management")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class ClientResource {

    private static final Logger logger = Logger.getLogger(ClientResource.class);

    private static final ClientDtoTransformer TRANSFORMER = new ClientDtoTransformer();
    private static final ClientService clientService = new ClientServiceImpl();

    @POST
    @ApiOperation(value = "Create Client", notes = "Create Client", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Client create success"),
            @ApiResponse(code = 500, message = "Client create failed, unauthorized.")
    })
    public Response createClient(@ApiParam(value = "Client Dto", required = true) ClientDto clientDto)
            throws CustomException {

        logger.info("Convert Dto to Object:: Start");
        Client client = TRANSFORMER.getClient(clientDto);
        logger.info("Convert Dto to Object:: End");

        if(!Utility.isNullOrEmpty(clientDto.getProjectIds())) {
            logger.info("Create client:: start");
            clientService.saveClient(client);
            clientService.saveClientProject(clientDto.getProjectIds(), client);
        }
        logger.info("Create client:: end");

        return Response.ok().entity(TRANSFORMER.getClientDto(
                clientService.getClientByID(client.getClientId()))).build();
    }
    
    @PUT
    @Path("/{client_id}")
    @ApiOperation(value = "Update Client", notes = "Update Client", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Client update success"),
            @ApiResponse(code = 500, message = "Client update failed, unauthorized.")
    })
    public Response updateClient(@PathParam("client_id") String clientID,
                                 @ApiParam(value = "Client Dto", required = true) ClientDto clientDto)
            throws CustomException {

        logger.info("Convert Dto to Object:: Start");
        Client client = TRANSFORMER.getClient(clientDto);
        logger.info("Convert Dto to Object:: End");

        logger.info("Client Update:: Start");
        client.setClientId(clientID);
        clientService.updateClient(client);
        logger.info("Client Update:: End");

        return Response.ok().entity(TRANSFORMER.getClientDto(
                clientService.getClientByID(clientID))).build();
    }
    
    @DELETE
    @Path("/{client_id}")
    @ApiOperation(value = "Delete Client", notes = "Delete Client", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Client delete success"),
            @ApiResponse(code = 500, message = "Client delete failed, unauthorized.")
    })
    public Response deleteClient(@PathParam("client_id") String clientID) throws CustomException {

        logger.info("Client delete:: Start");
        clientService.deleteClient(clientID);
        logger.info("Client delete:: End");

        return Response.ok().entity("Success").build();
    }
    
    @GET
    @Path("/{client_id}")
    @ApiOperation(value = "Read Client", notes = "Read Client", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read client success"),
            @ApiResponse(code = 500, message = "Read client failed, unauthorized.")
    })
    public Response readClientOrAllClients(@PathParam("client_id") String clientID) throws CustomException {
        
        logger.info("Read a client");
        return Response.ok().entity(TRANSFORMER.getClientDto(
                clientService.getClientByID(clientID))).build();
    }

    @GET
    @ApiOperation(value = "Search Or Read All Clients", notes = "Search Or Read All Clients", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search or read all clients success"),
            @ApiResponse(code = 500, message = "Search or read all clients failed, unauthorized.")
    })
    public Response searchClientOrAllClients(@QueryParam("clientName") String clientName,
                                             @QueryParam("organization") String organization,
                                             @QueryParam("clientEmail") String clientEmail) throws CustomException {

        logger.info("Read all client");
        return Response.ok().entity(TRANSFORMER.getClientsDto(clientService.
                searchClients(clientName, organization, clientEmail))).build();
    }
}
