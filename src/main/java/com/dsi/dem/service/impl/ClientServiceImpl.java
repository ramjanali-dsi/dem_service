package com.dsi.dem.service.impl;

import com.dsi.dem.dao.ClientDao;
import com.dsi.dem.dao.ProjectDao;
import com.dsi.dem.dao.impl.ClientDaoImpl;
import com.dsi.dem.dao.impl.ProjectDaoImpl;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Client;
import com.dsi.dem.model.Project;
import com.dsi.dem.model.ProjectClient;
import com.dsi.dem.service.ClientService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.Utility;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 8/1/16.
 */
public class ClientServiceImpl implements ClientService {

    private static final Logger logger = Logger.getLogger(ClientServiceImpl.class);

    private static final ClientDao clientDao = new ClientDaoImpl();
    private static final ProjectDao projectDao = new ProjectDaoImpl();

    @Override
    public void saveClient(Client client) throws CustomException {
        validateInputForCreation(client);

        boolean res = clientDao.saveClient(client);
        if(!res){
            //ErrorContext errorContext = new ErrorContext(null, "Client", "Client create failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
        logger.info("Save client success");
    }

    private void validateInputForCreation(Client client) throws CustomException {
        if(client.getMemberName() == null){
            //ErrorContext errorContext = new ErrorContext(null, "Client", "Client member name not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0004);
            throw new CustomException(errorMessage);
        }

        if(client.getMemberEmail() == null){
            //ErrorContext errorContext = new ErrorContext(null, "Client", "Client member email not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0005);
            throw new CustomException(errorMessage);
        }

        if(client.getOrganization() == null){
            //ErrorContext errorContext = new ErrorContext(null, "Client", "Client organization not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0006);
            throw new CustomException(errorMessage);
        }

        if(client.getMemberPosition() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0007);
            throw new CustomException(errorMessage);
        }

        if(clientDao.getClientByName(client.getMemberName()) != null){
            //ErrorContext errorContext = new ErrorContext(client.getMemberName(), "Client", "Client already exist by this name.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0003);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateClient(Client client) throws CustomException {
        validateInputForUpdate(client);

        Client existClient = clientDao.getClientByID(client.getClientId());

        existClient.setMemberName(client.getMemberName());
        existClient.setMemberEmail(client.getMemberEmail());
        existClient.setMemberPosition(client.getMemberPosition());
        existClient.setOrganization(client.getOrganization());
        existClient.setVersion(client.getVersion());
        boolean res = clientDao.updateClient(existClient);
        if(!res){
            //ErrorContext errorContext = new ErrorContext(client.getClientId(), "Client", "Client update failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
        logger.info("Update client success");
    }

    private void validateInputForUpdate(Client client) throws CustomException {
        if(client.getVersion() == 0){
            //ErrorContext errorContext = new ErrorContext(null, "Client", "Version not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_002);
            throw new CustomException(errorMessage);
        }

        if(clientDao.getClientByID(client.getClientId()) == null){
            //ErrorContext errorContext = new ErrorContext(client.getClientId(), "Client", "Client not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteClient(String clientID) throws CustomException {
        clientDao.deleteClientProject(clientID, null);

        boolean res = clientDao.deleteClient(clientID);
        if(!res){
            //ErrorContext errorContext = new ErrorContext(clientID, "Client", "Client delete failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
        logger.info("Delete client success");
    }

    @Override
    public Client getClientByID(String clientID) throws CustomException {
        Client client = clientDao.getClientByID(clientID);
        if(client == null){
            //ErrorContext errorContext = new ErrorContext(clientID, "Client", "Client not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        return setAllClientProperty(client);
    }

    @Override
    public List<Client> getAllClients() throws CustomException {
        List<Client> clients = clientDao.getAllClients();
        if(clients == null){
            //ErrorContext errorContext = new ErrorContext(null, "Client", "Client list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        List<Client> clientList = new ArrayList<>();
        for(Client client : clients){
            clientList.add(setAllClientProperty(client));
        }
        return clientList;
    }

    @Override
    public List<Client> searchClients(String clientName, String organization, String clientEmail,
                                      String from, String range) throws CustomException {
        List<Client> clients = clientDao.searchClients(clientName, organization, clientEmail, from, range);
        if(clients == null){
            //ErrorContext errorContext = new ErrorContext(null, "Client", "Client list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        List<Client> clientList = new ArrayList<>();
        for(Client client : clients){
            clientList.add(setAllClientProperty(client));
        }
        return clientList;
    }

    @Override
    public void saveClientProject(List<String> projectIds, Client client) throws CustomException {

        if(clientDao.deleteClientProject(client.getClientId(), null))
            logger.info("Delete all client projects.");

        for(String projectID : projectIds){
            Project project = projectDao.getProjectByID(projectID);
            ProjectClient projectClient = new ProjectClient();
            projectClient.setClient(client);
            projectClient.setProject(project);
            projectClient.setVersion(1);

            boolean res = clientDao.saveClientProject(projectClient);
            if(!res){
                //ErrorContext errorContext = new ErrorContext(null, "ClientProject", "Client project create failed.");
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                        Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0002);
                throw new CustomException(errorMessage);
            }
            logger.info("Save client project success");
        }
    }

    @Override
    public void deleteClientProject(String clientProjectID) throws CustomException {
        boolean res = clientDao.deleteClientProject(null, clientProjectID);
        if(!res){
            //ErrorContext errorContext = new ErrorContext(clientProjectID, "ClientProject", "Client project delete failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }
        logger.info("Delete client project success");
    }

    @Override
    public List<ProjectClient> getClientProjects(String clientID) throws CustomException {
        List<ProjectClient> projectClientList = clientDao.getClientProjects(clientID);
        if(projectClientList == null){
            //ErrorContext errorContext = new ErrorContext(null, "ClientProject", "Client project list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
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
