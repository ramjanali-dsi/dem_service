package com.dsi.dem.dao.impl;

import com.dsi.dem.dao.LeaveDao;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.*;
import com.dsi.dem.service.impl.CommonService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.Utility;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sabbir on 8/25/16.
 */
public class LeaveDaoImpl extends CommonService implements LeaveDao {

    private static final Logger logger = Logger.getLogger(LeaveDaoImpl.class);

    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public boolean isAvailableLeaveTypes(String leaveTypeID, String userId) {
        boolean success = false;
        Query query = session.createQuery("FROM EmployeeLeave el WHERE el.employee.userId =:userId");
        query.setParameter("userId", userId);

        EmployeeLeave leaveSummary = (EmployeeLeave) query.uniqueResult();

        query = session.createQuery("FROM LeaveType lt WHERE lt.leaveTypeId =:leaveTypeID");
        query.setParameter("leaveTypeID", leaveTypeID);

        LeaveType leaveType = (LeaveType) query.uniqueResult();

        if(leaveType.getLeaveTypeName().equals(Constants.CASUAL_TYPE_NAME)){
            if(leaveSummary.getTotalCasualUsed() < Constants.TOTAL_CASUAL){
                success = true;
            }
        }

        if(leaveType.getLeaveTypeName().equals(Constants.SICK_TYPE_NAME)){
            if(leaveSummary.getTotalSickUsed() < Constants.TOTAL_SICK){
                success = true;
            }
        }

        return success;
    }

    @Override
    public EmployeeLeave getEmployeeLeaveSummary(String employeeID) {
        Query query = session.createQuery("FROM EmployeeLeave el WHERE el.employee.employeeId =:employeeId");
        query.setParameter("employeeId", employeeID);

        EmployeeLeave employeeLeave = (EmployeeLeave) query.uniqueResult();
        if(employeeLeave != null){
            return employeeLeave;
        }
        return null;
    }

