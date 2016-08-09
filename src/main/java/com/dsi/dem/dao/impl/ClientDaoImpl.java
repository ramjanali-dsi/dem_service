package com.dsi.dem.dao.impl;

import com.dsi.dem.dao.ClientDao;
import com.dsi.dem.model.Client;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

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
    public boolean deleteClient(Client client) {
        return delete(client);
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
}
