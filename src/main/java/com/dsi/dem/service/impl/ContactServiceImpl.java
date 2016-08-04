package com.dsi.dem.service.impl;

import com.dsi.dem.dao.EmployeeDao;
import com.dsi.dem.dao.impl.EmployeeDaoImpl;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.EmployeeContact;
import com.dsi.dem.service.ContactService;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.Utility;
import org.apache.log4j.Logger;

import java.util.List;

/**
 * Created by sabbir on 8/4/16.
 */
public class ContactServiceImpl implements ContactService {

    private static final Logger logger = Logger.getLogger(ContactServiceImpl.class);

    private static final EmployeeDao employeeDao = new EmployeeDaoImpl();

    @Override
    public void saveEmployeeContactInfo(List<EmployeeContact> contactList, String employeeID) throws CustomException {

        if(Utility.isNullOrEmpty(contactList)){
            ErrorContext errorContext = new ErrorContext(null, "EmployeeContact", "Contact info list not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        for(EmployeeContact contact : contactList){
            validateInputForCreation(contact);

            contact.setEmployee(employeeDao.getEmployeeByID(employeeID));
            boolean res = employeeDao.saveEmployeeContactInfo(contact);
            if(!res){
                ErrorContext errorContext = new ErrorContext(null, "EmployeeContact", "Employees contact info create failed.");
                ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0002,
                        Constants.DEM_SERVICE_0002_DESCRIPTION, errorContext);
                throw new CustomException(errorMessage);
            }
            logger.info("Save employees contact info success.");
        }
    }

    private void validateInputForCreation(EmployeeContact contact) throws CustomException {
        if(contact.getPhone() == null){
            ErrorContext errorContext = new ErrorContext(null, "EmployeeContact", "Contact number not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(contact.getType().getContactTypeId() == null){
            ErrorContext errorContext = new ErrorContext(null, "EmployeeContact", "Contact info type not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void updateEmployeeContactInfo(EmployeeContact employeeContact, String employeeID) throws CustomException {
        validateInputForUpdate(employeeContact, employeeID);

        employeeContact.setEmployee(employeeDao.getEmployeeByID(employeeID));
        boolean res = employeeDao.updateEmployeeContactInfo(employeeContact);
        if(!res){
            ErrorContext errorContext = new ErrorContext(employeeContact.getContactNumberId(), "EmployeeContact",
                    "Employees contact info update failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0003,
                    Constants.DEM_SERVICE_0003_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Update employees contact info success");
    }

    private void validateInputForUpdate(EmployeeContact employeeContact, String employeeID) throws CustomException {
        if(employeeContact.getVersion() == 0){
            ErrorContext errorContext = new ErrorContext(null, "EmployeeContact", "Version not defined.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0001,
                    Constants.DEM_SERVICE_0001_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }

        if(employeeDao.getEmployeeContactByEmailIDAndEmployeeID(employeeContact.getContactNumberId(), employeeID) == null){
            ErrorContext errorContext = new ErrorContext(employeeContact.getContactNumberId(), "EmployeeContact",
                    "Employees contact info not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
    }

    @Override
    public void deleteEmployeeContactInfo(String contactInfoID) throws CustomException {
        boolean res = employeeDao.deleteEmployeeContactInfo(null, contactInfoID);
        if(!res){
            ErrorContext errorContext = new ErrorContext(contactInfoID, "EmployeeContact", "Employees contact info delete failed.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0004,
                    Constants.DEM_SERVICE_0004_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        logger.info("Delete employees contact info success");
    }

    @Override
    public List<EmployeeContact> getEmployeesContactInfoByEmployeeID(String employeeID) throws CustomException {
        List<EmployeeContact> contactList = employeeDao.getEmployeeContactsByEmployeeID(employeeID);
        if(contactList == null){
            ErrorContext errorContext = new ErrorContext(null, "EmployeeContact", "Employees contact info list not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return contactList;
    }

    @Override
    public EmployeeContact getEmployeeContactInfo(String contactInfoID, String employeeID) throws CustomException {
        EmployeeContact contact = employeeDao.getEmployeeContactByEmailIDAndEmployeeID(contactInfoID, employeeID);
        if(contact == null){
            ErrorContext errorContext = new ErrorContext(contactInfoID, "EmployeeContact", "Employees contact info not found.");
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0005,
                    Constants.DEM_SERVICE_0005_DESCRIPTION, errorContext);
            throw new CustomException(errorMessage);
        }
        return contact;
    }
}
