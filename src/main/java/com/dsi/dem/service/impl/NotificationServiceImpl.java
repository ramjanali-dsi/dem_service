package com.dsi.dem.service.impl;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.InMemoryCache;
import com.dsi.dem.util.NotificationConstant;
import com.dsi.httpclient.HttpClient;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;

/**
 * Created by sabbir on 1/6/17.
 */
public class NotificationServiceImpl {

    private static final Logger logger = Logger.getLogger(NotificationServiceImpl.class);

    private static final HttpClient httpClient = new HttpClient();

    private static InMemoryCache<String, JSONArray> cache;

    public NotificationServiceImpl(){
        cache = new InMemoryCache<String, JSONArray>
                (240, 500, 50);
    }

    public JSONArray getHrManagerEmailList() throws CustomException{

        JSONArray hrManagerEmailList = new JSONArray();
        try {
            logger.info("Get HR email list.");
            JSONArray hrEmailList = cache.get(NotificationConstant.HR_ROLE_TYPE);

            if (hrEmailList == null) {
                logger.info("Read hr email list from another service.");
                String result = httpClient.getRequest(APIProvider.API_USER_ROLE_TYPE + NotificationConstant.HR_ROLE_TYPE,
                        Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

                hrEmailList = new JSONArray(result);
                cache.put(NotificationConstant.HR_ROLE_TYPE, hrEmailList);
            }

            logger.info("Get Manager email list.");
            JSONArray managerEmailList = cache.get(NotificationConstant.MANAGER_ROLE_TYPE);

            if (managerEmailList == null) {
                logger.info("Read manager email list from another service.");
                String result = httpClient.getRequest(APIProvider.API_USER_ROLE_TYPE + NotificationConstant.MANAGER_ROLE_TYPE,
                        Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

                managerEmailList = new JSONArray(result);
                cache.put(NotificationConstant.MANAGER_ROLE_TYPE, managerEmailList);
            }

            hrManagerEmailList.put(hrEmailList);
            hrManagerEmailList.put(managerEmailList);
            return hrManagerEmailList;

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }
    }
}
