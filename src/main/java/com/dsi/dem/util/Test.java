package com.dsi.dem.util;

import com.dsi.dem.dao.LeaveDao;
import com.dsi.dem.dao.impl.LeaveDaoImpl;
import com.dsi.dem.dto.TeamDto;
import com.dsi.dem.dto.TeamMemberDto;
import com.dsi.dem.dto.TempAttendanceDto;
import com.dsi.dem.dto.transformer.EmployeeDtoTransformer;
import com.dsi.dem.dto.EmployeeDto;
import com.dsi.dem.dto.transformer.LeaveDtoTransformer;
import com.dsi.dem.dto.transformer.TeamDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.*;
import com.dsi.dem.service.EmployeeService;
import com.dsi.dem.service.LeaveService;
import com.dsi.dem.service.TeamService;
import com.dsi.dem.service.impl.EmployeeServiceImpl;
import com.dsi.dem.service.impl.LeaveServiceImpl;
import com.dsi.dem.service.impl.TeamServiceImpl;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.joda.time.DateTime;
import scala.util.parsing.combinator.testing.Str;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Time;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sabbir on 7/29/16.
 */
public class Test {

    private static final LeaveDao leaveDao = new LeaveDaoImpl();

    private static final EmployeeService employeeService = new EmployeeServiceImpl();
    private static final TeamService teamService = new TeamServiceImpl();
    private static final LeaveService leaveService = new LeaveServiceImpl();
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final EmployeeDtoTransformer dtoTransformer = new EmployeeDtoTransformer();
    private static final LeaveDtoTransformer LEAVE_DTO_TRANSFORMER = new LeaveDtoTransformer();

    public static void main(String[] args) {

        //createEmployeeTest();

        //updateEmployeeTest();

        //createTeamTest();

        //searchEmployeeTest();

        /*String date = "2016-05-01";
        Date format = null;
        DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        DateFormat sdf1 = new SimpleDateFormat("MMM dd, yyyy hh:mm:ss a");
        try {
            format = sdf.parse(date);
            System.out.print(sdf1.format(format));

        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        //System.out.print(Utility.getDateFromString("2016-05-01"));

        //System.out.println(Utility.getDaysBetween(Utility.getDateFormatFromDate(new Date()), Utility.getDateFromString("2016-10-31")));

        /*Date date = new Date();
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        dateFormat.format(date);
        System.out.println(dateFormat.format(date));

        try {
            if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("14:00"))){
                System.out.println("Greater");
            } else {
                System.out.println("Less");
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        //searchLeaveSummaryTest();
        //searchEmployeeTest();
        //searchLeaveTests();

        /*Date approveStart = Utility.getDateFromString("2016-10-21");
        Date approveEnd = Utility.getDateFromString("2016-10-22");

        Date start = Utility.getDateFromString("2016-10-20");
        Date end = Utility.getDateFromString("2016-10-22");

        System.out.println(approveStart.after(start) && approveStart.before(end) && approveEnd.compareTo(start) >= 0 && approveEnd.compareTo(end) <= 0);
        System.out.println(approveStart.after(start) && approveStart.before(end) && approveEnd.after(start) && approveEnd.before(end));*/


        /*Date start = Utility.getDateFromString("2016-10-22");
        Date end = Utility.getDateFromString("2016-10-23");

        Date check = Utility.getDateFromString("2016-10-23");

        System.out.println(Utility.checkDate(start, end, check));*/

        //leaveCheckTest();

        //attendanceRead();

        /*String time1 = "08:08:19";
        String time2 = "17:23:21";

        SimpleDateFormat format = new SimpleDateFormat("HH:mm:ss");
        Date date1 = null;
        String time = "";
        try {
            date1 = format.parse(time1);
            Date date2 = format.parse(time2);

            long difference = date2.getTime() - date1.getTime();

            System.out.println(difference);

            difference = difference/1000;

            long s = difference % 60;
            long m = (difference / 60) % 60;
            long h = (difference / (60 * 60)) % 24;

            time += String.format("%02d:%02d:%02d", h,m,s);
            System.out.println(time);

        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        /*String date = "2016-10-01";
        String date2 = "2016-10-02";

        System.out.println(Utility.getDaysBetween(Utility.getDateFromString(date), Utility.getDateFromString(date2)) + 1);*/

        //myLeaveRequestPatch();

        //attendanceTest();

    }

    private static void attendanceTest() {
        System.out.println(new Gson().toJson(leaveDao.getLeaveRequestByStatusAndEmployee("0310", "2016-10-23")));
    }

    private static void myLeaveRequestPatch() {
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setLeaveRequestId("dd1566c9-1b2e-496f-b6bf-fc70b8f83e73");
        leaveRequest.setVersion(1);

        try {
            leaveService.updateLeaveRequest(leaveRequest, "f9e9a19f-4859-4e8c-a8f4-dc134629a57b", 2);

            System.out.println(new Gson().toJson(LEAVE_DTO_TRANSFORMER.getLeaveRequestDto(
                    leaveService.getLeaveRequestById("dd1566c9-1b2e-496f-b6bf-fc70b8f83e73", null))));

        } catch (CustomException e) {
            e.printStackTrace();
        }
    }

