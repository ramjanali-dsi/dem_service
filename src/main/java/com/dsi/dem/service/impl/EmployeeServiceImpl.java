package com.dsi.dem.service.impl;

import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.dao.impl.EmployeeDaoImpl;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.*;
import com.dsi.dem.service.EmployeeService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.Utility;

import java.util.List;

/**
 * Created by sabbir on 7/14/16.
 */
public class EmployeeServiceImpl implements EmployeeService {

    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public void saveEmployee(Employee employee) throws CustomException {
        validateInputForCreation(employee);

        List<EmployeeDesignation> employeeDesignationList = employee.getDesignationList();
        List<EmployeeEmail> employeeEmailList = employee.getEmailList();
        List<EmployeeContact> employeeContactList = employee.getContactList();

        for(EmployeeEmail employeeEmail : employeeEmailList){
            EmployeeEmail isEmployeeEmail = employeeDao.getEmployeeEmailByEmailName(employeeEmail.getEmail());
            if(isEmployeeEmail != null){
                ErrorContext errorContext = new ErrorContext("Email", "Employee", "Employee already exist.");
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                        Constants.DEM_SERVICE_0002_DESCRIPTION, errorContext);
                throw new CustomException(errorMessage);
            }
        }

        Employee isEmployee = employeeDao.getEmployeeByUserID(employee.getUserId());
        if(isEmployee != null){
            ErrorContext errorContext = new ErrorContext("UserID", "Employee", "Employee already exist.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        employee.setCreatedDate(Utility.today());
        employee.setLastModifiedDate(Utility.today());

        boolean res = employeeDao.saveEmployee(employee);
        if(!res){
            ErrorContext errorContext = new ErrorContext(null, "Employee", "Employee create failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        EmployeeInfo employeeInfo = employee.getEmployeeInfo();
        employeeInfo.setEmployee(employee);

        employeeDao.saveEmployeeInfo(employeeInfo);

        saveEmployeesDesignation(employeeDesignationList, employee);

        saveEmployeesEmails(employeeEmailList, employee);

        saveEmployeesContacts(employeeContactList, employee);
    }

    private void saveEmployeesDesignation(List<EmployeeDesignation> employeeDesignationList, Employee employee) {
        for(EmployeeDesignation employeeDesignation : employeeDesignationList){
            employeeDesignation.setEmployee(employee);

            employeeDao.saveEmployeeDesignation(employeeDesignation);
        }
    }

    private void saveEmployeesContacts(List<EmployeeContact> employeeContactList, Employee employee) throws CustomException {
        for(EmployeeContact employeeContact : employeeContactList){
            employeeContact.setEmployee(employee);

            employeeDao.saveEmployeeContactInfo(employeeContact);
        }
    }

    private void saveEmployeesEmails(List<EmployeeEmail> employeeEmailList, Employee employee) throws CustomException {
        for(EmployeeEmail employeeEmail : employeeEmailList){
            employeeEmail.setEmployee(employee);

            employeeDao.saveEmployeeEmail(employeeEmail);
        }
    }

    private void validateInputForCreation(Employee employee) throws CustomException {
        if(employee.getFirstName() == null){
            ErrorContext errorContext = new ErrorContext(null, "Employee", "First Name not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        if(employee.getLastName() == null){
            ErrorContext errorContext = new ErrorContext(null, "Employee", "Last Name not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        if(employee.getUserId() == null){
            ErrorContext errorContext = new ErrorContext(null, "Employee", "UserID not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(employee.getEmployeeInfo().getPresentAddress() == null){
            ErrorContext errorContext = new ErrorContext(null, "EmployeeInfo", "Address not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(Utility.isNullOrEmpty(employee.getDesignationList())){
            ErrorContext errorContext = new ErrorContext(null, "EmployeeDesignation", "Employee designation not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(Utility.isNullOrEmpty(employee.getEmailList())){
            ErrorContext errorContext = new ErrorContext(null, "EmployeeEmail", "Employee email not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(Utility.isNullOrEmpty(employee.getContactList())){
            ErrorContext errorContext = new ErrorContext(null, "EmployeeContact", "Employee contact not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateEmployee(Employee employee) throws CustomException {
        validateInputForUpdate(employee);

        employee.setLastModifiedDate(Utility.today());

        boolean res = employeeDao.updateEmployee(employee);
        if(!res){
            ErrorContext errorContext = new ErrorContext(null, "Employee", "Employee delete failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        EmployeeInfo employeeInfo = employee.getEmployeeInfo();
        employeeInfo.setEmployee(employee);

        employeeDao.updateEmployeeInfo(employeeInfo);
    }

    private void validateInputForUpdate(Employee employee) throws CustomException {
        if(employee.getVersion() == 0){
            ErrorContext errorContext = new ErrorContext(null, "Employee", "Version not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteEmployee(Employee employee) throws CustomException {
        boolean res = employeeDao.deleteEmployee(employee);
        if(!res){
            ErrorContext errorContext = new ErrorContext(null, "Employee", "Employee update failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public Employee getEmployeeByID(String employeeID) throws CustomException {
        Employee employee = employeeDao.getEmployeeByID(employeeID);
        if(employee == null){
            ErrorContext errorContext = new ErrorContext(employeeID, "Employee", "Employee not found by employeeID: " + employeeID);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return employee;
    }

    @Override
    public Employee getEmployeeByUserID(String userID) throws CustomException {
        Employee employee = employeeDao.getEmployeeByUserID(userID);
        if(employee == null){
            ErrorContext errorContext = new ErrorContext(userID, "Employee", "Employee not found by userID: " + userID);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return employee;
    }

    @Override
    public Employee getEmployeeByEmployeeNO(String employeeNO) throws CustomException {
        Employee employee = employeeDao.getEmployeeByEmployeeNO(employeeNO);
        if(employee == null){
            ErrorContext errorContext = new ErrorContext(employeeNO, "Employee", "Employee not found by employeeNO: " + employeeNO);
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return employee;
    }

    @Override
    public List<Employee> getAllEmployees() throws CustomException {
        List<Employee> employeeList = employeeDao.getAllEmployees();
        if(employeeList == null){
            ErrorContext errorContext = new ErrorContext(null, "Employee", "Employee list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return employeeList;
    }
}
