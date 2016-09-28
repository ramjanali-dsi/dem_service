package com.dsi.dem.service.impl;

import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.dao.LeaveDao;
import com.dsi.dem.dao.impl.EmployeeDaoImpl;
import com.dsi.dem.dao.impl.LeaveDaoImpl;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Employee;
import com.dsi.dem.model.EmployeeLeave;
import com.dsi.dem.model.LeaveRequest;
import com.dsi.dem.service.LeaveService;
import com.dsi.dem.util.Constants;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by sabbir on 8/25/16.
 */
public class LeaveServiceImpl implements LeaveService {

    private static final Logger logger = Logger.getLogger(LeaveServiceImpl.class);

    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();
    private static final LeaveDao leaveDao = new LeaveDaoImpl();

    @Override
    public EmployeeLeave getEmployeeLeaveSummary(String employeeID) throws CustomException {
        EmployeeLeave employeeLeave = leaveDao.getEmployeeLeaveSummary(employeeID);
        if(employeeLeave == null){
            ErrorContext errorContext = new ErrorContext(employeeID, "LeaveSummary", "Employees leave summary not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return employeeLeave;
    }

    @Override
    public List<EmployeeLeave> searchOrReadEmployeesLeaveSummary(String employeeNo, String firstName, String lastName, String nickName,
                                                                 String email, String phone, String teamName, String projectName,
                                                                 String employeeId, String from, String range) throws CustomException {

        List<EmployeeLeave> employeeLeaveList = leaveDao.searchOrReadEmployeesLeaveSummary(employeeNo, firstName, lastName,
                nickName, email, phone, teamName, projectName, employeeId, from, range);
        if(employeeLeaveList == null){
            ErrorContext errorContext = new ErrorContext(null, "LeaveSummary", "Leave summary list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return employeeLeaveList;
    }

    @Override
    public LeaveRequest getEmployeesLeaveDetails(String employeeID) throws CustomException {
        LeaveRequest leaveDetails = leaveDao.getEmployeesLeaveDetails(employeeID);
        if(leaveDetails == null){
            ErrorContext errorContext = new ErrorContext(employeeID, "leaveDetails", "Employees leave details not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return leaveDetails;
    }

    @Override
    public List<LeaveRequest> searchOrReadLeaveDetails(String employeeNo, String firstName, String lastName, String nickName,
                                                       String email, String phone, String teamName, String projectName, String employeeId,
                                                       String leaveType, String requestType, String approvedStartDate, String approvedEndDate,
                                                       String approvedFirstName, String approvedLastName, String approvedNickName, String appliedStartDate,
                                                       String appliedEndDate, String leaveStatus, String from, String range) throws CustomException {

        List<LeaveRequest> leaveRequestList = leaveDao.searchOrReadLeaveDetails(employeeNo, firstName, lastName, nickName, email, phone,
                teamName, projectName, employeeId, leaveType, requestType, approvedStartDate, approvedEndDate, approvedFirstName,
                approvedLastName, approvedNickName, appliedStartDate, appliedEndDate, leaveStatus, from, range);
        if(leaveRequestList == null){
            ErrorContext errorContext = new ErrorContext(null, "LeaveDetail", "Leave detail list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return leaveRequestList;
    }

    @Override
    public void approveLeaveRequest(LeaveRequest leaveRequest) throws CustomException {
        validateInputForApproval(leaveRequest);

        boolean res = leaveDao.saveLeaveRequest(leaveRequest);
        if(!res){
            ErrorContext errorContext = new ErrorContext(null, "Leave", "Leave request create failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Leave request approval save success");

        //TODO LOGIC
    }

    private void validateInputForApproval(LeaveRequest leaveRequest) throws CustomException {
        if(leaveRequest.getApprovedStartDate() == null){
            ErrorContext errorContext = new ErrorContext(null, "Leave", "Leave approved start date not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(leaveRequest.getApprovedEndDate() == null){
            ErrorContext errorContext = new ErrorContext(null, "Leave", "Leave approved end date not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(leaveRequest.getDeniedReason() == null){
            ErrorContext errorContext = new ErrorContext(null, "Leave", "Leave denied reason not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void saveLeaveRequest(LeaveRequest leaveRequest, String userId) throws CustomException {
        validateInputForCreation(leaveRequest, userId);

        leaveRequest.setEmployee(employeeDao.getEmployeeByUserID(userId));
        leaveRequest.setVersion(1);
        boolean res = leaveDao.saveLeaveRequest(leaveRequest);
        if(!res){
            ErrorContext errorContext = new ErrorContext(null, "Leave", "Leave request create failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Save leave request success");
    }

    private void validateInputForCreation(LeaveRequest leaveRequest, String userId) throws CustomException {
        if(leaveRequest.getLeaveType().getLeaveTypeId() == null){
            ErrorContext errorContext = new ErrorContext(null, "Leave", "Leave type not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(leaveRequest.getStartDate() == null){
            ErrorContext errorContext = new ErrorContext(null, "Leave", "Leave start date not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(leaveRequest.getEndDate() == null){
            ErrorContext errorContext = new ErrorContext(null, "Leave", "Leave end date not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(leaveRequest.getRequestType() == null){
            ErrorContext errorContext = new ErrorContext(null, "Leave", "Leave request type not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateLeaveRequest(LeaveRequest leaveRequest, String userId) throws CustomException {
        validateInputForUpdate(leaveRequest, userId);

        boolean res = leaveDao.updateLeaveRequest(leaveRequest);
        if(!res){
            ErrorContext errorContext = new ErrorContext(leaveRequest.getLeaveRequestId(), "Leave", "Leave request update failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Update leave request success");
    }

    private void validateInputForUpdate(LeaveRequest leaveRequest, String userId) throws CustomException {
        if(leaveRequest.getVersion() == 0){
            ErrorContext errorContext = new ErrorContext(null, "Leave", "Version not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(leaveDao.getLeaveRequestByIdAndEmployeeId(leaveRequest.getLeaveRequestId(), userId) == null){
            ErrorContext errorContext = new ErrorContext(userId, "Leave", "Leave request not found by this user.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteLeaveRequest(String leaveRequestID, String userID) throws CustomException {

        if(leaveDao.getLeaveRequestByIdAndEmployeeId(leaveRequestID, userID) == null){
            ErrorContext errorContext = new ErrorContext(userID, "Leave", "Leave request not found by this user.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        boolean res = leaveDao.deleteLeaveRequest(leaveRequestID);
        if(!res){
            ErrorContext errorContext = new ErrorContext(leaveRequestID, "Leave", "Leave request delete failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Delete leave request success");
    }

    @Override
    public LeaveRequest getLeaveRequestById(String leaveRequestID) throws CustomException {
        LeaveRequest leaveRequest = leaveDao.getLeaveRequestById(leaveRequestID);
        if(leaveRequest == null){
            ErrorContext errorContext = new ErrorContext(leaveRequestID, "Leave", "Leave request not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return leaveRequest;
    }

    @Override
    public List<LeaveRequest> getLeaveRequestByEmployeeID(String employeeID) throws CustomException {
        List<LeaveRequest> leaveRequests = leaveDao.getLeaveRequestByEmployeeID(employeeID);
        if(leaveRequests == null){
            ErrorContext errorContext = new ErrorContext(employeeID, "Leave", "Leave request list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return leaveRequests;
    }

    @Override
    public List<LeaveRequest> getAllLeaveRequest() throws CustomException {
        List<LeaveRequest> leaveRequests = leaveDao.getAllLeaveRequest();
        if(leaveRequests == null){
            ErrorContext errorContext = new ErrorContext(null, "Leave", "Leave request list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return leaveRequests;
    }

    @Override
    public List<LeaveRequest> searchOrReadLeaveRequests(String userId, String teamName, String projectName, String leaveCnt, String leaveReason,
                                                        String leaveType, String leaveStatus, String requestType, String appliedStartDate,
                                                        String appliedEndDate, String deniedReason, String deniedBy, String leaveRequestId,
                                                        String from, String range) throws CustomException {
        List<LeaveRequest> leaveRequests = leaveDao.searchOrReadLeaveRequests(userId, teamName, projectName, leaveCnt, leaveReason, leaveType, leaveStatus,
                requestType, appliedStartDate, appliedEndDate, deniedReason, deniedBy, leaveRequestId, from, range);
        if(leaveRequests == null){
            ErrorContext errorContext = new ErrorContext(null, "Leave", "Leave request list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return leaveRequests;
    }
}
