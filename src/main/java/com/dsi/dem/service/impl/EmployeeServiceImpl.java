package com.dsi.dem.service.impl;

import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.dao.impl.EmployeeDaoImpl;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.*;
import com.dsi.dem.service.EmployeeService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.Utility;
import org.apache.log4j.Logger;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 7/14/16.
 */
public class EmployeeServiceImpl extends CommonService implements EmployeeService {

    private static final Logger logger = Logger.getLogger(EmployeeServiceImpl.class);

    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public Employee saveEmployee(Employee employee) throws CustomException {

        List<EmployeeDesignation> employeeDesignationList = employee.getDesignations();
        List<EmployeeEmail> employeeEmailList = employee.getEmailInfo();
        List<EmployeeContact> employeeContactList = employee.getContactInfo();

        employee.setVersion(1);
        employee.setCreatedDate(Utility.today());
        employee.setLastModifiedDate(Utility.today());

        boolean res = employeeDao.saveEmployee(employee);
        if(!res){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
        logger.info("Save employee success");

        EmployeeInfo employeeInfo = employee.getInfo();
        employeeInfo.setEmployee(employee);
        employeeInfo.setStatus(employeeDao.getEmployeeStatusById(employee.getInfo().getStatus().getEmployeeStatusId()));
        employeeInfo.setVersion(1);

        employeeDao.saveEmployeeInfo(employeeInfo);
        logger.info("Save employee info success");

        EmployeeLeave employeeLeave = new EmployeeLeave();
        employeeLeave.setEmployee(employee);
        employeeLeave.setVersion(1);

        employeeDao.saveEmployeeLeaveSummary(employeeLeave);
        logger.info("Save employee leave summary success");

        saveEmployeesDesignation(employeeDesignationList, employee);
        saveEmployeesEmails(employeeEmailList, employee);
        saveEmployeesContacts(employeeContactList, employee);

        return setEmployeesAllProperty(employee.getEmployeeId(), employee);
    }

    private void saveEmployeesDesignation(List<EmployeeDesignation> employeeDesignationList, Employee employee) throws CustomException {
        for(EmployeeDesignation employeeDesignation : employeeDesignationList){
            employeeDesignation.setEmployee(employee);
            employeeDesignation.setVersion(1);

            employeeDao.saveEmployeeDesignation(employeeDesignation);
        }
        logger.info("Save employee designation success");
    }

    private void saveEmployeesContacts(List<EmployeeContact> employeeContactList, Employee employee) throws CustomException {
        for(EmployeeContact employeeContact : employeeContactList){
            employeeContact.setEmployee(employee);
            employeeContact.setVersion(1);

            employeeDao.saveEmployeeContactInfo(employeeContact);
        }
        logger.info("Save employee contact info success");
    }

    private void saveEmployeesEmails(List<EmployeeEmail> employeeEmailList, Employee employee) throws CustomException {
        for(EmployeeEmail employeeEmail : employeeEmailList){
            employeeEmail.setEmployee(employee);
            employeeEmail.setVersion(1);
            employeeEmail.setPreferred(true);

            employeeDao.saveEmployeeEmail(employeeEmail);
        }
        logger.info("Save employee email info success");
    }

    public void validateInputForCreation(Employee employee) throws CustomException {

        if(employee.getFirstName() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0004);
            throw new CustomException(errorMessage);
        }

        if(employee.getLastName() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0005);
            throw new CustomException(errorMessage);
        }

