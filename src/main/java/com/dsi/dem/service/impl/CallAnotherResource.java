package com.dsi.dem.service.impl;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.httpclient.HttpClient;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by sabbir on 1/10/17.
 */
public class CallAnotherResource {

    private static final Logger logger = Logger.getLogger(CallAnotherResource.class);
    private static final HttpClient httpClient = new HttpClient();

    JSONObject sendPost(String url, String bodyObject) throws CustomException {
        logger.info("Request body:: " + bodyObject);
        String result = httpClient.sendPost(url, bodyObject, Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);
        logger.info(url + " api call result:: " + result);

        return getResultObj(result);
    }

    JSONObject sendPut(String url, String bodyObject) throws CustomException {
        logger.info("Request body:: " + bodyObject);
        String result = httpClient.sendPut(url, bodyObject, Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);
        logger.info(url + " api call result:: " + result);

        return getResultObj(result);
    }

    public JSONObject sendDelete(String url) throws CustomException {
        String result = httpClient.sendDelete(url, "", Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);
        logger.info(url + " api call result:: " + result);

        return getResultObj(result);
    }

    private JSONObject getResultObj(String result) throws CustomException {
        JSONObject resultObj;
        try{
            resultObj = new JSONObject(result);
            if (!resultObj.has(Constants.MESSAGE)) {
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0009,
                        Constants.DEM_SERVICE_0009_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_010);
                throw new CustomException(errorMessage);
            }
            return resultObj;

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }
    }
}
