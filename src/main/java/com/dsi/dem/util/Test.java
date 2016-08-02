package com.dsi.dem.util;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.Employee;

/**
 * Created by sabbir on 7/29/16.
 */
public class Test {

    public static void main(String[] args) {

        String body = "{\n" +
                "  \"employeeNo\": \"\",\n" +
                "  \"firstName\": \"\",\n" +
                "  \"lastName\": \"\",\n" +
                "  \"nickName\": \"\",\n" +
                "  \"bankAcNo\": \"\",\n" +
                "  \"ipAddress\": \"\",\n" +
                "  \"macAddress\": \"\",\n" +
                "  \"githubId\": \"\",\n" +
                "  \"skypeId\": \"\",\n" +
                "  \"nationalId\": \"\",\n" +
                "  \"eTinId\": \"\",\n" +
                "  \"joiningDate\": \"\",\n" +
                "  \"dateOfConfirmation\": \"\",\n" +
                "  \"resignDate\": \"\",\n" +
                "  \"employeeInfo\": {\n" +
                "    \"gender\": \"\",\n" +
                "    \"photoUrl\": \"\",\n" +
                "    \"fatherName\": \"\",\n" +
                "    \"motherName\": \"\",\n" +
                "    \"spouseName\": \"\",\n" +
                "    \"presentAddress\": \"\",\n" +
                "    \"permanentAddress\": \"\",\n" +
                "    \"dateOfBirth\": \"\",\n" +
                "    \"bloodGroup\": \"\"\n" +
                "  },\n" +
                "  \"designationList\": [\n" +
                "    {\n" +
                "      \"name\": \"\",\n" +
                "      \"designationDate\": \"\",\n" +
                "      \"isCurrent\": true\n" +
                "    }\n" +
                "  ],\n" +
                "  \"contactList\": [\n" +
                "    {\n" +
                "      \"phone\": \"\",\n" +
                "      \"type\": \"\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"emailList\": [\n" +
                "    {\n" +
                "      \"email\": \"\",\n" +
                "      \"type\": \"\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"active\": false,\n" +
                "  \"version\": 1\n" +
                "}";

        Employee employee = new Employee();
        try {
            employee = (Employee) Utility.convertMapToObject(body, employee);

            System.out.println(employee.getBankAcNo());

        } catch (CustomException e) {
            e.printStackTrace();
        }
    }
}
