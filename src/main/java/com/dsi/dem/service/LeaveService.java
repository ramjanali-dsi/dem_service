package com.dsi.dem.service;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.EmployeeLeave;
import com.dsi.dem.model.LeaveRequest;
import com.dsi.dem.model.LeaveStatus;

import java.util.List;

/**
 * Created by sabbir on 8/25/16.
 */
public interface LeaveService {

    boolean isAvailableLeaveTypes(String leaveType, String userId) throws CustomException;
    EmployeeLeave getEmployeeLeaveSummary(String employeeID) throws CustomException;
    List<EmployeeLeave> searchOrReadEmployeesLeaveSummary(String employeeNo, String firstName, String lastName, String nickName,
                                                          String email, String phone, String teamName, String projectName,
                                                          String employeeId, String from, String range, String userId) throws CustomException;

    LeaveRequest getEmployeesLeaveDetails(String employeeID) throws CustomException;
    List<LeaveRequest> searchOrReadLeaveDetails(String employeeNo, String firstName, String lastName, String nickName, String email,
                                                String phone, String teamName, String projectName, String employeeId, String leaveType,
                                                String requestType, String approvedStartDate, String approvedEndDate, String approvedFirstName,
                                                String approvedLastName, String approvedNickName, String appliedStartDate, String appliedEndDate,
                                                String leaveStatus, String from, String range, String userId) throws CustomException;

    void approveLeaveRequest(LeaveRequest leaveRequest, String userId) throws CustomException;

    void saveLeaveRequest(LeaveRequest leaveRequest, String userId) throws CustomException;
    void updateLeaveRequest(LeaveRequest leaveRequest, String userId, int mode) throws CustomException;
    void deleteLeaveRequest(String leaveRequestID, String userID) throws CustomException;
    LeaveRequest getLeaveRequestById(String leaveRequestID, String employeeID) throws CustomException;
    List<LeaveRequest> getLeaveRequestByEmployeeID(String employeeID) throws CustomException;
    List<LeaveRequest> getAllLeaveRequest() throws CustomException;
    List<LeaveRequest> searchOrReadLeaveRequests(String userId, String teamName, String projectName, String leaveCnt, String leaveReason,
                                                 String leaveType, String leaveStatus, String requestType, String appliedStartDate,
                                                 String appliedEndDate, String deniedReason, String deniedBy, String leaveRequestId,
                                                 String from, String range) throws CustomException;
}
