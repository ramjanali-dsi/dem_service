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
    public boolean deleteEmployee(String employeeID) {
        Session session = null;
        boolean success = true;
        try {
            session = getSession();
            Query query = session.createQuery("DELETE FROM Employee e WHERE e.employeeId =:employeeID");
            query.setParameter("employeeID", employeeID);

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
    public boolean deleteEmployeeInfo(String employeeID) {
        Session session = null;
        boolean success = true;
        try {
            session = getSession();
            Query query = session.createQuery("DELETE FROM EmployeeInfo ei WHERE ei.employee.employeeId =:employeeID");
            query.setParameter("employeeID", employeeID);

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
    public boolean deleteEmployeeDesignation(String employeeID, String designationID) {
        Session session = null;
        boolean success = true;
        Query query;
        try {
            session = getSession();
            if(employeeID != null) {
                query = session.createQuery("DELETE FROM EmployeeDesignation ed WHERE ed.employee.employeeId =:employeeID");
                query.setParameter("employeeID", employeeID);

            } else {
                query = session.createQuery("DELETE FROM EmployeeDesignation ed WHERE ed.designationId =:designationID");
                query.setParameter("designationID", designationID);
            }

            if(query.executeUpdate() > 0){
                success = true;
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
    public EmployeeDesignation getEmployeeDesignationByDesignationIDAndEmployeeID(String designationID, String employeeID) {
        Session session = null;
        EmployeeDesignation designation = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM EmployeeDesignation ed WHERE ed.employee.employeeId =:employeeID AND ed.designationId =:designationID");
            query.setParameter("employeeID", employeeID);
            query.setParameter("designationID", designationID);

            designation = (EmployeeDesignation) query.uniqueResult();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return designation;
    }

    @Override
    public boolean saveEmployeeEmail(EmployeeEmail employeeEmail) {
        return save(employeeEmail);
    }

    @Override
    public boolean updateEmployeeEmail(EmployeeEmail employeeEmail) {
        return update(employeeEmail);
    }

    @Override
    public boolean deleteEmployeeEmail(String employeeID, String emailID) {
        Session session = null;
        boolean success = true;
        Query query;
        try {
            session = getSession();
            if(employeeID != null) {
                query = session.createQuery("DELETE FROM EmployeeEmail ee WHERE ee.employee.employeeId =:employeeID");
                query.setParameter("employeeID", employeeID);

            } else {
                query = session.createQuery("DELETE FROM EmployeeEmail ee WHERE ee.emailId =:emailID");
                query.setParameter("emailID", emailID);
            }

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
    public EmployeeEmail getEmployeeEmailByEmailIDAndEmployeeID(String emailID, String employeeID) {
        Session session = null;
        EmployeeEmail employeeEmail = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM EmployeeEmail ee WHERE ee.emailId =:emailID AND ee.employee.employeeId =:employeeID");
            query.setParameter("emailID", emailID);
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
    public EmployeeEmail getEmployeeEmailByEmployeeIDAndTypeID(String employeeID, String typeID) {
        Session session = null;
        EmployeeEmail employeeEmail = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM EmployeeEmail ee WHERE ee.employee.employeeId =:employeeID " +
                    "AND ee.type.emailTypeId =:typeID");
            query.setParameter("employeeID", employeeID);
            query.setParameter("typeID", typeID);

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
    public boolean deleteEmployeeContactInfo(String employeeID, String contactInfoID) {
        Session session = null;
        boolean success = true;
        Query query;
        try {
            session = getSession();
            if(employeeID != null) {
                query = session.createQuery("DELETE FROM EmployeeContact ec WHERE ec.employee.employeeId =:employeeID");
                query.setParameter("employeeID", employeeID);

            } else {
                query = session.createQuery("DELETE FROM EmployeeContact ec WHERE ec.contactNumberId =:contactInfoID");
                query.setParameter("contactInfoID", contactInfoID);
            }

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
    public EmployeeContact getEmployeeContactByEmailIDAndEmployeeID(String contactID, String employeeID) {
        Session session = null;
        EmployeeContact employeeContact = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM EmployeeContact ec WHERE ec.contactNumberId =:contactID AND ec.employee.employeeId =:employeeID");
            query.setParameter("contactID", contactID);
            query.setParameter("employeeID", employeeID);

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

    @Override
    public EmployeeContact getEmployeeContactByPhoneAndType(String phone, String type) {
        Session session = null;
        EmployeeContact employeeContact = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM EmployeeContact ec WHERE ec.phone =:phone AND ec.type.contactTypeName =:mode");
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
