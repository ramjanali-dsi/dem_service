package com.dsi.dem.dao.impl;

import com.dsi.dem.dao.WorkFromHomeDao;
import com.dsi.dem.dto.ContextDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.WorkFormHomeStatus;
import com.dsi.dem.model.WorkFromHome;
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
 * Created by sabbir on 1/11/17.
 */
public class WorkFromHomeDaoImpl extends CommonService implements WorkFromHomeDao {

    private static final Logger logger = Logger.getLogger(WorkFromHomeDaoImpl.class);
    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void saveWorkFromHomeRequest(WorkFromHome workFromHome) throws CustomException {
        try{
            session.save(workFromHome);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_WFH_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateWorkFromHomeRequest(WorkFromHome workFromHome) throws CustomException {
        try{
            session.update(workFromHome);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_WFH_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public WorkFromHome getWorkFromHomeById(String wfhId) {
        Query query = session.createQuery("FROM WorkFromHome wfh WHERE wfh.workFromHomeId =:workFromHomeId");
        query.setParameter("workFromHomeId", wfhId);

        WorkFromHome workFromHome = (WorkFromHome) query.uniqueResult();
        if(workFromHome != null){
            return workFromHome;
        }
        return null;
    }

    @Override
    public WorkFromHome getWFHByIdAndUserId(String wfhId, String userId) {
        Query query = session.createQuery("FROM WorkFromHome wfh WHERE wfh.employee.userId =:userId AND " +
                "wfh.workFromHomeId =:wfhId");
        query.setParameter("userId", userId);
        query.setParameter("wfhId", wfhId);

        WorkFromHome workFromHome = (WorkFromHome) query.uniqueResult();
        if(workFromHome != null){
            return workFromHome;
        }
        return null;
    }

    @Override
    public WorkFromHome getWFHByEmployeeIdAndDate(String employeeId, Date date) {
        Query query = session.createQuery("FROM WorkFromHome wfh WHERE wfh.employee.employeeId =:employeeId " +
                "AND wfh.applyDate =:date AND wfh.status.workFromHomeStatusName =:statusName");
        query.setParameter("employeeId", employeeId);
        query.setParameter("date", date);
        query.setParameter("statusName", Constants.APPROVED_WFH_REQUEST);

        WorkFromHome wfh = (WorkFromHome) query.uniqueResult();
        if(wfh != null){
            return wfh;
        }
        return null;
    }

    @Override
    public List<WorkFromHome> searchOrReadWorkFromHomeRequest(String userId, String applyDate, String reason, String statusName,
                                                              String from, String range) {
        StringBuilder queryBuilder = new StringBuilder();
        Map<String, String> paramValue = new HashMap<>();

        queryBuilder.append("FROM WorkFromHome wfh WHERE wfh.employee.userId =:userId");
        paramValue.put("userId", userId);

        if(!Utility.isNullOrEmpty(reason)){
            queryBuilder.append(" AND wfh.reason like :reason");
            paramValue.put("reason", "%" + reason + "%");
        }

        if(!Utility.isNullOrEmpty(statusName)){
            queryBuilder.append(" AND wfh.status.workFromHomeStatusName =:statusName");
            paramValue.put("statusName", statusName);
        }

        if(!Utility.isNullOrEmpty(applyDate)){
            queryBuilder.append(" AND wfh.applyDate =:applyDate");
            paramValue.put("applyDate", applyDate);
        }

        queryBuilder.append(" ORDER BY wfh.status.priority ASC");

        logger.info("Query builder: " + queryBuilder.toString());
        Query query = session.createQuery(queryBuilder.toString());

        for(Map.Entry<String, String> entry : paramValue.entrySet()){
            if(entry.getKey().equals("applyDate")){
                query.setParameter(entry.getKey(), Utility.getDateFromString(entry.getValue()));

            } else {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        if(!Utility.isNullOrEmpty(from) && !Utility.isNullOrEmpty(range))
            query.setFirstResult(Integer.valueOf(from)).setMaxResults(Integer.valueOf(range));

        List<WorkFromHome> workFromHomes = query.list();
        if(workFromHomes != null){
            return workFromHomes;
        }
        return null;
    }

    @Override
    public int countAppliedWFH(String employeeId) {
        Long total;

        Query query = session.createQuery("SELECT COUNT(*) FROM WorkFromHome wfh WHERE wfh.employee.employeeId =:employeeId " +
                "AND wfh.status.workFromHomeStatusName =:statusName");
        query.setParameter("employeeId", employeeId);
        query.setParameter("statusName", Constants.APPLIED_WFH_REQUEST);

        total = (Long) query.uniqueResult();
        if(total == null){
            return 0;
        }
        return total.intValue();
    }

    @Override
    public int countApprovedWFHForMonth(String employeeId, Date startDate, Date endDate) {
        Long total;

        Query query = session.createQuery("SELECT COUNT(*) FROM WorkFromHome wfh WHERE wfh.employee.employeeId =:employeeId AND " +
                "wfh.status.workFromHomeStatusName =:statusName AND (wfh.applyDate BETWEEN :startDate AND :endDate)");
        query.setParameter("employeeId", employeeId);
        query.setParameter("statusName", Constants.APPROVED_WFH_REQUEST);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        total = (Long) query.uniqueResult();
        if(total == null){
            return 0;
        }
        return total.intValue();
    }

    @Override
    public boolean alreadyApprovedWFHExist(String employeeId, Date applyDate) {
        boolean success = false;

        Query query = session.createQuery("FROM WorkFromHome wfh WHERE wfh.employee.employeeId =:employeeId AND wfh.applyDate =:applyDate " +
                "AND wfh.status.workFromHomeStatusName =:statusName");
        query.setParameter("employeeId", employeeId);
        query.setParameter("applyDate", applyDate);
        query.setParameter("statusName", Constants.APPROVED_WFH_REQUEST);

        if(query.uniqueResult() != null){
            success = true;
        }
        return success;
    }

    @Override
    public boolean checkLeaveRequest(String employeeId, Date date) {
        Query query = session.createQuery("FROM LeaveRequest lr WHERE lr.employee.employeeId =:employeeId " +
                "AND (lr.leaveStatus.leaveStatusName =:statusName1 OR lr.leaveStatus.leaveStatusName =:statusName2) " +
                "AND (lr.startDate >=:date AND lr.endDate <=:date)");
        query.setParameter("employeeId", employeeId);
        query.setParameter("date", date);
        query.setParameter("statusName1", Constants.APPLIED_LEAVE_REQUEST);
        query.setParameter("statusName2", Constants.APPROVED_LEAVE_REQUEST);

        if(query.list().size() > 0) {
            return true;
        }
        return false;
    }

    @Override
    public WorkFormHomeStatus getWFHStatusById(String statusId) {
        Query query = session.createQuery("FROM WorkFormHomeStatus ws WHERE ws.workFromHomeStatusId =:statusId");
        query.setParameter("statusId", statusId);

        WorkFormHomeStatus status = (WorkFormHomeStatus) query.uniqueResult();
        if(status != null){
            return status;
        }
        return null;
    }

    @Override
    public WorkFormHomeStatus getWFHStatusByName(String name) {
        Query query = session.createQuery("FROM WorkFormHomeStatus ws WHERE ws.workFromHomeStatusName =:statusName");
        query.setParameter("statusName", name);

        WorkFormHomeStatus status = (WorkFormHomeStatus) query.uniqueResult();
        if(status != null){
            return status;
        }
        return null;
    }

    @Override
    public List<WorkFromHome> searchOrReadEmployeesWFHRequests(String userId, String date, String reason, String statusName, String employeeNo,
                                                               String firstName, String lastName, String nickName, String wfhId,
                                                               ContextDto contextDto, String from, String range) {

        StringBuilder queryBuilder = new StringBuilder();
        Map<String, String> paramValue = new HashMap<>();
        boolean hasClause = false;

        queryBuilder.append("FROM WorkFromHome wfh ");

        if(contextDto != null) {
            if (!Utility.isNullOrEmpty(contextDto.getTeamId())) {
                queryBuilder.append(" WHERE wfh.employee.employeeId in (SELECT tm.employee.employeeId FROM TeamMember " +
                        "tm WHERE tm.team.teamId in (:teamIds) GROUP BY tm.employee.employeeId)");
                paramValue.put("teamIds", null);
                hasClause = true;

            } else if (!Utility.isNullOrEmpty(contextDto.getEmployeeId())) {
                queryBuilder.append(" WHERE wfh.employee.employeeId =:employeeId)");
                paramValue.put("employeeId", contextDto.getEmployeeId());
                hasClause = true;
            }
        }

        if(!Utility.isNullOrEmpty(userId)) {
            if (hasClause) {
                queryBuilder.append(" AND wfh.employee.userId !=:userId");

            } else {
                queryBuilder.append(" WHERE wfh.employee.userId !=:userId");
                hasClause = true;
            }
            paramValue.put("userId", userId);
        }

        if(!Utility.isNullOrEmpty(employeeNo)){
            if(hasClause){
                queryBuilder.append(" AND wfh.employee.employeeNo like :employeeNo");

            } else {
                queryBuilder.append(" WHERE wfh.employee.employeeNo like :employeeNo");
                hasClause = true;
            }
            paramValue.put("employeeNo", "%" + employeeNo + "%");
        }

        if(!Utility.isNullOrEmpty(firstName)){
            if(hasClause){
                queryBuilder.append(" AND wfh.employee.firstName like :firstName");

            }else{
                queryBuilder.append(" WHERE wfh.employee.firstName like :firstName");
                hasClause = true;
            }
            paramValue.put("firstName", "%" + firstName + "%");
        }

        if(!Utility.isNullOrEmpty(lastName)){
            if(hasClause){
                queryBuilder.append(" AND wfh.employee.lastName like :lastName");

            } else {
                queryBuilder.append(" WHERE wfh.employee.lastName like :lastName");
                hasClause = true;
            }
            paramValue.put("lastName", "%" + lastName + "%");
        }

        if(!Utility.isNullOrEmpty(nickName)){
            if(hasClause) {
                queryBuilder.append(" AND wfh.employee.nickName like :nickName");

            } else {
                queryBuilder.append(" WHERE wfh.employee.nickName like :nickName");
                hasClause = true;
            }
            paramValue.put("nickName", "%" + nickName + "%");
        }

        if(!Utility.isNullOrEmpty(date)){
            if(hasClause) {
                queryBuilder.append(" AND wfh.applyDate =:applyDate");

            } else {
                queryBuilder.append(" WHERE wfh.applyDate =:applyDate");
                hasClause = true;
            }
            paramValue.put("applyDate", date);
        }

        if(!Utility.isNullOrEmpty(reason)){
            if(hasClause) {
                queryBuilder.append(" AND wfh.reason like :reason");

            } else {
                queryBuilder.append(" WHERE wfh.reason like :reason");
                hasClause = true;
            }
            paramValue.put("reason", "%" + reason + "%");
        }

        if(!Utility.isNullOrEmpty(statusName)){
            if(hasClause) {
                queryBuilder.append(" AND wfh.status.workFromHomeStatusName =:statusName");

            } else {
                queryBuilder.append(" WHERE wfh.status.workFromHomeStatusName =:statusName");
                hasClause = true;
            }
            paramValue.put("statusName", statusName);
        }

        if(!Utility.isNullOrEmpty(wfhId)){
            if(hasClause) {
                queryBuilder.append(" AND wfh.workFromHomeId =:workFromHomeId");

            } else {
                queryBuilder.append(" WHERE wfh.workFromHomeId =:workFromHomeId");
            }
            paramValue.put("workFromHomeId", wfhId);
        }

        queryBuilder.append(" ORDER BY wfh.applyDate DESC");

        logger.info("Query builder: " + queryBuilder.toString());
        Query query = session.createQuery(queryBuilder.toString());

        for(Map.Entry<String, String> entry : paramValue.entrySet()){
            if(entry.getKey().equals("applyDate")) {
                query.setParameter(entry.getKey(), Utility.getDateFromString(entry.getValue()));

            } else if(entry.getKey().equals("teamIds") && contextDto != null){
                query.setParameterList(entry.getKey(), contextDto.getTeamId());

            } else {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        if(!Utility.isNullOrEmpty(from) && !Utility.isNullOrEmpty(range)){
            logger.info("From: " + from + ", Range: " + range);
            query.setFirstResult(Integer.valueOf(from)).setMaxResults(Integer.valueOf(range));
        }

        List<WorkFromHome> wfhList = query.list();
        if(wfhList != null){
            return wfhList;
        }
        return null;
    }
}
