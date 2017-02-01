package com.dsi.dem.util;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.*;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Date;


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
        notificationObj.put("processed", true);
        notificationObj.put("retryInterval", "1");

        return notificationObj;
    }

    public static JSONObject getContentObjForEmployee(Employee employee, String tenantName,
                                                      String password, String email) throws JSONException {
        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", new JSONArray().put(email));
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
        contentObj.put("Recipients", email);
        contentObj.put("EmployeeFirstName", employee.getFirstName());
        contentObj.put("EmployeeLastName", employee.getLastName());
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForTeam(Team team, String tenantName, String firstName,
                                               String lastName, JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", email);
        contentObj.put("TeamName", team.getName());
        contentObj.put("LeadFirstName", firstName);
        contentObj.put("LeadLastName", lastName);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForTeamMemberAssignUnAssign(Employee employee, String teamName, Employee lead,
                                                                   String tenantName, JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", email);
        contentObj.put("TeamName", teamName);
        contentObj.put("EmployeeFirstName", employee.getFirstName());
        contentObj.put("EmployeeLastName", employee.getLastName());
        contentObj.put("LeadFirstName", lead.getFirstName());
        contentObj.put("LeadLastName", lead.getLastName());
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForProjectTeamAssignUnAssign(ProjectTeam projectTeam, String tenantName, Employee leadMember,
                                                                    int memberCnt, JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", email);
        contentObj.put("TeamName", projectTeam.getTeam().getName());
        contentObj.put("MemberCount", memberCnt);
        contentObj.put("ProjectName", projectTeam.getProject().getProjectName());
        contentObj.put("ProjectDescription", projectTeam.getProject().getDescription());
        contentObj.put("TenantName", tenantName);

        if(leadMember != null) {
            contentObj.put("LeadFirstName", leadMember.getFirstName());
            contentObj.put("LeadLastName", leadMember.getLastName());
        }

        return contentObj;
    }

    public static JSONObject getContentForProjectClientAssignUnAssign(ProjectClient projectClient, String tenantName,
                                                                      JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", email);
        contentObj.put("ClientName", projectClient.getClient().getMemberName());
        contentObj.put("ProjectName", projectClient.getProject().getProjectName());
        contentObj.put("ProjectDescription", projectClient.getProject().getDescription());
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForProject(Project project, String tenantName, String teamName,
                                                  JSONArray emailList) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", emailList);
        contentObj.put("TeamName", teamName);
        contentObj.put("ProjectName", project.getProjectName());
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForClient(Client client, String projectName, String tenantName,
                                                 JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", email);
        contentObj.put("ClientName", client.getMemberName());
        contentObj.put("ProjectName", projectName);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForWFHRequest(WorkFromHome workFromHome, String tenantName,
                                                          JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", email);
        contentObj.put("EmployeeFirstName", workFromHome.getEmployee().getFirstName());
        contentObj.put("EmployeeLastName", workFromHome.getEmployee().getLastName());
        contentObj.put("WorkFromHomeDate", workFromHome.getApplyDate());
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForApplyLeaveRequest(LeaveRequest leaveRequest, String tenantName,
                                                            JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", email);
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
        contentObj.put("Recipients", email);
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
        contentObj.put("Recipients", email);
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
        contentObj.put("Recipients", email);
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
        contentObj.put("Recipients", email);
        contentObj.put("AttendanceDate", attendanceDate);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForDraftAttendance(String draftAttendanceFileName, String tenantName,
                                                          JSONArray email) throws JSONException {
        Date afterDate = new Date();
        afterDate.setTime(afterDate.getTime() + 86400000);

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", email);
        contentObj.put("AttendanceExpiryDate", afterDate);
        contentObj.put("AttendanceCSVName", draftAttendanceFileName);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForAttendanceForEmployee(Employee employee, String attendanceDate,
                                                                String tenantName, JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", email);
        contentObj.put("EmployeeFirstName", employee.getFirstName());
        contentObj.put("EmployeeLastName", employee.getLastName());
        contentObj.put("AttendanceDate", attendanceDate);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForAttendanceApproveLeave(Employee employee, LeaveRequest leaveRequest, String attendanceDate,
                                                                 String tenantName, JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", email);
        contentObj.put("EmployeeFirstName", employee.getFirstName());
        contentObj.put("EmployeeLastName", employee.getLastName());
        contentObj.put("ApprovedStartDate", leaveRequest.getApprovedStartDate());
        contentObj.put("ApprovedEndDate", leaveRequest.getApprovedEndDate());
        contentObj.put("AttendanceDate", attendanceDate);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForAttendanceApproveWFH(WorkFromHome workFromHome, String attendanceDate,
                                                               String tenantName, JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", email);
        contentObj.put("EmployeeFirstName", workFromHome.getEmployee().getFirstName());
        contentObj.put("EmployeeLastName", workFromHome.getEmployee().getLastName());
        contentObj.put("WorkFromHomeDate", workFromHome.getApplyDate());
        contentObj.put("AttendanceDate", attendanceDate);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForHoliday(Holiday holiday, String tenantName, JSONArray emails) throws JSONException {
        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", emails);
        contentObj.put("HolidayStartDate", holiday.getStartDate());
        contentObj.put("HolidayEndDate", holiday.getEndDate());
        contentObj.put("HolidayName", holiday.getHolidayName());
        contentObj.put("TenantName", tenantName);

        Date afterEndDate = holiday.getEndDate();
        afterEndDate.setTime(afterEndDate.getTime() + 86400000);
        if(Utility.checkWeekendOfDate(afterEndDate)){
            afterEndDate.setTime(afterEndDate.getTime() + 86400000);

            if(Utility.checkWeekendOfDate(afterEndDate)){
                afterEndDate.setTime(afterEndDate.getTime() + 86400000);
            }
        }
        contentObj.put("OpenDate", afterEndDate);

        return contentObj;
    }

    public static JSONObject getContentForAutoNotifyHoliday(String holiday, String tenantName,
                                                            JSONArray emails) throws JSONException {
        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", emails);
        contentObj.put("HolidayDetail", holiday);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }
}
