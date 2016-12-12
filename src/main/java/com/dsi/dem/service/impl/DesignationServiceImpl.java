package com.dsi.dem.service.impl;

import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.dao.impl.EmployeeDaoImpl;
import com.dsi.dem.dto.EmployeeDesignationDto;
import com.dsi.dem.dto.transformer.EmployeeDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Employee;
import com.dsi.dem.model.EmployeeDesignation;
import com.dsi.dem.service.DesignationService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.Utility;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by sabbir on 8/4/16.
 */
public class DesignationServiceImpl extends CommonService implements DesignationService {

    private static final Logger logger = Logger.getLogger(DesignationServiceImpl.class);

    private static final EmployeeDtoTransformer EMPLOYEE_DTO_TRANSFORMER = new EmployeeDtoTransformer();
    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public List<EmployeeDesignationDto> saveEmployeeDesignation(List<EmployeeDesignationDto> designationDtoList, String employeeID) throws CustomException {
        logger.info("Employees Designations info Create:: Start");
        logger.info("Convert EmployeeDesignation Dto to EmployeeDesignation Object");
        List<EmployeeDesignation> designationList = EMPLOYEE_DTO_TRANSFORMER.getDesignationList(designationDtoList);

        if(Utility.isNullOrEmpty(designationList)){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0007);
            throw new CustomException(errorMessage);
        }


        for(EmployeeDesignation designation : designationList){
            validateInputForCreation(designation);
            saveDesignation(designation, employeeID);
        }

        List<EmployeeDesignation> designations = employeeDao.getEmployeeDesignationsByEmployeeID(employeeID);
        logger.info("Employee Designations info Create:: End");

        return EMPLOYEE_DTO_TRANSFORMER.getDesignationDtoList(designations);
    }

    private void validateInputForCreation(EmployeeDesignation designation) throws CustomException {
        if(designation.getName() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0016);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void saveEmployeeDesignation(EmployeeDesignation designation, String employeeID) throws CustomException {

        validateInputForCreation(designation);
        saveDesignation(designation, employeeID);


    }

    private void saveDesignation(EmployeeDesignation designation, String employeeID) throws CustomException {

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
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0007);
            throw new CustomException(errorMessage);
        }
        logger.info("Save employees designation success.");
    }

    @Override
    public EmployeeDesignationDto updateEmployeeDesignation(EmployeeDesignationDto designationDto, String employeeID,
                                                            String designationId) throws CustomException {
        logger.info("Employees designation info update:: Start");
        logger.info("Convert EmployeeDesignation Dto to EmployeeDesignation Object");
        EmployeeDesignation designation = EMPLOYEE_DTO_TRANSFORMER.getEmployeeDesignation(designationDto);

        validateInputForUpdate(designation);

        designation.setDesignationId(designationId);
        if(employeeDao.getEmployeeDesignationByDesignationIDAndEmployeeID(designation.getDesignationId(), employeeID) == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0007);
            throw new CustomException(errorMessage);
        }

        designation.setEmployee(employeeDao.getEmployeeByID(employeeID));
        boolean res = employeeDao.updateEmployeeDesignation(designation);
        if(!res){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0007);
            throw new CustomException(errorMessage);
        }
        logger.info("Update employees designation success");

        return EMPLOYEE_DTO_TRANSFORMER.getEmployeeDesignationDto(designation);
    }

    @Override
    public void updateEmployeeDesignation(EmployeeDesignation designation, String employeeID) throws CustomException {
        validateInputForUpdate(designation);

        if(employeeDao.getEmployeeDesignationByDesignationIDAndEmployeeID(designation.getDesignationId(), employeeID) == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0007);
            throw new CustomException(errorMessage);
        }

        designation.setEmployee(employeeDao.getEmployeeByID(employeeID));
        boolean res = employeeDao.updateEmployeeDesignation(designation);
        if(!res){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0007);
            throw new CustomException(errorMessage);
        }
        logger.info("Update employees designation success");
    }

    private void validateInputForUpdate(EmployeeDesignation designation) throws CustomException {
        if(designation.getVersion() == 0){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_002);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteEmployeeDesignation(String designationID) throws CustomException {
        logger.info("Employees designation info delete:: Start");

        boolean res = employeeDao.deleteEmployeeDesignation(null, designationID);
        if(!res){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0007);
            throw new CustomException(errorMessage);
        }
        logger.info("Delete employees designation success");
        logger.info("Employees designation info delete:: End");
    }

    @Override
    public List<EmployeeDesignationDto> getEmployeesDesignationByEmployeeID(String employeeID) throws CustomException {

        List<EmployeeDesignation> designationList = employeeDao.getEmployeeDesignationsByEmployeeID(employeeID);
        if(designationList == null){

            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        return EMPLOYEE_DTO_TRANSFORMER.getDesignationDtoList(designationList);
    }

    @Override
    public EmployeeDesignationDto getEmployeeDesignation(String designationID, String employeeID) throws CustomException {
        logger.info("Read an employees designation info");

        EmployeeDesignation designation = employeeDao.getEmployeeDesignationByDesignationIDAndEmployeeID(designationID, employeeID);
        if(designation == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        return EMPLOYEE_DTO_TRANSFORMER.getEmployeeDesignationDto(designation);
    }
}
