package com.dsi.dem.resource;

import com.dsi.dem.dto.ClientDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.service.ClientService;
import com.dsi.dem.service.impl.ClientServiceImpl;
import com.wordnik.swagger.annotations.*;

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

    private static final ClientService clientService = new ClientServiceImpl();

    @POST
    @ApiOperation(value = "Create Client", notes = "Create Client", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Client create success"),
            @ApiResponse(code = 500, message = "Client create failed, unauthorized.")
    })
    public Response createClient(@ApiParam(value = "Client Dto", required = true) ClientDto clientDto)
            throws CustomException {

        return Response.ok().entity(clientService.saveClient(clientDto)).build();
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

        return Response.ok().entity(clientService.updateClient(clientDto, clientID)).build();
    }
    
    @DELETE
    @Path("/{client_id}")
    @ApiOperation(value = "Delete Client", notes = "Delete Client", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Client delete success"),
            @ApiResponse(code = 500, message = "Client delete failed, unauthorized.")
    })
    public Response deleteClient(@PathParam("client_id") String clientID) throws CustomException {

        clientService.deleteClient(clientID);
        return Response.ok().entity(null).build();
    }
    
    @GET
    @Path("/{client_id}")
    @ApiOperation(value = "Read Client", notes = "Read Client", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read client success"),
            @ApiResponse(code = 500, message = "Read client failed, unauthorized.")
    })
    public Response readClientOrAllClients(@PathParam("client_id") String clientID) throws CustomException {

        return Response.ok().entity(clientService.getClientByID(clientID)).build();
    }

    @GET
    @ApiOperation(value = "Search Or Read All Clients", notes = "Search Or Read All Clients", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search or read all clients success"),
            @ApiResponse(code = 500, message = "Search or read all clients failed, unauthorized.")
    })
    public Response searchClientOrAllClients(@QueryParam("clientName") String clientName,
                                             @QueryParam("organization") String organization,
                                             @QueryParam("clientEmail") String clientEmail,
                                             @QueryParam("from") String from,
                                             @QueryParam("range") String range) throws CustomException {

        return Response.ok().entity(clientService.searchClients(clientName, organization,
                clientEmail, from, range)).build();
    }
}
