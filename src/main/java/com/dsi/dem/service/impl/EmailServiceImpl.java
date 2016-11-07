package com.dsi.dem.service.impl;

import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.dao.impl.EmployeeDaoImpl;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Employee;
import com.dsi.dem.model.EmployeeEmail;
import com.dsi.dem.service.EmailService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.Utility;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by sabbir on 8/1/16.
 */
public class EmailServiceImpl implements EmailService {

    private static final Logger logger = Logger.getLogger(EmailService.class);

    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public void saveEmployeeEmail(List<EmployeeEmail> employeeEmailList, String employeeID) throws CustomException {

        if(Utility.isNullOrEmpty(employeeEmailList)){
            //ErrorContext errorContext = new ErrorContext(null, "EmployeeEmail", "Email list not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0010);
            throw new CustomException(errorMessage);
        }

        for(EmployeeEmail employeeEmail : employeeEmailList) {
            saveEmail(employeeEmail, employeeID);
        }
    }

    @Override
    public void saveEmployeeEmail(EmployeeEmail employeeEmail, String employeeID) throws CustomException {
        saveEmail(employeeEmail, employeeID);
    }

    private void saveEmail(EmployeeEmail employeeEmail, String employeeID) throws CustomException {
        validateInputForCreation(employeeEmail);

        Employee employee = employeeDao.getEmployeeByID(employeeID);
        employeeEmail.setVersion(employee.getVersion());
        employeeEmail.setPreferred(false);
        employeeEmail.setEmployee(employee);
        boolean res = employeeDao.saveEmployeeEmail(employeeEmail);
        if(!res){
            //ErrorContext errorContext = new ErrorContext(null, "EmployeeEmail", "Employees email create failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0010);
            throw new CustomException(errorMessage);
        }
        logger.info("Save employees email success.");
    }

    private void validateInputForCreation(EmployeeEmail employeeEmail) throws CustomException {
        if(employeeEmail.getEmail() == null){
            //ErrorContext errorContext = new ErrorContext(null, "EmployeeEmail", "Email not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0011);
            throw new CustomException(errorMessage);
        }

        if(employeeEmail.getType().getEmailTypeId() == null){
            //ErrorContext errorContext = new ErrorContext(null, "EmployeeEmail", "Email type not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0012);
            throw new CustomException(errorMessage);
        }

        if(employeeDao.getEmployeeEmailByEmailName(employeeEmail.getEmail()) != null){
            //ErrorContext errorContext = new ErrorContext(employeeEmail.getEmail(), "EmployeeEmail", "Email already exist.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0013);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateEmployeeEmail(EmployeeEmail employeeEmail, String employeeID) throws CustomException {
        validateInputForUpdate(employeeEmail, employeeID);

        EmployeeEmail existEmail = employeeDao.getEmployeeEmailByEmailIDAndEmployeeID(
                employeeEmail.getEmailId(), employeeID);

        existEmail.setEmail(employeeEmail.getEmail());
        existEmail.setType(employeeEmail.getType());
        existEmail.setVersion(employeeEmail.getVersion());
        boolean res = employeeDao.updateEmployeeEmail(existEmail);
        if(!res){
            //ErrorContext errorContext = new ErrorContext(employeeEmail.getEmailId(), "EmployeeEmail", "Employees email update failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0010);
            throw new CustomException(errorMessage);
        }
        logger.info("Update employees email success");
    }

    private void validateInputForUpdate(EmployeeEmail employeeEmail, String employeeID) throws CustomException {
        if(employeeEmail.getVersion() == 0){
            //ErrorContext errorContext = new ErrorContext(null, "EmployeeEmail", "Version not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_002);
            throw new CustomException(errorMessage);
        }

        if(employeeDao.getEmployeeEmailByEmailIDAndEmployeeID(employeeEmail.getEmailId(), employeeID) == null){
            //ErrorContext errorContext = new ErrorContext(employeeEmail.getEmailId(), "EmployeeEmail", "Employees email not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0010);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteEmployeeEmail(String emailID) throws CustomException {
        boolean res = employeeDao.deleteEmployeeEmail(null, emailID);
        if(!res){
            //ErrorContext errorContext = new ErrorContext(emailID, "EmployeeEmail", "Employees email delete failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0010);
            throw new CustomException(errorMessage);
        }
        logger.info("Delete employees email success");
    }

    @Override
    public List<EmployeeEmail> getEmployeesEmailByEmployeeID(String employeeID) throws CustomException {
        List<EmployeeEmail> employeeEmails = employeeDao.getEmployeeEmailsByEmployeeID(employeeID);
        if(employeeEmails == null){
            //ErrorContext errorContext = new ErrorContext(null, "EmployeeEmail", "Employees email list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        return employeeEmails;
    }

    @Override
    public EmployeeEmail getEmployeeEmail(String emailID, String employeeID) throws CustomException {
        EmployeeEmail employeeEmail = employeeDao.getEmployeeEmailByEmailIDAndEmployeeID(emailID, employeeID);
        if(employeeEmail == null){
            //ErrorContext errorContext = new ErrorContext(emailID, "EmployeeEmail", "Employees email not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }
        return employeeEmail;
    }
}
