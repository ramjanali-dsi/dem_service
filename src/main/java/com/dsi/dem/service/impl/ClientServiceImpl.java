package com.dsi.dem.service.impl;

import com.dsi.dem.dao.ClientDao;
import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.dao.ProjectDao;
import com.dsi.dem.dao.impl.ClientDaoImpl;
import com.dsi.dem.dao.impl.EmployeeDaoImpl;
import com.dsi.dem.dao.impl.ProjectDaoImpl;
import com.dsi.dem.dto.ClientDto;
import com.dsi.dem.dto.ClientProjectDto;
import com.dsi.dem.dto.transformer.ClientDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Client;
import com.dsi.dem.model.Project;
import com.dsi.dem.model.ProjectClient;
import com.dsi.dem.model.TeamMember;
import com.dsi.dem.service.ClientService;
import com.dsi.dem.service.NotificationService;
import com.dsi.dem.util.*;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 8/1/16.
 */
public class ClientServiceImpl extends CommonService implements ClientService {

    private static final Logger logger = Logger.getLogger(ClientServiceImpl.class);

    private static final ClientDtoTransformer TRANSFORMER = new ClientDtoTransformer();
    private static final ClientDao clientDao = new ClientDaoImpl();
    private static final ProjectDao projectDao = new ProjectDaoImpl();
    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();
    private static final NotificationService notificationService = new NotificationServiceImpl();

    @Override
    public ClientDto saveClient(ClientDto clientDto, String tenantName) throws CustomException {
        logger.info("Client create:: Start");
        logger.info("Convert Client Dto to Client Object");
        Client client = TRANSFORMER.getClient(clientDto);
        validateInputForCreation(client);

        Session session = getSession();
        clientDao.setSession(session);
        projectDao.setSession(session);

        if(clientDao.getClientByName(client.getMemberName()) != null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0003);
            throw new CustomException(errorMessage);
        }
        client.setVersion(1);
        clientDao.saveClient(client);
        logger.info("Save client success");

        for(ProjectClient projectClient : client.getProjects()){
            projectClient.setProject(projectDao.getProjectByID(projectClient.getProject().getProjectId()));
            projectClient.setClient(client);
            projectClient.setVersion(1);
            clientDao.saveClientProject(projectClient);
        }
        logger.info("Save client project success");

        setAllClientProperty(client);
        logger.info("Client create:: End");
        close(session);

        String projectNames = "";
        for(int i=0; i<client.getProjects().size(); i++) {
            projectNames += client.getProjects().get(i).getProject().getProjectName();
            if (i != client.getProjects().size() - 1) {
                projectNames += ",";
            }
        }

