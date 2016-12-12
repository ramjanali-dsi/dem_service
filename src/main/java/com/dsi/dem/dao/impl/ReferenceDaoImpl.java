package com.dsi.dem.dao.impl;

import com.dsi.dem.dao.ReferenceDao;
import com.dsi.dem.model.*;
import com.dsi.dem.util.Constants;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by sabbir on 8/17/16.
 */
public class ReferenceDaoImpl extends BaseDao implements ReferenceDao {

    private static final Logger logger = Logger.getLogger(ReferenceDaoImpl.class);

    @Override
    public List<RoleType> getAllRoles() {
        Session session = null;
        List<RoleType> roleTypes = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM RoleType");

            roleTypes = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return roleTypes;
    }

    @Override
    public List<EmployeeEmailType> getAllEmailTypes() {
        Session session = null;
        List<EmployeeEmailType> emailTypes = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM EmployeeEmailType");

            emailTypes = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return emailTypes;
    }

    @Override
    public List<EmployeeContactType> getAllContactInfoTypes() {
        Session session = null;
        List<EmployeeContactType> contactTypes = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM EmployeeContactType");

            contactTypes = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return contactTypes;
    }

    @Override
    public List<EmployeeStatus> getAllEmployeeStatusNames() {
        Session session = null;
        List<EmployeeStatus> employeeStatusList = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM EmployeeStatus ");

            employeeStatusList = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return employeeStatusList;
    }

    @Override
    public List<ProjectStatus> getAllProjectStatusNames() {
        Session session = null;
        List<ProjectStatus> projectStatusList = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM ProjectStatus");

            projectStatusList = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return projectStatusList;
    }

    @Override
    public List<LeaveStatus> getAllLeaveStatusNames() {
        Session session = null;
        List<LeaveStatus> leaveStatusList = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM LeaveStatus");

            leaveStatusList = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return leaveStatusList;
    }

    @Override
    public List<LeaveType> getAllLeaveTypes(String mode) {
        Session session = null;
        List<LeaveType> leaveTypes = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM LeaveType l WHERE l.type =:mode");
            query.setParameter("mode", mode);

            leaveTypes = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return leaveTypes;
    }

    @Override
    public List<LeaveRequestType> getAllLeaveRequestTypes() {
        Session session = null;
        List<LeaveRequestType> leaveRequestTypes = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM LeaveRequestType");

            leaveRequestTypes = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return leaveRequestTypes;
    }

    @Override
    public List<HolidayType> getAllHolidayTypes() {
        Session session = null;
        List<HolidayType> holidayTypes = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM HolidayType");

            holidayTypes = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return holidayTypes;
    }
}
