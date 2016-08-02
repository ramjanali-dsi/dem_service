package com.dsi.dem.dao.impl;

import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.model.*;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.List;

/**
 * Created by sabbir on 7/14/16.
 */
public class EmployeeDaoImpl extends BaseDao implements EmployeeDao {

    private static final Logger logger = Logger.getLogger(EmployeeDaoImpl.class);

    @Override
    public boolean saveEmployee(Employee employee) {
        return save(employee);
    }

    @Override
    public boolean updateEmployee(Employee employee) {
        return update(employee);
    }

    @Override
    public boolean deleteEmployee(Employee employee) {
        return delete(employee);
    }

    @Override
    public Employee getEmployeeByID(String employeeID) {
        Session session = null;
        Employee employee = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM Employee e WHERE e.employeeId =:employeeID");
            query.setParameter("employeeID", employeeID);

            employee = (Employee) query.uniqueResult();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return employee;
    }

    @Override
    public Employee getEmployeeByUserID(String userID) {
        Session session = null;
        Employee employee = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM Employee e WHERE e.userId =:userID");
            query.setParameter("userID", userID);

            employee = (Employee) query.uniqueResult();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return employee;
    }

    @Override
    public Employee getEmployeeByEmployeeNO(String employeeNO) {
        Session session = null;
        Employee employee = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM Employee e WHERE e.employeeNo =:employeeNO");
            query.setParameter("employeeNO", employeeNO);

            employee = (Employee) query.uniqueResult();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() {
        Session session = null;
        List<Employee> employeeList = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM Employee");

            employeeList = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return employeeList;
    }

    @Override
    public boolean saveEmployeeInfo(EmployeeInfo employeeInfo) {
        return save(employeeInfo);
    }

    @Override
    public boolean updateEmployeeInfo(EmployeeInfo employeeInfo) {
        return update(employeeInfo);
    }

    @Override
    public boolean deleteEmployeeInfo(EmployeeInfo employeeInfo) {
        return delete(employeeInfo);
    }

    @Override
    public EmployeeInfo getEmployeeInfoByEmployeeID(String employeeID) {
        Session session = null;
        EmployeeInfo employeeInfo = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM EmployeeInfo ei WHERE ei.employee.employeeId =:employeeID");
            query.setParameter("employeeID", employeeID);

            employeeInfo = (EmployeeInfo) query.uniqueResult();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return employeeInfo;
    }

    @Override
    public boolean saveEmployeeDesignation(EmployeeDesignation employeeDesignation) {
        return save(employeeDesignation);
    }

    @Override
    public boolean updateEmployeeDesignation(EmployeeDesignation employeeDesignation) {
        return update(employeeDesignation);
    }

    @Override
    public boolean deleteEmployeeDesignation(EmployeeDesignation employeeDesignation) {
        return delete(employeeDesignation);
    }

    @Override
    public List<EmployeeDesignation> getEmployeeDesignationsByEmployeeID(String employeeID) {
        Session session = null;
        List<EmployeeDesignation> employeeDesignationList = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM EmployeeDesignation ed WHERE ed.employee.employeeId =:employeeID");
            query.setParameter("employeeID", employeeID);

            employeeDesignationList = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return employeeDesignationList;
    }

    @Override
    public boolean saveEmployeeEmail(EmployeeEmail employeeEmail) {
        return saveEmployeeEmail(employeeEmail);
    }

    @Override
    public boolean updateEmployeeEmail(EmployeeEmail employeeEmail) {
        return update(employeeEmail);
    }

    @Override
    public boolean deleteEmployeeEmail(EmployeeEmail employeeEmail) {
        return delete(employeeEmail);
    }

    @Override
    public List<EmployeeEmail> getEmployeeEmailsByEmployeeID(String employeeID) {
        Session session = null;
        List<EmployeeEmail> employeeEmailList = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM EmployeeEmail ee WHERE ee.employee.employeeId =:employeeID");
            query.setParameter("employeeID", employeeID);

            employeeEmailList = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return employeeEmailList;
    }

    @Override
    public EmployeeEmail getEmployeeEmailByEmailName(String email) {
        Session session = null;
        EmployeeEmail employeeEmail = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM EmployeeEmail ee WHERE ee.email =:email");
            query.setParameter("email", email);

            employeeEmail = (EmployeeEmail) query.uniqueResult();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return employeeEmail;
    }

    @Override
    public EmployeeEmail getEmployeeEmailByEmailAndEmployeeID(String email, String employeeID) {
        Session session = null;
        EmployeeEmail employeeEmail = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM EmployeeEmail ee WHERE ee.email =:email AND ee.employee.employeeId =:employeeID");
            query.setParameter("email", email);
            query.setParameter("employeeID", employeeID);

            employeeEmail = (EmployeeEmail) query.uniqueResult();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return employeeEmail;
    }

    @Override
    public EmployeeEmail getEmployeeEmailByEmailAndType(String email, String type) {
        Session session = null;
        EmployeeEmail employeeEmail = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM EmployeeEmail ee WHERE ee.email =:email AND ee.type =:mode");
            query.setParameter("email", email);
            query.setParameter("mode", type);

            employeeEmail = (EmployeeEmail) query.uniqueResult();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return employeeEmail;
    }

    @Override
    public boolean saveEmployeeContactInfo(EmployeeContact employeeContact) {
        return save(employeeContact);
    }

    @Override
    public boolean updateEmployeeContactInfo(EmployeeContact employeeContact) {
        return update(employeeContact);
    }

    @Override
    public boolean deleteEmployeeContactInfo(EmployeeContact employeeContact) {
        return delete(employeeContact);
    }

    @Override
    public List<EmployeeContact> getEmployeeContactsByEmployeeID(String employeeID) {
        Session session = null;
        List<EmployeeContact> employeeContactList = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM EmployeeContact ec WHERE ec.employee.employeeId =:employeeID");
            query.setParameter("employeeID", employeeID);

            employeeContactList = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return employeeContactList;
    }

    @Override
    public EmployeeContact getEmployeeContactByPhoneAndType(String phone, String type) {
        Session session = null;
        EmployeeContact employeeContact = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM EmployeeContact ec WHERE ec.phone =:phone AND ec.type =:mode");
            query.setParameter("phone", phone);
            query.setParameter("mode", type);

            employeeContact = (EmployeeContact) query.uniqueResult();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return employeeContact;
    }
}
