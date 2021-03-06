package com.dsi.dem.util;

import scala.util.parsing.combinator.testing.Str;

/**
 * Created by sabbir on 7/13/16.
 */
public class Constants {

    public static final String AUTHORIZATION = "authorization";

    public static final String TENANT_NAME = "Dynamic Solution Innovators Ltd.";
    public static final String MESSAGE = "message";
    public static final String SYSTEM = "system";
    public static final String SYSTEM_HEADER_ID = "_system_header_id_";

    static final String SYSTEM_ID = "_system_id_";

    static final String DATE_FORMAT="yyyy-MM-dd";

    public static final int DAYS_AGO_COUNT = 5;
    public static final int TOTAL_SICK = 10;
    public static final int TOTAL_CASUAL = 10;

    public static final String FROM = "0";
    public static final String RANGE = "10";

    public static final String GENERAL_LEAVE_TYPE = "General";
    public static final String SPECIAL_LEAVE_TYPE = "Special";

    public static final String PRE_REQUEST_TYPE_NAME = "Pre Request";
    public static final String POST_REQUEST_TYPE_NAME = "Post Request";

    public static final String APPLIED_LEAVE_REQUEST = "Pending";
    public static final String APPROVED_LEAVE_REQUEST = "Approved";
    public static final String DENIED_LEAVE_REQUEST = "Denied";
    public static final String CANCELLER_LEAVE_REQUEST = "Cancelled";

    public static final String APPLIED_WFH_REQUEST = "Pending";
    public static final String APPROVED_WFH_REQUEST = "Approved";
    public static final String DENIED_WFH_REQUEST = "Denied";
    public static final String CANCELLER_WFH_REQUEST = "Cancelled";

    public static final String CASUAL_TYPE_NAME = "Casual Leave Application";
    public static final String SICK_TYPE_NAME = "Sick Leave Application";
    public static final String SPECIAL_TYPE_NAME = "Miscellaneous Leave Application";

    public static final String CHECK_IN_TIME = "10:00";
    public static final String CHECK_OUT_TIME = "18:00";
    public static final String TEMPORARY_ATTENDANCE_TYPE = "Temporary";
    public static final String CONFIRM_ATTENDANCE_TYPE = "Confirm";
    public static final String LEAVE_COMMENT = "Employee has approved leave request";
    public static final String LEAVE_CANCEL_COMMENT = "Your approved leave request has been cancelled due to Presence.";
    public static final String WFH_COMMENT = "Employee has approved work from home request";
    public static final String WFH_CANCEL_COMMENT = "Your approved work from home request has been cancelled due to Presence.";

    //Error code
    public static final String DEM_SERVICE_0001 = "dem_service_0001";
    public static final String DEM_SERVICE_0001_DESCRIPTION = "Internal server error.";

    public static final String DEM_SERVICE_0002 = "dem_service_0002";
    public static final String DEM_SERVICE_0002_DESCRIPTION = "Create failed.";

    public static final String DEM_SERVICE_0003 = "dem_service_0003";
    public static final String DEM_SERVICE_0003_DESCRIPTION = "Update failed.";

    public static final String DEM_SERVICE_0004 = "dem_service_0004";
    public static final String DEM_SERVICE_0004_DESCRIPTION = "Delete failed.";

    public static final String DEM_SERVICE_0005 = "dem_service_0005";
    public static final String DEM_SERVICE_0005_DESCRIPTION = "Not found.";

    public static final String DEM_SERVICE_0006 = "dem_service_0006";
    public static final String DEM_SERVICE_0006_DESCRIPTION = "Photo upload failed.";

    public static final String DEM_SERVICE_0007 = "dem_service_0007";
    public static final String DEM_SERVICE_0007_DESCRIPTION = "Dto to Object convert failed.";

    public static final String DEM_SERVICE_0008 = "dem_service_0008";
    public static final String DEM_SERVICE_0008_DESCRIPTION = "Upload failed.";

    public static final String DEM_SERVICE_0009 = "dem_service_0009";
    public static final String DEM_SERVICE_0009_DESCRIPTION = "Another service call failed.";

    public static final String DEM_SERVICE_0010 = "dem_service_0010";
    public static final String DEM_SERVICE_0010_DESCRIPTION = "Read CSV file failed.";

    public static final String DEM_SERVICE_0011 = "dem_service_0011";
    public static final String DEM_SERVICE_0011_DESCRIPTION = "Photo fetch error.";

    public static final String DEM_SERVICE_0012 = "dem_service_0012";
    public static final String DEM_SERVICE_0012_DESCRIPTION = "Parse error.";

    public static final String DEM_SERVICE_0013 = "dem_service_0013";
    public static final String DEM_SERVICE_0013_DESCRIPTION = "Not allowed.";

    public static final String DEM_SERVICE_0014 = "dem_service_0001";
    public static final String DEM_SERVICE_0014_DESCRIPTION = "Not defined.";
}
