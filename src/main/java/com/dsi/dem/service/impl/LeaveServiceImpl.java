package com.dsi.dem.service.impl;

import com.dsi.dem.dao.*;
import com.dsi.dem.dao.impl.*;
import com.dsi.dem.dto.ContextDto;
import com.dsi.dem.dto.LeaveDetailsDto;
import com.dsi.dem.dto.LeaveRequestDto;
import com.dsi.dem.dto.LeaveSummaryDto;
import com.dsi.dem.dto.transformer.LeaveDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.*;
import com.dsi.dem.service.LeaveService;
import com.dsi.dem.service.NotificationService;
import com.dsi.dem.util.*;
import com.google.gson.Gson;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by sabbir on 8/25/16.
 */
public class LeaveServiceImpl extends CommonService implements LeaveService {

    private static final Logger logger = Logger.getLogger(LeaveServiceImpl.class);

    private static final LeaveDtoTransformer LEAVE_DTO_TRANSFORMER = new LeaveDtoTransformer();

    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();
    private static final LeaveDao leaveDao = new LeaveDaoImpl();
    private static final HolidayDao holidayDao = new HolidayDaoImpl();
    private static final AttendanceDao attendanceDao = new AttendanceDaoImpl();
    private static final TeamDao teamDao = new TeamDaoImpl();
    private static final ClientDao clientDao = new ClientDaoImpl();
    private static final NotificationService notificationService = new NotificationServiceImpl();

    @Override
    public boolean isAvailableLeaveTypes(String leaveType, String userId) throws CustomException {
        logger.info("Available leave type for this User id: " + userId);
        Session session = getSession();
        leaveDao.setSession(session);

        boolean result = leaveDao.isAvailableLeaveTypes(leaveType, userId);

        close(session);
        return result;
    }

