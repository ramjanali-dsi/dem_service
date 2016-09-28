package com.dsi.dem.service.impl;

import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.dao.impl.EmployeeDaoImpl;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Employee;
import com.dsi.dem.model.EmployeeDesignation;
import com.dsi.dem.service.DesignationService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.Utility;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by sabbir on 8/4/16.
 */
public class DesignationServiceImpl implements DesignationService {

    private static final Logger logger = Logger.getLogger(DesignationServiceImpl.class);

    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public void saveEmployeeDesignation(List<EmployeeDesignation> designationList, String employeeID) throws CustomException {

        if(Utility.isNullOrEmpty(designationList)){
            ErrorContext errorContext = new ErrorContext(null, "EmployeeDesignation", "Designation list not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        for(EmployeeDesignation designation : designationList){
            saveDesignation(designation, employeeID);
        }
    }

    @Override
    public void saveEmployeeDesignation(EmployeeDesignation designation, String employeeID) throws CustomException {
        saveDesignation(designation, employeeID);
    }

    private void saveDesignation(EmployeeDesignation designation, String employeeID) throws CustomException {
        validateInputForCreation(designation);

        if(employeeDao.getEmployeeDesignationsByEmployeeID(employeeID).size() > 0){
            logger.info("Updating previous designations");
            employeeDao.updatePrevEmployeeDesignations(employeeID);
        }

        Employee employee = employeeDao.getEmployeeByID(employeeID);
        designation.setVersion(employee.getVersion());
        designation.setCurrent(true);
        designation.setEmployee(employee);
        boolean res = employeeDao.saveEmployeeDesignation(designation);
        if(!res){
            ErrorContext errorContext = new ErrorContext(null, "EmployeeDesignation", "Employees designation create failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Save employees designation success.");
    }

    private void validateInputForCreation(EmployeeDesignation designation) throws CustomException {
        if(designation.getName() == null){
            ErrorContext errorContext = new ErrorContext(null, "EmployeeDesignation", "Designation name not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateEmployeeDesignation(EmployeeDesignation designation, String employeeID) throws CustomException {
        validateInputForUpdate(designation, employeeID);

        designation.setEmployee(employeeDao.getEmployeeByID(employeeID));
        boolean res = employeeDao.updateEmployeeDesignation(designation);
        if(!res){
            ErrorContext errorContext = new ErrorContext(designation.getDesignationId(), "EmployeeDesignation",
                    "Employees designation update failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Update employees designation success");
    }

    private void validateInputForUpdate(EmployeeDesignation designation, String employeeID) throws CustomException {
        if(designation.getVersion() == 0){
            ErrorContext errorContext = new ErrorContext(null, "EmployeeDesignation", "Version not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(employeeDao.getEmployeeDesignationByDesignationIDAndEmployeeID(designation.getDesignationId(), employeeID) == null){
            ErrorContext errorContext = new ErrorContext(designation.getDesignationId(), "EmployeeDesignation",
                    "Employees designation not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteEmployeeDesignation(String designationID) throws CustomException {
        boolean res = employeeDao.deleteEmployeeDesignation(null, designationID);
        if(!res){
            ErrorContext errorContext = new ErrorContext(designationID, "EmployeeDesignation", "Employees designation delete failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Delete employees designation success");
    }

    @Override
    public List<EmployeeDesignation> getEmployeesDesignationByEmployeeID(String employeeID) throws CustomException {
        List<EmployeeDesignation> designationList = employeeDao.getEmployeeDesignationsByEmployeeID(employeeID);
        if(designationList == null){
            ErrorContext errorContext = new ErrorContext(null, "EmployeeDesignation", "Employees designation list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return designationList;
    }

    @Override
    public EmployeeDesignation getEmployeeDesignation(String designationID, String employeeID) throws CustomException {
        EmployeeDesignation designation = employeeDao.getEmployeeDesignationByDesignationIDAndEmployeeID(designationID, employeeID);
        if(designation == null){
            ErrorContext errorContext = new ErrorContext(designationID, "EmployeeDesignation", "Employees designation not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return designation;
    }
}
