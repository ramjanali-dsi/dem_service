package com.dsi.dem.dao.impl;

import com.dsi.dem.dao.LeaveDao;
import com.dsi.dem.model.EmployeeLeave;
import com.dsi.dem.model.LeaveRequest;
import com.dsi.dem.util.Utility;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sabbir on 8/25/16.
 */
public class LeaveDaoImpl extends BaseDao implements LeaveDao {

    private static final Logger logger = Logger.getLogger(LeaveDaoImpl.class);

    @Override
    public EmployeeLeave getEmployeeLeaveSummary(String employeeID) {
        Session session = null;
        EmployeeLeave employeeLeave = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM EmployeeLeave el WHERE el.employee.employeeId =:employeeId");
            query.setParameter("employeeId", employeeID);

            employeeLeave = (EmployeeLeave) query.uniqueResult();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return employeeLeave;
    }

    @Override
    public List<EmployeeLeave> searchOrReadEmployeesLeaveSummary(String employeeNo, String firstName, String lastName, String nickName,
                                                                 String email, String phone, String teamName, String projectName,
                                                                 String employeeId, String from, String range) {

        Session session = null;
        List<EmployeeLeave> employeeLeaveList = null;
        StringBuilder queryBuilder = new StringBuilder();
        boolean hasClause = false;
        Map<String, String> paramValue = new HashMap<>();
        try{
            session = getSession();
            queryBuilder.append("FROM EmployeeLeave el");

            if(!Utility.isNullOrEmpty(employeeNo)){
                queryBuilder.append(" WHERE el.employee.employeeNo like :employeeNo");
                paramValue.put("employeeNo", "%" + employeeNo + "%");
                hasClause = true;
            }

            if(!Utility.isNullOrEmpty(firstName)){
                if(hasClause){
                    queryBuilder.append(" AND el.employee.firstName like :firstName");

                } else {
                    queryBuilder.append(" WHERE el.employee.firstName like :firstName");
                    hasClause = true;
                }
                paramValue.put("firstName", "%" + firstName + "%");
            }

            if(!Utility.isNullOrEmpty(lastName)){
                if(hasClause){
                    queryBuilder.append(" AND el.employee.lastName like :lastName");

                } else {
                    queryBuilder.append(" WHERE el.employee.lastName like :lastName");
                    hasClause = true;
                }
                paramValue.put("lastName", "%" + lastName + "%");
            }

            if(!Utility.isNullOrEmpty(nickName)){
                if(hasClause){
                    queryBuilder.append(" AND el.employee.nickName like :nickName");

                } else {
                    queryBuilder.append(" WHERE el.employee.nickName like :nickName");
                    hasClause = true;
                }
                paramValue.put("nickName", "%" + nickName + "%");
            }

            if(!Utility.isNullOrEmpty(email)){
                if(hasClause){
                    queryBuilder.append(" AND el.employee.employeeId in (SELECT ee.employee.employeeId FROM EmployeeEmail ee WHERE ee.email like :email)");

                } else {
                    queryBuilder.append(" WHERE el.employee.employeeId in (SELECT ee.employee.employeeId FROM EmployeeEmail ee WHERE ee.email like :email)");
                    hasClause = true;
                }
                paramValue.put("email", "%" + email + "%");
            }

            if(!Utility.isNullOrEmpty(phone)){
                if(hasClause){
                    queryBuilder.append(" AND el.employee.employeeId in (SELECT ec.employee.employeeId FROM EmployeeContact ec WHERE ec.phone like :phone)");

                } else {
                    queryBuilder.append(" WHERE el.employee.employeeId in (SELECT ec.employee.employeeId FROM EmployeeContact ec WHERE ec.phone like :phone)");
                    hasClause = true;
                }
                paramValue.put("phone", "%" + phone + "%");
            }

            if(!Utility.isNullOrEmpty(teamName)){
                if(hasClause){
                    queryBuilder.append(" AND el.employee.employeeId in (SELECT tm.employee.employeeId FROM TeamMember tm WHERE tm.team.name like :teamName)");

                } else {
                    queryBuilder.append(" WHERE el.employee.employeeId in (SELECT tm.employee.employeeId FROM TeamMember tm WHERE tm.team.name like :teamName)");
                    hasClause = true;
                }
                paramValue.put("teamName", "%" + teamName + "%");
            }

            if(!Utility.isNullOrEmpty(projectName)){
                if(hasClause){
                    queryBuilder.append(" AND el.employee.employeeId in (SELECT tm.employee.employeeId FROM TeamMember tm WHERE tm.team.teamId in " +
                            "(SELECT pt.team.teamId FROM ProjectTeam pt WHERE pt.project.projectName like :projectName))");

                } else {
                    queryBuilder.append(" WHERE el.employee.employeeId in (SELECT tm.employee.employeeId FROM TeamMember tm WHERE tm.team.teamId in " +
                            "(SELECT pt.team.teamId FROM ProjectTeam pt WHERE pt.project.projectName like :projectName))");
                }
                paramValue.put("projectName", "%" + projectName + "%");
            }

            if(!Utility.isNullOrEmpty(employeeId)){
                queryBuilder.append(" WHERE el.employee.employeeId =:employeeID");
                paramValue.put("employeeID", employeeId);
            }

            queryBuilder.append(" ORDER BY el.employee.firstName ASC");

            logger.info("Query builder: " + queryBuilder.toString());
            Query query = session.createQuery(queryBuilder.toString());

            for(Map.Entry<String, String> entry : paramValue.entrySet()){
                query.setParameter(entry.getKey(), entry.getValue());
            }

            if(!Utility.isNullOrEmpty(from) && !Utility.isNullOrEmpty(range))
                query.setFirstResult(Integer.valueOf(from)).setMaxResults(Integer.valueOf(range));

            employeeLeaveList = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return employeeLeaveList;
    }

    @Override
    public LeaveRequest getEmployeesLeaveDetails(String employeeID) {
        Session session = null;
        LeaveRequest leaveDetails = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM LeaveRequest lr WHERE lr.employee.employeeId =:employeeId");
            query.setParameter("employeeId", employeeID);

            leaveDetails = (LeaveRequest) query.uniqueResult();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return leaveDetails;
    }

    @Override
    public List<LeaveRequest> searchOrReadLeaveDetails(String employeeNo, String firstName, String lastName, String nickName,
                                                       String email, String phone, String teamName, String projectName, String employeeId,
                                                       String leaveType, String requestType, String approvedStartDate, String approvedEndDate,
                                                       String approvedFirstName, String approvedLastName, String approvedNickName, String appliedStartDate,
                                                       String appliedEndDate, String leaveStatus, String from, String range) {

        Session session = null;
        List<LeaveRequest> leaveRequestList = null;
        StringBuilder queryBuilder = new StringBuilder();
        boolean hasClause = false;
        Map<String, String> paramValue = new HashMap<>();
        try{
            session = getSession();
            queryBuilder.append("FROM LeaveRequest lr");

            if(!Utility.isNullOrEmpty(employeeNo)){
                queryBuilder.append(" WHERE lr.employee.employeeNo like :employeeNo");
                paramValue.put("employeeNo", "%" + employeeNo + "%");
                hasClause = true;
            }

            if(!Utility.isNullOrEmpty(firstName)){
                if(hasClause){
                    queryBuilder.append(" AND lr.employee.firstName like :firstName");

                } else {
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
                if(hasClause){
                    queryBuilder.append(" AND lr.employee.nickName like :nickName");

                } else {
                    queryBuilder.append(" WHERE lr.employee.nickName like :nickName");
                    hasClause = true;
                }
                paramValue.put("nickName", "%" + nickName + "%");
            }

            if(!Utility.isNullOrEmpty(email)){
                if(hasClause){
                    queryBuilder.append(" AND lr.employee.employeeId in (SELECT ee.employee.employeeId FROM EmployeeEmail ee WHERE ee.email like :email)");

                } else {
                    queryBuilder.append(" WHERE lr.employee.employeeId in (SELECT ee.employee.employeeId FROM EmployeeEmail ee WHERE ee.email like :email)");
                    hasClause = true;
                }
                paramValue.put("email", "%" + email + "%");
            }

            if(!Utility.isNullOrEmpty(phone)){
                if(hasClause){
                    queryBuilder.append(" AND lr.employee.employeeId in (SELECT ec.employee.employeeId FROM EmployeeContact ec WHERE ec.phone like :phone)");

                } else {
                    queryBuilder.append(" WHERE lr.employee.employeeId in (SELECT ec.employee.employeeId FROM EmployeeContact ec WHERE ec.phone like :phone)");
                    hasClause = true;
                }
                paramValue.put("phone", "%" + phone + "%");
            }

            if(!Utility.isNullOrEmpty(teamName)){
                if(hasClause){
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
                    queryBuilder.append(" AND lr.leaveType.leaveTypeId =:leaveType)");

                } else {
                    queryBuilder.append(" WHERE lr.leaveType.leaveTypeId =:leaveType)");
                    hasClause = true;
                }
                paramValue.put("leaveType", leaveType);
            }

            if(!Utility.isNullOrEmpty(requestType)){
                if(hasClause){
                    queryBuilder.append(" AND lr.requestType =:requestType)");

                } else {
                    queryBuilder.append(" WHERE lr.requestType =:requestType)");
                    hasClause = true;
                }
                paramValue.put("requestType", requestType);
            }

            if(!Utility.isNullOrEmpty(leaveStatus)){
                if(hasClause){
                    queryBuilder.append(" AND lr.leaveStatus.leaveStatusId =:leaveStatus)");

                } else {
                    queryBuilder.append(" WHERE lr.leaveStatus.leaveStatusId =:leaveStatus)");
                    hasClause = true;
                }
                paramValue.put("leaveStatus", leaveStatus);
            }

            /*if(!Utility.isNullOrEmpty(approvedFirstName)){
                if(hasClause){
                    queryBuilder.append(" AND lr.approvalId =:leaveStatus)");

                } else {
                    queryBuilder.append(" WHERE lr.leaveStatus.leaveStatusId =:leaveStatus)");
                    hasClause = true;
                }
                paramValue.put("leaveStatus", leaveStatus);
            }

            if(!Utility.isNullOrEmpty(approvedLastName)){
                if(hasClause){
                    queryBuilder.append(" AND lr.leaveStatus.leaveStatusId =:leaveStatus)");

                } else {
                    queryBuilder.append(" WHERE lr.leaveStatus.leaveStatusId =:leaveStatus)");
                    hasClause = true;
                }
                paramValue.put("leaveStatus", leaveStatus);
            }

            if(!Utility.isNullOrEmpty(approvedNickName)){
                if(hasClause){
                    queryBuilder.append(" AND lr.leaveStatus.leaveStatusId =:leaveStatus)");

                } else {
                    queryBuilder.append(" WHERE lr.leaveStatus.leaveStatusId =:leaveStatus)");
                    hasClause = true;
                }
                paramValue.put("leaveStatus", leaveStatus);
            }*/

            if(!Utility.isNullOrEmpty(approvedStartDate)){
                if(hasClause){
                    queryBuilder.append(" AND lr.approvedStartDate like :approvedStartDate)");

                } else {
                    queryBuilder.append(" WHERE lr.approvedStartDate like :approvedStartDate)");
                    hasClause = true;
                }
                paramValue.put("approvedStartDate", "%" + approvedStartDate + "%");
            }

            if(!Utility.isNullOrEmpty(approvedEndDate)){
                if(hasClause){
                    queryBuilder.append(" AND lr.approvedEndDate like :approvedEndDate)");

                } else {
                    queryBuilder.append(" WHERE lr.approvedEndDate like :approvedEndDate)");
                    hasClause = true;
                }
                paramValue.put("approvedEndDate", "%" + approvedEndDate + "%");
            }

            if(!Utility.isNullOrEmpty(appliedStartDate)){
                if(hasClause){
                    queryBuilder.append(" AND lr.startDate like :appliedStartDate)");

                } else {
                    queryBuilder.append(" WHERE lr.startDate like :appliedStartDate)");
                    hasClause = true;
                }
                paramValue.put("appliedStartDate", "%" + appliedStartDate + "%");
            }

            if(!Utility.isNullOrEmpty(appliedEndDate)){
                if(hasClause){
                    queryBuilder.append(" AND lr.endDate like :appliedEndDate)");

                } else {
                    queryBuilder.append(" WHERE lr.endDate like :appliedEndDate)");
                }
                paramValue.put("appliedEndDate", "%" + appliedEndDate + "%");
            }

            if(!Utility.isNullOrEmpty(employeeId)){
                queryBuilder.append(" WHERE lr.employee.employeeId =:employeeID");
                paramValue.put("employeeID", employeeId);
            }

            queryBuilder.append(" ORDER BY lr.employee.firstName ASC");

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

            if(!Utility.isNullOrEmpty(from) && !Utility.isNullOrEmpty(range))
                query.setFirstResult(Integer.valueOf(from)).setMaxResults(Integer.valueOf(range));

            leaveRequestList = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return leaveRequestList;
    }

    @Override
    public boolean saveLeaveRequest(LeaveRequest leaveRequest) {
        return save(leaveRequest);
    }

    @Override
    public boolean updateLeaveRequest(LeaveRequest leaveRequest) {
        return update(leaveRequest);
    }

    @Override
    public boolean deleteLeaveRequest(String leaveRequestID) {
        Session session = null;
        boolean success = true;
        try {
            session = getSession();
            Query query = session.createQuery("DELETE FROM LeaveRequest lr WHERE lr.leaveRequestId =:leaveRequestID");
            query.setParameter("leaveRequestID", leaveRequestID);

            if(query.executeUpdate() > 0){
                success = true;

            } else {
                success = false;
            }

        } catch (Exception e) {
            logger.error("Database error occurs when delete: " + e.getMessage());
            success = false;

        } finally {
            if(session != null) {
                close(session);
            }
        }
        return success;
    }

    @Override
    public LeaveRequest getLeaveRequestById(String leaveRequestID) {
        Session session = null;
        LeaveRequest leaveRequest = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM LeaveRequest lr WHERE lr.leaveRequestId =:leaveRequestId");
            query.setParameter("leaveRequestId", leaveRequestID);

            leaveRequest = (LeaveRequest) query.uniqueResult();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return leaveRequest;
    }

    @Override
    public LeaveRequest getLeaveRequestByIdAndEmployeeId(String leaveRequestId, String userId) {
        Session session = null;
        LeaveRequest leaveRequest = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM LeaveRequest lr WHERE lr.leaveRequestId =:leaveRequestId AND " +
                    "lr.employee.userId =:userId");
            query.setParameter("leaveRequestId", leaveRequestId);
            query.setParameter("userId", userId);

            leaveRequest = (LeaveRequest) query.uniqueResult();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return leaveRequest;
    }

    @Override
    public List<LeaveRequest> getLeaveRequestByEmployeeID(String employeeID) {
        Session session = null;
        List<LeaveRequest> leaveRequests = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM LeaveRequest lr WHERE lr.employee.employeeId =:employeeID");
            query.setParameter("employeeID", employeeID);

            leaveRequests = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return leaveRequests;
    }

    @Override
    public List<LeaveRequest> getAllLeaveRequest() {
        Session session = null;
        List<LeaveRequest> leaveRequestList = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM LeaveRequest");

            leaveRequestList = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return leaveRequestList;
    }

    @Override
    public List<LeaveRequest> searchOrReadLeaveRequests(String userId, String teamName, String projectName, String leaveCnt, String leaveReason,
                                                        String leaveType, String leaveStatus, String requestType, String appliedStartDate,
                                                        String appliedEndDate, String deniedReason, String deniedBy, String leaveRequestId,
                                                        String from, String range) {

        Session session = null;
        List<LeaveRequest> leaveRequestList = null;
        StringBuilder queryBuilder = new StringBuilder();
        Map<String, String> paramValue = new HashMap<>();
        try{
            session = getSession();
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
                queryBuilder.append(" AND lr.daysCount =:leaveCnt)");
                paramValue.put("leaveCnt", leaveCnt);
            }

            if(!Utility.isNullOrEmpty(leaveReason)){
                queryBuilder.append(" AND lr.leaveReason like :leaveReason)");
                paramValue.put("leaveReason", "%" + leaveReason + "%");
            }

            if(!Utility.isNullOrEmpty(leaveType)){
                queryBuilder.append(" AND lr.leaveType.leaveTypeId =:leaveType)");
                paramValue.put("leaveType", leaveType);
            }

            if(!Utility.isNullOrEmpty(requestType)){
                queryBuilder.append(" AND lr.requestType =:requestType)");
                paramValue.put("requestType", requestType);
            }

            if(!Utility.isNullOrEmpty(leaveStatus)){
                queryBuilder.append(" AND lr.leaveStatus.leaveStatusId =:leaveStatus)");
                paramValue.put("leaveStatus", leaveStatus);
            }

            if(!Utility.isNullOrEmpty(appliedStartDate)){
                queryBuilder.append(" AND lr.startDate like :appliedStartDate)");
                paramValue.put("appliedStartDate", "%" + appliedStartDate + "%");
            }

            if(!Utility.isNullOrEmpty(appliedEndDate)){
                queryBuilder.append(" AND lr.endDate like :appliedEndDate)");
                paramValue.put("appliedEndDate", "%" + appliedEndDate + "%");
            }

            if(!Utility.isNullOrEmpty(deniedReason)){
                queryBuilder.append(" AND lr.deniedReason like :deniedReason)");
                paramValue.put("deniedReason", "%" + deniedReason + "%");
            }

            if(!Utility.isNullOrEmpty(deniedBy)){
                queryBuilder.append(" AND lr.approvalId like :deniedBy)");
                paramValue.put("deniedBy", "%" + deniedBy + "%");
            }

            if(!Utility.isNullOrEmpty(leaveRequestId)){
                queryBuilder.append(" AND lr.leaveRequestId =:leaveRequestId");
                paramValue.put("leaveRequestId", leaveRequestId);
            }

            queryBuilder.append(" ORDER BY lr.leaveStatus.leaveStatusName ASC");

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

            leaveRequestList = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return leaveRequestList;
    }
}
