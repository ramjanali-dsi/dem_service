package com.dsi.dem.service;

import com.dsi.dem.dto.EmployeeContactDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.EmployeeContact;
import com.dsi.dem.model.EmployeeEmail;

import java.util.List;

/**
 * Created by sabbir on 8/4/16.
 */
public interface ContactService {

    List<EmployeeContactDto> saveEmployeeContactInfo(List<EmployeeContactDto> contactDtoList,
                                                     String employeeID) throws CustomException;
    void saveEmployeeContactInfo(EmployeeContact employeeContact, String employeeId) throws CustomException;
    EmployeeContactDto updateEmployeeContactInfo(EmployeeContactDto employeeContactDto,
                                                 String employeeID, String contactId) throws CustomException;
    void updateEmployeeContactInfo(EmployeeContact employeeContact, String employeeID) throws CustomException;
    void deleteEmployeeContactInfo(String contactInfoID) throws CustomException;
    List<EmployeeContactDto> getEmployeesContactInfoByEmployeeID(String employeeID) throws CustomException;
    EmployeeContactDto getEmployeeContactInfo(String contactInfoID, String employeeID) throws CustomException;
}