        if(employee.getRoleId() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0008);
            throw new CustomException(errorMessage);
        }

        if(employee.getInfo().getPresentAddress() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0006);
            throw new CustomException(errorMessage);
        }

        if(Utility.isNullOrEmpty(employee.getDesignations())){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0007);
            throw new CustomException(errorMessage);
        }

        if(Utility.isNullOrEmpty(employee.getEmailInfo())){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0010);
            throw new CustomException(errorMessage);
        }

        if(Utility.isNullOrEmpty(employee.getContactInfo())){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0009);
            throw new CustomException(errorMessage);
        }

        if(employeeDao.getEmployeeEmailByEmailName(employee.getEmailInfo().get(0).getEmail()) != null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }

        if(employeeDao.getEmployeeByEmployeeNO(employee.getEmployeeNo()) != null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0002);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public Employee updateEmployee(Employee employee) throws CustomException {
        validateInputForUpdate(employee);

        Employee existEmployee = employeeDao.getEmployeeByID(employee.getEmployeeId());
        if(existEmployee == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }

        employee.setUserId(existEmployee.getUserId());
        employee.setCreatedDate(existEmployee.getCreatedDate());
        employee.setLastModifiedDate(Utility.today());
        EmployeeInfo employeeInfo = employee.getInfo();

        boolean res = employeeDao.updateEmployee(employee);
        if(!res){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
        logger.info("Employee update success");

        employeeInfo.setEmployeeInfoId(employeeDao.getEmployeeInfoByEmployeeID(employee.getEmployeeId()).getEmployeeInfoId());
        employeeInfo.setEmployee(employee);
        employeeDao.updateEmployeeInfo(employeeInfo);
        logger.info("Employee info update success");

        return setEmployeesAllProperty(employee.getEmployeeId(), employee);
    }

    private void validateInputForUpdate(Employee employee) throws CustomException {
        if(employee.getVersion() == 0){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_002);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteEmployee(String employeeID) throws CustomException {
        logger.info("Employees Info delete start.");
        employeeDao.deleteEmployeeInfo(employeeID);
        employeeDao.deleteEmployeeLeaveSummary(employeeID);
        employeeDao.deleteEmployeeEmail(employeeID, null);
        employeeDao.deleteEmployeeContactInfo(employeeID, null);
        employeeDao.deleteEmployeeDesignation(employeeID, null);

        boolean res = employeeDao.deleteEmployee(employeeID);
        if(!res){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0001);
            throw new CustomException(errorMessage);
        }
        logger.info("Employee delete success");
    }

    @Override
    public Employee getEmployeeByID(String employeeID) throws CustomException {

        Employee employee = employeeDao.getEmployeeByID(employeeID);
        if(employee == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        return setEmployeesAllProperty(employeeID ,employee);
    }

    @Override
    public Employee getEmployeeByUserID(String userID) throws CustomException {

        Employee employee = employeeDao.getEmployeeByUserID(userID);
        if(employee == null){

            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        return setEmployeesAllProperty(employee.getEmployeeId() ,employee);
    }

    @Override
    public Employee getEmployeeByEmployeeNO(String employeeNO) throws CustomException {

        Employee employee = employeeDao.getEmployeeByEmployeeNO(employeeNO);
        if(employee == null){

            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        return setEmployeesAllProperty(employee.getEmployeeId() ,employee);
    }

    @Override
    public List<Employee> getAllEmployees() throws CustomException {

        List<Employee> employeeList = employeeDao.getAllEmployees();
        if(employeeList == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        List<Employee> employees = new ArrayList<>();
        for(Employee employee : employeeList){
            employees.add(setEmployeesAllProperty(employee.getEmployeeId(), employee));
        }
        return employees;
    }

    @Override
    public List<Employee> searchEmployees(String employeeNo, String firstName, String lastName, String nickName,
                                          String accountID, String ipAddress, String nationalID, String tinID, String phone,
                                          String email, String active, String joiningDate, String teamName, String projectName,
                                          String userID, String from, String range) throws CustomException {

        if(!Utility.isNullOrEmpty(joiningDate)){
            if(Utility.getDateFromString(joiningDate) == null){
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                        Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_004);
                throw new CustomException(errorMessage);
            }
        }

        List<Employee> employeeList = employeeDao.searchEmployees(employeeNo, firstName, lastName, nickName, accountID,
                ipAddress, nationalID, tinID, phone, email, active, joiningDate, teamName, projectName, userID, from, range);
        if(employeeList == null){

            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        logger.info("Employee list size:: " + employeeList.size());
        List<Employee> employees = new ArrayList<>();
        for(Employee employee : employeeList){
            employees.add(setEmployeesAllProperty(employee.getEmployeeId(), employee));
        }
        return employees;
    }

    public Employee setEmployeesAllProperty(String employeeID, Employee employee) {
        EmployeeInfo info = employeeDao.getEmployeeInfoByEmployeeID(employeeID);
        if(info != null){
            employee.setInfo(info);
        }

        EmployeeLeave leave = employeeDao.getEmployeeLeaveSummaryByEmployeeID(employeeID);
        if(leave != null){
            employee.setLeaveInfo(leave);
        }

        List<EmployeeDesignation> designations = employeeDao.getEmployeeDesignationsByEmployeeID(employeeID);
        if(!Utility.isNullOrEmpty(designations)){
            employee.setDesignations(designations);
        }

        List<EmployeeEmail> emails = employeeDao.getEmployeeEmailsByEmployeeID(employeeID);
        if(!Utility.isNullOrEmpty(emails)){
            employee.setEmailInfo(emails);
        }

        List<EmployeeContact> contacts = employeeDao.getEmployeeContactsByEmployeeID(employeeID);
        if(!Utility.isNullOrEmpty(contacts)){
            employee.setContactInfo(contacts);
        }
        return employee;
    }
}
