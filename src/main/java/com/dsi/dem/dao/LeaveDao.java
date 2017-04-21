package com.dsi.dem.dao;

import com.dsi.dem.dto.ContextDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.*;
import org.hibernate.Session;

import java.util.Date;
import java.util.List;

/**
 * Created by sabbir on 8/25/16.
 */
public interface LeaveDao {

    void setSession(Session session);
    boolean isAvailableLeaveTypes(String leaveType, String userId);
    EmployeeLeave getEmployeeLeaveSummary(String employeeID);
    void updateEmployeeLeaveSummary(EmployeeLeave leaveSummary) throws CustomException;
    List<EmployeeLeave> searchOrReadEmployeesLeaveSummary(String employeeNo, String firstName, String lastName, String nickName,
                                                          String email, String phone, String teamName, String projectName,
                                                          String employeeId, ContextDto contextDto, String from, String range);

    LeaveRequest getEmployeesLeaveDetails(String employeeID);
    boolean checkWFHRequest(String employeeId, Date startDate, Date endDate);
    boolean alreadyApprovedLeaveExist(String employeeID, Date leaveStartDate);
    int getLeaveCountByStatus(String employeeID, String statusName);
    List<LeaveRequest> searchOrReadLeaveDetails(String userId, String employeeNo, String firstName, String lastName, String nickName,
                                                String email, String phone, String teamName, String projectName, String employeeId,
                                                String leaveType, String requestType, String approvedStartDate, String approvedEndDate,
                                                String approvedFirstName, String approvedLastName, String approvedNickName,
                                                String appliedStartDate, String appliedEndDate, String leaveStatus, ContextDto contextDto,
                                                String from, String range);

    LeaveType getLeaveTypeByID(String leaveTypeId);
    LeaveRequestType getLeaveRequestTypeByID(String leaveRequestTypeId);

    void saveLeaveRequest(LeaveRequest leaveRequest) throws CustomException;
    void updateLeaveRequest(LeaveRequest leaveRequest) throws CustomException;
    void deleteLeaveRequest(String leaveRequestID) throws CustomException;
    LeaveRequest getLeaveRequestById(String leaveRequestID, String employeeId);
    LeaveRequest getLeaveRequestByIdAndEmployeeId(String leaveRequestId, String userId);
    List<LeaveRequest> getLeaveRequestByEmployeeID(String employeeID);
    List<LeaveRequest> getAllLeaveRequest();
    List<LeaveRequest> searchOrReadLeaveRequests(String userId, String teamName, String projectName, String leaveCnt, String leaveReason,
                                                 String leaveType, String leaveStatus, String requestType, String appliedStartDate,
                                                 String appliedEndDate, String deniedReason, String deniedBy, String leaveRequestId,
                                                 String from, String range);
    List<LeaveRequest> searchOrReadSpecialLeaveRequests(String employeeNo, String firstName, String lastName,
                                                        String nickName, String leaveStatus, String leaveType,
                                                        String requestType, String leaveRequestId, String from, String range);

    LeaveStatus getLeaveStatusByName(String typeName);
    boolean getLeaveRequestByRequestTypeAndEmployeeNo(String employeeNo, Date date);
    LeaveRequest getLeaveRequestByStatusAndEmployee(String employeeNo, Date date);

    List<LeaveRequest> getPendingLeaveApplication(Date date);
    List<LeaveRequest> getApprovedLeaveApplication(Date date);
}
