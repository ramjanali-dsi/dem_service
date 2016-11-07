package com.dsi.dem.dao.impl;

import com.dsi.dem.dao.AttendanceDao;
import com.dsi.dem.model.AttendanceStatus;
import com.dsi.dem.model.EmployeeAttendance;
import com.dsi.dem.model.TemporaryAttendance;
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
public class AttendanceDaoImpl extends BaseDao implements AttendanceDao {

    private static final Logger logger = Logger.getLogger(AttendanceDaoImpl.class);

    @Override
    public boolean saveAttendance(EmployeeAttendance attendance) {
        return save(attendance);
    }

    @Override
    public boolean updateAttendance(EmployeeAttendance attendance) {
        return update(attendance);
    }

    @Override
    public boolean deleteAttendance(String attendanceId, String employeeId) {
        Session session = null;
        boolean success = true;
        Query query;
        try {
            session = getSession();
            if(employeeId == null) {
                query = session.createQuery("DELETE FROM EmployeeAttendance et WHERE et.employeeAttendanceId =:employeeAttendanceID");
                query.setParameter("employeeAttendanceID", attendanceId);

            } else{
                query = session.createQuery("DELETE FROM EmployeeAttendance et WHERE et.employee.employeeId =:employeeID");
                query.setParameter("employeeID", employeeId);
            }

            success = query.executeUpdate() > 0;

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
    public EmployeeAttendance getAttendanceByID(String attendanceId, String employeeId) {
        Session session = null;
        EmployeeAttendance attendance = null;
        Query query;
        try{
            session = getSession();
            if(employeeId == null) {
                query = session.createQuery("FROM EmployeeAttendance et WHERE et.employeeAttendanceId =:employeeAttendanceId");
                query.setParameter("employeeAttendanceId", attendanceId);

            } else {
                query = session.createQuery("FROM EmployeeAttendance et WHERE et.employeeAttendanceId =:employeeAttendanceId AND " +
                        "et.employee.employeeId =:employeeID");

                query.setParameter("employeeAttendanceId", attendanceId);
                query.setParameter("employeeID", employeeId);
            }

            attendance = (EmployeeAttendance) query.uniqueResult();

        } catch (Exception e) {
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return attendance;
    }

    @Override
    public List<EmployeeAttendance> getEmployeesAllAttendances(String employeeId) {
        Session session = null;
        List<EmployeeAttendance> attendanceList = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM EmployeeAttendance et WHERE et.employee.employeeId =:employeeID");
            query.setParameter("employeeID", employeeId);

            attendanceList = query.list();

        } catch (Exception e) {
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return attendanceList;
    }

    @Override
    public List<EmployeeAttendance> getAllAttendancesByDate(String attendanceDate) {
        Session session = null;
        List<EmployeeAttendance> attendanceList = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM EmployeeAttendance et WHERE et.attendanceDate =:attendanceDate");
            query.setParameter("attendanceDate", Utility.getDateFromString(attendanceDate));

            attendanceList = query.list();

        } catch (Exception e) {
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return attendanceList;
    }

    @Override
    public List<EmployeeAttendance> searchOrReadAttendances(String userId, String employeeNo, String isAbsent, String firstName,
                                                            String lastName, String nickName, String attendanceDate, String teamName,
                                                            String projectName, String from, String range) {

        Session session = null;
        List<EmployeeAttendance> attendanceList = null;
        StringBuilder queryBuilder = new StringBuilder();
        boolean hasClause = false;
        Map<String, String> paramValue = new HashMap<>();
        try{
            session = getSession();
            queryBuilder.append("FROM EmployeeAttendance et");

            if(!Utility.isNullOrEmpty(employeeNo)){
                queryBuilder.append(" WHERE et.employee.employeeNo like :employeeNo");
                paramValue.put("employeeNo", employeeNo);
                hasClause = true;
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

            if(!Utility.isNullOrEmpty(userId)){
                queryBuilder.append(" WHERE et.employee.userId =:userId");
                paramValue.put("userId", userId);
            }

            queryBuilder.append(" ORDER BY et.attendanceDate DESC");

            logger.info("Query builder: " + queryBuilder.toString());
            Query query = session.createQuery(queryBuilder.toString());

            for(Map.Entry<String, String> entry : paramValue.entrySet()){
                if(entry.getKey().equals("absent")){
                    query.setParameter(entry.getKey(), entry.getValue().equals("true"));

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

            attendanceList = query.list();

        } catch (Exception e) {
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        logger.info("Total attendance list size:: " + attendanceList.size());
        return attendanceList;
    }

    @Override
    public boolean isAvailableEmployeeOrTempAttendanceSheet(String attendanceDate) {
        Session session = null;
        boolean success = false;
        try{
            session = getSession();
            Query query = session.createQuery("FROM TemporaryAttendance ta WHERE ta.attendanceDate =:attendanceDate");
            query.setParameter("attendanceDate", Utility.getDateFromString(attendanceDate));

            if(query.list().size() > 0){
                success = true;

            } else {
                query = session.createQuery("FROM EmployeeAttendance ea WHERE ea.attendanceDate =:attendanceDate");
                query.setParameter("attendanceDate", Utility.getDateFromString(attendanceDate));

                if(query.list().size() > 0){
                    success = true;
                }
            }

        } catch (Exception e) {
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return success;
    }

    @Override
    public boolean saveAttendanceStatus(AttendanceStatus attendanceStatus) {
        return save(attendanceStatus);
    }

    @Override
    public boolean updateAttendanceStatus(AttendanceStatus attendanceStatus) {
        return update(attendanceStatus);
    }

    @Override
    public AttendanceStatus getAttendanceStatus(String attendanceDate, String type) {
        Session session = null;
        AttendanceStatus attendanceStatus = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM AttendanceStatus a WHERE a.attendanceDate =:attendanceDate AND a.type =:typeName");
            query.setParameter("attendanceDate", Utility.getDateFromString(attendanceDate));
            query.setParameter("typeName", type);

            attendanceStatus = (AttendanceStatus) query.uniqueResult();

        } catch (Exception e) {
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return attendanceStatus;
    }

    @Override
    public boolean saveTempAttendance(TemporaryAttendance tempAttendance) {
        return save(tempAttendance);
    }

    @Override
    public boolean updateTempAttendance(TemporaryAttendance tempAttendance) {
        return update(tempAttendance);
    }

    @Override
    public boolean deleteTempAttendance(Date createdDate) {
        Session session = null;
        boolean success = true;
        try {
            session = getSession();
            Query query = session.createQuery("DELETE FROM TemporaryAttendance ta WHERE ta.createdDate =:createdDate");
            query.setParameter("createdDate", createdDate);

            success = query.executeUpdate() > 0;

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
    public List<TemporaryAttendance> getAllTempAttendances(String attendanceDate) {
        Session session = null;
        List<TemporaryAttendance> temporaryAttendances = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM TemporaryAttendance ta WHERE ta.attendanceDate =:attendanceDate");
            query.setParameter("attendanceDate", Utility.getDateFromString(attendanceDate));

            temporaryAttendances = query.list();

        } catch (Exception e) {
            logger.error("Database error occurs when delete: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return temporaryAttendances;
    }

    @Override
    public TemporaryAttendance getTemporaryAttendance(String tempAttendanceId) {
        Session session = null;
        TemporaryAttendance temporaryAttendance = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM TemporaryAttendance ta WHERE ta.tempAttendanceId =:tempAttendanceId");
            query.setParameter("tempAttendanceId", tempAttendanceId);

            temporaryAttendance = (TemporaryAttendance) query.uniqueResult();

        } catch (Exception e) {
            logger.error("Database error occurs when delete: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return temporaryAttendance;
    }
}
