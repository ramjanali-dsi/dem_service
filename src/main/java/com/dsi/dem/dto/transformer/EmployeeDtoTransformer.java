package com.dsi.dem.dto.transformer;

import com.dsi.dem.dto.*;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorContext;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.*;
import com.dsi.dem.util.Constants;
import com.dsi.dem.util.ErrorTypeConstants;
import com.dsi.dem.util.Utility;
import org.apache.commons.beanutils.BeanUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 8/3/16.
 */
public class EmployeeDtoTransformer {

    public Employee getEmployee(EmployeeDto employeeDto) throws CustomException {

        Employee employee = new Employee();
        try {
            BeanUtils.copyProperties(employee, employeeDto);

            EmployeeInfo employeeInfo = new EmployeeInfo();
            BeanUtils.copyProperties(employeeInfo, employeeDto.getEmployeeInfo());

            EmployeeStatus employeeStatus = new EmployeeStatus();
            BeanUtils.copyProperties(employeeStatus, employeeDto.getEmployeeInfo());
            employeeInfo.setStatus(employeeStatus);
            employee.setInfo(employeeInfo);

            EmployeeLeave employeeLeave = new EmployeeLeave();
            BeanUtils.copyProperties(employeeLeave, employeeDto.getLeaveSummary());
            employee.setLeaveInfo(employeeLeave);

            List<EmployeeDesignation> designationList = new ArrayList<>();
            for(EmployeeDesignationDto designationDto : employeeDto.getDesignationList()){
                designationList.add(getEmployeeDesignation(designationDto));
            }
            employee.setDesignations(designationList);

            List<EmployeeEmail> emailList = new ArrayList<>();
            for(EmployeeEmailDto emailDto : employeeDto.getEmailList()){
                emailList.add(getEmployeeEmail(emailDto));
            }
            employee.setEmailInfo(emailList);

            List<EmployeeContact> contactList = new ArrayList<>();
            for(EmployeeContactDto contactDto : employeeDto.getContactList()){
                contactList.add(getEmployeeContactInfo(contactDto));
            }
            employee.setContactInfo(contactList);

        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }

        return employee;
    }

    public Employee getEmployeeWithInfo(EmployeeDto employeeDto) throws CustomException {
        Employee employee = new Employee();
        try {
            BeanUtils.copyProperties(employee, employeeDto);

            EmployeeInfo employeeInfo = new EmployeeInfo();
            BeanUtils.copyProperties(employeeInfo, employeeDto.getEmployeeInfo());

            EmployeeStatus employeeStatus = new EmployeeStatus();
            BeanUtils.copyProperties(employeeStatus, employeeDto.getEmployeeInfo());
            employeeInfo.setStatus(employeeStatus);
            employee.setInfo(employeeInfo);

            EmployeeLeave employeeLeave = new EmployeeLeave();
            BeanUtils.copyProperties(employeeLeave, employeeDto.getLeaveSummary());
            employee.setLeaveInfo(employeeLeave);

        } catch (Exception e) {
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }

        return employee;
    }

    public List<EmployeeDesignation> getDesignationList(List<EmployeeDesignationDto> designationDtoList) throws CustomException {

        List<EmployeeDesignation> designationList = new ArrayList<>();
        if(!Utility.isNullOrEmpty(designationDtoList)){
            for(EmployeeDesignationDto designationDto : designationDtoList){
                designationList.add(getEmployeeDesignation(designationDto));
            }
        }
        return designationList;
    }