    private static void attendanceRead() {
        /*String csvFile = "/home/sabbir/Downloads/attendance.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        Map<String, String> inMap = new HashMap<>();
        Map<String, String> outMap = new HashMap<>();
        try {

            List<TempAttendanceDto> tempAttendances = new ArrayList<>();

            br = new BufferedReader(new FileReader(csvFile));
            int i=0;
            while ((line = br.readLine()) != null) {
                String[] lineSplit = line.split(cvsSplitBy);

                if(lineSplit.length > 0) {
                    i++;

                    if(i > 2){

                        if(lineSplit[lineSplit.length - 1].equals(Constants.SUCCESS)) {

                            if(lineSplit[Constants.CSV_TYPE_COLUMN].equals(Constants.INT_TIME)){
                                if(inMap.get(lineSplit[Constants.CSV_EMPLOYEE_ID_COLUMN]) != null){
                                    Timestamp nextDate = Utility.getTimeStampFromString(lineSplit[Constants.CSV_DATE_TIME_COLUMN]);
                                    Timestamp prevDate = Utility.getTimeStampFromString(inMap.get(lineSplit[Constants.CSV_EMPLOYEE_ID_COLUMN]));

                                    if(prevDate.after(nextDate)){
                                        inMap.put(lineSplit[Constants.CSV_EMPLOYEE_ID_COLUMN],
                                                lineSplit[Constants.CSV_DATE_TIME_COLUMN]);
                                    }

                                } else {
                                    inMap.put(lineSplit[Constants.CSV_EMPLOYEE_ID_COLUMN],
                                            lineSplit[Constants.CSV_DATE_TIME_COLUMN]);
                                }

                            } else if(lineSplit[Constants.CSV_TYPE_COLUMN].equals(Constants.OUT_TIME)){
                                if(outMap.get(lineSplit[Constants.CSV_EMPLOYEE_ID_COLUMN]) != null){
                                    Timestamp nextDate = Utility.getTimeStampFromString(lineSplit[Constants.CSV_DATE_TIME_COLUMN]);
                                    Timestamp prevDate = Utility.getTimeStampFromString(outMap.get(lineSplit[Constants.CSV_EMPLOYEE_ID_COLUMN]));

                                    if(prevDate.before(nextDate)){
                                        outMap.put(lineSplit[Constants.CSV_EMPLOYEE_ID_COLUMN],
                                                lineSplit[Constants.CSV_DATE_TIME_COLUMN]);
                                    }

                                } else {
                                    outMap.put(lineSplit[Constants.CSV_EMPLOYEE_ID_COLUMN],
                                            lineSplit[Constants.CSV_DATE_TIME_COLUMN]);
                                }
                            }
                        }
                    }
                }
            }

            *//*System.out.println(new Gson().toJson(inMap));
            System.out.println("\n\n\n\n");
            System.out.println(new Gson().toJson(outMap));*//*

            for(Map.Entry<String, String> map : inMap.entrySet()){
                TempAttendanceDto tempAttendance = new TempAttendanceDto();
                tempAttendance.setDate(map.getValue());
                tempAttendance.setEmployeeId(map.getKey());
                tempAttendance.setInTime(map.getValue());
                tempAttendance.setOutTime(outMap.get(map.getKey()));

                tempAttendances.add(tempAttendance);
            }

            System.out.println(new Gson().toJson(tempAttendances));

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (br != null) {
                try {
                    br.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }*/
    }

    private static void leaveCheckTest(){
        try {
            System.out.println(new Gson().toJson(leaveService.isAvailableLeaveTypes("553fb999-2ceb-4bf0-90f2-ccf52742c8a1",
                    "f9e9a19f-4859-4e8c-a8f4-dc134629a57b")));

        } catch (CustomException e) {
            e.printStackTrace();
        }
    }

    private static void searchLeaveTests() {
        try{
            System.out.println(new Gson().toJson(LEAVE_DTO_TRANSFORMER.getAllLeaveRequestDto(leaveService.searchOrReadLeaveRequests(
                    "5546e9ae-1eff-41fd-906d-879584315118", null, null, null, null, null, null, null, null, null, null, null, null, null, null
            ))));

        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private static void searchEmployeeTest() {

        try {
            System.out.println(new Gson().toJson(dtoTransformer.getEmployeesDto(employeeService.searchEmployees(null, null, null, null, null,
                    null, null, null, null, null, "true", null, null, null, "mine", "0", "10"))));

        } catch (CustomException e) {
            e.printStackTrace();
        }
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

    private static void searchLeaveSummaryTest(){
        try {
            System.out.println(leaveService.searchOrReadEmployeesLeaveSummary(null, null, null, null, null, null,
                    null, null, null, "0", "10", "f9e9a19f-4859-4e8c-a8f4-dc134629a57b").size());

        } catch (CustomException e) {
            e.printStackTrace();
        }
    }
}
