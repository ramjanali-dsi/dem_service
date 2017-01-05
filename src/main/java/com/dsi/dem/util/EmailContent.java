package com.dsi.dem.util;

import com.dsi.dem.model.*;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;


/**
 * Created by sabbir on 12/9/16.
 */
public class EmailContent {

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

    public static JSONObject getContentObjForGlobal(Employee employee, String tenantName,
                                                          JSONArray email) throws JSONException {
        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipient", email);
        contentObj.put("EmployeeFirstName", employee.getFirstName());
        contentObj.put("EmployeeLastName", employee.getLastName());
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForTeam(Team team, String tenantName, JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipient", email);
        contentObj.put("TeamName", team.getName());
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForProject(Project project, String tenantName, JSONArray emailList) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipient", emailList);
        contentObj.put("ProjectName", project.getProjectName());
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForClient(Client client, String projectName, String tenantName,
                                                 JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipient", email);
        contentObj.put("ClientName", client.getMemberName());
        contentObj.put("ProjectName", projectName);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForApplyLeaveRequest(LeaveRequest leaveRequest, String tenantName,
                                                            JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipient", email);
        contentObj.put("EmployeeFirstName", leaveRequest.getEmployee().getFirstName());
        contentObj.put("EmployeeLastName", leaveRequest.getEmployee().getLastName());
        contentObj.put("LeaveStartDate", leaveRequest.getStartDate());
        contentObj.put("LeaveEndDate", leaveRequest.getEndDate());
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForApproveLeaveRequest(LeaveRequest leaveRequest, String tenantName,
                                                              JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipient", email);
        contentObj.put("EmployeeFirstName", leaveRequest.getEmployee().getFirstName());
        contentObj.put("EmployeeLastName", leaveRequest.getEmployee().getLastName());
        contentObj.put("ApprovedStartDate", leaveRequest.getApprovedStartDate());
        contentObj.put("ApprovedEndDate", leaveRequest.getApprovedEndDate());
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForApproveSpecialLeaveRequest(LeaveRequest leaveRequest, String tenantName,
                                                                     JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipient", email);
        contentObj.put("EmployeeFirstName", leaveRequest.getEmployee().getFirstName());
        contentObj.put("EmployeeLastName", leaveRequest.getEmployee().getLastName());
        contentObj.put("ApprovedStartDate", leaveRequest.getApprovedStartDate());
        contentObj.put("ApprovedEndDate", leaveRequest.getApprovedEndDate());
        contentObj.put("Reason", leaveRequest.getDeniedReason());
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForApplySpecialLeaveRequest(LeaveRequest leaveRequest, String tenantName,
                                                                   JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipient", email);
        contentObj.put("EmployeeFirstName", leaveRequest.getEmployee().getFirstName());
        contentObj.put("EmployeeLastName", leaveRequest.getEmployee().getLastName());
        contentObj.put("LeaveStartDate", leaveRequest.getStartDate());
        contentObj.put("LeaveEndDate", leaveRequest.getEndDate());
        contentObj.put("Reason", leaveRequest.getLeaveReason());
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForAttendance(String attendanceDate, String tenantName,
                                                     JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipient", email);
        contentObj.put("AttendanceDate", attendanceDate);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForAttendanceForEmployee(Employee employee, String attendanceDate,
                                                                String tenantName, JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipient", email);
        contentObj.put("EmployeeFirstName", employee.getFirstName());
        contentObj.put("EmployeeLastName", employee.getLastName());
        contentObj.put("AttendanceDate", attendanceDate);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForAttendanceApproveLeave(Employee employee, LeaveRequest leaveRequest, String attendanceDate,
                                                                 String tenantName, JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipient", email);
        contentObj.put("EmployeeFirstName", employee.getFirstName());
        contentObj.put("EmployeeLastName", employee.getLastName());
        contentObj.put("ApprovedStartDate", leaveRequest.getApprovedStartDate());
        contentObj.put("ApprovedEndDate", leaveRequest.getApprovedEndDate());
        contentObj.put("AttendanceDate", attendanceDate);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }
}
