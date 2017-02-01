package com.dsi.dem.util;

/**
 * Created by sabbir on 10/28/16.
 */
public class ErrorTypeConstants {

    //Common
    public static final String DEM_ERROR_TYPE_001 = "internalServerError";
    public static final String DEM_ERROR_TYPE_002 = "version";
    public static final String DEM_ERROR_TYPE_003 = "photoFetch";
    public static final String DEM_ERROR_TYPE_004 = "date";
    public static final String DEM_ERROR_TYPE_005 = "csv";
    public static final String DEM_ERROR_TYPE_006 = "json";
    public static final String DEM_ERROR_TYPE_007 = "dto";
    public static final String DEM_ERROR_TYPE_008 = "upload";
    public static final String DEM_ERROR_TYPE_009 = "range";
    public static final String DEM_ERROR_TYPE_010 = "serviceFailed";

    //Employee
    public static final String DEM_EMPLOYEE_ERROR_TYPE_0001 = "employee";
    public static final String DEM_EMPLOYEE_ERROR_TYPE_0002 = "hasEmployee";
    public static final String DEM_EMPLOYEE_ERROR_TYPE_0003 = "hasLinkToOthers";
    public static final String DEM_EMPLOYEE_ERROR_TYPE_0004 = "firstName";
    public static final String DEM_EMPLOYEE_ERROR_TYPE_0005 = "lastName";
    public static final String DEM_EMPLOYEE_ERROR_TYPE_0006 = "presentAddress";
    public static final String DEM_EMPLOYEE_ERROR_TYPE_0007 = "employeeDesignations";
    public static final String DEM_EMPLOYEE_ERROR_TYPE_0008 = "roleId";
    public static final String DEM_EMPLOYEE_ERROR_TYPE_0009 = "employeeContacts";
    public static final String DEM_EMPLOYEE_ERROR_TYPE_0010 = "employeeEmails";
    public static final String DEM_EMPLOYEE_ERROR_TYPE_0011 = "emailName";
    public static final String DEM_EMPLOYEE_ERROR_TYPE_0012 = "emailType";
    public static final String DEM_EMPLOYEE_ERROR_TYPE_0013 = "hasEmail";
    public static final String DEM_EMPLOYEE_ERROR_TYPE_0014 = "phone";
    public static final String DEM_EMPLOYEE_ERROR_TYPE_0015 = "phoneType";
    public static final String DEM_EMPLOYEE_ERROR_TYPE_0016 = "designationName";
    public static final String DEM_EMPLOYEE_ERROR_TYPE_0017 = "employeeAttendances";
    public static final String DEM_EMPLOYEE_ERROR_TYPE_0018 = "employeeStatus";
    public static final String DEM_EMPLOYEE_ERROR_TYPE_0019 = "employeeLeaveSummary";
    public static final String DEM_EMPLOYEE_ERROR_TYPE_0020 = "hasLeadRole";
    public static final String DEM_EMPLOYEE_ERROR_TYPE_0021 = "hasAssignToTeam";


    //Project
    public static final String DEM_PROJECT_ERROR_TYPE_0001 = "project";
    public static final String DEM_PROJECT_ERROR_TYPE_0002 = "projectTeams";
    public static final String DEM_PROJECT_ERROR_TYPE_0003 = "projectClients";
    public static final String DEM_PROJECT_ERROR_TYPE_0004 = "hasProject";
    public static final String DEM_PROJECT_ERROR_TYPE_0005 = "projectName";
    public static final String DEM_PROJECT_ERROR_TYPE_0006 = "projectStatus";
    public static final String DEM_PROJECT_ERROR_TYPE_0007 = "hasAssignToClient";

    //Team
    public static final String DEM_TEAM_ERROR_TYPE_0001 = "team";
    public static final String DEM_TEAM_ERROR_TYPE_0002 = "teamMembers";
    public static final String DEM_TEAM_ERROR_TYPE_0003 = "teamProjects";
    public static final String DEM_TEAM_ERROR_TYPE_0004 = "hasTeam";
    public static final String DEM_TEAM_ERROR_TYPE_0005 = "teamName";
    public static final String DEM_TEAM_ERROR_TYPE_0006 = "teamMemberInfo";
    public static final String DEM_TEAM_ERROR_TYPE_0007 = "teamRoleInfo";
    public static final String DEM_TEAM_ERROR_TYPE_0008 = "teamMemberId";
    public static final String DEM_TEAM_ERROR_TYPE_0009 = "hasAssignToProject";


    //Client
    public static final String DEM_CLIENT_ERROR_TYPE_0001 = "client";
    public static final String DEM_CLIENT_ERROR_TYPE_0002 = "clientProjects";
    public static final String DEM_CLIENT_ERROR_TYPE_0003 = "hasClient";
    public static final String DEM_CLIENT_ERROR_TYPE_0004 = "memberName";
    public static final String DEM_CLIENT_ERROR_TYPE_0005 = "memberEmail";
    public static final String DEM_CLIENT_ERROR_TYPE_0006 = "memberOrganization";
    public static final String DEM_CLIENT_ERROR_TYPE_0007 = "memberPosition";

