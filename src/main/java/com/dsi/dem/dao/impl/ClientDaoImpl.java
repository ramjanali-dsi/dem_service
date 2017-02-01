package com.dsi.dem.dao.impl;

import com.dsi.dem.dao.ClientDao;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Client;
import com.dsi.dem.model.ProjectClient;
import com.dsi.dem.service.impl.CommonService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.Utility;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sabbir on 8/1/16.
 */
public class ClientDaoImpl extends CommonService implements ClientDao {

    private static final Logger logger = Logger.getLogger(ClientDaoImpl.class);

    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void saveClient(Client client) throws CustomException {
        try{
            session.save(client);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateClient(Client client) throws CustomException {
        try{
            session.update(client);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteClient(String clientID) throws CustomException {
        try {
            Query query = session.createQuery("DELETE FROM Client c WHERE c.clientId =:clientID");
            query.setParameter("clientID", clientID);

            query.executeUpdate();

        } catch (Exception e) {
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public Client getClientByID(String clientID) {
        Query query = session.createQuery("FROM Client c WHERE c.clientId =:clientID");
        query.setParameter("clientID", clientID);

        Client client = (Client) query.uniqueResult();
        if(client != null){
            return client;
        }
        return null;
    }

    @Override
    public Client getClientByName(String name) {
        Query query = session.createQuery("FROM Client c WHERE c.memberName =:name");
        query.setParameter("name", name);

        Client client = (Client) query.uniqueResult();
        if(client != null){
            return client;
        }
        return null;
    }

    @Override
    public List<Client> getAllClientsByEmployeeId(String employeeId) {
        Query query = session.createQuery("FROM Client c WHERE c.clientId in (SELECT pc.client.clientId FROM ProjectClient pc " +
                "WHERE pc.project.projectId in (SELECT pt.project.projectId FROM ProjectTeam pt WHERE pt.team.teamId in " +
                "(SELECT tm.team.teamId FROM TeamMember tm WHERE tm.employee.employeeId =:employeeID)))");
        query.setParameter("employeeID", employeeId);

        List<Client> clients = query.list();
        if(clients != null){
            return clients;
        }
        return null;
    }

    @Override
    public List<Client> getAllClients() {
        Query query = session.createQuery("FROM Client");

        List<Client> clientList = query.list();
        if(clientList != null){
            return clientList;
        }
        return null;
    }

    @Override
    public List<Client> getAllNotifiedClients() {
        Query query = session.createQuery("FROM Client c WHERE c.isNotify =:notify");
        query.setParameter("notify", true);

        List<Client> clients = query.list();
        if(clients != null){
            return clients;
        }
        return null;
    }

    @Override
    public List<Client> searchClients(String clientName, String organization, String clientEmail, String from, String range) {
        StringBuilder queryBuilder = new StringBuilder();
        boolean hasClause = false;
        Map<String, String> paramValue = new HashMap<>();

        queryBuilder.append("FROM Client c");

        if(!Utility.isNullOrEmpty(clientName)){
            queryBuilder.append(" WHERE c.memberName like :clientName");
            paramValue.put("clientName", "%" + clientName + "%");
            hasClause = true;
        }

        if(!Utility.isNullOrEmpty(organization)){
            if(hasClause){
                queryBuilder.append(" AND c.organization =:organization");

            } else {
                queryBuilder.append(" WHERE c.organization =:organization");
                hasClause = true;
            }
            paramValue.put("organization", organization);
        }

        if(!Utility.isNullOrEmpty(clientEmail)){
            if(hasClause){
                queryBuilder.append(" AND c.memberEmail =:clientEmail");

            } else {
                queryBuilder.append(" WHERE c.memberEmail =:clientEmail");
                //hasClause = true;
            }
            paramValue.put("clientEmail", clientEmail);
        }

        queryBuilder.append(" ORDER BY c.memberName ASC");

        logger.info("Query builder: " + queryBuilder.toString());
        Query query = session.createQuery(queryBuilder.toString());

        for(Map.Entry<String, String> entry : paramValue.entrySet()){
            query.setParameter(entry.getKey(), entry.getValue());
        }

        if(!Utility.isNullOrEmpty(from) && !Utility.isNullOrEmpty(range))
            query.setFirstResult(Integer.valueOf(from)).setMaxResults(Integer.valueOf(range));

        List<Client> clientList = query.list();
        if(clientList != null){
            return clientList;
        }
        return null;
    }

    @Override
    public void saveClientProject(ProjectClient projectClient) throws CustomException {
        try{
            session.save(projectClient);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }

    }

    @Override
    public void deleteClientProject(String clientID, String projectClientID) throws CustomException {
        Query query;
        try {
            if(clientID != null){
                query = session.createQuery("DELETE FROM ProjectClient pc WHERE pc.client.clientId =:clientID");
                query.setParameter("clientID", clientID);

            } else {
                query = session.createQuery("DELETE FROM ProjectClient pc WHERE pc.projectClientId =:projectClientID");
                query.setParameter("projectClientID", projectClientID);
            }

            query.executeUpdate();

        } catch (Exception e) {
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_CLIENT_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public List<ProjectClient> getClientProjects(String clientID) {
        Query query = session.createQuery("FROM ProjectClient pc WHERE pc.client.clientId =:clientID");
        query.setParameter("clientID", clientID);

        List<ProjectClient>projectClientList = query.list();
        if(projectClientList != null){
            return projectClientList;
        }
        return null;
    }
}
