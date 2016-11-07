package com.dsi.dem.service.impl;

import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.dao.LeaveDao;
import com.dsi.dem.dao.impl.EmployeeDaoImpl;
import com.dsi.dem.dao.impl.LeaveDaoImpl;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.*;
import com.dsi.dem.service.EmployeeService;
import com.dsi.dem.service.LeaveService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.Utility;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Objects;

/**
 * Created by sabbir on 8/25/16.
 */
public class LeaveServiceImpl implements LeaveService {

    private static final Logger logger = Logger.getLogger(LeaveServiceImpl.class);

    private static final EmployeeService employeeService = new EmployeeServiceImpl();

    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();
    private static final LeaveDao leaveDao = new LeaveDaoImpl();

    @Override
    public boolean isAvailableLeaveTypes(String leaveType, String userId) throws CustomException {
        return leaveDao.isAvailableLeaveTypes(leaveType, userId);
    }

    @Override
    public EmployeeLeave getEmployeeLeaveSummary(String employeeID) throws CustomException {
        EmployeeLeave employeeLeave = leaveDao.getEmployeeLeaveSummary(employeeID);
        if(employeeLeave == null){
            //ErrorContext errorContext = new ErrorContext(employeeID, "LeaveSummary", "Employees leave summary not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        Employee employee = employeeService.getEmployeeByID(employeeID);
        employeeLeave.setEmployee(employee);
        return employeeLeave;
    }

    @Override
    public List<EmployeeLeave> searchOrReadEmployeesLeaveSummary(String employeeNo, String firstName, String lastName, String nickName,
                                                                 String email, String phone, String teamName, String projectName,
                                                                 String employeeId, String from, String range, String userId) throws CustomException {

        List<EmployeeLeave> employeeLeaveList = leaveDao.searchOrReadEmployeesLeaveSummary(employeeNo, firstName, lastName,
                nickName, email, phone, teamName, projectName, employeeId, from, range, userId);
        if(employeeLeaveList == null){
            //ErrorContext errorContext = new ErrorContext(null, "LeaveSummary", "Leave summary list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        return employeeLeaveList;
    }

    @Override
    public LeaveRequest getEmployeesLeaveDetails(String employeeID) throws CustomException {
        LeaveRequest leaveDetails = leaveDao.getEmployeesLeaveDetails(employeeID);
        if(leaveDetails == null){
            //ErrorContext errorContext = new ErrorContext(employeeID, "leaveDetails", "Employees leave details not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        return leaveDetails;
    }

    @Override
    public List<LeaveRequest> searchOrReadLeaveDetails(String employeeNo, String firstName, String lastName, String nickName,
                                                       String email, String phone, String teamName, String projectName, String employeeId,
                                                       String leaveType, String requestType, String approvedStartDate, String approvedEndDate,
                                                       String approvedFirstName, String approvedLastName, String approvedNickName, String appliedStartDate,
                                                       String appliedEndDate, String leaveStatus, String from, String range, String userId) throws CustomException {

        List<LeaveRequest> leaveRequestList = leaveDao.searchOrReadLeaveDetails(employeeNo, firstName, lastName, nickName, email, phone,
                teamName, projectName, employeeId, leaveType, requestType, approvedStartDate, approvedEndDate, approvedFirstName,
                approvedLastName, approvedNickName, appliedStartDate, appliedEndDate, leaveStatus, from, range, userId);
        if(leaveRequestList == null){
            //ErrorContext errorContext = new ErrorContext(null, "LeaveDetail", "Leave detail list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        return leaveRequestList;
    }

    @Override
    public void approveLeaveRequest(LeaveRequest leaveRequest, String userId) throws CustomException {
        validateForApproval(leaveRequest);

        LeaveRequest existLeaveRequest = leaveDao.getLeaveRequestById(leaveRequest.getLeaveRequestId(), null);

        existLeaveRequest.setLastModifiedDate(Utility.today());
        existLeaveRequest.setApprovedDate(Utility.today());
        existLeaveRequest.setLeaveStatus(leaveDao.getLeaveStatusByName(leaveRequest.getLeaveStatus().getLeaveStatusName()));
        existLeaveRequest.setApprovalId(employeeDao.getEmployeeByUserID(userId).getEmployeeId());

        if(leaveRequest.getLeaveStatus().getLeaveStatusName().equals(Constants.APPROVED_LEAVE_REQUEST)) {

            if (!((leaveRequest.getApprovedStartDate().compareTo(existLeaveRequest.getStartDate()) >= 0
                    && leaveRequest.getApprovedStartDate().compareTo(existLeaveRequest.getEndDate()) <= 0)
                    && (leaveRequest.getApprovedEndDate().compareTo(existLeaveRequest.getStartDate()) >= 0
                    && leaveRequest.getApprovedEndDate().compareTo(existLeaveRequest.getEndDate()) >= 0))) {

                //ErrorContext errorContext = new ErrorContext(null, "Leave", "Approved dates are not in range.");
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                        Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0006);
                throw new CustomException(errorMessage);
            }

            existLeaveRequest.setApprovedDaysCount(Utility.getDaysBetween(leaveRequest.getApprovedStartDate(),
                    leaveRequest.getApprovedEndDate()) + 1);
            existLeaveRequest.setApprovedStartDate(leaveRequest.getApprovedStartDate());
            existLeaveRequest.setApprovedEndDate(leaveRequest.getApprovedEndDate());
            existLeaveRequest.setClientNotify(leaveRequest.isClientNotify());
        }
        existLeaveRequest.setDeniedReason(leaveRequest.getDeniedReason());

        boolean res = leaveDao.updateLeaveRequest(existLeaveRequest);
        if(!res){
            //ErrorContext errorContext = new ErrorContext(null, "Leave", "Leave request create failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
        logger.info("Leave request approved success");

        updateLeaveSummary(existLeaveRequest);
    }

    private void validateForApproval(LeaveRequest leaveRequest) throws CustomException {
        if(leaveRequest.getLeaveStatus().getLeaveStatusName().equals(Constants.APPROVED_LEAVE_REQUEST)) {
            if (leaveRequest.getApprovedStartDate() == null) {
                //ErrorContext errorContext = new ErrorContext(null, "Leave", "Leave approved start date not defined.");
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                        Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0007);
                throw new CustomException(errorMessage);
            }

            if (leaveRequest.getApprovedEndDate() == null) {
                //ErrorContext errorContext = new ErrorContext(null, "Leave", "Leave approved end date not defined.");
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                        Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0008);
                throw new CustomException(errorMessage);
            }

        } else if(leaveRequest.getLeaveStatus().getLeaveStatusName().equals(Constants.DENIED_LEAVE_REQUEST)){
            if (leaveRequest.getDeniedReason() == null) {
                //ErrorContext errorContext = new ErrorContext(null, "Leave", "Leave denied reason not defined.");
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                        Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0016);
                throw new CustomException(errorMessage);
            }
        }
    }

    private void updateLeaveSummary(LeaveRequest existLeaveRequest) {
        EmployeeLeave leaveSummary = leaveDao.getEmployeeLeaveSummary(existLeaveRequest.getEmployee().getEmployeeId());

        if(existLeaveRequest.getLeaveType().getLeaveTypeName().equals(Constants.CASUAL_TYPE_NAME)){
            leaveSummary.setTotalCasualUsed(leaveSummary.getTotalCasualUsed() +
                    Utility.getDaysBetween(existLeaveRequest.getStartDate(), existLeaveRequest.getEndDate()) + 1);

        } else if(existLeaveRequest.getLeaveType().getLeaveTypeName().equals(Constants.SICK_TYPE_NAME)){
            leaveSummary.setTotalSickUsed(leaveSummary.getTotalSickUsed() +
                    Utility.getDaysBetween(existLeaveRequest.getStartDate(), existLeaveRequest.getEndDate()) + 1);
        }

        leaveSummary.setTotalLeaveUsed(leaveSummary.getTotalCasualUsed() +
                leaveSummary.getTotalSickUsed() +
                leaveSummary.getTotalNotNotify() +
                leaveSummary.getTotalSpecialLeave());

        leaveDao.updateEmployeeLeaveSummary(leaveSummary);
        logger.info("Employee leave summary updated.");
    }

    @Override
    public void saveLeaveRequest(LeaveRequest leaveRequest, String userId) throws CustomException {
        validateInputForCreation(leaveRequest, userId);

        logger.info("Apply user ID: " + userId);

        leaveRequest.setLeaveStatus(leaveDao.getLeaveStatusByName(Constants.APPLIED_LEAVE_REQUEST));
        leaveRequest.setDaysCount(Utility.getDaysBetween(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1);
        leaveRequest.setEmployee(employeeDao.getEmployeeByUserID(userId));
        leaveRequest.setApplyDate(Utility.today());
        leaveRequest.setLastModifiedDate(Utility.today());
        leaveRequest.setVersion(1);
        boolean res = leaveDao.saveLeaveRequest(leaveRequest);
        if(!res){
            //ErrorContext errorContext = new ErrorContext(null, "Leave", "Leave request create failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
        logger.info("Save leave request success");
    }

    private void validateInputForCreation(LeaveRequest leaveRequest, String userId) throws CustomException {
        Employee employee = employeeDao.getEmployeeByUserID(userId);

        if(leaveDao.getLeaveCountByStatus(employee.getEmployeeId(), Constants.APPLIED_LEAVE_REQUEST) > 0){
            //ErrorContext errorContext = new ErrorContext(null, "Leave", "Leave request not allow.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }

        if(leaveRequest.getLeaveType().getLeaveTypeId() == null){
            //ErrorContext errorContext = new ErrorContext(null, "Leave", "Leave type not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0004);
            throw new CustomException(errorMessage);
        }

        if(leaveRequest.getRequestType().getLeaveRequestTypeId() == null){
            //ErrorContext errorContext = new ErrorContext(null, "Leave", "Leave request type not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0005);
            throw new CustomException(errorMessage);
        }

        if(leaveRequest.getStartDate() == null){
            //ErrorContext errorContext = new ErrorContext(null, "Leave", "Leave start date not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0009);
            throw new CustomException(errorMessage);
        }

        if(leaveRequest.getEndDate() == null){
            //ErrorContext errorContext = new ErrorContext(null, "Leave", "Leave end date not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0010);
            throw new CustomException(errorMessage);
        }

        LeaveRequestType requestType = leaveDao.getLeaveRequestTypeByID(leaveRequest.getRequestType().getLeaveRequestTypeId());
        LeaveType leaveType = leaveDao.getLeaveTypeByID(leaveRequest.getLeaveType().getLeaveTypeId());
        EmployeeLeave leaveSummary = leaveDao.getEmployeeLeaveSummary(employee.getEmployeeId());

        if(requestType.getLeaveRequestTypeName().equals(Constants.POST_REQUEST_TYPE_NAME)){
            logger.info("Post leave request application.");

            if(!leaveType.getLeaveTypeName().equals(Constants.CASUAL_TYPE_NAME)){
                //ErrorContext errorContext = new ErrorContext(null, "Leave", "Post leave request type must be Casual.");
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                        Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0003);
                throw new CustomException(errorMessage);
            }

            logger.info("Casual type leave application.");
            if((Constants.TOTAL_CASUAL - leaveSummary.getTotalCasualUsed())
                    < (Utility.getDaysBetween(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1)){

                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                        Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0017);
                throw new CustomException(errorMessage);
            }

            if(Utility.getDaysBetween(leaveRequest.getEndDate(), Utility.getDateFormatFromDate(Utility.today())) + 1 >= 5){
                //ErrorContext errorContext = new ErrorContext(null, "Leave", "Post leave request application not allowed after 5 days.");
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                        Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0011);
                throw new CustomException(errorMessage);
            }
        }

        if(requestType.getLeaveRequestTypeName().equals(Constants.PRE_REQUEST_TYPE_NAME)){
            logger.info("Pre leave request application.");

            if(leaveType.getLeaveTypeName().equals(Constants.CASUAL_TYPE_NAME)){

                logger.info("Casual type leave application.");
                if((Constants.TOTAL_CASUAL - leaveSummary.getTotalCasualUsed())
                        < (Utility.getDaysBetween(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1)){

                    ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                            Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0017);
                    throw new CustomException(errorMessage);
                }

                if(Utility.getDaysBetween(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1 >= 3){

                    if(Utility.getDaysBetween(Utility.getDateFormatFromDate(Utility.today()), leaveRequest.getStartDate()) + 1 < 14){
                        //ErrorContext errorContext = new ErrorContext(null, "Leave", "Pre/casual leave request application not allowed before 14 days.");
                        ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                                Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0012);
                        throw new CustomException(errorMessage);
                    }
                }
            }

            if(leaveType.getLeaveTypeName().equals(Constants.SICK_TYPE_NAME)){

                logger.info("Sick type leave application.");
                if((Constants.TOTAL_SICK - leaveSummary.getTotalSickUsed())
                        < (Utility.getDaysBetween(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1)){

                    ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                            Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0018);
                    throw new CustomException(errorMessage);
                }

                if(!(Utility.getDaysBetween(Utility.getDateFormatFromDate(Utility.today()), leaveRequest.getStartDate()) > 0)) {

                    if (Utility.isGreater02PM(leaveRequest.getStartDate())) {
                        //ErrorContext errorContext = new ErrorContext(null, "Leave", "Pre/sick leave request application not allowed after 12pm");
                        ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                                Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0013);
                        throw new CustomException(errorMessage);
                    }
                }
            }
        }
    }

    @Override
    public void updateLeaveRequest(LeaveRequest leaveRequest, String userId, int mode) throws CustomException {
        validateInputForUpdate(leaveRequest, userId, mode);

        LeaveRequest existLeaveRequest = leaveDao.getLeaveRequestById(leaveRequest.getLeaveRequestId(), null);

        if(mode == 1){
            existLeaveRequest.setStartDate(leaveRequest.getStartDate());
            existLeaveRequest.setEndDate(leaveRequest.getEndDate());
            existLeaveRequest.setDaysCount(Utility.getDaysBetween(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1);
            existLeaveRequest.setLeaveType(leaveDao.getLeaveTypeByID(leaveRequest.getLeaveType().getLeaveTypeId()));
            existLeaveRequest.setRequestType(leaveDao.getLeaveRequestTypeByID(leaveRequest.getRequestType().getLeaveRequestTypeId()));
            existLeaveRequest.setLeaveReason(leaveRequest.getLeaveReason());

        } else if(mode == 2){
            existLeaveRequest.setDeniedReason(leaveRequest.getLeaveReason());
            existLeaveRequest.setLeaveStatus(leaveDao.getLeaveStatusByName(Constants.CANCELLER_LEAVE_REQUEST));
            existLeaveRequest.setApprovedDate(Utility.today());
        }
        existLeaveRequest.setVersion(leaveRequest.getVersion());
        existLeaveRequest.setLastModifiedDate(Utility.today());
        boolean res = leaveDao.updateLeaveRequest(existLeaveRequest);
        if(!res){
            //ErrorContext errorContext = new ErrorContext(leaveRequest.getLeaveRequestId(), "Leave", "Leave request update failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
        logger.info("Update leave request success");

        if(mode == 2 && existLeaveRequest.getLeaveStatus().getLeaveStatusName().equals(Constants.APPROVED_LEAVE_REQUEST)){
            EmployeeLeave leaveSummary = leaveDao.getEmployeeLeaveSummary(existLeaveRequest.getEmployee().getEmployeeId());

            if(existLeaveRequest.getLeaveType().getLeaveTypeName().equals(Constants.CASUAL_TYPE_NAME)){
                leaveSummary.setTotalCasualUsed(leaveSummary.getTotalCasualUsed() - existLeaveRequest.getApprovedDaysCount());

            } else if(existLeaveRequest.getLeaveType().getLeaveTypeName().equals(Constants.SICK_TYPE_NAME)){
                leaveSummary.setTotalSickUsed(leaveSummary.getTotalSickUsed() - existLeaveRequest.getApprovedDaysCount());
            }

            leaveSummary.setTotalLeaveUsed(leaveSummary.getTotalCasualUsed() +
                    leaveSummary.getTotalSickUsed() +
                    leaveSummary.getTotalNotNotify() +
                    leaveSummary.getTotalSpecialLeave());

            leaveDao.updateEmployeeLeaveSummary(leaveSummary);
            logger.info("Employee leave summary updated.");
        }
    }

    private void validateInputForUpdate(LeaveRequest leaveRequest, String userId, int mode) throws CustomException {
        if(leaveRequest.getVersion() == 0){
            //ErrorContext errorContext = new ErrorContext(null, "Leave", "Version not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_002);
            throw new CustomException(errorMessage);
        }

        if(!(leaveDao.getLeaveRequestById(leaveRequest.getLeaveRequestId(), null).getLeaveStatus()
                .getLeaveStatusName().equals(Constants.APPLIED_LEAVE_REQUEST) ||
                leaveDao.getLeaveRequestById(leaveRequest.getLeaveRequestId(), null).getLeaveStatus()
                .getLeaveStatusName().equals(Constants.APPROVED_LEAVE_REQUEST))){

            //ErrorContext errorContext = new ErrorContext(userId, "Leave", "Unable to edit this leave request.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0014);
            throw new CustomException(errorMessage);
        }

        if(leaveDao.getLeaveRequestByIdAndEmployeeId(leaveRequest.getLeaveRequestId(), userId) == null){
            //ErrorContext errorContext = new ErrorContext(userId, "Leave", "Leave request not found by this user.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }

        if(mode == 2 && leaveDao.getLeaveRequestById(leaveRequest.getLeaveRequestId(), null).getLeaveStatus()
                .getLeaveStatusName().equals(Constants.APPROVED_LEAVE_REQUEST)){

            if(!Utility.today().before(leaveDao.getLeaveRequestById(leaveRequest.getLeaveRequestId(), null).getApprovedStartDate())){
                //ErrorContext errorContext = new ErrorContext(userId, "Leave", "Unable to cancel this approved leave request.");
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                        Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0015);
                throw new CustomException(errorMessage);
            }
        }
    }

    @Override
    public void deleteLeaveRequest(String leaveRequestID, String userID) throws CustomException {

        if(leaveDao.getLeaveRequestByIdAndEmployeeId(leaveRequestID, userID) == null){
            //ErrorContext errorContext = new ErrorContext(userID, "Leave", "Leave request not found by this user.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }

        boolean res = leaveDao.deleteLeaveRequest(leaveRequestID);
        if(!res){
            //ErrorContext errorContext = new ErrorContext(leaveRequestID, "Leave", "Leave request delete failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
        logger.info("Delete leave request success");
    }

    @Override
    public LeaveRequest getLeaveRequestById(String leaveRequestID, String employeeID) throws CustomException {
        LeaveRequest leaveRequest = leaveDao.getLeaveRequestById(leaveRequestID, employeeID);
        if(leaveRequest == null){
            //ErrorContext errorContext = new ErrorContext(leaveRequestID, "Leave", "Leave request not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        return leaveRequest;
    }

    @Override
    public List<LeaveRequest> getLeaveRequestByEmployeeID(String employeeID) throws CustomException {
        List<LeaveRequest> leaveRequests = leaveDao.getLeaveRequestByEmployeeID(employeeID);
        if(leaveRequests == null){
            //ErrorContext errorContext = new ErrorContext(employeeID, "Leave", "Leave request list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        return leaveRequests;
    }

    @Override
    public List<LeaveRequest> getAllLeaveRequest() throws CustomException {
        List<LeaveRequest> leaveRequests = leaveDao.getAllLeaveRequest();
        if(leaveRequests == null){
            //ErrorContext errorContext = new ErrorContext(null, "Leave", "Leave request list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
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
            //ErrorContext errorContext = new ErrorContext(null, "Leave", "Internal server error.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        return leaveRequests;
    }
}
