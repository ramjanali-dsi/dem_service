package com.dsi.dem.resource;

import com.dsi.dem.dto.TeamDto;
import com.dsi.dem.dto.TeamMemberDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.RoleType;
import com.dsi.dem.model.TeamMember;
import com.dsi.dem.service.TeamService;
import com.dsi.dem.service.impl.APIProvider;
import com.dsi.dem.service.impl.TeamServiceImpl;
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

@Path("/v1/team")
@Api(value = "/Team", description = "Operations about Team Management")
@Produces({MediaType.APPLICATION_JSON})
@Consumes({MediaType.APPLICATION_JSON})
public class TeamResource {

    private static final Logger logger = Logger.getLogger(TeamResource.class);

    private static final TeamService teamService = new TeamServiceImpl();
    private static final HttpClient httpClient = new HttpClient();

    @Context
    HttpServletRequest request;

    @POST
    @ApiOperation(value = "Create Team", notes = "Create Team", position = 1)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Team create success"),
            @ApiResponse(code = 500, message = "Team create failed, unauthorized.")
    })
    public Response createTeam(@ApiParam(value = "Team Dto", required = true) TeamDto teamDto) throws CustomException {

        /*String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        JSONObject resultObj, contentObj;
        String result;

        try{
            TeamDto finalTeamDto = teamService.saveTeam(teamDto);

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
            contentObj.put("TeamName", finalTeamDto.getName());

            for(TeamMemberDto memberDto : finalTeamDto.getMemberList()){
                if(memberDto.getRoleName().equals(NotificationConstant.LEAD_ROLE_TYPE)){
                    contentObj.put("LeadFirstName", memberDto.getFirstName());
                    contentObj.put("LeadLastName", memberDto.getLastName());

                    break;
                }
            }
            contentObj.put("TenantName", tenantName);

            logger.info("Request body for notification create: " + Utility.getNotificationObject(contentObj,
                    NotificationConstant.TEAM_CREATE_TEMPLATE_ID));

            result = httpClient.sendPost(APIProvider.API_NOTIFICATION_CREATE, Utility.getNotificationObject(contentObj,
                    NotificationConstant.TEAM_CREATE_TEMPLATE_ID), Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

            resultObj = new JSONObject(result);
            if(!resultObj.has(Constants.MESSAGE)){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result).build();
            }

            return Response.ok().entity(finalTeamDto).build();

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }*/

        return Response.ok().entity(teamService.saveTeam(teamDto)).build();
    }

    @PUT
    @Path("/{team_id}")
    @ApiOperation(value = "Update Team", notes = "Update Team", position = 2)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Team update success"),
            @ApiResponse(code = 500, message = "Team update failed, unauthorized.")
    })
    public Response updateTeam(@PathParam("team_id") String teamID,
                               @ApiParam(value = "Team Dto", required = true) TeamDto teamDto) throws CustomException {

        /*String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        JSONObject resultObj, contentObj;
        String result;

        try{
            TeamDto finalTeamDto = teamService.updateTeam(teamDto, teamID);

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
            contentObj.put("TeamName", finalTeamDto.getName());

            for(TeamMemberDto memberDto : finalTeamDto.getMemberList()){
                if(memberDto.getRoleName().equals(NotificationConstant.LEAD_ROLE_TYPE)){
                    contentObj.put("LeadFirstName", memberDto.getFirstName());
                    contentObj.put("LeadLastName", memberDto.getLastName());

                    break;
                }
            }
            contentObj.put("TenantName", tenantName);

            logger.info("Request body for notification create: " + Utility.getNotificationObject(contentObj,
                    NotificationConstant.TEAM_UPDATE_TEMPLATE_ID));

            result = httpClient.sendPost(APIProvider.API_NOTIFICATION_CREATE, Utility.getNotificationObject(contentObj,
                    NotificationConstant.TEAM_UPDATE_TEMPLATE_ID), Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

            resultObj = new JSONObject(result);
            if(!resultObj.has(Constants.MESSAGE)){
                return Response.status(Response.Status.INTERNAL_SERVER_ERROR).entity(result).build();
            }

            return Response.ok().entity(finalTeamDto).build();

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }*/

        return Response.ok().entity(teamService.updateTeam(teamDto, teamID)).build();
    }

    @DELETE
    @Path("/{team_id}")
    @ApiOperation(value = "Delete Team", notes = "Delete Team", position = 3)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Team delete success"),
            @ApiResponse(code = 500, message = "Team delete failed, unauthorized.")
    })
    public Response deleteTeam(@PathParam("team_id") String teamID) throws CustomException {

        /*String tenantName = request.getAttribute("tenant_name") != null ?
                request.getAttribute("tenant_name").toString() : null;

        JSONObject resultObj, contentObj;
        String result;

        try{
            TeamDto finalTeamDto = teamService.getTeamByID(teamID);

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
            contentObj.put("TeamName", finalTeamDto.getName());

            for(TeamMemberDto memberDto : finalTeamDto.getMemberList()){
                if(memberDto.getRoleName().equals(NotificationConstant.LEAD_ROLE_TYPE)){
                    contentObj.put("LeadFirstName", memberDto.getFirstName());
                    contentObj.put("LeadLastName", memberDto.getLastName());

                    break;
                }
            }
            contentObj.put("TenantName", tenantName);

            teamService.deleteTeam(teamID);

            logger.info("Request body for notification create: " + Utility.getNotificationObject(contentObj,
                    NotificationConstant.TEAM_DELETE_TEMPLATE_ID));

            result = httpClient.sendPost(APIProvider.API_NOTIFICATION_CREATE, Utility.getNotificationObject(contentObj,
                    NotificationConstant.TEAM_DELETE_TEMPLATE_ID), Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

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

        teamService.deleteTeam(teamID);
        return Response.ok().entity(null).build();
    }

    @GET
    @Path("/{team_id}")
    @ApiOperation(value = "Read Team", notes = "Read Team", position = 4)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Read team success"),
            @ApiResponse(code = 500, message = "read team failed, unauthorized.")
    })
    public Response readTeamOrAllTeams(@PathParam("team_id") String teamID) throws CustomException {

        return Response.ok().entity(teamService.getTeamByID(teamID)).build();
    }

    @GET
    @ApiOperation(value = "Search Or Read All Teams", notes = "Search Or Read All Teams", position = 5)
    @ApiResponses(value = {
            @ApiResponse(code = 200, message = "Search or read all teams success"),
            @ApiResponse(code = 500, message = "Search or read all teams failed, unauthorized.")
    })
    public Response searchTeamOrAllTeams(@QueryParam("teamName") String teamName,
                                         @QueryParam("status") String status,
                                         @QueryParam("floor") String floor,
                                         @QueryParam("room") String room,
                                         @QueryParam("memberName") String memberName,
                                         @QueryParam("projectName") String projectName,
                                         @QueryParam("clientName") String clientName,
                                         @QueryParam("from") String from,
                                         @QueryParam("range") String range) throws CustomException {

        return Response.ok().entity(teamService.searchTeams(teamName, status, floor, room, memberName,
                projectName, clientName, from, range)).build();
    }
}
