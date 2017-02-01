package com.dsi.dem.dao.impl;

import com.dsi.dem.dao.AttendanceDao;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.AttendanceStatus;
import com.dsi.dem.model.DraftAttendance;
import com.dsi.dem.model.EmployeeAttendance;
import com.dsi.dem.model.TemporaryAttendance;
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
 * Created by sabbir on 10/19/16.
 */
public class AttendanceDaoImpl extends CommonService implements AttendanceDao {

    private static final Logger logger = Logger.getLogger(AttendanceDaoImpl.class);

    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

    @Override
    public void saveAttendance(EmployeeAttendance attendance) throws CustomException {
        try{
            session.save(attendance);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateAttendance(EmployeeAttendance attendance) throws CustomException {
        try{
            session.update(attendance);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteAttendance(String attendanceId, String employeeId) throws CustomException {
        Query query;
        try {
            if(employeeId == null) {
                query = session.createQuery("DELETE FROM EmployeeAttendance et WHERE et.employeeAttendanceId =:employeeAttendanceID");
                query.setParameter("employeeAttendanceID", attendanceId);

            } else{
                query = session.createQuery("DELETE FROM EmployeeAttendance et WHERE et.employee.employeeId =:employeeID");
                query.setParameter("employeeID", employeeId);
            }

            query.executeUpdate();

        } catch (Exception e) {
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public EmployeeAttendance getAttendanceByID(String attendanceId, String employeeId) {
        Query query;
        if(employeeId == null) {
            query = session.createQuery("FROM EmployeeAttendance et WHERE et.employeeAttendanceId =:employeeAttendanceId");
            query.setParameter("employeeAttendanceId", attendanceId);

        } else {
            query = session.createQuery("FROM EmployeeAttendance et WHERE et.employeeAttendanceId =:employeeAttendanceId AND " +
                    "et.employee.employeeId =:employeeID");

            query.setParameter("employeeAttendanceId", attendanceId);
            query.setParameter("employeeID", employeeId);
        }

        EmployeeAttendance attendance = (EmployeeAttendance) query.uniqueResult();
        if(attendance != null){
            return attendance;
        }
        return null;
    }

    @Override
    public int getAttendanceCountByIdAndDate(String employeeId, Date startDate, Date endDate) {
        Query query = session.createQuery("FROM EmployeeAttendance et WHERE et.employee.employeeId =:employeeId " +
                "AND (et.attendanceDate BETWEEN :startDate AND :endDate) AND et.isAbsent = true");
        query.setParameter("employeeId", employeeId);
        query.setParameter("startDate", startDate);
        query.setParameter("endDate", endDate);

        return query.list().size();
    }

    @Override
    public List<EmployeeAttendance> getEmployeesAllAttendances(String employeeId) {
        Query query = session.createQuery("FROM EmployeeAttendance et WHERE et.employee.employeeId =:employeeID");
        query.setParameter("employeeID", employeeId);

        List<EmployeeAttendance> attendanceList = query.list();
        if(attendanceList != null){
            return attendanceList;
        }
        return null;
    }

    @Override
    public List<EmployeeAttendance> getAllAttendancesByDate(Date attendanceDate) {
        Query query = session.createQuery("FROM EmployeeAttendance et WHERE et.attendanceDate =:attendanceDate");
        query.setParameter("attendanceDate", attendanceDate);

        List<EmployeeAttendance> attendanceList = query.list();
        if(attendanceList != null){
            return attendanceList;
        }
        return null;
    }

    @Override
    public List<EmployeeAttendance> searchOrReadAttendances(String employeeNo, String isAbsent, String firstName,
                                                            String lastName, String nickName, String attendanceDate, String teamName,
                                                            String projectName, List<String> contextList, String from, String range) {

        StringBuilder queryBuilder = new StringBuilder();
        boolean hasClause = false;
        Map<String, String> paramValue = new HashMap<>();

        queryBuilder.append("FROM EmployeeAttendance et");

        if(!Utility.isNullOrEmpty(contextList)){
            queryBuilder.append(" WHERE et.employee.employeeId in (SELECT tm.employee.employeeId FROM TeamMember " +
                    "tm WHERE tm.team.teamId in (:teamIds) GROUP BY tm.employee.employeeId)");
            paramValue.put("teamIds", null);
            hasClause = true;
        }

        if(!Utility.isNullOrEmpty(employeeNo)){
            if(hasClause){
                queryBuilder.append(" AND et.employee.employeeNo like :employeeNo");

            } else {
                queryBuilder.append(" WHERE et.employee.employeeNo like :employeeNo");
                hasClause = true;
            }
            paramValue.put("employeeNo", employeeNo);
        }

        if(!Utility.isNullOrEmpty(firstName)){
            if(hasClause){
                queryBuilder.append(" AND et.employee.firstName like :firstName");

            } else {
                queryBuilder.append(" WHERE et.employee.firstName like :firstName");
                hasClause = true;
            }
            paramValue.put("firstName", "%" + firstName + "%");
        }

        if(!Utility.isNullOrEmpty(lastName)){
            if(hasClause){
                queryBuilder.append(" AND et.employee.lastName like :lastName");

            } else {
                queryBuilder.append(" WHERE et.employee.lastName like :lastName");
                hasClause = true;
            }
            paramValue.put("lastName", "%" + lastName + "%");
        }

        if(!Utility.isNullOrEmpty(nickName)){
            if(hasClause){
                queryBuilder.append(" AND et.employee.nickName like :nickName");

            } else {
                queryBuilder.append(" WHERE et.employee.nickName like :nickName");
                hasClause = true;
            }
            paramValue.put("nickName", "%" + nickName + "%");
        }

        if(!Utility.isNullOrEmpty(teamName)){
            if(hasClause){
                queryBuilder.append(" AND et.employee.employeeId in (SELECT tm.employee.employeeId FROM TeamMember tm WHERE tm.team.name like :teamName)");

            } else {
                queryBuilder.append(" WHERE et.employee.employeeId in (SELECT tm.employee.employeeId FROM TeamMember tm WHERE tm.team.name like :teamName)");
                hasClause = true;
            }
            paramValue.put("teamName", "%" + teamName + "%");
        }

        if(!Utility.isNullOrEmpty(projectName)){
            if(hasClause){
                queryBuilder.append(" AND et.employee.employeeId in (SELECT tm.employee.employeeId FROM TeamMember tm WHERE tm.team.teamId in " +
                        "(SELECT pt.team.teamId FROM ProjectTeam pt WHERE pt.project.projectName like :projectName))");

            } else {
                queryBuilder.append(" WHERE et.employee.employeeId in (SELECT tm.employee.employeeId FROM TeamMember tm WHERE tm.team.teamId in " +
                        "(SELECT pt.team.teamId FROM ProjectTeam pt WHERE pt.project.projectName like :projectName))");
                hasClause = true;
            }
            paramValue.put("projectName", "%" + projectName + "%");
        }

        if(!Utility.isNullOrEmpty(isAbsent)){
            if(hasClause){
                queryBuilder.append(" AND et.isAbsent =:absent");

            } else {
                queryBuilder.append(" WHERE et.isAbsent =:absent");
                hasClause = true;
            }
            paramValue.put("absent", isAbsent);
        }

        if(!Utility.isNullOrEmpty(attendanceDate)){
            if(hasClause){
                queryBuilder.append(" AND et.attendanceDate =:attendanceDate");

            } else {
                queryBuilder.append(" WHERE et.attendanceDate =:attendanceDate");
            }
            paramValue.put("attendanceDate", attendanceDate);
        }

        queryBuilder.append(" ORDER BY et.attendanceDate DESC");

        logger.info("Query builder: " + queryBuilder.toString());
        Query query = session.createQuery(queryBuilder.toString());

        for(Map.Entry<String, String> entry : paramValue.entrySet()){
            if(entry.getKey().equals("absent")){
                query.setParameter(entry.getKey(), entry.getValue().equals("true"));

            } else if(entry.getKey().equals("teamIds")){
                query.setParameterList(entry.getKey(), contextList);

            } else if(entry.getKey().equals("attendanceDate")){
                query.setParameter(entry.getKey(), Utility.getDateFromString(entry.getValue()));

            } else {
                query.setParameter(entry.getKey(), entry.getValue());
            }
        }

        if(!Utility.isNullOrEmpty(from) && !Utility.isNullOrEmpty(range)) {
            logger.info("From: " + from + ", Range: " + range);
            query.setFirstResult(Integer.valueOf(from)).setMaxResults(Integer.valueOf(range));
        }

        List<EmployeeAttendance> attendanceList = query.list();
        if(attendanceList != null){
            return attendanceList;
        }
        return null;
    }

    @Override
    public boolean isAvailableEmployeeOrTempAttendanceSheet(Date attendanceDate) {
        boolean success = false;
        Query query = session.createQuery("SELECT COUNT(ea.employeeAttendanceId) FROM EmployeeAttendance ea " +
                "WHERE ea.attendanceDate =:attendanceDate");
        query.setParameter("attendanceDate", attendanceDate);

        Long count = (Long) query.uniqueResult();
        if(count > 0){
            success = true;

        } else {
            query = session.createQuery("SELECT COUNT(ta.tempAttendanceId) FROM TemporaryAttendance ta " +
                    "WHERE ta.attendanceDate =:attendanceDate");
            query.setParameter("attendanceDate", attendanceDate);

            count = (Long) query.uniqueResult();
            if(count > 0){
                success = true;
            }
        }
        return success;
    }

    @Override
    public void saveAttendanceStatus(AttendanceStatus attendanceStatus) throws CustomException {
        try{
            session.save(attendanceStatus);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0005);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateAttendanceStatus(AttendanceStatus attendanceStatus) throws CustomException {
        try{
            session.update(attendanceStatus);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0005);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public AttendanceStatus getAttendanceStatus(Date attendanceDate, String type) {
        Query query = session.createQuery("FROM AttendanceStatus a WHERE a.attendanceDate =:attendanceDate AND a.type =:typeName");
        query.setParameter("attendanceDate", attendanceDate);
        query.setParameter("typeName", type);

        AttendanceStatus attendanceStatus = (AttendanceStatus) query.uniqueResult();
        if(attendanceStatus != null){
            return attendanceStatus;
        }
        return null;
    }

    @Override
    public void saveTempAttendance(TemporaryAttendance tempAttendance) throws CustomException {
        try{
            session.save(tempAttendance);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0006);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateTempAttendance(TemporaryAttendance tempAttendance) throws CustomException {
        try{
            session.update(tempAttendance);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0006);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteTempAttendance(Date attendanceDate) throws CustomException {
        try{
            Query query = session.createQuery("DELETE FROM TemporaryAttendance ta WHERE ta.attendanceDate =:attendanceDate");
            query.setParameter("attendanceDate", attendanceDate);

            query.executeUpdate();

        } catch (Exception e) {
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0006);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteTemporaryAttendanceFromCron(Date createdDate) throws CustomException {
        try{
            Query query = session.createQuery("DELETE FROM TemporaryAttendance ta WHERE ta.createdDate =:createdDate");
            query.setParameter("createdDate", createdDate);

            query.executeUpdate();

        } catch (Exception e) {
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0006);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public List<TemporaryAttendance> getAllTempAttendances(Date attendanceDate) {
        Query query = session.createQuery("FROM TemporaryAttendance ta WHERE ta.attendanceDate =:attendanceDate");
        query.setParameter("attendanceDate", attendanceDate);

        List<TemporaryAttendance> temporaryAttendances = query.list();
        if(temporaryAttendances != null){
            return temporaryAttendances;
        }
        return null;
    }

    @Override
    public TemporaryAttendance getTemporaryAttendance(String tempAttendanceId) {
        Query query = session.createQuery("FROM TemporaryAttendance ta WHERE ta.tempAttendanceId =:tempAttendanceId");
        query.setParameter("tempAttendanceId", tempAttendanceId);

        TemporaryAttendance temporaryAttendance = (TemporaryAttendance) query.uniqueResult();
        if(temporaryAttendance != null){
            return temporaryAttendance;
        }
        return null;
    }

    @Override
    public void saveAttendanceDraft(DraftAttendance draftAttendance) throws CustomException {
        try{
            session.save(draftAttendance);

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0008);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteAttendanceDraft(Date attendanceDate) throws CustomException {
        try{
            Query query = session.createQuery("DELETE FROM DraftAttendance da WHERE da.attendanceDate =:attendanceDate");
            query.setParameter("attendanceDate", attendanceDate);

            query.executeUpdate();

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0008);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteAttendanceDraftFromCron(Date createdDate) throws CustomException {
        try{
            Query query = session.createQuery("DELETE FROM DraftAttendance da WHERE da.createdDate =:createdDate");
            query.setParameter("createdDate", createdDate);

            query.executeUpdate();

        } catch (Exception e){
            close(session);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_ATTENDANCE_ERROR_TYPE_0008);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public DraftAttendance getDraftAttendanceFileByDate(Date attendanceDate) throws CustomException {
        Query query = session.createQuery("FROM DraftAttendance da WHERE da.attendanceDate =:attendanceDate");
        query.setParameter("attendanceDate", attendanceDate);

        DraftAttendance draftAttendance = (DraftAttendance) query.uniqueResult();
        if(draftAttendance != null){
            return draftAttendance;
        }
        return null;
    }

    @Override
    public List<DraftAttendance> getAllDraftAttendanceFileDetails(String from, String range) {
        Query query = session.createQuery("FROM DraftAttendance da ORDER BY da.attendanceDate DESC");
        query.setFirstResult(Integer.valueOf(from));
        query.setMaxResults(Integer.valueOf(range));

        List<DraftAttendance> draftAttendances = query.list();
        if(draftAttendances != null){
            return draftAttendances;
        }
        return null;
    }

    @Override
    public List<DraftAttendance> getAllDraftAttendanceFileByCreatedDate(Date createdDate) {
        Query query = session.createQuery("FROM DraftAttendance da WHERE da.createdDate =:createdDate");
        query.setParameter("createdDate", createdDate);

        List<DraftAttendance> draftAttendances = query.list();
        if(draftAttendances != null){
            return draftAttendances;
        }
        return null;
    }
}
