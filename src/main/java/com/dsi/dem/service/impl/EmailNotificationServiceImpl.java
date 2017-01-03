package com.dsi.dem.service.impl;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Employee;
import com.dsi.dem.service.EmailNotificationService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.EmailContent;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.NotificationConstant;
import com.dsi.httpclient.HttpClient;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by sabbir on 1/3/17.
 */
public class EmailNotificationServiceImpl implements EmailNotificationService {

    private static final Logger logger = Logger.getLogger(EmailNotificationServiceImpl.class);

    private static final HttpClient httpClient = new HttpClient();

    @Override
    public void notificationForEmployeeCreate(Employee employee, String tenantName,
                                              String password, String email) throws CustomException {
        try {
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();

            JSONObject contentObj = EmailContent.getContentObjForEmployee(employee, tenantName, email, password);

            notificationList.put(EmailContent.getNotificationObject(contentObj,
                    NotificationConstant.EMPLOYEE_CREATE_TEMPLATE_ID_FOR_EMPLOYEE));


            JSONArray emailList = new JSONArray();
            //TODO Manager & HR email config

            contentObj = EmailContent.getContentObjForGlobal(employee, tenantName, emailList);

            notificationList.put(EmailContent.getNotificationObject(contentObj,
                    NotificationConstant.EMPLOYEE_CREATE_TEMPLATE_ID_FOR_MANAGER_HR));

            logger.info("Notification create request body :: " + notificationList.toString());
            String result = httpClient.sendPost(APIProvider.API_NOTIFICATION_CREATE, notificationList.toString(),
                    Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

            JSONObject resultObj = new JSONObject(result);
            if (!resultObj.has(Constants.MESSAGE)) {
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0009,
                        Constants.DEM_SERVICE_0009_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_010);
                throw new CustomException(errorMessage);
            }
            logger.info("Notification create:: End");

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }
    }
}