    public EmployeeDesignation getEmployeeDesignation(EmployeeDesignationDto designationDto) throws CustomException {

        EmployeeDesignation designation = new EmployeeDesignation();
        try{
            BeanUtils.copyProperties(designation, designationDto);

        } catch (Exception e) {
            //ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }

        return designation;
    }

    public List<EmployeeEmail> getEmailList(List<EmployeeEmailDto> emailDtoList) throws CustomException {

        List<EmployeeEmail> emailList = new ArrayList<>();
        if(!Utility.isNullOrEmpty(emailDtoList)){
            for(EmployeeEmailDto emailDto : emailDtoList){
                emailList.add(getEmployeeEmail(emailDto));
            }
        }
        return emailList;
    }

    public EmployeeEmail getEmployeeEmail(EmployeeEmailDto emailDto) throws CustomException {

        EmployeeEmail email = new EmployeeEmail();
        try{
            BeanUtils.copyProperties(email, emailDto);

            EmployeeEmailType emailType = new EmployeeEmailType();
            BeanUtils.copyProperties(emailType, emailDto);
            email.setType(emailType);

        } catch (Exception e) {
            //ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }

        return email;
    }

    public List<EmployeeContact> getContactInfoList(List<EmployeeContactDto> contactDtoList) throws CustomException {

        List<EmployeeContact> contactList = new ArrayList<>();
        if(!Utility.isNullOrEmpty(contactDtoList)){
            for(EmployeeContactDto contactDto : contactDtoList){
                contactList.add(getEmployeeContactInfo(contactDto));
            }
        }
        return contactList;
    }

    public EmployeeContact getEmployeeContactInfo(EmployeeContactDto contactDto) throws CustomException {

        EmployeeContact contact = new EmployeeContact();
        try{
            BeanUtils.copyProperties(contact, contactDto);

            EmployeeContactType contactType = new EmployeeContactType();
            BeanUtils.copyProperties(contactType, contactDto);
            contact.setType(contactType);

        } catch (Exception e) {
            //ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }

        return contact;
    }

    public EmployeeDto getEmployeeDto(Employee employee) throws CustomException {

        EmployeeDto employeeDto = new EmployeeDto();
        try {
            BeanUtils.copyProperties(employeeDto, employee);

            EmployeeInfoDto infoDto = new EmployeeInfoDto();
            BeanUtils.copyProperties(infoDto, employee.getInfo());
            BeanUtils.copyProperties(infoDto, employee.getInfo().getStatus());
            employeeDto.setEmployeeInfo(infoDto);

            LeaveSummaryDto leaveDto = new LeaveSummaryDto();
            BeanUtils.copyProperties(leaveDto, employee.getLeaveInfo());
            leaveDto.setTotalLeave(employee.getLeaveInfo().getTotalSick() + employee.getLeaveInfo().getTotalCasual());
            employeeDto.setLeaveSummary(leaveDto);

            List<EmployeeDesignationDto> designationList = new ArrayList<>();
            for(EmployeeDesignation designation : employee.getDesignations()){
                designationList.add(getEmployeeDesignationDto(designation));
            }
            employeeDto.setDesignationList(designationList);

            List<EmployeeEmailDto> emailList = new ArrayList<>();
            for(EmployeeEmail email : employee.getEmailInfo()){
                emailList.add(getEmployeeEmailDto(email));
            }
            employeeDto.setEmailList(emailList);

            List<EmployeeContactDto> contactList = new ArrayList<>();
            for(EmployeeContact contact : employee.getContactInfo()){
                contactList.add(getEmployeeContactInfoDto(contact));
            }
            employeeDto.setContactList(contactList);

        } catch (Exception e) {
            //ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }

        return employeeDto;
    }

    public List<EmployeeDto> getEmployeesDto(List<Employee> employees) throws CustomException {

        List<EmployeeDto> employeeDtoList = new ArrayList<>();
        if(!Utility.isNullOrEmpty(employees)){
            for(Employee employee : employees){
                employeeDtoList.add(getEmployeeDto(employee));
            }
        }
        return employeeDtoList;
    }

    public List<EmployeeDesignationDto> getDesignationDtoList(List<EmployeeDesignation> designationList) throws CustomException {

        List<EmployeeDesignationDto> designationDtoList = new ArrayList<>();
        if(!Utility.isNullOrEmpty(designationList)){
            for(EmployeeDesignation designation : designationList){
                designationDtoList.add(getEmployeeDesignationDto(designation));
            }
        }
        return designationDtoList;
    }

    public EmployeeDesignationDto getEmployeeDesignationDto(EmployeeDesignation designation) throws CustomException {

        EmployeeDesignationDto designationDto = new EmployeeDesignationDto();
        try{
            BeanUtils.copyProperties(designationDto, designation);
            designationDto.setActivity(2);

        } catch (Exception e) {
            //ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }

        return designationDto;
    }

    public List<EmployeeEmailDto> getEmailDtoList(List<EmployeeEmail> emailList) throws CustomException {

        List<EmployeeEmailDto> emailDtoList = new ArrayList<>();
        if(!Utility.isNullOrEmpty(emailList)){
            for(EmployeeEmail email : emailList){
                emailDtoList.add(getEmployeeEmailDto(email));
            }
        }
        return emailDtoList;
    }

    public EmployeeEmailDto getEmployeeEmailDto(EmployeeEmail email) throws CustomException {

        EmployeeEmailDto emailDto = new EmployeeEmailDto();
        try{
            BeanUtils.copyProperties(emailDto, email);
            BeanUtils.copyProperties(emailDto, email.getType());
            emailDto.setActivity(2);

        } catch (Exception e) {
            //ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }

        return emailDto;
    }

    public List<EmployeeContactDto> getContactInfoDtoList(List<EmployeeContact> contactList) throws CustomException {

        List<EmployeeContactDto> contactDtoList = new ArrayList<>();
        if(!Utility.isNullOrEmpty(contactList)){
            for(EmployeeContact contact : contactList){
                contactDtoList.add(getEmployeeContactInfoDto(contact));
            }
        }
        return contactDtoList;
    }

    public EmployeeContactDto getEmployeeContactInfoDto(EmployeeContact contact) throws CustomException {

        EmployeeContactDto contactDto = new EmployeeContactDto();
        try{
            BeanUtils.copyProperties(contactDto, contact);
            BeanUtils.copyProperties(contactDto, contact.getType());
            contactDto.setActivity(2);

        } catch (Exception e) {
            //ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0007,
                    Constants.DEM_SERVICE_0007_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_007);
            throw new CustomException(errorMessage);
        }

        return contactDto;
    }
}
