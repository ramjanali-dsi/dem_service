package com.dsi.dem.resource;

import com.dsi.dem.dto.ClientDto;
import com.dsi.dem.dto.ClientProjectDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.service.ClientService;
import com.dsi.dem.service.impl.APIProvider;
import com.dsi.dem.service.impl.ClientServiceImpl;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.NotificationConstant;
import com.dsi.dem.util.Utility;
import com.dsi.httpclient.HttpClient;
import com.wordnik.swagger.annotations.*;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
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

    private static final ClientService clientService = new ClientServiceImpl();
    private static final HttpClient httpClient = new HttpClient();

    @Context
    HttpServletRequest request;

    @POST
    @ApiOperation(value = "Create Client", notes = "Create Client", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Client create success"),
            @ApiResponse(code = 500, message = "Client create failed, unauthorized.")
    })
    public Response createClient(@ApiParam(value = "Client Dto", required = true) ClientDto clientDto)
            throws CustomException {

        /*String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        JSONObject contentObj, resultObj;
        String result;
        try{
            ClientDto finalClientDto = clientService.saveClient(clientDto);

            JSONArray recipients = new JSONArray();

            logger.info("Get HR email list.");
            result = httpClient.getRequest(APIProvider.API_USER_ROLE + NotificationConstant.HR_ROLE_TYPE,
                    Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

            JSONArray resultArray = new JSONArray(result);
            if(resultArray.length() > 0){
                recipients.put(resultArray);
            }

            logger.info("Get Manager email list.");
            result = httpClient.getRequest(APIProvider.API_USER_ROLE + NotificationConstant.MANAGER_ROLE_TYPE,
                    Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

            resultArray = new JSONArray(result);
            if(resultArray.length() > 0){
                recipients.put(resultArray);
            }

            contentObj = new JSONObject();
            contentObj.put("Recipient", recipients);
            contentObj.put("ClientName", finalClientDto.getMemberName());

            String projectNames = "";
            for(int i=0; i<finalClientDto.getProjectList().size(); i++) {
                projectNames += clientDto.getProjectList().get(i).getProjectName();
                if (i != clientDto.getProjectList().size() - 1) {
                    projectNames += ",";
                }
            }
            contentObj.put("ProjectName", projectNames);
            contentObj.put("TenantName", tenantName);

            logger.info("Request body for notification create: " + Utility.getNotificationObject(contentObj,
                    NotificationConstant.CLIENT_CREATE_TEMPLATE_ID));

            result = httpClient.sendPost(APIProvider.API_NOTIFICATION_CREATE, Utility.getNotificationObject(contentObj,
                    NotificationConstant.CLIENT_CREATE_TEMPLATE_ID), Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

            resultObj = new JSONObject(result);
            if(!resultObj.has(Constants.MESSAGE)){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result).build();
            }

            return Response.ok().entity(finalClientDto).build();

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }*/

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

        /*String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        JSONObject contentObj, resultObj;
        String result;

        try{
            ClientDto finalClientDto = clientService.updateClient(clientDto, clientID);

            JSONArray recipients = new JSONArray();

            logger.info("Get HR email list.");
            result = httpClient.getRequest(APIProvider.API_USER_ROLE + NotificationConstant.HR_ROLE_TYPE,
                    Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

            JSONArray resultArray = new JSONArray(result);
            if(resultArray.length() > 0){
                recipients.put(resultArray);
            }

            logger.info("Get Manager email list.");
            result = httpClient.getRequest(APIProvider.API_USER_ROLE + NotificationConstant.MANAGER_ROLE_TYPE,
                    Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

            resultArray = new JSONArray(result);
            if(resultArray.length() > 0){
                recipients.put(resultArray);
            }

            contentObj = new JSONObject();
            contentObj.put("Recipient", recipients);
            contentObj.put("ClientName", finalClientDto.getMemberName());

            String projectNames = "";
            for(int i=0; i<finalClientDto.getProjectList().size(); i++) {
                projectNames += clientDto.getProjectList().get(i).getProjectName();
                if (i != clientDto.getProjectList().size() - 1) {
                    projectNames += ",";
                }
            }
            contentObj.put("ProjectName", projectNames);
            contentObj.put("TenantName", tenantName);

            logger.info("Request body for notification create: " + Utility.getNotificationObject(contentObj,
                    NotificationConstant.CLIENT_UPDATE_TEMPLATE_ID));

            result = httpClient.sendPost(APIProvider.API_NOTIFICATION_CREATE, Utility.getNotificationObject(contentObj,
                    NotificationConstant.CLIENT_UPDATE_TEMPLATE_ID), Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

            resultObj = new JSONObject(result);
            if(!resultObj.has(Constants.MESSAGE)){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result).build();
            }

            return Response.ok().entity(finalClientDto).build();

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }*/

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

        /*String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        JSONObject contentObj, resultObj;
        String result;

        try{
            ClientDto clientDto = clientService.getClientByID(clientID);

            JSONArray recipients = new JSONArray();

            logger.info("Get HR email list.");
            result = httpClient.getRequest(APIProvider.API_USER_ROLE + NotificationConstant.HR_ROLE_TYPE,
                    Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

            JSONArray resultArray = new JSONArray(result);
            if(resultArray.length() > 0){
                recipients.put(resultArray);
            }

            logger.info("Get Manager email list.");
            result = httpClient.getRequest(APIProvider.API_USER_ROLE + NotificationConstant.MANAGER_ROLE_TYPE,
                    Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

            resultArray = new JSONArray(result);
            if(resultArray.length() > 0){
                recipients.put(resultArray);
            }

            contentObj = new JSONObject();
            contentObj.put("Recipient", recipients);
            contentObj.put("ClientName", clientDto.getMemberName());

            String projectNames = "";
            for(int i=0; i<clientDto.getProjectList().size(); i++) {
                projectNames += clientDto.getProjectList().get(i).getProjectName();
                if (i != clientDto.getProjectList().size() - 1) {
                    projectNames += ",";
                }
            }
            contentObj.put("ProjectName", projectNames);
            contentObj.put("TenantName", tenantName);

            clientService.deleteClient(clientID);

            logger.info("Request body for notification create: " + Utility.getNotificationObject(contentObj,
                    NotificationConstant.CLIENT_DELETE_TEMPLATE_ID));

            result = httpClient.sendPost(APIProvider.API_NOTIFICATION_CREATE, Utility.getNotificationObject(contentObj,
                    NotificationConstant.CLIENT_DELETE_TEMPLATE_ID), Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

            resultObj = new JSONObject(result);
            if(!resultObj.has(Constants.MESSAGE)){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result).build();
            }

            return Response.ok().entity(null).build();

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }*/

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