    @Override
    public LeaveSummaryDto getEmployeeLeaveSummary(String employeeID) throws CustomException {
        logger.info("Read leave summary by employee id: " + employeeID);
        Session session = getSession();
        leaveDao.setSession(session);

        EmployeeLeave employeeLeave = leaveDao.getEmployeeLeaveSummary(employeeID);
        if(employeeLeave == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        Employee employee = employeeDao.getEmployeeByID(employeeID);
        employeeLeave.setEmployee(employee);

        close(session);
        return LEAVE_DTO_TRANSFORMER.getEmployeeLeaveDto(employeeLeave);
    }

    @Override
    public LeaveDetailsDto getEmployeesAllLeaveDetails(String employeeId, String leaveRequestId) throws CustomException {
        logger.info("Read employees all leave details by id: " + employeeId);
        Session session = getSession();
        leaveDao.setSession(session);
        LEAVE_DTO_TRANSFORMER.setSession(session);

        LeaveDetailsDto detailsDto;
        if(leaveRequestId != null) {
            List<LeaveRequest> requestList = new ArrayList<>();
            requestList.add(leaveDao.getLeaveRequestById(leaveRequestId, employeeId));

            detailsDto = LEAVE_DTO_TRANSFORMER.getEmployeesLeaveDetails(
                    leaveDao.getEmployeeLeaveSummary(employeeId), requestList);

        } else {
            detailsDto = LEAVE_DTO_TRANSFORMER.getEmployeesLeaveDetails(leaveDao.getEmployeeLeaveSummary(employeeId),
                    leaveDao.getLeaveRequestByEmployeeID(employeeId));
        }

        close(session);
        return detailsDto;
    }

    @Override
    public List<LeaveSummaryDto> searchOrReadEmployeesLeaveSummary(String employeeNo, String firstName, String lastName, String nickName,
                                                                   String email, String phone, String teamName, String projectName,
                                                                   String employeeId, String context, String from, String range) throws CustomException {
        logger.info("Search or read employees leave summaries");
        Session session = getSession();
        leaveDao.setSession(session);

        ContextDto contextDto = Utility.getContextDtoObj(context);
        //List<String> contextList = Utility.getContextObj(context);
        List<EmployeeLeave> employeeLeaveList = leaveDao.searchOrReadEmployeesLeaveSummary(employeeNo, firstName, lastName,
                nickName, email, phone, teamName, projectName, employeeId, contextDto, from, range);
        if(employeeLeaveList == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        List<LeaveSummaryDto> leaveSummaryList = LEAVE_DTO_TRANSFORMER.getAllEmployeesLeaveDto(employeeLeaveList);
        logger.info("Leave summary list size: " + leaveSummaryList.size());

        close(session);
        return leaveSummaryList;
    }

    @Override
    public LeaveRequest getEmployeesLeaveDetails(String employeeID) throws CustomException {
        Session session = getSession();
        leaveDao.setSession(session);

        LeaveRequest leaveDetails = leaveDao.getEmployeesLeaveDetails(employeeID);
        if(leaveDetails == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        close(session);
        return leaveDetails;
    }

    @Override
    public List<LeaveDetailsDto> searchOrReadLeaveDetails(String userId, String employeeNo, String firstName, String lastName,
                                                          String nickName, String email, String phone, String teamName,
                                                          String projectName, String employeeId, String leaveType, String requestType,
                                                          String approvedStartDate, String approvedEndDate, String approvedFirstName,
                                                          String approvedLastName, String approvedNickName, String appliedStartDate,
                                                          String appliedEndDate, String leaveStatus, String context, String from,
                                                          String range) throws CustomException {

        logger.info("Search or read employees leave details");
        validationDate(appliedStartDate, appliedEndDate, approvedStartDate, approvedEndDate);

        Session session = getSession();
        leaveDao.setSession(session);
        LEAVE_DTO_TRANSFORMER.setSession(session);

        ContextDto contextDto = Utility.getContextDtoObj(context);
        List<LeaveRequest> leaveRequestList = leaveDao.searchOrReadLeaveDetails(userId, employeeNo, firstName, lastName, nickName,
                email, phone, teamName, projectName, employeeId, leaveType, requestType, approvedStartDate, approvedEndDate,
                approvedFirstName, approvedLastName, approvedNickName, appliedStartDate, appliedEndDate, leaveStatus,
                contextDto, from, range);

        if(leaveRequestList == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        List<LeaveDetailsDto> requestDtoList = LEAVE_DTO_TRANSFORMER.getAllEmployeesLeaveDetails(leaveRequestList);
        logger.info("Leave details list size: " + requestDtoList.size());

        close(session);
        return requestDtoList;
    }

    private void validationDate(String appliedStartDate, String appliedEndDate,
                                String approvedStartDate, String approvedEndDate) throws CustomException {

        if(!Utility.isNullOrEmpty(appliedStartDate)){
            validate(appliedStartDate);
        }

        if(!Utility.isNullOrEmpty(appliedEndDate)){
            validate(appliedEndDate);
        }

        if(!Utility.isNullOrEmpty(approvedStartDate)){
            validate(approvedStartDate);
        }

        if(!Utility.isNullOrEmpty(approvedEndDate)){
            validate(approvedEndDate);
        }
    }

    private void validate(String date) throws CustomException {
        if(Utility.getDateFromString(date) == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_004);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public LeaveRequestDto approveLeaveRequest(LeaveRequestDto leaveRequestDto, String userId,
                                               String leaveRequestId, String tenantName) throws CustomException {
        logger.info("Employees leave request approval:: Start");
        logger.info("Approved user id: " + userId);
        if(Utility.isNullOrEmpty(userId)){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }

        LeaveRequest leaveRequest = LEAVE_DTO_TRANSFORMER.getLeaveRequest(leaveRequestDto);

        leaveRequest.setLeaveRequestId(leaveRequestId);
        validateForApproval(leaveRequest);

        Session session = getSession();
        leaveDao.setSession(session);
        clientDao.setSession(session);
        teamDao.setSession(session);
        attendanceDao.setSession(session);
        LEAVE_DTO_TRANSFORMER.setSession(session);

        LeaveRequest existLeaveRequest = leaveDao.getLeaveRequestById(leaveRequest.getLeaveRequestId(), null);
        if(existLeaveRequest == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }

        if(!existLeaveRequest.getLeaveStatus().getLeaveStatusName().equals(Constants.APPLIED_LEAVE_REQUEST)){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0023);
            throw new CustomException(errorMessage);
        }

        LeaveType leaveType = existLeaveRequest.getLeaveType();

        if(leaveRequest.getLeaveStatus().getLeaveStatusName().equals(Constants.APPROVED_LEAVE_REQUEST)) {

            Date approveStartDate = Utility.getDateFormatFromDate(leaveRequest.getApprovedStartDate());
            Date approveEndDate = Utility.getDateFormatFromDate(leaveRequest.getApprovedEndDate());
            Date startDate = Utility.getDateFormatFromDate(existLeaveRequest.getStartDate());
            Date endDate = Utility.getDateFormatFromDate(existLeaveRequest.getEndDate());

            //Check approved dates are in range
            if(!((!startDate.after(approveStartDate) && !endDate.before(approveStartDate))
                && (!startDate.after(approveEndDate) && !endDate.before(approveEndDate)))){

                close(session);
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

        existLeaveRequest.setLastModifiedDate(Utility.today());
        existLeaveRequest.setApprovedDate(Utility.today());
        existLeaveRequest.setLeaveStatus(leaveDao.getLeaveStatusByName(leaveRequest.getLeaveStatus().getLeaveStatusName()));
        existLeaveRequest.setApprovalId(employeeDao.getEmployeeByUserID(userId).getEmployeeId());
        existLeaveRequest.setDeniedReason(leaveRequest.getDeniedReason());

        leaveDao.updateLeaveRequest(existLeaveRequest);
        logger.info("Leave request approved success");

        if(leaveRequest.getLeaveStatus().getLeaveStatusName().equals(Constants.APPROVED_LEAVE_REQUEST)) {
            updateLeaveSummary(existLeaveRequest);
        }

        LeaveRequestDto requestDto = LEAVE_DTO_TRANSFORMER.getLeaveRequestDto(existLeaveRequest);
        logger.info("Employees leave request approval:: End");

        JSONObject globalContentObj;
        JSONArray hrManagerEmailList, leadEmails, membersEmail, clientEmails;
        try{
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();
            String specialLeavReqLink = RoutingConstant.SPECIAL_LEAVE_REQ + leaveRequest.getLeaveRequestId();
            String myLeaveApplicationLink = RoutingConstant.LEAVE_APPLICATION ;
            String demLink = NotificationConstant.WEBSITE_LINK;
            String appprovedLeaveLink = RoutingConstant.APPROVED_LEAVE_DETAIL +
                                        leaveRequest.getEmployee().getEmployeeId() + "/" + leaveRequest.getLeaveRequestId();
            String deniedLeaveReqLink = RoutingConstant.DENIED_LEAVE_SUMMARY + leaveRequest.getEmployee().getEmployeeId();

            String email = employeeDao.getPreferredEmail(existLeaveRequest.getEmployee().getEmployeeId()).getEmail();
            hrManagerEmailList = notificationService.getHrManagerEmailList();
            if(leaveRequest.getLeaveStatus().getLeaveStatusName().equals(Constants.APPROVED_LEAVE_REQUEST)){

                leadEmails = new JSONArray();
                membersEmail = new JSONArray();
                clientEmails = new JSONArray();

                List<TeamMember> teamMemberList = teamDao.getTeamMemberByEmployeeId(existLeaveRequest.getEmployee().getEmployeeId());

                if(!Utility.isNullOrEmpty(teamMemberList)){
                    logger.info("Team member list size:: " + teamMemberList.size());

                    for(TeamMember member : teamMemberList){

                        String emails = employeeDao.getPreferredEmail(member.getEmployee().getEmployeeId()).getEmail();
                        if(member.getRole().getRoleName().equals(RoleName.LEAD.getValue())){
                            leadEmails.put(emails);

                        } else if(member.getRole().getRoleName().equals(RoleName.MEMBER.getValue())){
                            membersEmail.put(emails);
                        }
                    }
                }

                if(leaveRequest.isClientNotify()) {
                    List<Client> clientList = clientDao.getAllClientsByEmployeeId(existLeaveRequest.getEmployee().getEmployeeId());

                    if (!Utility.isNullOrEmpty(clientList)) {
                        for (Client client : clientList) {
                            if (client.isNotify()) {
                                clientEmails.put(client.getMemberEmail());
                            }
                        }
                    }
                }

                if(leaveType.getType().equals(Constants.SPECIAL_LEAVE_TYPE)){
                    globalContentObj = EmailContent.getContentForApproveSpecialLeaveRequest(existLeaveRequest, tenantName,
                            new JSONArray().put(email), myLeaveApplicationLink);
                    notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                            NotificationConstant.SPECIAL_LEAVE_APPROVE_TEMPLATE_ID_FOR_EMPLOYEE));

                    globalContentObj = EmailContent.getContentForApproveSpecialLeaveRequest(existLeaveRequest, tenantName, hrManagerEmailList, specialLeavReqLink);
                    notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                            NotificationConstant.SPECIAL_LEAVE_APPROVE_TEMPLATE_ID_FOR_MANAGER_HR));

                    if(leadEmails.length() > 0) {
                        globalContentObj = EmailContent.getContentForApproveSpecialLeaveRequest(existLeaveRequest, tenantName, leadEmails, appprovedLeaveLink);
                        notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                                NotificationConstant.SPECIAL_LEAVE_APPROVE_TEMPLATE_ID_FOR_LEAD));
                    }

                    if(membersEmail.length() > 0){
                        globalContentObj = EmailContent.getContentForApproveSpecialLeaveRequest(existLeaveRequest, tenantName, membersEmail, myLeaveApplicationLink);
                        notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                                NotificationConstant.SPECIAL_LEAVE_APPROVE_TEMPLATE_ID_FOR_MEMBERS));
                    }

                    if(clientEmails.length() > 0){
                        globalContentObj = EmailContent.getContentForApproveSpecialLeaveRequest(existLeaveRequest, tenantName, clientEmails, demLink);
                        notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                                NotificationConstant.SPECIAL_LEAVE_APPROVE_TEMPLATE_ID_FOR_CLIENT));
                    }

                } else {
                    globalContentObj = EmailContent.getContentForApproveLeaveRequest(existLeaveRequest,
                            tenantName, new JSONArray().put(email), myLeaveApplicationLink);
                    notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                            NotificationConstant.LEAVE_APPROVE_TEMPLATE_ID_FOR_EMPLOYEE));

                    globalContentObj = EmailContent.getContentForApproveLeaveRequest(existLeaveRequest, tenantName, hrManagerEmailList, appprovedLeaveLink);
                    notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                            NotificationConstant.LEAVE_APPROVE_TEMPLATE_ID_FOR_MANAGER_HR));

                    if(leadEmails.length() > 0) {
                        globalContentObj = EmailContent.getContentForApproveLeaveRequest(existLeaveRequest, tenantName, leadEmails, appprovedLeaveLink);
                        notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                                NotificationConstant.LEAVE_APPROVE_TEMPLATE_ID_FOR_LEAD));
                    }

                    if(clientEmails.length() > 0){
                        globalContentObj = EmailContent.getContentForApproveLeaveRequest(existLeaveRequest, tenantName, clientEmails, demLink);
                        notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                                NotificationConstant.LEAVE_APPROVE_TEMPLATE_ID_FOR_CLIENT));
                    }
                }

            } else if (leaveRequest.getLeaveStatus().getLeaveStatusName().equals(Constants.DENIED_LEAVE_REQUEST)) {


                if(leaveType.getType().equals(Constants.SPECIAL_LEAVE_TYPE)){
                    globalContentObj = EmailContent.getContentForApplySpecialLeaveRequest(existLeaveRequest, tenantName, hrManagerEmailList, specialLeavReqLink);
                    notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                            NotificationConstant.SPECIAL_LEAVE_DENIED_TEMPLATE_ID_FOR_MANAGER_HR));

                    globalContentObj = EmailContent.getContentForApplyLeaveRequest(existLeaveRequest, tenantName,
                            new JSONArray().put(email), myLeaveApplicationLink);
                    notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                            NotificationConstant.SPECIAL_LEAVE_DENIED_TEMPLATE_ID_FOR_EMPLOYEE));

                } else {
                    globalContentObj = EmailContent.getContentForApplyLeaveRequest(existLeaveRequest, tenantName, hrManagerEmailList, deniedLeaveReqLink);
                    notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                            NotificationConstant.LEAVE_DENIED_TEMPLATE_ID_FOR_MANAGER_HR));

                    globalContentObj = EmailContent.getContentForApplyLeaveRequest(existLeaveRequest, tenantName,
                            new JSONArray().put(email), myLeaveApplicationLink);
                    notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                            NotificationConstant.LEAVE_DENIED_TEMPLATE_ID_FOR_EMPLOYEE));
                }
            }

            close(session);
            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }

        return requestDto;
    }

    private void validateForApproval(LeaveRequest leaveRequest) throws CustomException {
        if(leaveRequest.getLeaveStatus().getLeaveStatusName().equals(Constants.APPROVED_LEAVE_REQUEST)) {
            if (leaveRequest.getApprovedStartDate() == null) {
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                        Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0007);
                throw new CustomException(errorMessage);
            }

            if (leaveRequest.getApprovedEndDate() == null) {
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                        Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0008);
                throw new CustomException(errorMessage);
            }

        } else if(leaveRequest.getLeaveStatus().getLeaveStatusName().equals(Constants.DENIED_LEAVE_REQUEST)){
            if (leaveRequest.getDeniedReason() == null) {
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                        Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0016);
                throw new CustomException(errorMessage);
            }
        }
    }

    private void updateLeaveSummary(LeaveRequest existLeaveRequest) throws CustomException {
        EmployeeLeave leaveSummary = leaveDao.getEmployeeLeaveSummary(existLeaveRequest.getEmployee().getEmployeeId());

        int result = 0;
        if(existLeaveRequest.getRequestType().getLeaveRequestTypeName().equals(Constants.POST_REQUEST_TYPE_NAME)){
            logger.info("Post request type leave application.");
            logger.info("Check un-notified attendances.");

            result = attendanceDao.getAttendanceCountByIdAndDate(existLeaveRequest.getEmployee().getEmployeeId(),
                    existLeaveRequest.getApprovedStartDate(), existLeaveRequest.getApprovedEndDate());

            logger.info("Total un-notified attendance count: " + result);
        }
        leaveSummary.setTotalNotNotify(leaveSummary.getTotalNotNotify() - result);

        switch (existLeaveRequest.getLeaveType().getLeaveTypeName()) {
            case Constants.CASUAL_TYPE_NAME:
                leaveSummary.setTotalCasualUsed(leaveSummary.getTotalCasualUsed() +
                        Utility.getDaysBetween(existLeaveRequest.getStartDate(), existLeaveRequest.getEndDate()) + 1);

                break;
            case Constants.SICK_TYPE_NAME:
                leaveSummary.setTotalSickUsed(leaveSummary.getTotalSickUsed() +
                        Utility.getDaysBetween(existLeaveRequest.getStartDate(), existLeaveRequest.getEndDate()) + 1);

                break;
            case Constants.SPECIAL_TYPE_NAME:
                leaveSummary.setTotalSpecialLeave(leaveSummary.getTotalSpecialLeave() +
                        Utility.getDaysBetween(existLeaveRequest.getStartDate(), existLeaveRequest.getEndDate()) + 1);
                break;
        }

        leaveSummary.setTotalLeaveUsed(leaveSummary.getTotalCasualUsed() +
                leaveSummary.getTotalSickUsed() +
                leaveSummary.getTotalNotNotify() +
                leaveSummary.getTotalSpecialLeave());

        leaveDao.updateEmployeeLeaveSummary(leaveSummary);
        logger.info("Employee leave summary updated.");
    }

    @Override
    public LeaveRequestDto saveLeaveRequest(LeaveRequestDto leaveRequestDto, String userId,
                                            String tenantName) throws CustomException {

        logger.info("Employees leave request create:: Start");
        logger.info("Apply user ID: " + userId);
        if(Utility.isNullOrEmpty(userId)){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }

        LeaveRequest leaveRequest = LEAVE_DTO_TRANSFORMER.getLeaveRequest(leaveRequestDto);

        Session session = getSession();
        leaveDao.setSession(session);
        holidayDao.setSession(session);
        LEAVE_DTO_TRANSFORMER.setSession(session);

        validateInputForCreation(leaveRequest, userId, session);

        leaveRequest.setLeaveType(leaveDao.getLeaveTypeByID(leaveRequest.getLeaveType().getLeaveTypeId()));
        leaveRequest.setLeaveStatus(leaveDao.getLeaveStatusByName(Constants.APPLIED_LEAVE_REQUEST));
        leaveRequest.setDaysCount(Utility.getDaysBetween(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1);
        leaveRequest.setEmployee(employeeDao.getEmployeeByUserID(userId));
        leaveRequest.setApplyDate(Utility.today());
        leaveRequest.setLastModifiedDate(Utility.today());
        leaveRequest.setVersion(1);
        leaveDao.saveLeaveRequest(leaveRequest);
        logger.info("Save leave request success");
        logger.info("Employees leave request create:: End");

        LeaveRequestDto requestDto = LEAVE_DTO_TRANSFORMER.getLeaveRequestDto(leaveRequest);
        close(session);

        try{
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();
            String leaveRequestLink = RoutingConstant.LEAVE_REQUEST_PENDING + leaveRequest.getEmployee().getEmployeeId() + "/" + leaveRequest.getLeaveRequestId();

            JSONArray emailList = notificationService.getHrManagerEmailList();
            JSONObject globalContentObj = EmailContent.getContentForApplyLeaveRequest(leaveRequest, tenantName, emailList, leaveRequestLink);
            notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                    NotificationConstant.LEAVE_APPLY_TEMPLATE_ID_FOR_MANAGER_HR));

            List<Employee> teamLeads = employeeDao.getTeamLeadsProfileOfAnEmployee(leaveRequest.getEmployee().getEmployeeId());
            if(!Utility.isNullOrEmpty(teamLeads)){

                emailList = new JSONArray();
                for(Employee teamLead : teamLeads){
                    emailList.put(employeeDao.getPreferredEmail(teamLead.getEmployeeId()).getEmail());
                }

                globalContentObj = EmailContent.getContentForApplyLeaveRequest(leaveRequest, tenantName, emailList, leaveRequestLink);
                notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                        NotificationConstant.LEAVE_APPLY_TEMPLATE_ID_FOR_LEAD));
            }

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }

        return requestDto;
    }

    private void validateInputForCreation(LeaveRequest leaveRequest, String userId, Session session) throws CustomException {

        if(leaveRequest.getLeaveType().getLeaveTypeId() == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0004);
            throw new CustomException(errorMessage);
        }

        if(leaveRequest.getRequestType().getLeaveRequestTypeId() == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0005);
            throw new CustomException(errorMessage);
        }

        if(leaveRequest.getStartDate() == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0009);
            throw new CustomException(errorMessage);
        }

        if(leaveRequest.getEndDate() == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0010);
            throw new CustomException(errorMessage);
        }

        //Holiday check.
        if(holidayDao.checkHolidayFromRangeAndYear(leaveRequest.getStartDate(), leaveRequest.getEndDate(),
                Utility.getYearFromDate(leaveRequest.getStartDate()))){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_HOLIDAY_ERROR_TYPE_0004);
            throw new CustomException(errorMessage);
        }

        //Weekend check
        if(Utility.getWeekendBetweenDate(leaveRequest.getStartDate(), leaveRequest.getEndDate())){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_HOLIDAY_ERROR_TYPE_0005);
            throw new CustomException(errorMessage);
        }

        //Already applied/approved WFH check
        Employee employee = employeeDao.getEmployeeByUserID(userId);
        if(leaveDao.checkWFHRequest(employee.getEmployeeId(), leaveRequest.getStartDate(), leaveRequest.getEndDate())){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0022);
            throw new CustomException(errorMessage);
        }

        //Applied leave request more than 0 check
        if(leaveDao.getLeaveCountByStatus(employee.getEmployeeId(), Constants.APPLIED_LEAVE_REQUEST) > 0){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }

        //Approved leave request check
        if(leaveDao.alreadyApprovedLeaveExist(employee.getEmployeeId(), leaveRequest.getStartDate())){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0019);
            throw new CustomException(errorMessage);
        }

        LeaveRequestType requestType = leaveDao.getLeaveRequestTypeByID(leaveRequest.getRequestType().getLeaveRequestTypeId());
        LeaveType leaveType = leaveDao.getLeaveTypeByID(leaveRequest.getLeaveType().getLeaveTypeId());
        EmployeeLeave leaveSummary = leaveDao.getEmployeeLeaveSummary(employee.getEmployeeId());

        if(requestType.getLeaveRequestTypeName().equals(Constants.POST_REQUEST_TYPE_NAME)){
            logger.info("Post leave request application.");

            if(!leaveType.getLeaveTypeName().equals(Constants.CASUAL_TYPE_NAME)){
                close(session);
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                        Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0003);
                throw new CustomException(errorMessage);
            }

            logger.info("Casual type leave application.");
            if((leaveSummary.getTotalCasual() - leaveSummary.getTotalCasualUsed())
                    < (Utility.getDaysBetween(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1)){
                close(session);
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                        Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0017);
                throw new CustomException(errorMessage);
            }

            if(Utility.getDaysBetween(leaveRequest.getEndDate(), Utility.getDateFormatFromDate(Utility.today())) + 1 >= 5){
                close(session);
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                        Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0011);
                throw new CustomException(errorMessage);
            }
        }

        if(requestType.getLeaveRequestTypeName().equals(Constants.PRE_REQUEST_TYPE_NAME)){
            logger.info("Pre leave request application.");

            if(leaveType.getLeaveTypeName().equals(Constants.CASUAL_TYPE_NAME)){

                logger.info("Casual type leave application.");
                if((leaveSummary.getTotalCasual() - leaveSummary.getTotalCasualUsed())
                        < (Utility.getDaysBetween(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1)){
                    close(session);
                    ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                            Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0017);
                    throw new CustomException(errorMessage);
                }

                if(Utility.getDaysBetween(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1 >= 3){

                    if(Utility.getDaysBetween(Utility.getDateFormatFromDate(Utility.today()), leaveRequest.getStartDate()) + 1 < 14){
                        close(session);
                        ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                                Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0012);
                        throw new CustomException(errorMessage);
                    }
                }
            }

            if(leaveType.getLeaveTypeName().equals(Constants.SICK_TYPE_NAME)){

                logger.info("Sick type leave application.");
                if((leaveSummary.getTotalSick() - leaveSummary.getTotalSickUsed())
                        < (Utility.getDaysBetween(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1)){
                    close(session);
                    ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                            Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0018);
                    throw new CustomException(errorMessage);
                }

                if(!(Utility.getDaysBetween(Utility.getDateFormatFromDate(Utility.today()), leaveRequest.getStartDate()) > 0)) {

                    if (Utility.isGreater02PM(leaveRequest.getStartDate())) {
                        close(session);
                        ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                                Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0013);
                        throw new CustomException(errorMessage);
                    }
                }
            }
        }
    }

    @Override
    public LeaveRequestDto updateLeaveRequest(LeaveRequestDto leaveRequestDto, String userId,
                                              String leaveRequestId, String tenantName) throws CustomException {
        logger.info("Employees leave request update:: Start");
        if(Utility.isNullOrEmpty(userId)){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }

        LeaveRequest leaveRequest = LEAVE_DTO_TRANSFORMER.getLeaveRequest(leaveRequestDto);

        Session session = getSession();
        leaveDao.setSession(session);
        LEAVE_DTO_TRANSFORMER.setSession(session);

        int mode = leaveRequestDto.getMode();
        leaveRequest.setLeaveRequestId(leaveRequestId);
        validateInputForUpdate(leaveRequest, userId, mode, session);

        LeaveRequest existLeaveRequest = leaveDao.getLeaveRequestById(leaveRequest.getLeaveRequestId(), null);
        String statusName = existLeaveRequest.getLeaveStatus().getLeaveStatusName();

        if(mode == 1){
            logger.info("Edit mode.");
            existLeaveRequest.setStartDate(leaveRequest.getStartDate());
            existLeaveRequest.setEndDate(leaveRequest.getEndDate());
            existLeaveRequest.setDaysCount(Utility.getDaysBetween(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1);
            existLeaveRequest.setLeaveType(leaveDao.getLeaveTypeByID(leaveRequest.getLeaveType().getLeaveTypeId()));
            existLeaveRequest.setRequestType(leaveDao.getLeaveRequestTypeByID(leaveRequest.getRequestType().getLeaveRequestTypeId()));
            existLeaveRequest.setLeaveReason(leaveRequest.getLeaveReason());

        } else if(mode == 2){
            existLeaveRequest.setDeniedReason(leaveRequest.getLeaveReason());
            existLeaveRequest.setLeaveStatus(leaveDao.getLeaveStatusByName(Constants.CANCELLER_LEAVE_REQUEST));
            existLeaveRequest.setApprovalId(employeeDao.getEmployeeByUserID(userId).getEmployeeId());
            existLeaveRequest.setApprovedDate(Utility.today());
        }
        existLeaveRequest.setVersion(leaveRequest.getVersion());
        existLeaveRequest.setLastModifiedDate(Utility.today());
        leaveDao.updateLeaveRequest(existLeaveRequest);
        logger.info("Update leave request success");

        if(mode == 2 && statusName.equals(Constants.APPROVED_LEAVE_REQUEST)){
            logger.info("Cancel approve leave request.");
            EmployeeLeave leaveSummary = leaveDao.getEmployeeLeaveSummary(existLeaveRequest.getEmployee().getEmployeeId());

            switch (existLeaveRequest.getLeaveType().getLeaveTypeName()) {
                case Constants.CASUAL_TYPE_NAME:
                    leaveSummary.setTotalCasualUsed(leaveSummary.getTotalCasualUsed() - existLeaveRequest.getApprovedDaysCount());

                    break;
                case Constants.SICK_TYPE_NAME:
                    leaveSummary.setTotalSickUsed(leaveSummary.getTotalSickUsed() - existLeaveRequest.getApprovedDaysCount());

                    break;
                case Constants.SPECIAL_TYPE_NAME:
                    leaveSummary.setTotalSpecialLeave(leaveSummary.getTotalSpecialLeave() - existLeaveRequest.getApprovedDaysCount());
                    break;
            }

            leaveSummary.setTotalLeaveUsed(leaveSummary.getTotalCasualUsed() +
                    leaveSummary.getTotalSickUsed() +
                    leaveSummary.getTotalNotNotify() +
                    leaveSummary.getTotalSpecialLeave());

            leaveDao.updateEmployeeLeaveSummary(leaveSummary);
            logger.info("Employee leave summary updated.");
        }
        logger.info("Employees leave request update:: End");
        LeaveRequestDto requestDto = LEAVE_DTO_TRANSFORMER.getLeaveRequestDto(existLeaveRequest);

        logger.info("Notification create:: Start");
        JSONObject globalContentObj;
        JSONArray hrManagerEmailList, leadEmails, memberEmails;

        try{
            JSONArray notificationList = new JSONArray();
            String pendingLeaveRequestLink = RoutingConstant.LEAVE_REQUEST_PENDING + leaveRequest.getEmployee().getEmployeeId() + "/" + leaveRequest.getLeaveRequestId();
            String appprovedLeaveLink = RoutingConstant.APPROVED_LEAVE_DETAIL +
                    leaveRequest.getEmployee().getEmployeeId() + "/" + leaveRequest.getLeaveRequestId();
            String myLeaveApplicationLink = RoutingConstant.LEAVE_APPLICATION ;
            String demLink = NotificationConstant.WEBSITE_LINK;

            leadEmails = new JSONArray();
            memberEmails = new JSONArray();
            hrManagerEmailList = notificationService.getHrManagerEmailList();
            globalContentObj = EmailContent.getContentForApplyLeaveRequest(existLeaveRequest, tenantName, hrManagerEmailList, pendingLeaveRequestLink);

            teamDao.setSession(session);
            clientDao.setSession(session);
            List<TeamMember> teamMemberList = teamDao.getTeamMemberByEmployeeId(existLeaveRequest.getEmployee().getEmployeeId());

            if(!Utility.isNullOrEmpty(teamMemberList)){

                for(TeamMember member : teamMemberList){

                    if(member.getRole().getRoleName().equals(RoleName.LEAD.getValue())){
                        leadEmails.put(employeeDao.getPreferredEmail(member.getEmployee().getEmployeeId()).getEmail());

                    } else if(member.getRole().getRoleName().equals(RoleName.MEMBER.getValue())){
                        memberEmails.put(employeeDao.getPreferredEmail(member.getEmployee().getEmployeeId()).getEmail());
                    }
                }
            }

            JSONObject leadContentObj = EmailContent.getContentForApplyLeaveRequest(existLeaveRequest, tenantName, leadEmails, pendingLeaveRequestLink);

            if(mode == 1) {
                notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                        NotificationConstant.PENDING_EDIT_TEMPLATE_ID_FOR_MANAGER_HR));

                if(leadEmails.length() > 0) {
                    notificationList.put(EmailContent.getNotificationObject(leadContentObj,
                            NotificationConstant.PENDING_EDIT_TEMPLATE_ID_FOR_LEAD));
                }

            } else if(mode == 2 && !existLeaveRequest.getLeaveStatus().getLeaveStatusName().equals(Constants.APPROVED_LEAVE_REQUEST)){
                notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                        NotificationConstant.PENDING_CANCEL_TEMPLATE_ID_FOR_MANAGER_HR));

                if(leadEmails.length() > 0) {
                    notificationList.put(EmailContent.getNotificationObject(leadContentObj,
                            NotificationConstant.PENDING_CANCEL_TEMPLATE_ID_FOR_LEAD));
                }

            } else if(mode == 2 && existLeaveRequest.getLeaveStatus().getLeaveStatusName().equals(Constants.APPROVED_LEAVE_REQUEST)){
                globalContentObj = EmailContent.getContentForApproveLeaveRequest(existLeaveRequest, tenantName, hrManagerEmailList, appprovedLeaveLink);
                notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                        NotificationConstant.PENDING_APPROVE_CANCEL_TEMPLATE_ID_FOR_MANAGER_HR));

                if(leadEmails.length() > 0) {
                    globalContentObj = EmailContent.getContentForApproveLeaveRequest(existLeaveRequest, tenantName, leadEmails, appprovedLeaveLink);
                    notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                            NotificationConstant.PENDING_APPROVE_CANCEL_TEMPLATE_ID_FOR_LEAD));
                }

                if(memberEmails.length() > 0) {
                    globalContentObj = EmailContent.getContentForApproveLeaveRequest(existLeaveRequest, tenantName, memberEmails, myLeaveApplicationLink);
                    notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                            NotificationConstant.PENDING_APPROVE_CANCEL_TEMPLATE_ID_FOR_MEMBERS));
                }

                if(leaveRequest.isClientNotify()){
                    List<Client> clientList = clientDao.getAllClientsByEmployeeId(existLeaveRequest.getEmployee().getEmployeeId());

                    if(!Utility.isNullOrEmpty(clientList)){
                        JSONArray emailList = new JSONArray();
                        for(Client client : clientList){
                            if(client.isNotify()) {
                                emailList.put(client.getMemberEmail());
                            }
                        }

                        if(emailList.length() > 0) {
                            globalContentObj = EmailContent.getContentForApproveLeaveRequest(existLeaveRequest, tenantName, emailList, demLink);
                            notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                                    NotificationConstant.PENDING_APPROVE_CANCEL_TEMPLATE_ID_FOR_CLIENT));
                        }
                    }
                }
            }
            close(session);
            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }

        return requestDto;
    }

    private void validateInputForUpdate(LeaveRequest leaveRequest, String userId,
                                        int mode, Session session) throws CustomException {
        if(leaveRequest.getVersion() == 0){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_002);
            throw new CustomException(errorMessage);
        }

        LeaveRequest existLeaveRequest = leaveDao.getLeaveRequestById(leaveRequest.getLeaveRequestId(), null);
        LeaveStatus leaveStatus = existLeaveRequest.getLeaveStatus();

        if(!(leaveStatus.getLeaveStatusName().equals(Constants.APPLIED_LEAVE_REQUEST) ||
                leaveStatus.getLeaveStatusName().equals(Constants.APPROVED_LEAVE_REQUEST))){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0014);
            throw new CustomException(errorMessage);
        }

        if(leaveDao.getLeaveRequestByIdAndEmployeeId(leaveRequest.getLeaveRequestId(), userId) == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }

        if(mode == 2 && leaveStatus.getLeaveStatusName().equals(Constants.APPROVED_LEAVE_REQUEST)){

            if(!Utility.today().before(existLeaveRequest.getApprovedStartDate())){
                close(session);
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                        Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0015);
                throw new CustomException(errorMessage);
            }
        }
    }

    @Override
    public void deleteLeaveRequest(String leaveRequestID, String userID) throws CustomException {
        logger.info("Employees leave request delete:: Start");
        Session session = getSession();
        leaveDao.setSession(session);

        if(leaveDao.getLeaveRequestByIdAndEmployeeId(leaveRequestID, userID) == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }

        leaveDao.deleteLeaveRequest(leaveRequestID);
        logger.info("Delete leave request success");
        logger.info("Employees leave request delete:: End");

        close(session);
    }

    @Override
    public LeaveRequest getLeaveRequestById(String leaveRequestID, String employeeID) throws CustomException {
        Session session = getSession();
        leaveDao.setSession(session);

        LeaveRequest leaveRequest = leaveDao.getLeaveRequestById(leaveRequestID, employeeID);
        if(leaveRequest == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        close(session);
        return leaveRequest;
    }

    @Override
    public List<LeaveRequest> getLeaveRequestByEmployeeID(String employeeID) throws CustomException {
        Session session = getSession();
        leaveDao.setSession(session);

        List<LeaveRequest> leaveRequests = leaveDao.getLeaveRequestByEmployeeID(employeeID);
        if(leaveRequests == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        logger.info("Leave requests list size: " + leaveRequests.size());

        close(session);
        return leaveRequests;
    }

    @Override
    public List<LeaveRequest> getAllLeaveRequest() throws CustomException {
        Session session = getSession();
        leaveDao.setSession(session);

        List<LeaveRequest> leaveRequests = leaveDao.getAllLeaveRequest();
        if(leaveRequests == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        logger.info("Leave requests list size: " + leaveRequests.size());

        close(session);
        return leaveRequests;
    }

    @Override
    public List<LeaveRequestDto> searchOrReadLeaveRequests(String userId, String teamName, String projectName, String leaveCnt, String leaveReason,
                                                           String leaveType, String leaveStatus, String requestType, String appliedStartDate,
                                                           String appliedEndDate, String deniedReason, String deniedBy, String leaveRequestId,
                                                           String from, String range) throws CustomException {

        logger.info("Search or read employees leave requests by user id: " + userId);
        validationDate(appliedStartDate, appliedEndDate, null, null);

        Session session = getSession();
        leaveDao.setSession(session);
        LEAVE_DTO_TRANSFORMER.setSession(session);

        List<LeaveRequest> leaveRequests = leaveDao.searchOrReadLeaveRequests(userId, teamName, projectName, leaveCnt, leaveReason, leaveType, leaveStatus,
                requestType, appliedStartDate, appliedEndDate, deniedReason, deniedBy, leaveRequestId, from, range);
        if(leaveRequests == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        List<LeaveRequestDto> requestDtoList = LEAVE_DTO_TRANSFORMER.getAllLeaveRequestDto(leaveRequests);
        logger.info("Leave requests list size: " + requestDtoList.size());

        close(session);
        return requestDtoList;
    }

    @Override
    public LeaveRequestDto saveSpecialLeave(LeaveRequestDto specialLeaveDto, String tenantName) throws CustomException {
        logger.info("Special leave application create:: Start");
        logger.info("Convert LeaveRequest Dto to LeaveRequest Object.");
        LeaveRequest leaveRequest = LEAVE_DTO_TRANSFORMER.getLeaveRequest(specialLeaveDto);

        Session session = getSession();
        leaveDao.setSession(session);
        LEAVE_DTO_TRANSFORMER.setSession(session);

        validateInputForSpecialLeaveCreation(leaveRequest, specialLeaveDto.getEmployeeId(), session);

        leaveRequest.setLeaveType(leaveDao.getLeaveTypeByID(leaveRequest.getLeaveType().getLeaveTypeId()));
        leaveRequest.setLeaveStatus(leaveDao.getLeaveStatusByName(Constants.APPLIED_LEAVE_REQUEST));
        leaveRequest.setDaysCount(Utility.getDaysBetween(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1);
        leaveRequest.setEmployee(employeeDao.getEmployeeByID(specialLeaveDto.getEmployeeId()));
        leaveRequest.setApplyDate(Utility.today());
        leaveRequest.setLastModifiedDate(Utility.today());
        leaveRequest.setVersion(1);
        leaveDao.saveLeaveRequest(leaveRequest);
        logger.info("Save leave request success");

        LeaveRequestDto requestDto = LEAVE_DTO_TRANSFORMER.getLeaveRequestDto(leaveRequest);
        logger.info("Employees special leave request create:: End");
        close(session);

        try{
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();
            String specialLeavReqLink = RoutingConstant.SPECIAL_LEAVE_REQ + leaveRequest.getLeaveRequestId();
            String myLeaveApplicationLink = RoutingConstant.LEAVE_APPLICATION ;
            String employeeLeaveReqSummaryLink = RoutingConstant.DENIED_LEAVE_SUMMARY + leaveRequest.getEmployee().getEmployeeId();

            String email = employeeDao.getPreferredEmail(leaveRequest.getEmployee().getEmployeeId()).getEmail();
            JSONObject globalContentObj = EmailContent.getContentForApplySpecialLeaveRequest(leaveRequest, tenantName,
                    new JSONArray().put(email), myLeaveApplicationLink);
            notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                    NotificationConstant.SPECIAL_LEAVE_APPLY_TEMPLATE_ID_FOR_EMPLOYEE));

            JSONArray emailList = notificationService.getHrManagerEmailList();
            globalContentObj = EmailContent.getContentForApplySpecialLeaveRequest(leaveRequest, tenantName, emailList, specialLeavReqLink);
            notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                    NotificationConstant.SPECIAL_LEAVE_APPLY_TEMPLATE_ID_FOR_MANAGER_HR));

            List<Employee> teamLeads = employeeDao.getTeamLeadsProfileOfAnEmployee(leaveRequest.getEmployee().getEmployeeId());
            if(!Utility.isNullOrEmpty(teamLeads)){

                emailList = new JSONArray();
                for(Employee teamLead : teamLeads){
                    emailList.put(employeeDao.getPreferredEmail(teamLead.getEmployeeId()).getEmail());
                }

                globalContentObj = EmailContent.getContentForApplySpecialLeaveRequest(leaveRequest, tenantName, emailList, employeeLeaveReqSummaryLink);
                notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                        NotificationConstant.SPECIAL_LEAVE_APPLY_TEMPLATE_ID_FOR_LEAD));
            }

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }

        return requestDto;
    }

    private void validateInputForSpecialLeaveCreation(LeaveRequest leaveRequest, String employeeId,
                                                      Session session) throws CustomException {
        Employee employee = employeeDao.getEmployeeByID(employeeId);

        if(leaveDao.getLeaveCountByStatus(employee.getEmployeeId(), Constants.APPLIED_LEAVE_REQUEST) > 0){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }

        if(leaveDao.alreadyApprovedLeaveExist(employee.getEmployeeId(), leaveRequest.getStartDate())){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0019);
            throw new CustomException(errorMessage);
        }

        if(leaveRequest.getLeaveType().getLeaveTypeId() == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0004);
            throw new CustomException(errorMessage);
        }

        if(leaveRequest.getRequestType().getLeaveRequestTypeId() == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0005);
            throw new CustomException(errorMessage);
        }

        if(leaveRequest.getStartDate() == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0009);
            throw new CustomException(errorMessage);
        }

        if(leaveRequest.getEndDate() == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0010);
            throw new CustomException(errorMessage);
        }

        LeaveRequestType requestType = leaveDao.getLeaveRequestTypeByID(leaveRequest.getRequestType().getLeaveRequestTypeId());
        LeaveType leaveType = leaveDao.getLeaveTypeByID(leaveRequest.getLeaveType().getLeaveTypeId());
        EmployeeLeave leaveSummary = leaveDao.getEmployeeLeaveSummary(employee.getEmployeeId());

        if(requestType.getLeaveRequestTypeName().equals(Constants.POST_REQUEST_TYPE_NAME)){
            logger.info("Post leave request application.");

            if(!leaveType.getLeaveTypeName().equals(Constants.SICK_TYPE_NAME)){
                close(session);
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                        Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0020);
                throw new CustomException(errorMessage);
            }

            logger.info("Sick type leave application.");
            if((leaveSummary.getTotalSick() - leaveSummary.getTotalSickUsed())
                    < (Utility.getDaysBetween(leaveRequest.getStartDate(), leaveRequest.getEndDate()) + 1)){
                close(session);
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                        Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0018);
                throw new CustomException(errorMessage);
            }
        }

        if(requestType.getLeaveRequestTypeName().equals(Constants.PRE_REQUEST_TYPE_NAME)){
            logger.info("Pre leave request application.");

            if(!leaveType.getLeaveTypeName().equals(Constants.SPECIAL_TYPE_NAME)){
                close(session);
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                        Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0021);
                throw new CustomException(errorMessage);
            }
            logger.info("Special type leave application.");
        }
    }

    @Override
    public LeaveRequestDto updateSpecialLeave(LeaveRequestDto specialLeaveDto, String userId,
                                              String leaveRequestId, String tenantName) throws CustomException {
        logger.info("Special leave request update:: Start");
        logger.info("Convert LeaveRequest Dto to LeaveRequest Object.");
        LeaveRequest leaveRequest = LEAVE_DTO_TRANSFORMER.getLeaveRequest(specialLeaveDto);

        Session session = getSession();
        leaveDao.setSession(session);
        LEAVE_DTO_TRANSFORMER.setSession(session);

        int mode = specialLeaveDto.getMode();
        leaveRequest.setLeaveRequestId(leaveRequestId);
        validateInputForSpecialLeaveUpdate(leaveRequest, session);

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
            existLeaveRequest.setApprovalId(employeeDao.getEmployeeByUserID(userId).getEmployeeId());
            existLeaveRequest.setApprovedDate(Utility.today());
        }
        existLeaveRequest.setVersion(leaveRequest.getVersion());
        existLeaveRequest.setLastModifiedDate(Utility.today());
        leaveDao.updateLeaveRequest(existLeaveRequest);
        logger.info("Update leave request success");

        if(mode == 2 && existLeaveRequest.getLeaveStatus().getLeaveStatusName().equals(Constants.APPROVED_LEAVE_REQUEST)){
            EmployeeLeave leaveSummary = leaveDao.getEmployeeLeaveSummary(existLeaveRequest.getEmployee().getEmployeeId());

            if(existLeaveRequest.getLeaveType().getLeaveTypeName().equals(Constants.CASUAL_TYPE_NAME)){
                leaveSummary.setTotalCasualUsed(leaveSummary.getTotalCasualUsed() - existLeaveRequest.getApprovedDaysCount());

            } else if(existLeaveRequest.getLeaveType().getLeaveTypeName().equals(Constants.SICK_TYPE_NAME)){
                leaveSummary.setTotalSickUsed(leaveSummary.getTotalSickUsed() - existLeaveRequest.getApprovedDaysCount());

            } else if(existLeaveRequest.getLeaveType().getLeaveTypeName().equals(Constants.SPECIAL_TYPE_NAME)){
                leaveSummary.setTotalSpecialLeave(leaveSummary.getTotalSpecialLeave() - existLeaveRequest.getApprovedDaysCount());
            }

            leaveSummary.setTotalLeaveUsed(leaveSummary.getTotalCasualUsed() +
                    leaveSummary.getTotalSickUsed() +
                    leaveSummary.getTotalNotNotify() +
                    leaveSummary.getTotalSpecialLeave());

            leaveDao.updateEmployeeLeaveSummary(leaveSummary);
            logger.info("Employee leave summary updated.");
        }

        LeaveRequestDto requestDto = LEAVE_DTO_TRANSFORMER.getLeaveRequestDto(existLeaveRequest);
        logger.info("Special leave request update:: End");
        close(session);

        logger.info("Notification create:: Start");
        JSONObject globalContentObj, employeeContentObj;
        JSONArray hrManagerEmailList, leadEmails;
        try{
            JSONArray notificationList = new JSONArray();
            String employeeLeaveAppLink = RoutingConstant.LEAVE_APPLICATION;
            String specialLeaveReqLink = RoutingConstant.SPECIAL_LEAVE_REQ + leaveRequest.getLeaveRequestId();
            String leaveSummaryLink = RoutingConstant.LEAVE_SUMMARY + leaveRequest.getEmployee().getEmployeeId();
            String demLink = NotificationConstant.WEBSITE_LINK;

            String email = employeeDao.getPreferredEmail(existLeaveRequest.getEmployee().getEmployeeId()).getEmail();
            employeeContentObj = EmailContent.getContentForApplySpecialLeaveRequest(existLeaveRequest, tenantName,
                    new JSONArray().put(email), employeeLeaveAppLink);

            leadEmails = new JSONArray();
            hrManagerEmailList = notificationService.getHrManagerEmailList();
            globalContentObj = EmailContent.getContentForApplySpecialLeaveRequest(existLeaveRequest, tenantName, hrManagerEmailList, specialLeaveReqLink);

            List<Employee> leadList = employeeDao.getTeamLeadsProfileOfAnEmployee(existLeaveRequest.getEmployee().getEmployeeId());
            if(!Utility.isNullOrEmpty(leadList)){

                for(Employee lead : leadList){
                    leadEmails.put(employeeDao.getPreferredEmail(lead.getEmployeeId()).getEmail());
                }
            }

            JSONObject leadContentObj = EmailContent.getContentForApplySpecialLeaveRequest(existLeaveRequest, tenantName, leadEmails, leaveSummaryLink);

            if(mode == 1) {
                notificationList.put(EmailContent.getNotificationObject(employeeContentObj,
                        NotificationConstant.PENDING_SPECIAL_LEAVE_TEMPLATE_ID_FOR_EMPLOYEE));

                notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                        NotificationConstant.PENDING_SPECIAL_LEAVE_TEMPLATE_ID_FOR_MANAGER_HR));

                if(leadEmails.length() > 0) {
                    notificationList.put(EmailContent.getNotificationObject(leadContentObj,
                            NotificationConstant.PENDING_SPECIAL_LEAVE_TEMPLATE_ID_FOR_LEAD));
                }

            } else if(mode == 2 && !existLeaveRequest.getLeaveStatus().getLeaveStatusName().equals(Constants.APPROVED_LEAVE_REQUEST)){
                notificationList.put(EmailContent.getNotificationObject(employeeContentObj,
                        NotificationConstant.PENDING_SPECIAL_CANCEL_LEAVE_TEMPLATE_ID_FOR_EMPLOYEE));

                notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                        NotificationConstant.PENDING_SPECIAL_CANCEL_LEAVE_TEMPLATE_ID_FOR_MANAGER_HR));

                if(leadEmails.length() > 0) {
                    notificationList.put(EmailContent.getNotificationObject(leadContentObj,
                            NotificationConstant.PENDING_SPECIAL_CANCEL_LEAVE_TEMPLATE_ID_FOR_LEAD));
                }

            } else if(mode == 2 && existLeaveRequest.getLeaveStatus().getLeaveStatusName().equals(Constants.APPROVED_LEAVE_REQUEST)){
                globalContentObj = EmailContent.getContentForApproveSpecialLeaveRequest(existLeaveRequest, tenantName,
                        new JSONArray().put(email), employeeLeaveAppLink);
                notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                        NotificationConstant.PENDING_SPECIAL_APPROVE_CANCEL_LEAVE_TEMPLATE_ID_FOR_EMPLOYEE));

                globalContentObj = EmailContent.getContentForApproveSpecialLeaveRequest(existLeaveRequest, tenantName, hrManagerEmailList, specialLeaveReqLink);
                notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                        NotificationConstant.PENDING_SPECIAL_APPROVE_CANCEL_LEAVE_TEMPLATE_ID_FOR_MANAGER_HR));

                if(leadEmails.length() > 0) {
                    globalContentObj = EmailContent.getContentForApproveSpecialLeaveRequest(existLeaveRequest, tenantName, leadEmails, leaveSummaryLink);
                    notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                            NotificationConstant.PENDING_SPECIAL_APPROVE_CANCEL_LEAVE_TEMPLATE_ID_FOR_LEAD));
                }

                if(existLeaveRequest.isClientNotify()){
                    session = getSession();
                    clientDao.setSession(session);

                    List<Client> clientList = clientDao.getAllClientsByEmployeeId(existLeaveRequest.getEmployee().getEmployeeId());
                    close(session);

                    if(!Utility.isNullOrEmpty(clientList)){
                        JSONArray emailList = new JSONArray();
                        for(Client client : clientList){
                            if(client.isNotify()) {
                                emailList.put(client.getMemberEmail());
                            }
                        }

                        globalContentObj = EmailContent.getContentForApproveSpecialLeaveRequest(existLeaveRequest, tenantName, emailList, demLink);
                        notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                                NotificationConstant.PENDING_SPECIAL_APPROVE_CANCEL_LEAVE_TEMPLATE_ID_FOR_CLIENT));
                    }
                }
            }

            notificationService.createNotification(notificationList.toString());
            logger.info("Notification create:: End");

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }

        return requestDto;
    }

    private void validateInputForSpecialLeaveUpdate(LeaveRequest leaveRequest, Session session) throws CustomException {
        if(leaveRequest.getVersion() == 0){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_002);
            throw new CustomException(errorMessage);
        }

        LeaveRequest request = leaveDao.getLeaveRequestById(leaveRequest.getLeaveRequestId(), null);
        if(request == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }

        if(!(request.getLeaveStatus().getLeaveStatusName().equals(Constants.APPLIED_LEAVE_REQUEST))){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0014);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteSpecialLeave(String leaveRequestId) throws CustomException {
        logger.info("Employees leave request delete by HR:: Start");
        Session session = getSession();
        leaveDao.setSession(session);

        leaveDao.deleteLeaveRequest(leaveRequestId);
        logger.info("Delete leave request success");
        logger.info("Employees leave request delete by HR:: End");

        close(session);
    }

    @Override
    public List<LeaveDetailsDto> searchOrReadAllSpecialLeave(String employeeNo, String firstName, String lastName,
                                                             String nickName, String leaveStatus, String leaveType, String requestType,
                                                             String leaveRequestId, String from, String range) throws CustomException {
        logger.info("Search or read employees special leave requests.");
        Session session = getSession();
        leaveDao.setSession(session);
        LEAVE_DTO_TRANSFORMER.setSession(session);

        List<LeaveRequest> leaveRequests = leaveDao.searchOrReadSpecialLeaveRequests(employeeNo, firstName, lastName,
                nickName, leaveStatus, leaveType, requestType, leaveRequestId, from, range);
        if(leaveRequests == null){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        List<LeaveDetailsDto> requestDtoList = LEAVE_DTO_TRANSFORMER.getAllEmployeesLeaveDetails(leaveRequests);
        logger.info("Leave requests list size: " + requestDtoList.size());

        close(session);
        return requestDtoList;
    }

    @Override
    public void getPendingLeaveApplication(Date date) {
        Session session = getSession();
        leaveDao.setSession(session);

        List<LeaveRequest> leaveRequests = leaveDao.getPendingLeaveApplication(date);
        try {
            logger.info("Notification create:: Start");
            JSONArray notificationList = new JSONArray();
            String leaveSummaryLink = RoutingConstant.LEAVE_SUMMARY;

            if(!Utility.isNullOrEmpty(leaveRequests)) {
                logger.info("Pending leave requests list size:: " + leaveRequests.size());
                JSONArray emailList;
                JSONObject globalContentObj;

                for(LeaveRequest leaveRequest : leaveRequests){
                    emailList = notificationService.getHrManagerEmailList();

                    List<Employee> teamLeads = employeeDao.getTeamLeadsProfileOfAnEmployee(
                            leaveRequest.getEmployee().getEmployeeId());
                    if(!Utility.isNullOrEmpty(teamLeads)) {
                        for (Employee teamLead : teamLeads) {
                            emailList.put(employeeDao.getPreferredEmail(teamLead.getEmployeeId()).getEmail());
                        }
                    }

                    globalContentObj = EmailContent.getContentForApplyLeaveRequest(leaveRequest,
                            Constants.TENANT_NAME, emailList, leaveSummaryLink);
                    notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                            NotificationConstant.AUTO_NOTIFY_PENDING_LEAVE_TEMPLATE_ID));
                }

                notificationService.createNotification(notificationList.toString());
            }
            logger.info("Notification create:: End");

        } catch (Exception e){
            close(session);
            e.printStackTrace();
        }

        close(session);
    }

    @Override
    public void getApproveLeaveApplication(Date date) {
        Session session = getSession();
        leaveDao.setSession(session);

        List<LeaveRequest> leaveRequests = leaveDao.getApprovedLeaveApplication(date);
        try {
            logger.info("Notification create:: Start");
            String leaveSummaryLink = RoutingConstant.LEAVE_SUMMARY;

            if(!Utility.isNullOrEmpty(leaveRequests)) {
                logger.info("Approved leave requests list size:: " + leaveRequests.size());
                JSONArray notificationList = new JSONArray();
                JSONArray emailList;
                JSONObject globalContentObj;

                for(LeaveRequest leaveRequest : leaveRequests){
                    emailList = new JSONArray();

                    List<Employee> teamLeads = employeeDao.getTeamLeadsProfileOfAnEmployee(
                            leaveRequest.getEmployee().getEmployeeId());
                    if(!Utility.isNullOrEmpty(teamLeads)) {
                        for (Employee teamLead : teamLeads) {
                            emailList.put(employeeDao.getPreferredEmail(teamLead.getEmployeeId()).getEmail());
                        }
                    }

                    globalContentObj = EmailContent.getContentForApproveLeaveRequest(leaveRequest,
                            Constants.TENANT_NAME, emailList, leaveSummaryLink);
                    notificationList.put(EmailContent.getNotificationObject(globalContentObj,
                            NotificationConstant.AUTO_NOTIFY_APPROVED_LEAVE_TEMPLATE_ID));
                }

                notificationService.createNotification(notificationList.toString());
            }
            logger.info("Notification create:: End");

        } catch (Exception e){
            close(session);
            e.printStackTrace();
        }

        close(session);
    }
}
