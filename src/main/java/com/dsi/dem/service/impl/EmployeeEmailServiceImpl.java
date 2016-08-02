package com.dsi.dem.service.impl;

import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.dao.impl.EmployeeDaoImpl;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.EmployeeEmail;
import com.dsi.dem.service.EmployeeEmailService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.Utility;

import java.util.List;

/**
 * Created by sabbir on 8/1/16.
 */
public class EmployeeEmailServiceImpl implements EmployeeEmailService {

    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public void saveEmployeeEmail(List<EmployeeEmail> employeeEmailList, String employeeID) throws CustomException {
        if(Utility.isNullOrEmpty(employeeEmailList)){
            ErrorContext errorContext = new ErrorContext(null, "EmployeeEmail", "Email list not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        for(EmployeeEmail employeeEmail : employeeEmailList) {
            validateInputForCreation(employeeEmail);

            EmployeeEmail isEmployeeEmail = employeeDao.getEmployeeEmailByEmailName(employeeEmail.getEmail());
            if(isEmployeeEmail != null){
                ErrorContext errorContext = new ErrorContext("Email", "Employee", "Employee already exist.");
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                        Constants.DEM_SERVICE_0002_DESCRIPTION, errorContext);
                throw new CustomException(errorMessage);
            }

            employeeEmail.setEmployee(employeeDao.getEmployeeByID(employeeID));
            employeeDao.saveEmployeeEmail(employeeEmail);
        }
    }

    private void validateInputForCreation(EmployeeEmail employeeEmail) throws CustomException {
        if(employeeEmail.getEmail() == null){
            ErrorContext errorContext = new ErrorContext(null, "EmployeeEmail", "Email not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        if(employeeEmail.getType().getEmployeeEmailTypeId() == null){
            ErrorContext errorContext = new ErrorContext(null, "EmployeeEmail", "Email type not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateEmployeeEmail(EmployeeEmail employeeEmail, String employeeID) throws CustomException {

    }

    @Override
    public void deleteEmployeeEmail(String emailID) throws CustomException {

    }

    @Override
    public EmployeeEmail getEmployeeEmailByEmailAndType(String email, String type) {
        return null;
    }
}