    @Override
    public void updateEmployeeLeaveSummary(EmployeeLeave leaveSummary) throws CustomException {
        try{
            session.save(leaveSummary);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0019);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public List<EmployeeLeave> searchOrReadEmployeesLeaveSummary(String employeeNo, String firstName, String lastName, String nickName,
                                                                 String email, String phone, String teamName, String projectName,
                                                                 String employeeId, String from, String range, String userId) {

        StringBuilder queryBuilder = new StringBuilder();
        Map<String, String> paramValue = new HashMap<>();
        boolean hasClause = false;

        queryBuilder.append("FROM EmployeeLeave el ");

        if (!Utility.isNullOrEmpty(employeeNo)) {
            queryBuilder.append(" WHERE el.employee.employeeNo like :employeeNo");
            paramValue.put("employeeNo", "%" + employeeNo + "%");
            hasClause = true;
        }

        if (!Utility.isNullOrEmpty(firstName)) {
            if (hasClause) {
                queryBuilder.append(" AND el.employee.firstName like :firstName");

            } else {
                queryBuilder.append(" WHERE el.employee.firstName like :firstName");
                hasClause = true;
            }
            paramValue.put("firstName", "%" + firstName + "%");
        }

        if (!Utility.isNullOrEmpty(lastName)) {
            if (hasClause) {
                queryBuilder.append(" AND el.employee.lastName like :lastName");

            } else {
                queryBuilder.append(" WHERE el.employee.lastName like :lastName");
                hasClause = true;
            }
            paramValue.put("lastName", "%" + lastName + "%");
        }

        if (!Utility.isNullOrEmpty(nickName)) {
            if (hasClause) {
                queryBuilder.append(" AND el.employee.nickName like :nickName");

            } else {
                queryBuilder.append(" WHERE el.employee.nickName like :nickName");
                hasClause = true;
            }
            paramValue.put("nickName", "%" + nickName + "%");
        }

        if (!Utility.isNullOrEmpty(email)) {
            if (hasClause) {
                queryBuilder.append(" AND el.employee.employeeId in (SELECT ee.employee.employeeId FROM EmployeeEmail ee WHERE ee.email like :email)");

            } else {
                queryBuilder.append(" WHERE el.employee.employeeId in (SELECT ee.employee.employeeId FROM EmployeeEmail ee WHERE ee.email like :email)");
                hasClause = true;
            }
            paramValue.put("email", "%" + email + "%");
        }

        if (!Utility.isNullOrEmpty(phone)) {
            if (hasClause) {
                queryBuilder.append(" AND el.employee.employeeId in (SELECT ec.employee.employeeId FROM EmployeeContact ec WHERE ec.phone like :phone)");

            } else {
                queryBuilder.append(" WHERE el.employee.employeeId in (SELECT ec.employee.employeeId FROM EmployeeContact ec WHERE ec.phone like :phone)");
                hasClause = true;
            }
            paramValue.put("phone", "%" + phone + "%");
        }

        if (!Utility.isNullOrEmpty(teamName)) {
            if (hasClause) {
                queryBuilder.append(" AND el.employee.employeeId in (SELECT tm.employee.employeeId FROM TeamMember tm WHERE tm.team.name like :teamName)");

            } else {
                queryBuilder.append(" WHERE el.employee.employeeId in (SELECT tm.employee.employeeId FROM TeamMember tm WHERE tm.team.name like :teamName)");
                hasClause = true;
            }
            paramValue.put("teamName", "%" + teamName + "%");
        }

        if (!Utility.isNullOrEmpty(projectName)) {
            if (hasClause) {
                queryBuilder.append(" AND el.employee.employeeId in (SELECT tm.employee.employeeId FROM TeamMember tm WHERE tm.team.teamId in " +
                        "(SELECT pt.team.teamId FROM ProjectTeam pt WHERE pt.project.projectName like :projectName))");

            } else {
                queryBuilder.append(" WHERE el.employee.employeeId in (SELECT tm.employee.employeeId FROM TeamMember tm WHERE tm.team.teamId in " +
                        "(SELECT pt.team.teamId FROM ProjectTeam pt WHERE pt.project.projectName like :projectName))");
                hasClause = true;
            }
            paramValue.put("projectName", "%" + projectName + "%");
        }

        if (!Utility.isNullOrEmpty(employeeId)) {
            if (hasClause) {
                queryBuilder.append(" AND el.employee.employeeId =:employeeID");

            } else {
                queryBuilder.append(" WHERE el.employee.employeeId =:employeeID");
            }
            paramValue.put("employeeID", employeeId);
        }

        queryBuilder.append(" ORDER BY el.employee.createdDate DESC");

        logger.info("Query builder: " + queryBuilder.toString());
        Query query = session.createQuery(queryBuilder.toString());

        for (Map.Entry<String, String> entry : paramValue.entrySet()) {
            query.setParameter(entry.getKey(), entry.getValue());
        }

        if (!Utility.isNullOrEmpty(from) && !Utility.isNullOrEmpty(range)) {
            logger.info("From: " + from + ", Range: " + range);
            query.setFirstResult(Integer.valueOf(from)).setMaxResults(Integer.valueOf(range));
        }

        List<EmployeeLeave> employeeLeaveList = query.list();
        if(employeeLeaveList != null){
            return employeeLeaveList;
        }
        return null;
    }

    @Override
    public LeaveRequest getEmployeesLeaveDetails(String employeeID) {
        Query query = session.createQuery("FROM LeaveRequest lr WHERE lr.employee.employeeId =:employeeId");
        query.setParameter("employeeId", employeeID);

        LeaveRequest leaveDetails = (LeaveRequest) query.uniqueResult();
        if(leaveDetails != null){
            return leaveDetails;
        }
        return null;
    }

    @Override
    public boolean checkWFHRequest(String employeeId, Date startDate, Date endDate) {
        Query query = session.createQuery("FROM WorkFromHome wfh WHERE wfh.employee.employeeId =:employeeId " +
                "AND wfh.applyDate BETWEEN :startDate AND :endDate AND (wfh.status.workFromHomeStatusName =:statusName1 " +
                "OR wfh.status.workFromHomeStatusName =:statusName2)");
        query.setParameter("employeeId", employeeId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);
        query.setParameter("statusName1", Constants.APPLIED_WFH_REQUEST);
        query.setParameter("statusName2", Constants.APPROVED_WFH_REQUEST);

        return query.list().size() > 0;
    }

    @Override
    public boolean alreadyApprovedLeaveExist(String employeeID, Date leaveStartDate) {
        boolean success = false;

        Query query = session.createQuery("FROM LeaveRequest lr WHERE lr.employee.employeeId =:employeeId AND lr.leaveStatus.leaveStatusName =:statusName" +
                " AND (lr.approvedStartDate <=:date AND lr.approvedEndDate >=:date)");
        query.setParameter("employeeId", employeeID);
        query.setParameter("statusName", Constants.APPROVED_LEAVE_REQUEST);
        query.setParameter("date", leaveStartDate);

        if (query.uniqueResult() != null) {
            success = true;
        }

        return success;
    }

    @Override
    public int getLeaveCountByStatus(String employeeID, String statusName) {
        Long total;

        Query query = session.createQuery("SELECT COUNT(*) FROM LeaveRequest lr WHERE lr.employee.employeeId =:employeeId " +
                "AND lr.leaveStatus.leaveStatusName =:statusName");
        query.setParameter("employeeId", employeeID);
        query.setParameter("statusName", statusName);

        total = (Long) query.uniqueResult();
        if(total == null){
            return 0;
        }
        return total.intValue();
    }

    @Override
    public List<LeaveRequest> searchOrReadLeaveDetails(String employeeNo, String firstName, String lastName, String nickName,
                                                       String email, String phone, String teamName, String projectName, String employeeId,
                                                       String leaveType, String requestType, String approvedStartDate, String approvedEndDate,
                                                       String approvedFirstName, String approvedLastName, String approvedNickName, String appliedStartDate,
                                                       String appliedEndDate, String leaveStatus, String from, String range, String userId) {

        StringBuilder queryBuilder = new StringBuilder();
        Map<String, String> paramValue = new HashMap<>();
        boolean hasClause = false;

        queryBuilder.append("FROM LeaveRequest lr ");

        if(!Utility.isNullOrEmpty(employeeNo)){
            queryBuilder.append(" WHERE lr.employee.employeeNo like :employeeNo");
            paramValue.put("employeeNo", "%" + employeeNo + "%");
            hasClause = true;
        }

        if(!Utility.isNullOrEmpty(firstName)){
            if(hasClause){
                queryBuilder.append(" AND lr.employee.firstName like :firstName");

            }else{
                queryBuilder.append(" WHERE lr.employee.firstName like :firstName");
                hasClause = true;
            }
            paramValue.put("firstName", "%" + firstName + "%");
        }

        if(!Utility.isNullOrEmpty(lastName)){
            if(hasClause){
                queryBuilder.append(" AND lr.employee.lastName like :lastName");

            } else {
                queryBuilder.append(" WHERE lr.employee.lastName like :lastName");
                hasClause = true;
            }
            paramValue.put("lastName", "%" + lastName + "%");
        }

        if(!Utility.isNullOrEmpty(nickName)){
            if(hasClause) {
                queryBuilder.append(" AND lr.employee.nickName like :nickName");

            } else {
                queryBuilder.append(" WHERE lr.employee.nickName like :nickName");
                hasClause = true;
            }
            paramValue.put("nickName", "%" + nickName + "%");
        }

        if(!Utility.isNullOrEmpty(email)){
            if(hasClause) {
                queryBuilder.append(" AND lr.employee.employeeId in (SELECT ee.employee.employeeId FROM EmployeeEmail ee WHERE ee.email like :email)");

            } else {
                queryBuilder.append(" WHERE lr.employee.employeeId in (SELECT ee.employee.employeeId FROM EmployeeEmail ee WHERE ee.email like :email)");
                hasClause = true;
            }
            paramValue.put("email", "%" + email + "%");
        }

        if(!Utility.isNullOrEmpty(phone)){
            if(hasClause) {
                queryBuilder.append(" AND lr.employee.employeeId in (SELECT ec.employee.employeeId FROM EmployeeContact ec WHERE ec.phone like :phone)");

            } else {
                queryBuilder.append(" WHERE lr.employee.employeeId in (SELECT ec.employee.employeeId FROM EmployeeContact ec WHERE ec.phone like :phone)");
                hasClause = true;
            }
            paramValue.put("phone", "%" + phone + "%");
        }

        if(!Utility.isNullOrEmpty(teamName)){
            if(hasClause) {
                queryBuilder.append(" AND lr.employee.employeeId in (SELECT tm.employee.employeeId FROM TeamMember tm WHERE tm.team.name like :teamName)");

            } else {
                queryBuilder.append(" WHERE lr.employee.employeeId in (SELECT tm.employee.employeeId FROM TeamMember tm WHERE tm.team.name like :teamName)");
                hasClause = true;
            }
            paramValue.put("teamName", "%" + teamName + "%");
        }

        if(!Utility.isNullOrEmpty(projectName)){
            if(hasClause){
                queryBuilder.append(" AND lr.employee.employeeId in (SELECT tm.employee.employeeId FROM TeamMember tm WHERE tm.team.teamId in " +
                        "(SELECT pt.team.teamId FROM ProjectTeam pt WHERE pt.project.projectName like :projectName))");

            } else {
                queryBuilder.append(" WHERE lr.employee.employeeId in (SELECT tm.employee.employeeId FROM TeamMember tm WHERE tm.team.teamId in " +
                        "(SELECT pt.team.teamId FROM ProjectTeam pt WHERE pt.project.projectName like :projectName))");
                hasClause = true;
            }
            paramValue.put("projectName", "%" + projectName + "%");
        }

        if(!Utility.isNullOrEmpty(leaveType)){
            if(hasClause){
                queryBuilder.append(" AND lr.leaveType.leaveTypeName =:leaveType");

            } else {
                queryBuilder.append(" WHERE lr.leaveType.leaveTypeName =:leaveType");
                hasClause = true;
            }
            paramValue.put("leaveType", leaveType);
        }

        if(!Utility.isNullOrEmpty(requestType)){
            if(hasClause) {
                queryBuilder.append(" AND lr.requestType.leaveRequestTypeName =:requestType");

            } else {
                queryBuilder.append(" WHERE lr.requestType.leaveRequestTypeName =:requestType");
                hasClause = true;
            }
            paramValue.put("requestType", requestType);
        }

        if(!Utility.isNullOrEmpty(leaveStatus)){
            if(hasClause) {
                queryBuilder.append(" AND lr.leaveStatus.leaveStatusName =:leaveStatus");

            } else {
                queryBuilder.append(" WHERE lr.leaveStatus.leaveStatusName =:leaveStatus");
                hasClause = true;
            }
            paramValue.put("leaveStatus", leaveStatus);
        }

        if(!Utility.isNullOrEmpty(approvedFirstName)){
            if(hasClause) {
                queryBuilder.append(" AND lr.approvalId in (SELECT e.employeeId FROM Employee e WHERE " +
                        "e.firstName like :approvedFirstName)");

            } else {
                queryBuilder.append(" WHERE lr.approvalId in (SELECT e.employeeId FROM Employee e WHERE " +
                        "e.firstName like :approvedFirstName)");
                hasClause = true;
            }
            paramValue.put("approvedFirstName", approvedFirstName);
        }

        if(!Utility.isNullOrEmpty(approvedLastName)){
            if(hasClause) {
                queryBuilder.append(" AND lr.approvalId in (SELECT e.employeeId FROM Employee e WHERE " +
                        "e.lastName like :approvedLastName)");

            } else {
                queryBuilder.append(" WHERE lr.approvalId in (SELECT e.employeeId FROM Employee e WHERE " +
                        "e.lastName like :approvedLastName)");
                hasClause = true;
            }
            paramValue.put("approvedLastName", approvedLastName);
        }

        if(!Utility.isNullOrEmpty(approvedNickName)){
            if(hasClause) {
                queryBuilder.append(" AND lr.approvalId in (SELECT e.employeeId FROM Employee e WHERE " +
                        "e.nickName like :approvedNickName)");

            } else {
                queryBuilder.append(" WHERE lr.approvalId in (SELECT e.employeeId FROM Employee e WHERE " +
                        "e.nickName like :approvedNickName)");
                hasClause = true;
            }
            paramValue.put("approvedNickName", approvedNickName);
        }

        if(!Utility.isNullOrEmpty(approvedStartDate)){
            if(hasClause) {
                queryBuilder.append(" AND lr.approvedStartDate =:approvedStartDate");

            } else {
                queryBuilder.append(" WHERE lr.approvedStartDate =:approvedStartDate");
                hasClause = true;
            }
            paramValue.put("approvedStartDate", approvedStartDate);
        }

        if(!Utility.isNullOrEmpty(approvedEndDate)){
            if(hasClause) {
                queryBuilder.append(" AND lr.approvedEndDate =:approvedEndDate");

            } else {
                queryBuilder.append(" WHERE lr.approvedEndDate =:approvedEndDate");
                hasClause = true;
            }
            paramValue.put("approvedEndDate", approvedEndDate);
        }

        if(!Utility.isNullOrEmpty(appliedStartDate)){
            if(hasClause) {
                queryBuilder.append(" AND lr.startDate =:appliedStartDate");

            } else {
                queryBuilder.append(" WHERE lr.startDate =:appliedStartDate");
                hasClause = true;
            }
            paramValue.put("appliedStartDate", appliedStartDate);
        }

        if(!Utility.isNullOrEmpty(appliedEndDate)){
            if(hasClause) {
                queryBuilder.append(" AND lr.endDate =:appliedEndDate");

            } else {
                queryBuilder.append(" WHERE lr.endDate =:appliedEndDate");
                hasClause = true;
            }
            paramValue.put("appliedEndDate", appliedEndDate);
        }

        if(!Utility.isNullOrEmpty(employeeId)){
            if(hasClause) {
                queryBuilder.append(" AND lr.employee.employeeId =:employeeID");

            } else {
                queryBuilder.append(" WHERE lr.employee.employeeId =:employeeID");
            }
            paramValue.put("employeeID", employeeId);
        }

        queryBuilder.append(" ORDER BY lr.applyDate DESC");

        logger.info("Query builder: " + queryBuilder.toString());
        Query query = session.createQuery(queryBuilder.toString());

        for(Map.Entry<String, String> entry : paramValue.entrySet()){
            if(entry.getKey().equals("appliedStartDate") || entry.getKey().equals("appliedEndDate")
                    || entry.getKey().equals("approvedStartDate") || entry.getKey().equals("approvedEndDate")){
                query.setParameter(entry.getKey(), Utility.getDateFromString(entry.getValue()));

            } else {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        if(!Utility.isNullOrEmpty(from) && !Utility.isNullOrEmpty(range)){
            logger.info("From: " + from + ", Range: " + range);
            query.setFirstResult(Integer.valueOf(from)).setMaxResults(Integer.valueOf(range));
        }

        List<LeaveRequest> leaveRequestList = query.list();
        if(leaveRequestList != null){
            return leaveRequestList;
        }
        return null;
    }

    @Override
    public LeaveType getLeaveTypeByID(String leaveTypeId) {
        Query query = session.createQuery("FROM LeaveType lt WHERE lt.leaveTypeId =:leaveTypeId");
        query.setParameter("leaveTypeId", leaveTypeId);

        LeaveType leaveType = (LeaveType) query.uniqueResult();
        if(leaveType != null){
            return leaveType;
        }
        return null;
    }

    @Override
    public LeaveRequestType getLeaveRequestTypeByID(String leaveRequestTypeId) {
        Query query = session.createQuery("FROM LeaveRequestType lrt WHERE lrt.leaveRequestTypeId =:leaveRequestTypeId");
        query.setParameter("leaveRequestTypeId", leaveRequestTypeId);

        LeaveRequestType leaveRequestType = (LeaveRequestType) query.uniqueResult();
        if(leaveRequestType != null){
            return leaveRequestType;
        }
        return null;
    }

    @Override
    public void saveLeaveRequest(LeaveRequest leaveRequest) throws CustomException {
        try{
            session.save(leaveRequest);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateLeaveRequest(LeaveRequest leaveRequest) throws CustomException {
        try{
            session.update(leaveRequest);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteLeaveRequest(String leaveRequestID) throws CustomException {
        try{
            Query query = session.createQuery("DELETE FROM LeaveRequest lr WHERE lr.leaveRequestId =:leaveRequestID");
            query.setParameter("leaveRequestID", leaveRequestID);

            query.executeUpdate();

        } catch (Exception e) {
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_LEAVE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public LeaveRequest getLeaveRequestById(String leaveRequestID, String employeeId) {
        Query query;
        if(employeeId != null){
            query = session.createQuery("FROM LeaveRequest lr WHERE lr.leaveRequestId =:leaveRequestId AND lr.employee.employeeId =:employeeId");
            query.setParameter("leaveRequestId", leaveRequestID);
            query.setParameter("employeeId", employeeId);

        } else {
            query = session.createQuery("FROM LeaveRequest lr WHERE lr.leaveRequestId =:leaveRequestId");
            query.setParameter("leaveRequestId", leaveRequestID);
        }

        LeaveRequest leaveRequest = (LeaveRequest) query.uniqueResult();
        if(leaveRequest != null){
            return leaveRequest;
        }
        return null;
    }

    @Override
    public LeaveRequest getLeaveRequestByIdAndEmployeeId(String leaveRequestId, String userId) {
        Query query = session.createQuery("FROM LeaveRequest lr WHERE lr.leaveRequestId =:leaveRequestId AND " +
                "lr.employee.userId =:userId");
        query.setParameter("leaveRequestId", leaveRequestId);
        query.setParameter("userId", userId);

        LeaveRequest leaveRequest = (LeaveRequest) query.uniqueResult();
        if(leaveRequest != null){
            return leaveRequest;
        }
        return null;
    }

    @Override
    public List<LeaveRequest> getLeaveRequestByEmployeeID(String employeeID) {
        Query query = session.createQuery("FROM LeaveRequest lr WHERE lr.employee.employeeId =:employeeID");
        query.setParameter("employeeID", employeeID);

        List<LeaveRequest> leaveRequests = query.list();
        if(leaveRequests != null){
            return leaveRequests;
        }
        return null;
    }

    @Override
    public List<LeaveRequest> getAllLeaveRequest() {
        Query query = session.createQuery("FROM LeaveRequest");

        List<LeaveRequest> leaveRequestList = query.list();
        if(leaveRequestList != null){
            return leaveRequestList;
        }
        return null;
    }

    @Override
    public List<LeaveRequest> searchOrReadLeaveRequests(String userId, String teamName, String projectName, String leaveCnt, String leaveReason,
                                                        String leaveType, String leaveStatus, String requestType, String appliedStartDate,
                                                        String appliedEndDate, String deniedReason, String deniedBy, String leaveRequestId,
                                                        String from, String range) {

        StringBuilder queryBuilder = new StringBuilder();
        Map<String, String> paramValue = new HashMap<>();

        queryBuilder.append("FROM LeaveRequest lr WHERE lr.employee.userId =:userId");
        paramValue.put("userId", userId);

        if(!Utility.isNullOrEmpty(teamName)){
            queryBuilder.append(" AND lr.employee.employeeId in (SELECT tm.employee.employeeId FROM TeamMember tm WHERE tm.team.name like :teamName)");
            paramValue.put("teamName", "%" + teamName + "%");
        }

        if(!Utility.isNullOrEmpty(projectName)){
            queryBuilder.append(" AND lr.employee.employeeId in (SELECT tm.employee.employeeId FROM TeamMember tm WHERE tm.team.teamId in " +
                        "(SELECT pt.team.teamId FROM ProjectTeam pt WHERE pt.project.projectName like :projectName))");
            paramValue.put("projectName", "%" + projectName + "%");
        }

        if(!Utility.isNullOrEmpty(leaveCnt)){
            queryBuilder.append(" AND lr.daysCount =:leaveCnt");
            paramValue.put("leaveCnt", leaveCnt);
        }

        if(!Utility.isNullOrEmpty(leaveReason)){
            queryBuilder.append(" AND lr.leaveReason like :leaveReason");
            paramValue.put("leaveReason", "%" + leaveReason + "%");
        }

        if(!Utility.isNullOrEmpty(leaveType)){
            queryBuilder.append(" AND lr.leaveType.leaveTypeName =:leaveType");
            paramValue.put("leaveType", leaveType);
        }

        if(!Utility.isNullOrEmpty(requestType)){
            queryBuilder.append(" AND lr.requestType.leaveRequestTypeName =:requestType");
            paramValue.put("requestType", requestType);
        }

        if(!Utility.isNullOrEmpty(leaveStatus)){
            queryBuilder.append(" AND lr.leaveStatus.leaveStatusName =:leaveStatus");
            paramValue.put("leaveStatus", leaveStatus);
        }

        if(!Utility.isNullOrEmpty(appliedStartDate)){
            queryBuilder.append(" AND lr.startDate =:appliedStartDate");
            paramValue.put("appliedStartDate", appliedStartDate);
        }

        if(!Utility.isNullOrEmpty(appliedEndDate)){
            queryBuilder.append(" AND lr.endDate =:appliedEndDate");
            paramValue.put("appliedEndDate", appliedEndDate);
        }

        if(!Utility.isNullOrEmpty(deniedReason)){
            queryBuilder.append(" AND lr.deniedReason like :deniedReason");
            paramValue.put("deniedReason", "%" + deniedReason + "%");
        }

        if(!Utility.isNullOrEmpty(deniedBy)){
            queryBuilder.append(" AND lr.approvalId like :deniedBy");
            paramValue.put("deniedBy", "%" + deniedBy + "%");
        }

        if(!Utility.isNullOrEmpty(leaveRequestId)){
            queryBuilder.append(" AND lr.leaveRequestId =:leaveRequestId");
            paramValue.put("leaveRequestId", leaveRequestId);
        }

        queryBuilder.append(" ORDER BY lr.leaveStatus.priority ASC");

        logger.info("Query builder: " + queryBuilder.toString());
        Query query = session.createQuery(queryBuilder.toString());

        for(Map.Entry<String, String> entry : paramValue.entrySet()){
            if(entry.getKey().equals("leaveCnt")){
                query.setParameter(entry.getKey(), Integer.parseInt(entry.getValue()));

            } else if(entry.getKey().equals("appliedStartDate") || entry.getKey().equals("appliedEndDate")){
                query.setParameter(entry.getKey(), Utility.getDateFromString(entry.getValue()));

            } else {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        if(!Utility.isNullOrEmpty(from) && !Utility.isNullOrEmpty(range))
            query.setFirstResult(Integer.valueOf(from)).setMaxResults(Integer.valueOf(range));

        List<LeaveRequest> leaveRequestList = query.list();
        if(leaveRequestList != null){
            return leaveRequestList;
        }
        return null;
    }

    @Override
    public List<LeaveRequest> searchOrReadSpecialLeaveRequests(String employeeNo, String firstName, String lastName,
                                                               String nickName, String leaveStatus, String leaveType,
                                                               String requestType, String leaveRequestId, String from, String range) {
        StringBuilder queryBuilder = new StringBuilder();
        Map<String, String> paramValue = new HashMap<>();

        queryBuilder.append("FROM LeaveRequest lr WHERE lr.leaveType.type =:mode");
        paramValue.put("mode", Constants.SPECIAL_LEAVE_TYPE);

        if(!Utility.isNullOrEmpty(employeeNo)){
            queryBuilder.append(" AND lr.employee.employeeNo like :employeeNo");
            paramValue.put("employeeNo", "%" + employeeNo + "%");
        }

        if(!Utility.isNullOrEmpty(firstName)){
            queryBuilder.append(" AND lr.employee.firstName like :firstName");
            paramValue.put("firstName", "%" + firstName + "%");
        }

        if(!Utility.isNullOrEmpty(lastName)){
            queryBuilder.append(" AND lr.employee.lastName like :lastName");
            paramValue.put("lastName", "%" + lastName + "%");
        }

        if(!Utility.isNullOrEmpty(nickName)){
            queryBuilder.append(" AND lr.employee.nickName like :nickName");
            paramValue.put("nickName", "%" + nickName + "%");
        }

        if(!Utility.isNullOrEmpty(leaveType)){
            queryBuilder.append(" AND lr.leaveType.leaveTypeName =:leaveType");
            paramValue.put("leaveType", leaveType);
        }

        if(!Utility.isNullOrEmpty(leaveStatus)){
            queryBuilder.append(" AND lr.leaveStatus.leaveStatusName =:leaveStatus");
            paramValue.put("leaveStatus", leaveStatus);
        }

        if(!Utility.isNullOrEmpty(requestType)){
            queryBuilder.append(" AND lr.requestType.leaveRequestTypeName =:requestType");
            paramValue.put("requestType", requestType);
        }

        if(!Utility.isNullOrEmpty(leaveRequestId)){
            queryBuilder.append(" AND lr.leaveRequestId =:leaveRequestId");
            paramValue.put("leaveRequestId", leaveRequestId);
        }
        queryBuilder.append(" ORDER BY lr.leaveStatus.priority ASC");

        logger.info("Query builder: " + queryBuilder.toString());
        Query query = session.createQuery(queryBuilder.toString());

        for(Map.Entry<String, String> entry : paramValue.entrySet()){
            query.setParameter(entry.getKey(), entry.getValue());
        }

        if(!Utility.isNullOrEmpty(from) && !Utility.isNullOrEmpty(range)){
            logger.info("From: " + from + ", Range: " + range);
            query.setFirstResult(Integer.valueOf(from)).setMaxResults(Integer.valueOf(range));
        }

        List<LeaveRequest> leaveRequestList = query.list();
        if(leaveRequestList != null){
            return leaveRequestList;
        }
        return null;
    }

    @Override
    public LeaveStatus getLeaveStatusByName(String typeName) {
        Query query = session.createQuery("FROM LeaveStatus lr WHERE lr.leaveStatusName =:statusName");
        query.setParameter("statusName", typeName);

        LeaveStatus leaveStatus = (LeaveStatus) query.uniqueResult();
        if(leaveStatus != null){
            return leaveStatus;
        }
        return null;
    }

    @Override
    public boolean getLeaveRequestByRequestTypeAndEmployeeNo(String employeeNo, Date date) {
        boolean success = false;

        Query query = session.createQuery("FROM LeaveRequest lr WHERE lr.employee.employeeNo =:employeeNo AND " +
                "lr.leaveStatus.leaveStatusName =:leaveStatusName AND (lr.approvedStartDate <=:date AND lr.approvedEndDate >=:date)");
        query.setParameter("employeeNo", employeeNo);
        query.setParameter("leaveStatusName", Constants.APPROVED_LEAVE_REQUEST);
        query.setParameter("date", date);

        if(query.list().size() > 0){
            success = true;
        }

        return success;
    }

    @Override
    public LeaveRequest getLeaveRequestByStatusAndEmployee(String employeeNo, Date date) {
        Query query = session.createQuery("FROM LeaveRequest lr WHERE lr.employee.employeeNo =:employeeNo AND " +
                "lr.leaveStatus.leaveStatusName =:leaveStatusName AND (lr.approvedStartDate <=:date AND lr.approvedEndDate >=:date)");
        query.setParameter("employeeNo", employeeNo);
        query.setParameter("leaveStatusName", Constants.APPROVED_LEAVE_REQUEST);
        query.setParameter("date", date);

        LeaveRequest leaveRequest = (LeaveRequest) query.uniqueResult();
        if(leaveRequest != null){
            return leaveRequest;
        }
        return null;
    }
}
