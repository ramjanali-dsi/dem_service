package com.dsi.dem.util;

import com.dsi.dem.model.Employee;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

/**
 * Created by sabbir on 12/9/16.
 */
public class EmailBodyTemplate {

    public static JSONObject getNotificationObject(JSONObject contentObj, Long templateId) throws JSONException {
        JSONObject notificationObj = new JSONObject();
        notificationObj.put("notificationTypeId",  NotificationConstant.NOTIFICATION_EMAIL_TYPE_ID);
        notificationObj.put("notificationTemplateId", templateId);
        notificationObj.put("systemId", Constants.SYSTEM_ID);
        notificationObj.put("contentJson", contentObj.toString());
        notificationObj.put("maxRetryCount", 5);
        notificationObj.put("isProcessed", true);
        notificationObj.put("retryInterval", "1");

        return notificationObj;
    }

    public static JSONObject getContentObjForEmployee(Employee employee, String tenantName,
                                                      String password, String email) throws JSONException {
        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipient", new JSONArray().put(email));
        contentObj.put("EmployeeFirstName", employee.getFirstName());
        contentObj.put("EmployeeLastName", employee.getLastName());
        contentObj.put("TenantName", tenantName);
        contentObj.put("Link", NotificationConstant.WEBSITE_LINK);
        contentObj.put("Username", email);
        contentObj.put("Password", password);

        return contentObj;
    }
}
