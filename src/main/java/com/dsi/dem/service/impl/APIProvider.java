package com.dsi.dem.service.impl;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sabbir on 7/12/16.
 */
public class APIProvider {

    private static final Logger logger = Logger.getLogger(APIProvider.class);

    public static final String API_URLS_FILE = "Api.properties";
    private static final Properties apiProp = new Properties();

    static {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream propIS = loader.getResourceAsStream(API_URLS_FILE);
            apiProp.load(propIS);
        } catch (IOException e) {
            logger.error("An error occurred while loading urls.", e);
        }
    }

    private static final String BASE_AUTHORIZE_URL = apiProp.getProperty("base.authorization.url");
    private static final String BASE_AUTHENTICATE_URL = apiProp.getProperty("base.authentication.url");
    private static final String BASE_NOTIFICATION_URL = apiProp.getProperty("base.notification.url");
    //public static final String BASE_DEM_URL = apiProp.getProperty("base.dem.url");

    public static final String API_USER = BASE_AUTHORIZE_URL + apiProp.getProperty("authorization.user");
    public static final String API_USER_ROLE = BASE_AUTHORIZE_URL + apiProp.getProperty("authorization.user.role");
    public static final String API_USER_CONTEXT = BASE_AUTHORIZE_URL + apiProp.getProperty("authorization.user.context");

    public static final String API_LOGIN_SESSION_CREATE = BASE_AUTHENTICATE_URL + apiProp.getProperty("authentication.login");
    public static final String API_LOGIN_SESSION_UPDATE = BASE_AUTHENTICATE_URL + apiProp.getProperty("authentication.login.update");
    public static final String API_LOGIN_SESSION_DELETE = BASE_AUTHENTICATE_URL + apiProp.getProperty("authentication.login.delete");

    public static final String API_NOTIFICATION_CREATE = BASE_NOTIFICATION_URL + apiProp.getProperty("notification.create");

    public static final String PHOTO_DIRECTORY = apiProp.getProperty("dem.photoDirectory");

    public static final String ATTENDANCE_IGNORE_LIST = apiProp.getProperty("attendance.ignoreList");
}