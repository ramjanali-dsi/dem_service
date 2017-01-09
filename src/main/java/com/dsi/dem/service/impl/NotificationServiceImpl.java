package com.dsi.dem.service.impl;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.service.NotificationService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.InMemoryCache;
import com.dsi.dem.util.NotificationConstant;
import com.dsi.httpclient.HttpClient;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by sabbir on 1/6/17.
 */
public class NotificationServiceImpl implements NotificationService {

    private static final Logger logger = Logger.getLogger(NotificationServiceImpl.class);

    private static final HttpClient httpClient = new HttpClient();

    private static InMemoryCache<String, JSONArray> cache;

    public NotificationServiceImpl(){
        cache = new InMemoryCache<String, JSONArray>
                (240, 500, 50);
    }

    public JSONArray getHrManagerEmailList() throws CustomException {

        JSONObject resultObj;
        JSONArray hrManagerEmailList = new JSONArray();
        try {
            logger.info("Get HR & Manager email list.");
            JSONArray hrEmailList = cache.get(NotificationConstant.HR_ROLE_TYPE);
            JSONArray managerEmailList = cache.get(NotificationConstant.MANAGER_ROLE_TYPE);

            if (hrEmailList == null) {
                logger.info("Read hr & manager email list from another service.");
                String result = httpClient.getRequest(APIProvider.API_USER_ROLE, Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

                logger.info("API result Data :: " + result);
                resultObj = new JSONObject(result);
                if(resultObj.has(NotificationConstant.HR_ROLE_TYPE)){
                    hrEmailList = resultObj.getJSONArray(NotificationConstant.HR_ROLE_TYPE);
                    cache.put(NotificationConstant.HR_ROLE_TYPE, hrEmailList);

                    managerEmailList = resultObj.getJSONArray(NotificationConstant.MANAGER_ROLE_TYPE);
                    cache.put(NotificationConstant.MANAGER_ROLE_TYPE, managerEmailList);
                }
            }

            if(hrEmailList != null && hrEmailList.length() > 0) {
                for (int i = 0; i < hrEmailList.length(); i++) {
                    hrManagerEmailList.put(hrEmailList.get(i));
                }
            }

            if(managerEmailList != null && managerEmailList.length() > 0) {
                for (int i = 0; i < managerEmailList.length(); i++) {
                    hrManagerEmailList.put(managerEmailList.get(i));
                }
            }
            return hrManagerEmailList;

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }
    }
}
