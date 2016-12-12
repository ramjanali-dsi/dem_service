package com.dsi.dem.util;

import com.dsi.dem.service.impl.APIProvider;
import org.apache.log4j.Logger;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Created by sabbir on 11/25/16.
 */
public class ReadXMLFile {

    private static final Logger logger = Logger.getLogger(ReadXMLFile.class);

    public static final String XML_FILE = "csvConfig.xml";
    private static final Properties xmlProp = new Properties();

    static {
        try {
            ClassLoader loader = Thread.currentThread().getContextClassLoader();
            InputStream propIS = loader.getResourceAsStream(XML_FILE);
            xmlProp.loadFromXML(propIS);

        } catch (IOException e) {
            logger.error("An error occurred while loading urls.", e);
        }
    }

    public static final String EVENT_TIME = xmlProp.getProperty("csv.dateTime.column");
    public static final String USER_ID = xmlProp.getProperty("csv.employeeId.column");
    public static final String TERMINAL_ID = xmlProp.getProperty("csv.terminalId.column");
    public static final String RESULT = xmlProp.getProperty("csv.result.column");

    public static final String IN_TIME = xmlProp.getProperty("csv.inTime");
    public static final String OUT_TIME = xmlProp.getProperty("csv.outTime");
    public static final String STATUS = xmlProp.getProperty("csv.status");
}
