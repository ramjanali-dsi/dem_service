package com.dsi.dem.service.impl;

import com.dsi.dem.dao.ClientDao;
import com.dsi.dem.dao.ProjectDao;
import com.dsi.dem.dao.impl.ClientDaoImpl;
import com.dsi.dem.dao.impl.ProjectDaoImpl;
import com.dsi.dem.dto.ClientDto;
import com.dsi.dem.dto.ClientProjectDto;
import com.dsi.dem.dto.transformer.ClientDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Client;
import com.dsi.dem.model.Project;
import com.dsi.dem.model.ProjectClient;
import com.dsi.dem.service.ClientService;
import com.dsi.dem.util.*;
import com.dsi.httpclient.HttpClient;
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

    private static final HttpClient httpClient = new HttpClient();

    @Override
    public ClientDto saveClient(ClientDto clientDto, String tenantName) throws CustomException {
        logger.info("Client create:: Start");
        if(Utility.isNullOrEmpty(clientDto.getProjectIds())){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }

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

        clientDao.saveClient(client);
        logger.info("Save client success");

        saveClientProject(clientDto.getProjectIds(), client);
        setAllClientProperty(client);
        logger.info("Client create:: End");
        close(session);

        /*String projectNames = "";
        for(int i=0; i<client.getProjects().size(); i++) {
            projectNames += client.getProjects().get(i).getProject().getProjectName();
            if (i != client.getProjects().size() - 1) {
                projectNames += ",";
            }
        }

        try{
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            JSONArray emailList = new JSONArray();
            //TODO Manager & HR email config

            JSONObject globalContentObj = EmailBodyTemplate.getContentForClient(client, projectNames, tenantName, emailList);
            notificationList.put(EmailBodyTemplate.getNotificationObject(globalContentObj,
                    NotificationConstant.CLIENT_CREATE_TEMPLATE_ID));

            logger.info("Notification create request body :: " + notificationList.toString());
            String result = httpClient.sendPost(APIProvider.API_NOTIFICATION_CREATE, notificationList.toString(),
                    Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

            JSONObject resultObj = new JSONObject(result);
            if(!resultObj.has(Constants.MESSAGE)){
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0009,
                        Constants.DEM_SERVICE_0009_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_010);
                throw new CustomException(errorMessage);
            }
            logger.info("Notification create:: End");

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }*/

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

        /*String projectNames = "";
        for(int i=0; i<client.getProjects().size(); i++) {
            projectNames += client.getProjects().get(i).getProject().getProjectName();
            if (i != client.getProjects().size() - 1) {
                projectNames += ",";
            }
        }

        try{
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            JSONArray emailList = new JSONArray();
            //TODO Manager & HR email config

            JSONObject globalContentObj = EmailBodyTemplate.getContentForClient(client, projectNames, tenantName, emailList);
            notificationList.put(EmailBodyTemplate.getNotificationObject(globalContentObj,
                    NotificationConstant.CLIENT_UPDATE_TEMPLATE_ID));

            logger.info("Notification create request body :: " + notificationList.toString());
            String result = httpClient.sendPost(APIProvider.API_NOTIFICATION_CREATE, notificationList.toString(),
                    Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

            JSONObject resultObj = new JSONObject(result);
            if(!resultObj.has(Constants.MESSAGE)){
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0009,
                        Constants.DEM_SERVICE_0009_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_010);
                throw new CustomException(errorMessage);
            }
            logger.info("Notification create:: End");

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }*/

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

        clientDao.deleteClientProject(clientID, null);
        clientDao.deleteClient(clientID);
        logger.info("Delete client success");
        logger.info("Client delete:: End");
        close(session);

        /*Client client = clientDao.getClientByID(clientID);
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

            JSONArray emailList = new JSONArray();
            //TODO Manager & HR email config

            JSONObject globalContentObj = EmailBodyTemplate.getContentForClient(client, projectNames, tenantName, emailList);
            notificationList.put(EmailBodyTemplate.getNotificationObject(globalContentObj,
                    NotificationConstant.CLIENT_DELETE_TEMPLATE_ID));

            clientDao.deleteClientProject(clientID, null);
            clientDao.deleteClient(clientID);
            logger.info("Delete client success");
            logger.info("Client delete:: End");
            close(session);

            logger.info("Notification create request body :: " + notificationList.toString());
            String result = httpClient.sendPost(APIProvider.API_NOTIFICATION_CREATE, notificationList.toString(),
                    Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

            JSONObject resultObj = new JSONObject(result);
            if(!resultObj.has(Constants.MESSAGE)){
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0009,
                        Constants.DEM_SERVICE_0009_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_010);
                throw new CustomException(errorMessage);
            }
            logger.info("Notification create:: End");

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }*/

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
    public List<ClientProjectDto> createClientProjects(String clientId, ClientDto clientDto) throws CustomException {
        logger.info("Client project create:: Start");
        if(Utility.isNullOrEmpty(clientDto.getProjectIds())){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }

        Session session = getSession();
        clientDao.setSession(session);
        projectDao.setSession(session);

        saveClientProject(clientDto.getProjectIds(), clientDao.getClientByID(clientId));
        List<ProjectClient> clientProjects = clientDao.getClientProjects(clientId);
        logger.info("Client project create:: End");

        close(session);
        return TRANSFORMER.getClientProjectsDto(clientProjects);
    }

    @Override
    public void saveClientProject(List<String> projectIds, Client client) throws CustomException {

        clientDao.deleteClientProject(client.getClientId(), null);
        logger.info("Delete all client projects.");

        for(String projectID : projectIds){
            Project project = projectDao.getProjectByID(projectID);
            ProjectClient projectClient = new ProjectClient();
            projectClient.setClient(client);
            projectClient.setProject(project);
            projectClient.setVersion(1);

            clientDao.saveClientProject(projectClient);
        }
        logger.info("Save client projects success");
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
