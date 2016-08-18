package com.dsi.dem.dao.impl;

import com.dsi.dem.dao.ClientDao;
import com.dsi.dem.model.Client;
import com.dsi.dem.model.ProjectClient;
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
public class ClientDaoImpl extends BaseDao implements ClientDao {

    private static final Logger logger = Logger.getLogger(ClientDaoImpl.class);

    @Override
    public boolean saveClient(Client client) {
        return save(client);
    }

    @Override
    public boolean updateClient(Client client) {
        return update(client);
    }

    @Override
    public boolean deleteClient(String clientID) {
        Session session = null;
        boolean success = true;
        try {
            session = getSession();
            Query query = session.createQuery("DELETE FROM Client c WHERE c.clientId =:clientID");
            query.setParameter("clientID", clientID);

            if(query.executeUpdate() > 0){
                success = true;

            } else {
                success = false;
            }

        } catch (Exception e) {
            logger.error("Database error occurs when delete: " + e.getMessage());
            success = false;

        } finally {
            if(session != null) {
                close(session);
            }
        }
        return success;
    }

    @Override
    public Client getClientByID(String clientID) {
        Session session = null;
        Client client = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM Client c WHERE c.clientId =:clientID");
            query.setParameter("clientID", clientID);

            client = (Client) query.uniqueResult();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return client;
    }

    @Override
    public Client getClientByName(String name) {
        Session session = null;
        Client client = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM Client c WHERE c.memberName =:name");
            query.setParameter("name", name);

            client = (Client) query.uniqueResult();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return client;
    }

    @Override
    public List<Client> getAllClients() {
        Session session = null;
        List<Client> clientList = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM Client");

            clientList = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return clientList;
    }

    @Override
    public List<Client> searchClients(String clientName, String organization, String clientEmail) {
        Session session = null;
        List<Client> clientList = null;
        StringBuilder queryBuilder = new StringBuilder();
        boolean hasClause = false;
        Map<String, String> paramValue = new HashMap<>();
        try{
            session = getSession();
            queryBuilder.append("FROM Client ");

            if(!Utility.isNullOrEmpty(clientName)){
                queryBuilder.append("c WHERE c.memberName like :clientName");
                paramValue.put("clientName", clientName);
                hasClause = true;
            }

            if(!Utility.isNullOrEmpty(organization)){
                if(hasClause){
                    queryBuilder.append(" AND c.organization =:organization");

                } else {
                    queryBuilder.append("c WHERE c.organization =:organization");
                    hasClause = true;
                }
                paramValue.put("organization", organization);
            }

            if(!Utility.isNullOrEmpty(clientEmail)){
                if(hasClause){
                    queryBuilder.append(" AND c.memberEmail =:clientEmail");

                } else {
                    queryBuilder.append("c WHERE c.memberEmail =:clientEmail");
                    //hasClause = true;
                }
                paramValue.put("clientEmail", clientEmail);
            }

            logger.info("Query builder: " + queryBuilder.toString());
            Query query = session.createQuery(queryBuilder.toString());

            for(Map.Entry<String, String> entry : paramValue.entrySet()){
                query.setParameter(entry.getKey(), entry.getValue());
            }

            clientList = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return clientList;
    }

    @Override
    public boolean saveClientProject(ProjectClient projectClient) {
        return save(projectClient);
    }

    @Override
    public boolean deleteClientProject(String clientID, String projectClientID) {
        Session session = null;
        boolean success = true;
        Query query;
        try {
            session = getSession();
            if(clientID != null){
                query = session.createQuery("DELETE FROM ProjectClient pc WHERE pc.client.clientId =:clientID");
                query.setParameter("clientID", clientID);

            } else {
                query = session.createQuery("DELETE FROM ProjectClient pc WHERE pc.projectClientId =:projectClientID");
                query.setParameter("projectClientID", projectClientID);
            }

            if(query.executeUpdate() > 0){
                success = true;

            } else {
                success = false;
            }

        } catch (Exception e) {
            logger.error("Database error occurs when delete: " + e.getMessage());
            success = false;

        } finally {
            if(session != null) {
                close(session);
            }
        }
        return success;
    }

    @Override
    public List<ProjectClient> getClientProjects(String clientID) {
        Session session = null;
        List<ProjectClient> projectClientList = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM ProjectClient pc WHERE pc.client.clientId =:clientID");
            query.setParameter("clientID", clientID);

            projectClientList = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return projectClientList;
    }
}
