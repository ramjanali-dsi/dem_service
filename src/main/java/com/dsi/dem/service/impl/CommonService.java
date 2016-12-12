package com.dsi.dem.service.impl;

import com.dsi.dem.util.SessionUtil;
import org.hibernate.Session;

/**
 * Created by sabbir on 11/15/16.
 */
public class CommonService {

    public Session getSession(){
        Session session = SessionUtil.getSession();
        session.beginTransaction();
        return session;
    }

    public void close(Session session){
        session.getTransaction().commit();
        session.close();
    }
}
