package com.dsi.dem.util;

import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.dao.impl.EmployeeDaoImpl;
import com.dsi.dem.model.*;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;

import java.util.Date;


/**
 * Created by sabbir on 12/9/16.
 */
public class EmailContent {

    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();

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
        contentObj.put("Link", NotificationConstant.WEBSITE_LINK);
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
        contentObj.put("Link", NotificationConstant.WEBSITE_LINK);
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
        contentObj.put("Link", NotificationConstant.WEBSITE_LINK);
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
        contentObj.put("Link", NotificationConstant.WEBSITE_LINK);
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
        contentObj.put("Link", NotificationConstant.WEBSITE_LINK);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForProject(Project project, String tenantName, String teamName,
                                                  JSONArray emailList) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", emailList);
        contentObj.put("TeamName", teamName);
        contentObj.put("ProjectName", project.getProjectName());
        contentObj.put("Link", NotificationConstant.WEBSITE_LINK);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForClient(Client client, String projectName, String tenantName,
                                                 JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", email);
        contentObj.put("ClientName", client.getMemberName());
        contentObj.put("ProjectName", projectName);
        contentObj.put("Link", NotificationConstant.WEBSITE_LINK);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForWFHRequest(WorkFromHome workFromHome, String tenantName,
                                                          JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", email);
        contentObj.put("EmployeeFirstName", workFromHome.getEmployee().getFirstName());
        contentObj.put("EmployeeLastName", workFromHome.getEmployee().getLastName());
        contentObj.put("WorkFromHomeDate", Utility.getDate(workFromHome.getApplyDate()));

        String name = "";
        if(workFromHome.getApprovedBy() != null) {
            Employee employee = employeeDao.getEmployeeByUserID(workFromHome.getApprovedBy());
            name = employee.getFirstName() + " " + employee.getLastName();
        }

        contentObj.put("Name", name);
        contentObj.put("Link", NotificationConstant.WEBSITE_LINK);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForApplyLeaveRequest(LeaveRequest leaveRequest, String tenantName,
                                                            JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", email);
        contentObj.put("EmployeeFirstName", leaveRequest.getEmployee().getFirstName());
        contentObj.put("EmployeeLastName", leaveRequest.getEmployee().getLastName());

        String leaveStartDate = Utility.getDate(leaveRequest.getStartDate());
        String leaveEndDate = Utility.getDate(leaveRequest.getEndDate());

        String leaveDate = "";
        if(leaveStartDate.equals(leaveEndDate)){
            leaveDate += "date: " + leaveStartDate;

        } else {
            leaveDate += "dates: " + leaveStartDate + " - " + leaveEndDate;
        }

        String name = "";
        if(leaveRequest.getApprovalId() != null) {
            Employee deniedEmployee = employeeDao.getEmployeeByID(leaveRequest.getApprovalId());
            if (deniedEmployee != null) {
                name = deniedEmployee.getFirstName() + " " + deniedEmployee.getLastName();
            }
        }

        contentObj.put("LeaveType", leaveRequest.getLeaveType().getLeaveTypeName());
        contentObj.put("DeniedName", name);
        contentObj.put("LeaveDate", leaveDate);
        contentObj.put("Link", NotificationConstant.WEBSITE_LINK);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForApproveLeaveRequest(LeaveRequest leaveRequest, String tenantName,
                                                              JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", email);
        contentObj.put("EmployeeFirstName", leaveRequest.getEmployee().getFirstName());
        contentObj.put("EmployeeLastName", leaveRequest.getEmployee().getLastName());

        String leaveStartDate = Utility.getDate(leaveRequest.getApprovedStartDate());
        String leaveEndDate = Utility.getDate(leaveRequest.getApprovedEndDate());

        String leaveApproveDate = "";
        if(leaveStartDate.equals(leaveEndDate)){
            leaveApproveDate += "date: " + leaveStartDate;

        } else {
            leaveApproveDate += "dates: " + leaveStartDate + " - " + leaveEndDate;
        }

        String name = "";
        if(leaveRequest.getApprovalId() != null) {
            Employee approvalEmployee = employeeDao.getEmployeeByID(leaveRequest.getApprovalId());
            if (approvalEmployee != null) {
                name = approvalEmployee.getFirstName() + " " + approvalEmployee.getLastName();
            }
        }

        contentObj.put("LeaveType", leaveRequest.getLeaveType().getLeaveTypeName());
        contentObj.put("ApprovalName", name);
        contentObj.put("ApprovedDate", leaveApproveDate);
        contentObj.put("Link", NotificationConstant.WEBSITE_LINK);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForApproveSpecialLeaveRequest(LeaveRequest leaveRequest, String tenantName,
                                                                     JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", email);
        contentObj.put("EmployeeFirstName", leaveRequest.getEmployee().getFirstName());
        contentObj.put("EmployeeLastName", leaveRequest.getEmployee().getLastName());

        String leaveStartDate = Utility.getDate(leaveRequest.getApprovedStartDate());
        String leaveEndDate = Utility.getDate(leaveRequest.getApprovedEndDate());

        String leaveApproveDate = "";
        if(leaveStartDate.equals(leaveEndDate)){
            leaveApproveDate += "date: " + leaveStartDate;

        } else {
            leaveApproveDate += "dates: " + leaveStartDate + " - " + leaveEndDate;
        }

        String name = "";
        if(leaveRequest.getApprovalId() != null) {
            Employee approvalEmployee = employeeDao.getEmployeeByID(leaveRequest.getApprovalId());
            if (approvalEmployee != null) {
                name = approvalEmployee.getFirstName() + " " + approvalEmployee.getLastName();
            }
        }

        contentObj.put("ApprovalName", name);
        contentObj.put("LeaveType", leaveRequest.getLeaveType().getLeaveTypeName());
        contentObj.put("ApprovedDate", leaveApproveDate);
        contentObj.put("Reason", leaveRequest.getDeniedReason());
        contentObj.put("Link", NotificationConstant.WEBSITE_LINK);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForApplySpecialLeaveRequest(LeaveRequest leaveRequest, String tenantName,
                                                                   JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", email);
        contentObj.put("EmployeeFirstName", leaveRequest.getEmployee().getFirstName());
        contentObj.put("EmployeeLastName", leaveRequest.getEmployee().getLastName());

        String leaveStartDate = Utility.getDate(leaveRequest.getStartDate());
        String leaveEndDate = Utility.getDate(leaveRequest.getEndDate());

        String leaveDate = "";
        if(leaveStartDate.equals(leaveEndDate)){
            leaveDate += "date: " + leaveStartDate;

        } else {
            leaveDate += "dates: " + leaveStartDate + " - " + leaveEndDate;
        }

        String name = "";
        if(leaveRequest.getApprovalId() != null) {
            Employee approvalEmployee = employeeDao.getEmployeeByID(leaveRequest.getApprovalId());
            if (approvalEmployee != null) {
                name = approvalEmployee.getFirstName() + " " + approvalEmployee.getLastName();

            }
        }

        contentObj.put("DeniedName", name);
        contentObj.put("LeaveType", leaveRequest.getLeaveType().getLeaveTypeName());
        contentObj.put("LeaveDate", leaveDate);
        contentObj.put("Reason", leaveRequest.getLeaveReason());
        contentObj.put("Link", NotificationConstant.WEBSITE_LINK);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForAttendance(String attendanceDate, String tenantName,
                                                     JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", email);
        contentObj.put("AttendanceDate", Utility.getDate(Utility.getDateFromString(attendanceDate)));
        contentObj.put("Link", NotificationConstant.WEBSITE_LINK);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForDraftAttendance(String draftAttendanceFileName, String tenantName,
                                                          JSONArray email) throws JSONException {
        Date afterDate = new Date();
        afterDate.setTime(afterDate.getTime() + 86400000);

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", email);
        contentObj.put("AttendanceExpiryDate", Utility.getDate(afterDate));
        contentObj.put("AttendanceCSVName", draftAttendanceFileName);
        contentObj.put("Link", NotificationConstant.WEBSITE_LINK);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForAttendanceForEmployee(Employee employee, String attendanceDate,
                                                                String tenantName, JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", email);
        contentObj.put("EmployeeFirstName", employee.getFirstName());
        contentObj.put("EmployeeLastName", employee.getLastName());
        contentObj.put("AttendanceDate", Utility.getDate(Utility.getDateFromString(attendanceDate)));
        contentObj.put("Link", NotificationConstant.WEBSITE_LINK);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForAttendanceApproveLeave(Employee employee, LeaveRequest leaveRequest, String attendanceDate,
                                                                 String tenantName, JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", email);
        contentObj.put("EmployeeFirstName", employee.getFirstName());
        contentObj.put("EmployeeLastName", employee.getLastName());

        String leaveStartDate = Utility.getDate(leaveRequest.getApprovedStartDate());
        String leaveEndDate = Utility.getDate(leaveRequest.getApprovedEndDate());

        String leaveApproveDate = "";
        if(leaveStartDate.equals(leaveEndDate)){
            leaveApproveDate += "date: " + leaveStartDate;

        } else {
            leaveApproveDate += "dates: " + leaveStartDate + " - " + leaveEndDate;
        }

        String name = "";
        if(leaveRequest.getApprovalId() != null) {
            Employee approvalEmployee = employeeDao.getEmployeeByID(leaveRequest.getApprovalId());
            if (approvalEmployee != null) {
                name = approvalEmployee.getFirstName() + " " + approvalEmployee.getLastName();
            }
        }

        contentObj.put("ApprovalName", name);
        contentObj.put("LeaveType", leaveRequest.getLeaveType().getLeaveTypeName());
        contentObj.put("ApprovedDate", leaveApproveDate);
        contentObj.put("AttendanceDate", Utility.getDate(Utility.getDateFromString(attendanceDate)));
        contentObj.put("Link", NotificationConstant.WEBSITE_LINK);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForAttendanceApproveWFH(WorkFromHome workFromHome, String attendanceDate,
                                                               String tenantName, JSONArray email) throws JSONException {

        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", email);
        contentObj.put("EmployeeFirstName", workFromHome.getEmployee().getFirstName());
        contentObj.put("EmployeeLastName", workFromHome.getEmployee().getLastName());
        contentObj.put("WorkFromHomeDate", Utility.getDate(workFromHome.getApplyDate()));
        contentObj.put("AttendanceDate", Utility.getDate(Utility.getDateFromString(attendanceDate)));
        contentObj.put("Link", NotificationConstant.WEBSITE_LINK);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }

    public static JSONObject getContentForHoliday(Holiday holiday, String tenantName, JSONArray emails) throws JSONException {
        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", emails);

        String holidayStartDate = Utility.getDate(holiday.getStartDate());
        String holidayEndDate = Utility.getDate(holiday.getEndDate());

        String holidayDate = "";
        if(holidayStartDate.equals(holidayEndDate)){
            holidayDate += holidayStartDate;

        } else {
            holidayDate += "from " + holidayStartDate + " - " + holidayEndDate;
        }

        contentObj.put("HolidayDate", holidayDate);
        contentObj.put("HolidayName", holiday.getHolidayName());
        contentObj.put("TenantName", tenantName);

        Date afterEndDate = Utility.getDayAfterDate(holiday.getEndDate());
        if(Utility.checkWeekendOfDate(afterEndDate)){
            afterEndDate = Utility.getDayAfterDate(afterEndDate);

            if(Utility.checkWeekendOfDate(afterEndDate)){
                afterEndDate = Utility.getDayAfterDate(afterEndDate);
            }
        }
        contentObj.put("Link", NotificationConstant.WEBSITE_LINK);
        contentObj.put("OpenDate", Utility.getDate(afterEndDate));

        return contentObj;
    }

    public static JSONObject getContentForAutoNotifyHoliday(String holiday, String tenantName,
                                                            JSONArray emails) throws JSONException {
        JSONObject contentObj = new JSONObject();
        contentObj.put("Recipients", emails);
        contentObj.put("HolidayDetail", holiday);
        contentObj.put("Link", NotificationConstant.WEBSITE_LINK);
        contentObj.put("TenantName", tenantName);

        return contentObj;
    }
}
