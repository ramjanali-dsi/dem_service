package com.dsi.dem.util;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.reflections.Reflections;

import javax.persistence.Entity;

/**
 * Created by sabbir on 6/10/16.
 */
public class SessionUtil {

    private static SessionUtil instance = new SessionUtil();
    private SessionFactory sessionFactory;

    public static SessionUtil getInstance(){
        return instance;
    }

    private SessionUtil(){
        Configuration configuration = new Configuration();
        configuration.configure("hibernate-dem.cfg.xml");

        final Reflections reflections = new Reflections("com.dsi.dem.model");

        for (Class<?> cl : reflections.getTypesAnnotatedWith(Entity.class)) {
            configuration.addAnnotatedClass(cl);
        }

        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().
                applySettings(configuration.getProperties()).build();

        sessionFactory = configuration.buildSessionFactory(serviceRegistry);
    }

    public static Session getSession(){

        return getInstance().sessionFactory.openSession();
    }
}
