package com.dsi.dem.service;

import com.dsi.dem.dto.LeaveDetailsDto;
import com.dsi.dem.dto.LeaveRequestDto;
import com.dsi.dem.dto.LeaveSummaryDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.LeaveRequest;

import java.util.Date;
import java.util.List;

/**
 * Created by sabbir on 8/25/16.
 */
public interface LeaveService {

    boolean isAvailableLeaveTypes(String leaveType, String userId) throws CustomException;
    LeaveSummaryDto getEmployeeLeaveSummary(String employeeID) throws CustomException;
    LeaveDetailsDto getEmployeesAllLeaveDetails(String employeeId, String leaveRequestId) throws CustomException;
    List<LeaveSummaryDto> searchOrReadEmployeesLeaveSummary(String employeeNo, String firstName, String lastName,
                                                            String nickName, String email, String phone, String teamName,
                                                            String projectName, String employeeId, String context, String from,
                                                            String range) throws CustomException;

    LeaveRequest getEmployeesLeaveDetails(String employeeID) throws CustomException;
    List<LeaveDetailsDto> searchOrReadLeaveDetails(String userId, String employeeNo, String firstName, String lastName,
                                                   String nickName, String email, String phone, String teamName,
                                                   String projectName, String employeeId, String leaveType, String requestType,
                                                   String approvedStartDate, String approvedEndDate, String approvedFirstName,
                                                   String approvedLastName, String approvedNickName, String appliedStartDate,
                                                   String appliedEndDate, String leaveStatus, String context, String from,
                                                   String range) throws CustomException;

    LeaveRequestDto approveLeaveRequest(LeaveRequestDto leaveRequestDto, String userId,
                                        String leaveRequestId, String tenantName) throws CustomException;

    LeaveRequestDto saveLeaveRequest(LeaveRequestDto leaveRequestDto, String userId, String tenantName) throws CustomException;
    LeaveRequestDto updateLeaveRequest(LeaveRequestDto leaveRequestDto, String userId,
                                       String leaveRequestId, String tenantName) throws CustomException;
    void deleteLeaveRequest(String leaveRequestID, String userID) throws CustomException;
    LeaveRequest getLeaveRequestById(String leaveRequestID, String employeeID) throws CustomException;
    List<LeaveRequest> getLeaveRequestByEmployeeID(String employeeID) throws CustomException;
    List<LeaveRequest> getAllLeaveRequest() throws CustomException;
    List<LeaveRequestDto> searchOrReadLeaveRequests(String userId, String teamName, String projectName, String leaveCnt,
                                                    String leaveReason, String leaveType, String leaveStatus, String requestType,
                                                    String appliedStartDate, String appliedEndDate, String deniedReason,
                                                    String deniedBy, String leaveRequestId, String from,
                                                    String range) throws CustomException;

    LeaveRequestDto saveSpecialLeave(LeaveRequestDto specialLeaveDto, String tenantName) throws CustomException;
    LeaveRequestDto updateSpecialLeave(LeaveRequestDto specialLeaveDto, String leaveRequestId, String tenantName) throws CustomException;
    void deleteSpecialLeave(String leaveRequestId) throws CustomException;
    List<LeaveDetailsDto> searchOrReadAllSpecialLeave(String employeeNo, String firstName, String lastName,
                                                      String nickName, String leaveStatus, String leaveType, String requestType,
                                                      String leaveRequestId, String from, String range) throws CustomException;

    void getPendingLeaveApplication(Date date);
}