    // Leave
    public static final String DEM_LEAVE_ERROR_TYPE_0001 = "leaveRequest";
    public static final String DEM_LEAVE_ERROR_TYPE_0002 = "hasPending";
    public static final String DEM_LEAVE_ERROR_TYPE_0003 = "mustBeCasual";
    public static final String DEM_LEAVE_ERROR_TYPE_0004 = "leaveType";
    public static final String DEM_LEAVE_ERROR_TYPE_0005 = "leaveRequestType";
    public static final String DEM_LEAVE_ERROR_TYPE_0006 = "approvedDatesNotInRange";
    public static final String DEM_LEAVE_ERROR_TYPE_0007 = "approvedStartDate";
    public static final String DEM_LEAVE_ERROR_TYPE_0008 = "approvedEndDate";
    public static final String DEM_LEAVE_ERROR_TYPE_0009 = "leaveStartDate";
    public static final String DEM_LEAVE_ERROR_TYPE_0010 = "leaveEndDate";
    public static final String DEM_LEAVE_ERROR_TYPE_0011 = "postNotAllowed";
    public static final String DEM_LEAVE_ERROR_TYPE_0012 = "preCasualNotAllowed";
    public static final String DEM_LEAVE_ERROR_TYPE_0013 = "preSickNotAllowed";
    public static final String DEM_LEAVE_ERROR_TYPE_0014 = "editNotAllowed";
    public static final String DEM_LEAVE_ERROR_TYPE_0015 = "cancelNotAllowed";
    public static final String DEM_LEAVE_ERROR_TYPE_0016 = "deniedReason";
    public static final String DEM_LEAVE_ERROR_TYPE_0017 = "insufficientCasualLeave";
    public static final String DEM_LEAVE_ERROR_TYPE_0018 = "insufficientSickLeave";
    public static final String DEM_LEAVE_ERROR_TYPE_0019 = "hasApprovedLeave";
    public static final String DEM_LEAVE_ERROR_TYPE_0020 = "specialPostMustBeSick";
    public static final String DEM_LEAVE_ERROR_TYPE_0021 = "specialPreMustBeSpecial";
    public static final String DEM_LEAVE_ERROR_TYPE_0022 = "alreadyHasForWFH";


    //Holiday
    public static final String DEM_HOLIDAY_ERROR_TYPE_0001 = "holiday";
    public static final String DEM_HOLIDAY_ERROR_TYPE_0002 = "holidayName";
    public static final String DEM_HOLIDAY_ERROR_TYPE_0003 = "hasHoliday";
    public static final String DEM_HOLIDAY_ERROR_TYPE_0004 = "holidayPresent";
    public static final String DEM_HOLIDAY_ERROR_TYPE_0005 = "weekendPresent";

    //Attendance
    public static final String DEM_ATTENDANCE_ERROR_TYPE_0001 = "attendance";
    public static final String DEM_ATTENDANCE_ERROR_TYPE_0002 = "notAbsentCheckInOutTime";
    public static final String DEM_ATTENDANCE_ERROR_TYPE_0003 = "absentCheckInOutTime";
    public static final String DEM_ATTENDANCE_ERROR_TYPE_0004 = "attendanceDate";
    public static final String DEM_ATTENDANCE_ERROR_TYPE_0005 = "attendanceStatus";
    public static final String DEM_ATTENDANCE_ERROR_TYPE_0006 = "tempAttendance";
    public static final String DEM_ATTENDANCE_ERROR_TYPE_0007 = "hasAttendance";
    public static final String DEM_ATTENDANCE_ERROR_TYPE_0008 = "attendanceDraft";

    //Work From Home
    public static final String DEM_WFH_ERROR_TYPE_0001 = "workFromHome";
    public static final String DEM_WFH_ERROR_TYPE_0002 = "reason";
    public static final String DEM_WFH_ERROR_TYPE_0003 = "hasPendingWFH";
    public static final String DEM_WFH_ERROR_TYPE_0004 = "editNotAllowedWFH";
    public static final String DEM_WFH_ERROR_TYPE_0005 = "cancelNotAllowedWFH";
    public static final String DEM_WFH_ERROR_TYPE_0006 = "maxWFH";
    public static final String DEM_WFH_ERROR_TYPE_0007 = "hasApprovedWFH";
    public static final String DEM_WFH_ERROR_TYPE_0008 = "applyDate";
    public static final String DEM_WFH_ERROR_TYPE_0009 = "wfhNotAllowed";
    public static final String DEM_WFH_ERROR_TYPE_0010 = "alreadyHasForLR";
}

