package com.dsi.dem.util;

import com.dsi.dem.dto.TeamDto;
import com.dsi.dem.dto.TeamMemberDto;
import com.dsi.dem.dto.transformer.EmployeeDtoTransformer;
import com.dsi.dem.dto.EmployeeDto;
import com.dsi.dem.dto.transformer.TeamDtoTransformer;
import com.dsi.dem.model.Employee;
import com.dsi.dem.model.Team;
import com.dsi.dem.model.TeamMember;
import com.dsi.dem.service.EmployeeService;
import com.dsi.dem.service.TeamService;
import com.dsi.dem.service.impl.EmployeeServiceImpl;
import com.dsi.dem.service.impl.TeamServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 7/29/16.
 */
public class Test {

    private static final EmployeeService employeeService = new EmployeeServiceImpl();
    private static final TeamService teamService = new TeamServiceImpl();
    private static final ObjectMapper mapper = new ObjectMapper();

    public static void main(String[] args) {

        //createEmployeeTest();

        //updateEmployeeTest();

        createTeamTest();
    }

    private static void createEmployeeTest(){

        String body = "{\n" +
                "  \"employeeNo\": \"01\",\n" +
                "  \"firstName\": \"Sabbir\",\n" +
                "  \"lastName\": \"Ahmed\",\n" +
                "  \"nickName\": \"\",\n" +
                "  \"userId\": \"1\",\n" +
                "  \"bankAcNo\": \"1245687\",\n" +
                "  \"ipAddress\": \"127.0.0.1\",\n" +
                "  \"macAddress\": \"ASD432\",\n" +
                "  \"githubId\": \"sabbir\",\n" +
                "  \"skypeId\": \"sabbir.cse\",\n" +
                "  \"nationalId\": \"1234658796522\",\n" +
                "  \"etinId\": \"123WER4\",\n" +
                "  \"joiningDate\": \"2016-05-01\",\n" +
                "  \"dateOfConfirmation\": \"2016-05-01\",\n" +
                "  \"resignDate\": \"\",\n" +
                "  \"version\": 1,\n" +
                "  \"employeeInfo\": {\n" +
                "    \"gender\": \"Male\",\n" +
                "    \"photoUrl\": \"http://www.dsi.com/image/sabbir.jpg\",\n" +
                "    \"fatherName\": \"Abdul Haye\",\n" +
                "    \"motherName\": \"Sufia Begum\",\n" +
                "    \"spouseName\": \"Shetu Hossain\",\n" +
                "    \"presentAddress\": \"Mirpur, Dhaka\",\n" +
                "    \"permanentAddress\": \"Barisal\",\n" +
                "    \"dateOfBirth\": \"1991-04-02\",\n" +
                "    \"bloodGroup\": \"A+\",\n" +
                "    \"version\": 1\n" +
                "  },\n" +
                "  \"designationList\": [\n" +
                "    {\n" +
                "      \"name\": \"Software Engineer\",\n" +
                "      \"designationDate\": \"2015-10-01\",\n" +
                "      \"version\": 1,\n" +
                "      \"current\": true\n" +
                "    }\n" +
                "  ],\n" +
                "  \"emailList\": [\n" +
                "    {\n" +
                "      \"email\": \"sabbir.dsi@gmail.com\",\n" +
                "      \"emailTypeId\": \"78b5c3c5-5470-49f7-aafe-b016a33b3883\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"contactList\": [\n" +
                "    {\n" +
                "      \"phone\": \"01730034202\",\n" +
                "      \"contactTypeId\": \"cf2689f0-5165-4c02-a55c-25a9a32d8dd4\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"active\": true\n" +
                "}";

        try {
            EmployeeDto employeeDto = mapper.readValue(body, EmployeeDto.class);

            EmployeeDtoTransformer employeeDtoTransformer = new EmployeeDtoTransformer();
            Employee employee = employeeDtoTransformer.getEmployee(employeeDto);

            System.out.println("Employee Object After Convert: " + new Gson().toJson(employee));

            employeeService.saveEmployee(employee);

            employeeDto = employeeDtoTransformer.getEmployeeDto(employeeService.getEmployeeByUserID(employee.getUserId()));

            System.out.println("Final Object: " + new Gson().toJson(employeeDto));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void updateEmployeeTest(){

        String body = "{\n" +
                "  \"employeeNo\": \"01\",\n" +
                "  \"firstName\": \"Sabbir\",\n" +
                "  \"lastName\": \"Ahmed\",\n" +
                "  \"nickName\": \"Masum\",\n" +
                "  \"userId\": \"1\",\n" +
                "  \"bankAcNo\": \"1245687\",\n" +
                "  \"ipAddress\": \"127.0.0.1\",\n" +
                "  \"macAddress\": \"ASD432\",\n" +
                "  \"githubId\": \"sabbir\",\n" +
                "  \"skypeId\": \"sabbir.cse\",\n" +
                "  \"nationalId\": \"1234658796522\",\n" +
                "  \"etinId\": \"123WER4\",\n" +
                "  \"joiningDate\": \"2016-05-01\",\n" +
                "  \"dateOfConfirmation\": \"2016-05-01\",\n" +
                "  \"resignDate\": \"\",\n" +
                "  \"version\": 1,\n" +
                "  \"employeeInfo\": {\n" +
                "    \"gender\": \"Male\",\n" +
                "    \"photoUrl\": \"http://www.dsi.com/image/sabbir.jpg\",\n" +
                "    \"fatherName\": \"Abdul Haye\",\n" +
                "    \"motherName\": \"Sufia Begum\",\n" +
                "    \"spouseName\": \"Shetu Hossain\",\n" +
                "    \"presentAddress\": \"Mirpur\",\n" +
                "    \"permanentAddress\": \"Barisal\",\n" +
                "    \"dateOfBirth\": \"1991-04-02\",\n" +
                "    \"bloodGroup\": \"A+\",\n" +
                "    \"version\": 1\n" +
                "  },\n" +
                "  \"active\": true\n" +
                "}";

        try {
            EmployeeDto employeeDto = mapper.readValue(body, EmployeeDto.class);

            EmployeeDtoTransformer employeeDtoTransformer = new EmployeeDtoTransformer();
            Employee employee = employeeDtoTransformer.getEmployee(employeeDto);

            System.out.println("Employee Object After Convert: " + new Gson().toJson(employee));

            employee.setEmployeeId("d0f3baa2-c3e2-4b68-91ec-160c0523d815");
            employeeService.updateEmployee(employee);

            employeeDto = employeeDtoTransformer.getEmployeeDto(employeeService.getEmployeeByUserID(employee.getUserId()));

            System.out.println("Final Object: " + new Gson().toJson(employeeDto));

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void createTeamTest() {

        String body = "{\n" +
                "  \"name\": \"Product\",\n" +
                "  \"floor\": \"Ground\",\n" +
                "  \"room\": \"1\",\n" +
                "  \"memberCount\": 2,\n" +
                "  \"memberList\": [\n" +
                "    {\n" +
                "      \"employeeId\": \"3b7c1dc1-7a64-4ac4-b7c6-e4c9cd6cb95b\",\n" +
                "      \"roleId\": \"ff4ea6f2-f0eb-4189-8d21-4673fd51a12a\"\n" +
                "    }\n" +
                "  ],\n" +
                "  \"projectIds\": [],\n" +
                "  \"active\": true,\n" +
                "  \"version\": 1\n" +
                "}";

        try{
            TeamDto teamDto = mapper.readValue(body, TeamDto.class);

            TeamDtoTransformer transformer = new TeamDtoTransformer();
            //Team team = transformer.getTeam(teamDto);

            //teamService.saveTeam(team);

            teamDto = transformer.getTeamDto(teamService.getTeamByID("18a30edb-b037-47cb-b0fc-89be50ebd8d1"));


            System.out.println("Final object: " + new Gson().toJson(teamDto));

        } catch (Exception e){
            e.printStackTrace();
        }
    }
}
