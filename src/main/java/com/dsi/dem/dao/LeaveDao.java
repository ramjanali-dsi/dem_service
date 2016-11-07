package com.dsi.dem.dao;

import com.dsi.dem.model.*;

import java.util.List;

/**
 * Created by sabbir on 8/25/16.
 */
public interface LeaveDao {

    boolean isAvailableLeaveTypes(String leaveType, String userId);
    EmployeeLeave getEmployeeLeaveSummary(String employeeID);
    boolean updateEmployeeLeaveSummary(EmployeeLeave leaveSummary);
    List<EmployeeLeave> searchOrReadEmployeesLeaveSummary(String employeeNo, String firstName, String lastName, String nickName,
                                                          String email, String phone, String teamName, String projectName,
                                                          String employeeId, String from, String range, String userId);

    LeaveRequest getEmployeesLeaveDetails(String employeeID);
    int getLeaveCountByStatus(String employeeID, String statusName);
    List<LeaveRequest> searchOrReadLeaveDetails(String employeeNo, String firstName, String lastName, String nickName, String email,
                                                String phone, String teamName, String projectName, String employeeId, String leaveType,
                                                String requestType, String approvedStartDate, String approvedEndDate, String approvedFirstName,
                                                String approvedLastName, String approvedNickName, String appliedStartDate, String appliedEndDate,
                                                String leaveStatus, String from, String range, String userId);

    LeaveType getLeaveTypeByID(String leaveTypeId);
    LeaveRequestType getLeaveRequestTypeByID(String leaveRequestTypeId);

    boolean saveLeaveRequest(LeaveRequest leaveRequest);
    boolean updateLeaveRequest(LeaveRequest leaveRequest);
    boolean deleteLeaveRequest(String leaveRequestID);
    LeaveRequest getLeaveRequestById(String leaveRequestID, String employeeId);
    LeaveRequest getLeaveRequestByIdAndEmployeeId(String leaveRequestId, String userId);
    List<LeaveRequest> getLeaveRequestByEmployeeID(String employeeID);
    List<LeaveRequest> getAllLeaveRequest();
    List<LeaveRequest> searchOrReadLeaveRequests(String userId, String teamName, String projectName, String leaveCnt, String leaveReason,
                                                 String leaveType, String leaveStatus, String requestType, String appliedStartDate,
                                                 String appliedEndDate, String deniedReason, String deniedBy, String leaveRequestId,
                                                 String from, String range);

    LeaveStatus getLeaveStatusByName(String typeName);
    boolean getLeaveRequestByRequestTypeAndEmployeeNo(String employeeNo, String date);
    LeaveRequest getLeaveRequestByStatusAndEmployee(String employeeNo, String date);
}
