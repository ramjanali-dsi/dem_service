package com.dsi.dem.service.impl;

import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.dao.impl.EmployeeDaoImpl;
import com.dsi.dem.dto.EmployeeEmailDto;
import com.dsi.dem.dto.transformer.EmployeeDtoTransformer;
import com.dsi.dem.exception.CustomException;
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
public class EmailServiceImpl extends CommonService implements EmailService {

    private static final Logger logger = Logger.getLogger(EmailService.class);

    private static final EmployeeDtoTransformer EMPLOYEE_DTO_TRANSFORMER = new EmployeeDtoTransformer();
    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public List<EmployeeEmailDto> saveEmployeeEmail(List<EmployeeEmailDto> emailDtoList, String employeeID) throws CustomException {
        logger.info("Employee Emails Create:: Start");
        logger.info("Convert EmployeeEmail Dto to EmployeeEmail Object");
        List<EmployeeEmail> employeeEmailList = EMPLOYEE_DTO_TRANSFORMER.getEmailList(emailDtoList);

        if(Utility.isNullOrEmpty(employeeEmailList)){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0010);
            throw new CustomException(errorMessage);
        }

        for(EmployeeEmail employeeEmail : employeeEmailList) {
            validateInputForCreation(employeeEmail);
            saveEmail(employeeEmail, employeeID);
        }

        List<EmployeeEmail> employeeEmails = employeeDao.getEmployeeEmailsByEmployeeID(employeeID);
        logger.info("Employee Emails Create:: End");

        return EMPLOYEE_DTO_TRANSFORMER.getEmailDtoList(employeeEmails);
    }

    @Override
    public void saveEmployeeEmail(EmployeeEmail employeeEmail, String employeeId) throws CustomException {

        validateInputForCreation(employeeEmail);
        saveEmail(employeeEmail, employeeId);

    }

    private void saveEmail(EmployeeEmail employeeEmail, String employeeID) throws CustomException {

        Employee employee = employeeDao.getEmployeeByID(employeeID);
        employeeEmail.setVersion(employee.getVersion());
        employeeEmail.setPreferred(false);
        employeeEmail.setEmployee(employee);
        boolean res = employeeDao.saveEmployeeEmail(employeeEmail);
        if(!res){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0010);
            throw new CustomException(errorMessage);
        }
        logger.info("Save employees email success.");
    }

    private void validateInputForCreation(EmployeeEmail employeeEmail) throws CustomException {
        if(employeeEmail.getEmail() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0011);
            throw new CustomException(errorMessage);
        }

        if(employeeEmail.getType().getEmailTypeId() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0012);
            throw new CustomException(errorMessage);
        }

        if(employeeDao.getEmployeeEmailByEmailName(employeeEmail.getEmail()) != null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0013,
                    Constants.DEM_SERVICE_0013_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0013);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public EmployeeEmailDto updateEmployeeEmail(EmployeeEmailDto employeeEmailDto, String employeeID,
                                                String emailId) throws CustomException {
        logger.info("Employees email update:: Start");
        logger.info("Convert EmployeeEmail Dto to EmployeeEmail Object");
        EmployeeEmail employeeEmail = EMPLOYEE_DTO_TRANSFORMER.getEmployeeEmail(employeeEmailDto);

        validateInputForUpdate(employeeEmail);

        employeeEmail.setEmailId(emailId);
        EmployeeEmail existEmail = employeeDao.getEmployeeEmailByEmailIDAndEmployeeID(
                employeeEmail.getEmailId(), employeeID);

        if(existEmail == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0010);
            throw new CustomException(errorMessage);
        }

        existEmail.setEmail(employeeEmail.getEmail());
        existEmail.setType(employeeEmail.getType());
        existEmail.setVersion(employeeEmail.getVersion());
        boolean res = employeeDao.updateEmployeeEmail(existEmail);
        if(!res){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0010);
            throw new CustomException(errorMessage);
        }
        logger.info("Update employees email success");

        return EMPLOYEE_DTO_TRANSFORMER.getEmployeeEmailDto(existEmail);
    }

    @Override
    public void updateEmployeeEmail(EmployeeEmail employeeEmail, String employeeId) throws CustomException {
        validateInputForUpdate(employeeEmail);

        EmployeeEmail existEmail = employeeDao.getEmployeeEmailByEmailIDAndEmployeeID(
                employeeEmail.getEmailId(), employeeId);

        if(existEmail == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0010);
            throw new CustomException(errorMessage);
        }

        existEmail.setEmail(employeeEmail.getEmail());
        existEmail.setType(employeeEmail.getType());
        existEmail.setVersion(employeeEmail.getVersion());
        boolean res = employeeDao.updateEmployeeEmail(existEmail);
        if(!res){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0010);
            throw new CustomException(errorMessage);
        }
        logger.info("Update employees email success");
    }

    private void validateInputForUpdate(EmployeeEmail employeeEmail) throws CustomException {
        if(employeeEmail.getVersion() == 0){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_002);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteEmployeeEmail(String emailID) throws CustomException {
        logger.info("Employees email delete:: Start");

        boolean res = employeeDao.deleteEmployeeEmail(null, emailID);
        if(!res){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0010);
            throw new CustomException(errorMessage);
        }
        logger.info("Delete employees email success");
        logger.info("Employees email delete:: End");
    }

    @Override
    public List<EmployeeEmailDto> getEmployeesEmailByEmployeeID(String employeeID) throws CustomException {
        logger.info("Read employees all email info");

        List<EmployeeEmail> employeeEmails = employeeDao.getEmployeeEmailsByEmployeeID(employeeID);
        if(employeeEmails == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        return EMPLOYEE_DTO_TRANSFORMER.getEmailDtoList(employeeEmails);
    }

    @Override
    public EmployeeEmailDto getEmployeeEmail(String emailID, String employeeID) throws CustomException {
        logger.info("Read an employees email info");

        EmployeeEmail employeeEmail = employeeDao.getEmployeeEmailByEmailIDAndEmployeeID(emailID, employeeID);
        if(employeeEmail == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        return EMPLOYEE_DTO_TRANSFORMER.getEmployeeEmailDto(employeeEmail);
    }
}