        try{
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            JSONArray emailList = notificationService.getHrManagerEmailList();
            JSONObject globalContentObj = EmailContent.getContentForClient(client, projectNames, tenantName, emailList);
            notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                    NotificationConstant.CLIENT_CREATE_TEMPLATE_ID));

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }

        return TRANSFORMER.getClientDto(client);
    }

    private void validateInputForCreation(Client client) throws CustomException {
        if(client.getMemberName() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0004);
            throw new CustomException(errorMessage);
        }

        if(client.getMemberEmail() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0005);
            throw new CustomException(errorMessage);
        }

        if(client.getOrganization() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0006);
            throw new CustomException(errorMessage);
        }

        if(client.getMemberPosition() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0007);
            throw new CustomException(errorMessage);
        }

        if(Utility.isNullOrEmpty(client.getProjects())){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public ClientDto updateClient(ClientDto clientDto, String clientId, String tenantName) throws CustomException {
        logger.info("Client Update:: Start");
        logger.info("Convert Client Dto to Client Object");
        Client client = TRANSFORMER.getClient(clientDto);

        validateInputForUpdate(client);

        Session session = getSession();
        clientDao.setSession(session);

        client.setClientId(clientId);
        if(clientDao.getClientByID(client.getClientId()) == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }

        Client existClient = clientDao.getClientByID(client.getClientId());

        existClient.setMemberName(client.getMemberName());
        existClient.setMemberEmail(client.getMemberEmail());
        existClient.setMemberPosition(client.getMemberPosition());
        existClient.setOrganization(client.getOrganization());
        existClient.setNotify(client.isNotify());
        existClient.setVersion(client.getVersion());
        clientDao.updateClient(existClient);
        logger.info("Update client success");

        setAllClientProperty(existClient);
        logger.info("Client Update:: End");
        close(session);

        String projectNames = "";
        for(int i=0; i<client.getProjects().size(); i++) {
            projectNames += client.getProjects().get(i).getProject().getProjectName();
            if (i != client.getProjects().size() - 1) {
                projectNames += ",";
            }
        }

        try{
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            JSONArray emailList = notificationService.getHrManagerEmailList();
            JSONObject globalContentObj = EmailContent.getContentForClient(client, projectNames, tenantName, emailList);
            notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                    NotificationConstant.CLIENT_UPDATE_TEMPLATE_ID));

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }

        return TRANSFORMER.getClientDto(existClient);
    }

    private void validateInputForUpdate(Client client) throws CustomException {
        if(client.getVersion() == 0){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_002);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public String deleteClient(String clientID, String tenantName) throws CustomException {
        logger.info("Client delete:: Start");
        Session session = getSession();
        clientDao.setSession(session);

        /*clientDao.deleteClientProject(clientID, null);
        clientDao.deleteClient(clientID);
        logger.info("Delete client success");
        logger.info("Client delete:: End");
        close(session);*/

        Client client = clientDao.getClientByID(clientID);
        setAllClientProperty(client);

        String projectNames = "";
        for(int i=0; i<client.getProjects().size(); i++) {
            projectNames += client.getProjects().get(i).getProject().getProjectName();
            if (i != client.getProjects().size() - 1) {
                projectNames += ",";
            }
        }

        try{
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            JSONArray emailList = notificationService.getHrManagerEmailList();
            JSONObject globalContentObj = EmailContent.getContentForClient(client, projectNames, tenantName, emailList);
            notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                    NotificationConstant.CLIENT_DELETE_TEMPLATE_ID));

            clientDao.deleteClientProject(clientID, null);
            clientDao.deleteClient(clientID);
            logger.info("Delete client success");
            logger.info("Client delete:: End");
            close(session);

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }

        return null;
    }

    @Override
    public ClientDto getClientByID(String clientID) throws CustomException {
        logger.info("Read client:: Start");
        Session session = getSession();
        clientDao.setSession(session);

        Client client = clientDao.getClientByID(clientID);
        if(client == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        setAllClientProperty(client);
        logger.info("Read client:: End");

        close(session);
        return TRANSFORMER.getClientDto(client);
    }

    @Override
    public List<Client> getAllClients() throws CustomException {
        Session session = getSession();
        clientDao.setSession(session);

        List<Client> clients = clientDao.getAllClients();
        if(clients == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        List<Client> clientList = new ArrayList<>();
        for(Client client : clients){
            clientList.add(setAllClientProperty(client));
        }

        close(session);
        return clientList;
    }

    @Override
    public List<ClientDto> searchClients(String clientName, String organization, String clientEmail,
                                      String from, String range) throws CustomException {

        logger.info("Read all client:: Start");
        Session session = getSession();
        clientDao.setSession(session);

        List<Client> clients = clientDao.searchClients(clientName, organization, clientEmail, from, range);
        if(clients == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        logger.info("Client list size: " + clients.size());

        List<Client> clientList = new ArrayList<>();
        for(Client client : clients){
            clientList.add(setAllClientProperty(client));
        }
        logger.info("Read all client:: End");

        close(session);
        return TRANSFORMER.getClientsDto(clientList);
    }

    @Override
    public List<ClientProjectDto> createClientProjects(String clientId, List<ClientProjectDto> clientProjectDtoList,
                                                       String tenantName) throws CustomException {
        logger.info("Convert ClientProject Dto to ProjectClient Object");
        List<ProjectClient> projectClients = TRANSFORMER.getClientProjects(clientProjectDtoList);
        if(Utility.isNullOrEmpty(projectClients)){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }

        Session session = getSession();
        clientDao.setSession(session);
        projectDao.setSession(session);
        Client client = clientDao.getClientByID(clientId);

        List<ProjectClient> assignedProjectClient = new ArrayList<>();
        List<ProjectClient> unassignedProjectClient = new ArrayList<>();

        for(ProjectClient projectClient : projectClients){

            switch (projectClient.getActivity()){
                case 1:
                    logger.info("Client project create:: Start");
                    validateInput(projectClient, session);

                    saveClientProject(projectClient, client);
                    logger.info("Client project create:: End");

                    projectClient.setClient(client);
                    assignedProjectClient.add(projectClient);
                    break;

                case 2:
                    logger.info("Delete client project:: Start");
                    validateInput(projectClient, session);

                    projectDao.deleteProjectClientByClientId(projectClient.getProject().getProjectId(), clientId);
                    logger.info("Delete client project:: End");

                    projectClient.setClient(client);
                    unassignedProjectClient.add(projectClient);
                    break;
            }
        }
        List<ProjectClient> clientProjects = clientDao.getClientProjects(clientId);

        JSONArray memberEmails;
        JSONObject contentObj;
        try {

            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            JSONArray hrManagerEmailList = notificationService.getHrManagerEmailList();

            if(!Utility.isNullOrEmpty(assignedProjectClient)) {
                for (ProjectClient assignedClient : assignedProjectClient) {

                    memberEmails = new JSONArray();
                    List<TeamMember> teamMembers = projectDao.getTeamMembersByProjectId(assignedClient.getProject().getProjectId());
                    if(!Utility.isNullOrEmpty(teamMembers)){
                        for(TeamMember member : teamMembers){
                            memberEmails.put(employeeDao.getEmployeeEmailsByEmployeeID(member.getEmployee().getEmployeeId())
                                    .get(0).getEmail());
                        }
                    }

                    contentObj = EmailContent.getContentForProjectClientAssignUnAssign(assignedClient, tenantName, hrManagerEmailList);
                    notificationList.put(EmailContent.getNotificationObject(contentObj,
                            NotificationConstant.PROJECT_CLIENT_ASSIGNED_TEMPLATE_ID_FOR_MANAGER_HR));

                    if(memberEmails.length() > 0) {
                        contentObj = EmailContent.getContentForProjectClientAssignUnAssign(assignedClient, tenantName, memberEmails);
                        notificationList.put(EmailContent.getNotificationObject(contentObj,
                                NotificationConstant.PROJECT_CLIENT_ASSIGNED_TEMPLATE_ID_FOR_MEMBERS));
                    }

                    contentObj = EmailContent.getContentForProjectClientAssignUnAssign(assignedClient, tenantName,
                            new JSONArray().put(assignedClient.getClient().getMemberEmail()));
                    notificationList.put(EmailContent.getNotificationObject(contentObj,
                            NotificationConstant.PROJECT_CLIENT_ASSIGNED_TEMPLATE_ID_FOR_CLIENT));

                }
            }

            if(!Utility.isNullOrEmpty(unassignedProjectClient)){
                for(ProjectClient unassignedClient : unassignedProjectClient){

                    memberEmails = new JSONArray();
                    List<TeamMember> teamMembers = projectDao.getTeamMembersByProjectId(unassignedClient.getProject().getProjectId());
                    if(!Utility.isNullOrEmpty(teamMembers)){
                        for(TeamMember member : teamMembers){
                            memberEmails.put(employeeDao.getEmployeeEmailsByEmployeeID(member.getEmployee().getEmployeeId())
                                    .get(0).getEmail());
                        }
                    }

                    contentObj = EmailContent.getContentForProjectClientAssignUnAssign(unassignedClient, tenantName, hrManagerEmailList);
                    notificationList.put(EmailContent.getNotificationObject(contentObj,
                            NotificationConstant.PROJECT_CLIENT_UNASSIGNED_TEMPLATE_ID_FOR_MANAGER_HR));

                    if(memberEmails.length() > 0) {
                        contentObj = EmailContent.getContentForProjectClientAssignUnAssign(unassignedClient, tenantName, memberEmails);
                        notificationList.put(EmailContent.getNotificationObject(contentObj,
                                NotificationConstant.PROJECT_CLIENT_UNASSIGNED_TEMPLATE_ID_FOR_MEMBERS));
                    }

                    /*contentObj = EmailContent.getContentForProjectClientAssignUnAssign(unassignedClient,null,
                            new JSONArray().put(unassignedClient.getClient().getMemberEmail()));
                    notificationList.put(EmailContent.getNotificationObject(contentObj,
                            NotificationConstant.PROJECT_CLIENT_UNASSIGNED_TEMPLATE_ID_FOR_CLIENT));*/

                }
            }

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }

        close(session);
        return TRANSFORMER.getClientProjectsDto(clientProjects);
    }

    private void validateInput(ProjectClient projectClient, Session session) throws CustomException {
        if(projectClient.getProject().getProjectId() == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }
    }

    private void saveClientProject(ProjectClient projectClient, Client client) throws CustomException {
        ProjectClient existClientProject = projectDao.getProjectClientByClientIdAndProjectId(client.getClientId(),
                projectClient.getProject().getProjectId());
        if(existClientProject == null){
            projectClient.setProject(projectDao.getProjectByID(projectClient.getProject().getProjectId()));
            projectClient.setClient(client);
            projectClient.setVersion(1);
            clientDao.saveClientProject(projectClient);
            logger.info("Save client projects success");
        }
    }

    @Override
    public void deleteClientProject(String clientProjectID) throws CustomException {
        logger.info("Client project delete:: Start");
        Session session = getSession();
        clientDao.setSession(session);

        clientDao.deleteClientProject(null, clientProjectID);
        logger.info("Delete client project success");
        logger.info("Project client delete:: End");

        close(session);
    }

    @Override
    public List<ProjectClient> getClientProjects(String clientID) throws CustomException {
        Session session = getSession();
        clientDao.setSession(session);

        List<ProjectClient> projectClientList = clientDao.getClientProjects(clientID);
        if(projectClientList == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        close(session);
        return projectClientList;
    }

    private Client setAllClientProperty(Client client) throws CustomException {

        List<ProjectClient> projectClients = clientDao.getClientProjects(client.getClientId());
        if(!Utility.isNullOrEmpty(projectClients)){
            client.setProjects(projectClients);
        }
        return client;
    }
}
