package com.dsi.dem.service;

import com.dsi.dem.model.*;

import java.util.List;

/**
 * Created by sabbir on 8/17/16.
 */
public interface ReferenceService {

    List<RoleType> getAllRoles();
    List<EmployeeEmailType> getAllEmailTypes();
    List<EmployeeContactType> getAllContactInfoTypes();
    List<ProjectStatus> getAllProjectStatusNames();
    List<LeaveStatus> getAllLeaveStatusNames();
    List<LeaveType> getAllLeaveTypes();
    List<LeaveRequestType> getAllLeaveRequestTypes();
}
