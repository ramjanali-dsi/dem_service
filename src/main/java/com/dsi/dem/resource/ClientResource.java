package com.dsi.dem.resource;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Client;
import com.dsi.dem.util.Utility;
import com.wordnik.swagger.annotations.Api;
import com.wordnik.swagger.annotations.ApiOperation;
import com.wordnik.swagger.annotations.ApiResponse;
import com.wordnik.swagger.annotations.ApiResponses;
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

    @POST
    @ApiOperation(value = "Create Client", notes = "Create Client", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Client create success"),
            @ApiResponse(code = 500, message = "Client create failed, unauthorized.")
    })
    public Response createClient(Client client) throws CustomException {

        return null;
    }
    
    @PUT
    @Path("/{client_id}")
    @ApiOperation(value = "Update Client", notes = "Update Client", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Client update success"),
            @ApiResponse(code = 500, message = "Client update failed, unauthorized.")
    })
    public Response updateClient(@PathParam("client_id") String clientID,
                                 Client client) throws CustomException {
        
        return null;
    }
    
    @DELETE
    @Path("/{client_id}")
    @ApiOperation(value = "Delete Client", notes = "Delete Client", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Client delete success"),
            @ApiResponse(code = 500, message = "Client delete failed, unauthorized.")
    })
    public Response deleteClient(@PathParam("client_id") String clientID) throws CustomException {
        
        return null;
    }
    
    @GET
    @Path("/{client_id}")
    @ApiOperation(value = "Read Client Or All Clients", notes = "Read Client Or All Clients", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read client or all clients success"),
            @ApiResponse(code = 500, message = "Read client or all clients failed, unauthorized.")
    })
    public Response readClientOrAllClients(@PathParam("client_id") String clientID) throws CustomException {
        
        if(!Utility.isNullOrEmpty(clientID)){
            //TODO read a client
            
        } else {
            //TODO read all clients
        }
        return null;
    }

    @GET
    @Path("/search")
    @ApiOperation(value = "Search Client Or All Clients", notes = "Search Client Or All Clients", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search client or all clients success"),
            @ApiResponse(code = 500, message = "Search client or all clients failed, unauthorized.")
    })
    public Response searchClientOrAllClients(@QueryParam("search_text") String searchText) throws CustomException {

        if(!Utility.isNullOrEmpty(searchText)){
            //TODO search a client

        }
        return null;
    }
}
