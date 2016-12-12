package com.dsi.dem.service.impl;

import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.dao.impl.EmployeeDaoImpl;
import com.dsi.dem.dto.EmployeeContactDto;
import com.dsi.dem.dto.transformer.EmployeeDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Employee;
import com.dsi.dem.model.EmployeeContact;
import com.dsi.dem.service.ContactService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.Utility;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by sabbir on 8/4/16.
 */
public class ContactServiceImpl extends CommonService implements ContactService {

    private static final Logger logger = Logger.getLogger(ContactServiceImpl.class);

    private static final EmployeeDtoTransformer EMPLOYEE_DTO_TRANSFORMER = new EmployeeDtoTransformer();
    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public List<EmployeeContactDto> saveEmployeeContactInfo(List<EmployeeContactDto> contactDtoList, String employeeID) throws CustomException {
        logger.info("Employees Contacts info Create:: Start");
        logger.info("Convert EmployeeContact Dto to EmployeeContact Object");
        List<EmployeeContact> contactList = EMPLOYEE_DTO_TRANSFORMER.getContactInfoList(contactDtoList);

        if(Utility.isNullOrEmpty(contactList)){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0009);
            throw new CustomException(errorMessage);
        }

        for(EmployeeContact contact : contactList){
            validationInputForCreation(contact);
            saveContact(contact, employeeID);
        }

        List<EmployeeContact> employeeContacts = employeeDao.getEmployeeContactsByEmployeeID(employeeID);
        logger.info("Employees Contacts info Create:: End");

        return EMPLOYEE_DTO_TRANSFORMER.getContactInfoDtoList(employeeContacts);
    }

    @Override
    public void saveEmployeeContactInfo(EmployeeContact employeeContact, String employeeId) throws CustomException {

        validationInputForCreation(employeeContact);
        saveContact(employeeContact, employeeId);

    }

    private void saveContact(EmployeeContact contact, String employeeID) throws CustomException {

        Employee employee = employeeDao.getEmployeeByID(employeeID);
        contact.setVersion(employee.getVersion());
        contact.setEmployee(employee);
        boolean res = employeeDao.saveEmployeeContactInfo(contact);
        if(!res){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                    Constants.DEM_SERVICE_0002_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0009);
            throw new CustomException(errorMessage);
        }
        logger.info("Save employees contact info success.");
    }

    private void validationInputForCreation(EmployeeContact contact) throws CustomException {
        if(contact.getPhone() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0014);
            throw new CustomException(errorMessage);
        }

        if(contact.getType().getContactTypeId() == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0015);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public EmployeeContactDto updateEmployeeContactInfo(EmployeeContactDto employeeContactDto,
                                                        String employeeID, String contactId) throws CustomException {
        logger.info("Employees contact info update:: Start");
        logger.info("Convert EmployeeContact Dto to EmployeeContact Object");
        EmployeeContact employeeContact = EMPLOYEE_DTO_TRANSFORMER.getEmployeeContactInfo(employeeContactDto);

        validateInputForUpdate(employeeContact);

        employeeContact.setContactNumberId(contactId);
        EmployeeContact existContact = employeeDao.getEmployeeContactByIDAndEmployeeID(
                employeeContact.getContactNumberId(), employeeID);

        if(existContact == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0009);
            throw new CustomException(errorMessage);
        }

        existContact.setPhone(employeeContact.getPhone());
        existContact.setType(employeeContact.getType());
        existContact.setVersion(employeeContact.getVersion());
        boolean res = employeeDao.updateEmployeeContactInfo(existContact);
        if(!res){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0009);
            throw new CustomException(errorMessage);
        }
        logger.info("Update employees contact info success");

        logger.info("Employees contact info update:: End");
        return EMPLOYEE_DTO_TRANSFORMER.getEmployeeContactInfoDto(existContact);
    }

    @Override
    public void updateEmployeeContactInfo(EmployeeContact employeeContact, String employeeID) throws CustomException {
        validateInputForUpdate(employeeContact);

        EmployeeContact existContact = employeeDao.getEmployeeContactByIDAndEmployeeID(
                employeeContact.getContactNumberId(), employeeID);

        if(existContact == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0009);
            throw new CustomException(errorMessage);
        }

        existContact.setPhone(employeeContact.getPhone());
        existContact.setType(employeeContact.getType());
        existContact.setVersion(employeeContact.getVersion());
        boolean res = employeeDao.updateEmployeeContactInfo(existContact);
        if(!res){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0009);
            throw new CustomException(errorMessage);
        }
        logger.info("Update employees contact info success");

    }

    private void validateInputForUpdate(EmployeeContact employeeContact) throws CustomException {
        if(employeeContact.getVersion() == 0){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0014,
                    Constants.DEM_SERVICE_0014_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_002);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteEmployeeContactInfo(String contactInfoID) throws CustomException {
        logger.info("Employees contact info delete:: Start");

        boolean res = employeeDao.deleteEmployeeContactInfo(null, contactInfoID);
        if(!res){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, ErrorTypeConstants.DEM_EMPLOYEE_ERROR_TYPE_0009);
            throw new CustomException(errorMessage);
        }
        logger.info("Delete employees contact info success");
        logger.info("Employees contact info delete:: End");
    }

    @Override
    public List<EmployeeContactDto> getEmployeesContactInfoByEmployeeID(String employeeID) throws CustomException {
        logger.info("Read employees all contact info");

        List<EmployeeContact> contactList = employeeDao.getEmployeeContactsByEmployeeID(employeeID);
        if(contactList == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        return EMPLOYEE_DTO_TRANSFORMER.getContactInfoDtoList(contactList);
    }

    @Override
    public EmployeeContactDto getEmployeeContactInfo(String contactInfoID, String employeeID) throws CustomException {
        logger.info("Read an employees contact info");

        EmployeeContact contact = employeeDao.getEmployeeContactByIDAndEmployeeID(contactInfoID, employeeID);
        if(contact == null){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_001);
            throw new CustomException(errorMessage);
        }

        return EMPLOYEE_DTO_TRANSFORMER.getEmployeeContactInfoDto(contact);
    }
}
