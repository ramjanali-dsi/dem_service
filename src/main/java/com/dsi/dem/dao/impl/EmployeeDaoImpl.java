package com.dsi.dem.dao.impl;

import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.model.*;
import com.dsi.dem.util.NotificationConstant;
import com.dsi.dem.util.Utility;
import org.apache.log4j.Logger;
import org.hibernate.Query;
import org.hibernate.Session;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by sabbir on 7/14/16.
 */
public class EmployeeDaoImpl extends BaseDao implements EmployeeDao {

    private static final Logger logger = Logger.getLogger(EmployeeDaoImpl.class);

    private Session session;

    @Override
    public void setSession(Session session) {
        this.session = session;
    }

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
    public List<Employee> getTeamLeadsProfileOfAnEmployee(String employeeId) {
        Session session = null;
        List<Employee> employeeList = new ArrayList<>();
        try{
            session = getSession();
            Query query = session.createQuery("FROM TeamMember tm WHERE tm.role.roleName =:roleName AND tm.team.teamId " +
                    "IN (SELECT tm1.team.teamId FROM TeamMember tm1 WHERE tm1.employee.employeeId =:employeeId) " +
                    "AND tm.employee.employeeId not in :employeeId");
            query.setParameter("roleName", RoleName.LEAD.getValue());
            query.setParameter("employeeId", employeeId);

            List<TeamMember> teamMembers = query.list();

            logger.info("Team member size: " + teamMembers.size());

            for(TeamMember member : teamMembers){
                employeeList.add(member.getEmployee());
            }

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
    public List<Employee> searchEmployees(String employeeNo, String firstName, String lastName, String nickName,
                                          String accountID, String ipAddress, String nationalID, String tinID, String phone,
                                          String email, String active, String joiningDate, String teamName, String projectName,
                                          String userID, String from, String range) {

        Session session = null;
        List<Employee> employeeList = null;
        StringBuilder queryBuilder = new StringBuilder();
        boolean hasClause = false;
        Map<String, String> paramValue = new HashMap<>();
        try{
            session = getSession();
            queryBuilder.append("FROM Employee e");

            if(!Utility.isNullOrEmpty(employeeNo)){
                queryBuilder.append(" WHERE e.employeeNo like :employeeNo");
                paramValue.put("employeeNo", "%" + employeeNo + "%");
                hasClause = true;
            }

            if(!Utility.isNullOrEmpty(firstName)){
                if(hasClause){
                    queryBuilder.append(" AND e.firstName like :firstName");

                } else {
                    queryBuilder.append(" WHERE e.firstName like :firstName");
                    hasClause = true;
                }
                paramValue.put("firstName", "%" + firstName + "%");
            }

            if(!Utility.isNullOrEmpty(lastName)){
                if(hasClause){
                    queryBuilder.append(" AND e.lastName like :lastName");

                } else {
                    queryBuilder.append(" WHERE e.lastName like :lastName");
                    hasClause = true;
                }
                paramValue.put("lastName", "%" + lastName + "%");
            }

            if(!Utility.isNullOrEmpty(nickName)){
                if(hasClause){
                    queryBuilder.append(" AND e.nickName like :nickName");

                } else {
                    queryBuilder.append(" WHERE e.nickName like :nickName");
                    hasClause = true;
                }
                paramValue.put("nickName", "%" + nickName + "%");
            }

            if(!Utility.isNullOrEmpty(email)){
                if(hasClause){
                    queryBuilder.append(" AND e.employeeId in (SELECT ee.employee.employeeId FROM EmployeeEmail ee WHERE ee.email like :email)");

                } else {
                    queryBuilder.append(" WHERE e.employeeId in (SELECT ee.employee.employeeId FROM EmployeeEmail ee WHERE ee.email like :email)");
                    hasClause = true;
                }
                paramValue.put("email", "%" + email + "%");
            }

            if(!Utility.isNullOrEmpty(phone)){
                if(hasClause){
                    queryBuilder.append(" AND e.employeeId in (SELECT ec.employee.employeeId FROM EmployeeContact ec WHERE ec.phone like :phone)");

                } else {
                    queryBuilder.append(" WHERE e.employeeId in (SELECT ec.employee.employeeId FROM EmployeeContact ec WHERE ec.phone like :phone)");
                    hasClause = true;
                }
                paramValue.put("phone", "%" + phone + "%");
            }

            if(!Utility.isNullOrEmpty(active)){
                if(hasClause){
                    queryBuilder.append(" AND e.isActive =:active");

                } else {
                    queryBuilder.append(" WHERE e.isActive =:active");
                    hasClause = true;
                }
                paramValue.put("active", active);
            }

            if(!Utility.isNullOrEmpty(accountID)){
                if(hasClause){
                    queryBuilder.append(" AND e.bankAcNo =:accountID");

                } else {
                    queryBuilder.append(" WHERE e.bankAcNo =:accountID");
                    hasClause = true;
                }
                paramValue.put("accountID", accountID);
            }

            if(!Utility.isNullOrEmpty(ipAddress)){
                if(hasClause){
                    queryBuilder.append(" AND e.ipAddress =:ipAddress");

                } else {
                    queryBuilder.append(" WHERE e.ipAddress =:ipAddress");
                    hasClause = true;
                }
                paramValue.put("ipAddress", ipAddress);
            }

            if(!Utility.isNullOrEmpty(nationalID)){
                if(hasClause){
                    queryBuilder.append(" AND e.nationalId =:nationalID");

                } else {
                    queryBuilder.append(" WHERE e.nationalId =:nationalID");
                    hasClause = true;
                }
                paramValue.put("nationalID", nationalID);
            }

            if(!Utility.isNullOrEmpty(tinID)){
                if(hasClause){
                    queryBuilder.append(" AND e.etinId =:tinID");

                } else {
                    queryBuilder.append(" WHERE e.etinId =:tinID");
                    hasClause = true;
                }
                paramValue.put("tinID", tinID);
            }

            if(!Utility.isNullOrEmpty(joiningDate)){
                if(hasClause){
                    queryBuilder.append(" AND e.joiningDate =:joiningDate");

                } else {
                    queryBuilder.append(" WHERE e.joiningDate =:joiningDate");
                    hasClause = true;
                }
                paramValue.put("joiningDate", joiningDate);
            }

            if(!Utility.isNullOrEmpty(teamName)){
                if(hasClause){
                    queryBuilder.append(" AND e.employeeId in (SELECT tm.employee.employeeId FROM TeamMember tm WHERE tm.team.name like :teamName)");

                } else {
                    queryBuilder.append(" WHERE e.employeeId in (SELECT tm.employee.employeeId FROM TeamMember tm WHERE tm.team.name like :teamName)");
                    hasClause = true;
                }
                paramValue.put("teamName", "%" + teamName + "%");
            }

            if(!Utility.isNullOrEmpty(projectName)){
                if(hasClause){
                    queryBuilder.append(" AND e.employeeId in (SELECT tm.employee.employeeId FROM TeamMember tm WHERE tm.team.teamId in " +
                            "(SELECT pt.team.teamId FROM ProjectTeam pt WHERE pt.project.projectName like :projectName))");

                } else {
                    queryBuilder.append(" WHERE e.employeeId in (SELECT tm.employee.employeeId FROM TeamMember tm WHERE tm.team.teamId in " +
                            "(SELECT pt.team.teamId FROM ProjectTeam pt WHERE pt.project.projectName like :projectName))");
                    //hasClause = true;
                }
                paramValue.put("projectName", "%" + projectName + "%");
            }

            if(!Utility.isNullOrEmpty(userID)){
                queryBuilder.append(" WHERE e.userId =:userID");
                paramValue.put("userID", userID);
            }

            queryBuilder.append(" ORDER BY e.createdDate DESC");

            logger.info("Query builder: " + queryBuilder.toString());
            Query query = session.createQuery(queryBuilder.toString());

            for(Map.Entry<String, String> entry : paramValue.entrySet()){
                if(entry.getKey().equals("active")){
                    query.setParameter(entry.getKey(), entry.getValue().equals("true"));

                } else if(entry.getKey().equals("joiningDate")){
                    query.setParameter(entry.getKey(), Utility.getDateFromString(entry.getValue()));

                } else {
                    query.setParameter(entry.getKey(), entry.getValue());
                }
            }

            if(!Utility.isNullOrEmpty(from) && !Utility.isNullOrEmpty(range)) {
                logger.info("From: " + from + ", Range: " + range);
                query.setFirstResult(Integer.valueOf(from)).setMaxResults(Integer.valueOf(range));
            }

            employeeList = query.list();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        logger.info("Total employee list size: " + employeeList.size());
        return employeeList;
    }

    @Override
    public boolean checkEmployeeAsLead(String employeeId) {
        boolean success = false;
        Session session = null;
        try{
            session = getSession();
            Query query = session.createQuery("SELECT COUNT(*) FROM TeamMember tm WHERE tm.employee.employeeId =:employeeId " +
                    "AND tm.role.roleName =:roleName");
            query.setParameter("employeeId", employeeId);
            query.setParameter("roleName", RoleName.LEAD.getValue());

            Long result = (Long) query.uniqueResult();
            if(result != null){

                if(result.intValue() > 0){
                    success = true;
                }
            }
        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return success;
    }

    @Override
    public boolean checkEmployeeHasTeam(String employeeId) {
        boolean success = false;
        Session session = null;
        try{
            session = getSession();
            Query query = session.createQuery("SELECT COUNT(*) FROM TeamMember tm WHERE tm.employee.employeeId =:employeeId");
            query.setParameter("employeeId", employeeId);

            Long result = (Long) query.uniqueResult();
            if(result != null){

                if(result.intValue() > 0){
                    success = true;
                }
            }

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return success;
    }

    @Override
    public EmployeeStatus getEmployeeStatusById(String statusId) {
        Session session = null;
        EmployeeStatus employeeStatus = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM EmployeeStatus es WHERE es.employeeStatusId =:statusId");
            query.setParameter("statusId", statusId);

            employeeStatus = (EmployeeStatus) query.uniqueResult();

        } catch (Exception e){
            logger.error("Database error occurs when get: " + e.getMessage());
        } finally {
            if(session != null) {
                close(session);
            }
        }
        return employeeStatus;
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
        boolean success;
        try {
            session = getSession();
            Query query = session.createQuery("DELETE FROM EmployeeInfo ei WHERE ei.employee.employeeId =:employeeID");
            query.setParameter("employeeID", employeeID);

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
    public boolean saveEmployeeLeaveSummary(EmployeeLeave employeeLeave) {
        return save(employeeLeave);
    }

    @Override
    public boolean deleteEmployeeLeaveSummary(String employeeID) {
        Session session = null;
        boolean success = true;
        try {
            session = getSession();
            Query query = session.createQuery("DELETE FROM EmployeeLeave el WHERE el.employee.employeeId =:employeeID");
            query.setParameter("employeeID", employeeID);

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
    public EmployeeLeave getEmployeeLeaveSummaryByEmployeeID(String employeeID) {
        Session session = null;
        EmployeeLeave employeeLeave = null;
        try{
            session = getSession();
            Query query = session.createQuery("FROM EmployeeLeave el WHERE el.employee.employeeId =:employeeID");
            query.setParameter("employeeID", employeeID);

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
    public boolean saveEmployeeDesignation(EmployeeDesignation employeeDesignation) {
        return save(employeeDesignation);
    }

    @Override
    public boolean updateEmployeeDesignation(EmployeeDesignation employeeDesignation) {
        return update(employeeDesignation);
    }

    @Override
    public boolean updatePrevEmployeeDesignations(String employeeID) {
        Session session = null;
        boolean success = true;
        try{
            session = getSession();
            Query query = session.createQuery("UPDATE EmployeeDesignation SET isCurrent =:current " +
                    "WHERE employee.employeeId =:employeeID");
            query.setParameter("current", false);
            query.setParameter("employeeID", employeeID);

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
    public EmployeeContact getEmployeeContactByIDAndEmployeeID(String contactID, String employeeID) {
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
