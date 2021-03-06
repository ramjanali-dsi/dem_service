package com.dsi.dem.util;

import com.dsi.dem.dao.*;
import com.dsi.dem.dao.impl.*;
import com.dsi.dem.dto.*;
import com.dsi.dem.dto.transformer.EmployeeDtoTransformer;
import com.dsi.dem.dto.transformer.LeaveDtoTransformer;
import com.dsi.dem.dto.transformer.TeamDtoTransformer;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.model.*;
import com.dsi.dem.service.AttendanceService;
import com.dsi.dem.service.EmployeeService;
import com.dsi.dem.service.LeaveService;
import com.dsi.dem.service.TeamService;
import com.dsi.dem.service.impl.*;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.hibernate.Session;
import org.joda.time.DateTime;
import scala.util.parsing.combinator.testing.Str;

import javax.mail.Message;
import javax.mail.MessagingException;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import java.io.*;
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

    //private static final HolidayDao holidayDao = new HolidayDaoImpl();
    //private static final LeaveService leaveService = new LeaveServiceImpl();

    /*
    private static final LeaveDao leaveDao = new LeaveDaoImpl();
    private static final EmployeeService employeeService = new EmployeeServiceImpl();
    private static final TeamService teamService = new TeamServiceImpl();
    private static final LeaveService leaveService = new LeaveServiceImpl();
    private static final AttendanceService attendanceService = new AttendanceServiceImpl();
    private static final ObjectMapper mapper = new ObjectMapper();

    private static final EmployeeDtoTransformer dtoTransformer = new EmployeeDtoTransformer();
    private static final LeaveDtoTransformer LEAVE_DTO_TRANSFORMER = new LeaveDtoTransformer();*/

    //private static final HazelcastInstance instance = Hazelcast.newHazelcastInstance(new Config());

    public static void main(String[] args) throws CustomException, IOException, JSONException, MessagingException {

        /*WorkFromHomeDao workFromHomeDao = new WorkFromHomeDaoImpl();
        Session session = SessionUtil.getSession();
        workFromHomeDao.setSession(session);
        boolean res = workFromHomeDao.checkLeaveRequest("23a9a5f0-59da-4de1-ab75-b8d5885182f0",
                Utility.getDateFromString("2017-04-10"));
        System.out.println(res);

        TeamDao teamDao = new TeamDaoImpl();*/
        /*Session session = SessionUtil.getSession();
        teamDao.setSession(session);
        List<TeamMember> teamMembers = teamDao.getTeamMembers(null, "facfc656-8830-4a37-883c-c2738ed03528");
        System.out.println(new Gson().toJson(teamMembers));*/

        /*LeaveDao leaveDao = new LeaveDaoImpl();
        Session session = SessionUtil.getSession();
        leaveDao.setSession(session);
        boolean res = leaveDao.getLeaveRequestByRequestTypeAndEmployeeNo("0310", new Date());
        System.out.println(res);*/

        /*String json = "{\"startDate\":\"2017-03-09\",\"endDate\":\"2017-03-10\",\"leaveRequestTypeId\":\"0b9cb019-74de-4e3a-90e0-ebca64c6df95\",\"leaveTypeId\":\"553fb999-2ceb-4bf0-90f2-ccf52742c8a1\",\"leaveReason\":\"\",\"version\":1}";

        ObjectMapper mapper = new ObjectMapper();
        LeaveRequestDto requestDto = mapper.readValue(json, LeaveRequestDto.class);

        leaveService.saveLeaveRequest(requestDto, "b49d0f2e-ac31-4da8-a2d3-edf89beb8fcd", "DSI");*/

        /*Date date = Utility.getDateFromString("2017-03-09");
        Date date1 = Utility.getDateFormatFromDate(Utility.getDayAfterDate(date));

        System.out.println("Date: " + date);
        System.out.println("Date1: " + date1);*/


        /*Date startDate = Utility.getDateFromString("2017-03-09");
        Date endDate = Utility.getDateFromString("2017-03-10");

        System.out.println("Start date: " + startDate);
        System.out.println("End date: " + endDate);

        System.out.println(new Gson().toJson(holidayDao.checkHolidayFromRangeAndYear(startDate, endDate, 2017)));*/

        //TODO HR & Manager email list API
        /*logger.info("Get HR email list.");
            result = httpClient.getRequest(APIProvider.API_USER_ROLE + NotificationConstant.HR_ROLE_TYPE,
                    Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

            JSONArray resultArray = new JSONArray(result);
            if(resultArray.length() > 0){
                recipients.put(resultArray);
            }

            logger.info("Get Manager email list.");
            result = httpClient.getRequest(APIProvider.API_USER_ROLE + NotificationConstant.MANAGER_ROLE_TYPE,
                    Constants.SYSTEM, Constants.SYSTEM_HEADER_ID);

            resultArray = new JSONArray(result);
            if(resultArray.length() > 0){
                recipients.put(resultArray);
            }*/


        /*Map<Integer, String> customerMap = instance.getMap("customers");

        if(customerMap.isEmpty()){
            customerMap.put(1, "Bangalore");
            customerMap.put(2, "Chennai");
            customerMap.put(3, "Hyderabad");

            System.out.println("Map Size:" + customerMap.size());
            Set<Map.Entry<Integer,String>> customers = customerMap.entrySet();

            for (Map.Entry<Integer, String> entry : customers) {
                System.out.println("Customer Id : " + entry.getKey() + " Customer Name : " + entry.getValue());
            }
        } else {
            System.out.println("Not empty: Map Size:" + customerMap.size());
            Set<Map.Entry<Integer,String>> customers = customerMap.entrySet();

            for (Map.Entry<Integer, String> entry : customers) {
                System.out.println("Customer Id : " + entry.getKey() + " Customer Name : " + entry.getValue());
            }
        }*/

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
        Date approveEnd = Utility.getDateFromString("2016-10-21");

        Date start = Utility.getDateFromString("2016-10-21");
        Date end = Utility.getDateFromString("2016-10-21");


        if(!((approveStart.compareTo(start) >= 0 && approveStart.compareTo(end) <=0 )
                && ((approveEnd.compareTo(start) >= 0 && approveEnd.compareTo(end) <=0 )))){
            System.out.println(true);
        }*/

        /*Date start = Utility.getDateFromString("2016-10-22");
        Date end = Utility.getDateFromString("2016-10-23");

        Date check = Utility.getDateFromString("2016-10-23");

        System.out.println(Utility.checkDate(start, end, check));*/

        //leaveCheckTest();
        //attendanceRead();

        /*String time1 = "08:08";
        String time2 = "17:23";

        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        Date date1 = null;
        String time = "";
        try {
            date1 = format.parse(time1);
            Date date2 = format.parse(time2);

            long difference = date2.getTime() - date1.getTime();

            System.out.println(difference);

            difference = difference/1000;

            long m = (difference / 60) % 60;
            long h = (difference / (60 * 60)) % 24;

            time += String.format("%02d:%02d", h,m);
            System.out.println(time);

        } catch (ParseException e) {
            e.printStackTrace();
        }*/

        /*String date = "2016-10-01";
        String date2 = "2016-10-02";

        System.out.println(Utility.getDaysBetween(Utility.getDateFromString(date), Utility.getDateFromString(date2)) + 1);*/

        //myLeaveRequestPatch();
        //attendanceTest();

        /*LeaveRequestDto requestDto = new LeaveRequestDto();
        requestDto.setLeaveTypeId("7e1c5ac7-f7a6-43e0-b5da-31cf8b899f10");
        requestDto.setLeaveRequestTypeId("0b9cb019-74de-4e3a-90e0-ebca64c6df95");
        requestDto.setStartDate(Utility.getDateFromString("2016-09-29"));
        requestDto.setEndDate(Utility.getDateFromString("2016-09-30"));
        requestDto.setLeaveReason("Sick");

        try {
            System.out.println(new Gson().toJson(leaveService.saveLeaveRequest(requestDto, "f9e9a19f-4859-4e8c-a8f4-dc134629a57b")));

        } catch (CustomException e) {
            e.printStackTrace();
        }*/

       /* try {
            readXmlFile();
        } catch (IOException e) {
            e.printStackTrace();
        }*/

        /*String csvFile = "/home/sabbir/Downloads/attendance.csv";

        try {
            InputStream inputStream = new FileInputStream(csvFile);
            attendanceService.saveTempAttendance(inputStream, "f9e9a19f-4859-4e8c-a8f4-dc134629a57b");

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (CustomException e) {
            e.printStackTrace();
        }*/

        /*try {
            System.out.println(new Gson().toJson(dtoTransformer.getEmployeesDto(employeeService.searchEmployees(null, null, null, null, null,
                    null, null, null, null, null, null, null, null, null, null, "0", "10"))));

        } catch (CustomException e) {
            e.printStackTrace();
        }*/

        /*String date = "10/25/2016 23:38:17";
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat finalFormat = new SimpleDateFormat("yyyy-MM-dd");

        Date my = null;
        try {
            my = dateFormat.parse(date);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        System.out.println(finalFormat.format(my));*/

        //attendanceRead();

        /*Date date = Utility.getDateFromString("2016-10-11");
        System.out.println(date);

        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        System.out.println(calendar.get(Calendar.YEAR));*/

        /*Session session = SessionUtil.getSession();
        HolidayDao dao = new HolidayDaoImpl();
        dao.setSession(session);

        Date start = Utility.getDateFromString("2016-04-10");
        Date end = Utility.getDateFromString("2016-04-17");
        int year = 2016;
        System.out.println(dao.checkHolidayFromRangeAndYear(start, end, year));*/

        /*Date date = new Date();
        date.setYear(date.getYear()+1);
        System.out.println(date);*/

        /*Session session = SessionUtil.getSession();
        HolidayDao dao = new HolidayDaoImpl();
        dao.setSession(session);

        dao.deleteHoliday("c1d7bd06-f1f9-4d6b-9823-361b7628f5a1");*/

        /*Date start = Utility.getDateFromString("2016-12-01");
        Date end = Utility.getDateFromString("2016-12-05");

        Date approveStart = Utility.getDateFromString("2016-12-02");
        Date approveEnd = Utility.getDateFromString("2016-12-02");

        if((approveStart.compareTo(start) >= 0 && approveStart.compareTo(end) <= 0)
                && (approveEnd.compareTo(start) >= 0 && approveEnd.compareTo(end) <= 0)){
            System.out.println("Dates are in range.");
        }*/

        /*EmployeeDao employeeDao = new EmployeeDaoImpl();
        List<Employee> employeeList = employeeDao.getTeamLeadsProfileOfAnEmployee("6aefc8a6-85be-4d14-a7ad-ba3ebf0137a7");
        System.out.println(new Gson().toJson(employeeList));*/
        //System.out.println(Utility.getWeekendBetweenDate(start, end));

        //attendanceRead();

       /* Session session = SessionUtil.getSession();
        leaveDao.setSession(session);
        List<LeaveRequest> leaveRequests = leaveDao.searchOrReadSpecialLeaveRequests(null, null, null, null, null, null, null ,null, "0", "10");
        System.out.println(new Gson().toJson(leaveRequests));*/

        /*String body = "{\"approvedDaysCount\":1,\"approvedStartDate\":\"2016-12-31\",\"approvedEndDate\":\"2017-01-12\",\"clientNotify\":false,\"deniedReason\":\"sabbir\",\"leaveStatusName\":\"Approved\"}";
        String userId = "f9e9a19f-4859-4e8c-a8f4-dc134629a57b";
        String leaveRequestId = "4af247b1-29c2-4ebc-ada9-d29c37227ad6";

        ObjectMapper mapper = new ObjectMapper();
        LeaveRequestDto requestDto = mapper.readValue(body, LeaveRequestDto.class);

        leaveService.approveLeaveRequest(requestDto, userId, leaveRequestId, null);*/

        /*TeamDao teamDao = new TeamDaoImpl();
        Session session = SessionUtil.getSession();
        session.beginTransaction();
        teamDao.setSession(session);
        teamDao.deleteProjectTeamByProjectId("bc9fd97b-9d9a-474f-b0c8-ef43a75c0ae5", "0fafc7ec-4e80-4bb1-9ed6-5e5163acb7c2");

        session.getTransaction().commit();
        session.close();*/

        /*JSONObject object = new JSONObject();
        object.put("Tenant", "DSI");
        object.put("Name", "Sabbir");

        String body = "Dear {Name}, You are assigned to {Tenant} yesterday.";

        int contentKeyIndex, bodyIndex;
        String newBody = "";
        for(bodyIndex = 0; bodyIndex < body.length(); bodyIndex++){

            if(body.charAt(bodyIndex) == '{'){
                contentKeyIndex = bodyIndex + 1;
                String contentKey = "";
                while(body.charAt(contentKeyIndex) != '}'){
                    contentKey += body.charAt(contentKeyIndex);
                    contentKeyIndex++;
                }
                bodyIndex = contentKeyIndex;

                newBody += object.getString(contentKey);

            } else {
                newBody += body.charAt(bodyIndex);
            }
        }

        System.out.println("New body: " + newBody);*/


        /*Date date = new Date();

        date.setTime(date.getTime() + 86400000 + 86400000 + 86400000 + 86400000);
        System.out.println("Current date: " + date);
        if(Utility.checkWeekendOfDate(date)){
            System.out.println("Weekend.");

        } else {
            System.out.println("Not weekend.");
        }*/

        //attendanceRead();


        /*String body = "Dear Manager/HR,\n\n{EmployeeFirstName} {EmployeeLastName} has applied for leave request for the following dates: {LeaveStartDate} - {LeaveEndDate}\n\nRegards\n{TenantName}";

        JSONObject contentObj = new JSONObject("{\"Recipients\":[\"nargis.akter@dsinnovators.com\"],\"EmployeeFirstName\":\"Nargis\",\"EmployeeLastName\":\"Akter\",\"LeaveStartDate\":\"2017-03-28\",\"LeaveEndDate\":\"2017-03-28\",\"Reason\":\"Due to illness\\nTime off sheet updated\",\"TenantName\":\"Dynamic Solution\"}");

        int contentKeyIndex, bodyIndex;
        String newBody = "";
        for(bodyIndex = 0; bodyIndex < body.length(); bodyIndex++){

            if(body.charAt(bodyIndex) == '{'){
                contentKeyIndex = bodyIndex + 1;
                String contentKey = "";
                while(body.charAt(contentKeyIndex) != '}'){
                    contentKey += body.charAt(contentKeyIndex);
                    contentKeyIndex++;
                }
                bodyIndex = contentKeyIndex;

                if(contentKey.equals("LeaveStartDate") || contentKey.equals("LeaveEndDate")) {
                    newBody += Utility.getDate(Utility.getDateFromString(contentObj.getString(contentKey)));

                } else {
                    newBody += contentObj.getString(contentKey);
                }

            } else {
                newBody += body.charAt(bodyIndex);
            }
        }

        System.out.println(newBody);


        String subject = "Employee Registration Confirmation {EmployeeFirstName}";

        int subjectIndex;
        newBody = "";

        if(subject.contains("{")) {
            for (subjectIndex = 0; subjectIndex < subject.length(); subjectIndex++) {

                if (subject.charAt(subjectIndex) == '{') {
                    contentKeyIndex = subjectIndex + 1;
                    String contentKey = "";
                    while (subject.charAt(contentKeyIndex) != '}') {
                        contentKey += subject.charAt(contentKeyIndex);
                        contentKeyIndex++;
                    }
                    subjectIndex = contentKeyIndex;

                    newBody += contentObj.getString(contentKey);

                } else {
                    newBody += subject.charAt(subjectIndex);
                }
            }

            System.out.println(newBody);

        } else {
            System.out.println(subject);
        }*/

        String key = "0104";
        List<String> ignoreLIst = Utility.getAttendanceIgnoreList();

        if(!ignoreLIst.contains(key)){
            System.out.println("Key don't exist.");

        } else {
            System.out.println("Key exist.");
        }

        /*JSONObject contentObj = new JSONObject("{\"Recipients\":[\"sabbir.ahmed@dsinnovators.com\"],\"EmployeeFirstName\":\"Sabbir\",\"EmployeeLastName\":\"Ahmed\",\"Link\":\"http://dem.dsinnovators.com/#!/\",\"TenantName\":\"Dynamic Solution Innovators Ltd.\"}");

        String json = "{\n" +
                "  \"subject\": \"Updated Employee Profile\",\n" +
                "  \"body\": \"Dear Employee,<br> <br> Your profile has been <strong>successfully</strong> updated. Please view the changes made and contact HR for more information. You can also view your information in the following link: <br><a href='{Link}'>Click</a> <br><br>Regards,<br>{TenantName}\"\n" +
                "}";

        JSONObject jsonObject = new JSONObject(json);
        String subject = jsonObject.getString("subject");
        String body = jsonObject.getString("body");

        Properties emailProp = new Properties();
        emailProp.setProperty("mail.smtp.host", "smtp.gmail.com");
        emailProp.setProperty("mail.smtp.auth", "true");
        emailProp.setProperty("mail.smtp.port", "587");
        emailProp.setProperty("mail.smtp.starttls.enable", "true");

        javax.mail.Session session = javax.mail.Session.getDefaultInstance(emailProp, null);
        MimeMessage message = new MimeMessage(session);

        message.setFrom(new InternetAddress("triposocial.info@gmail.com"));
        message.addRecipient(Message.RecipientType.TO, new InternetAddress("sabbir.ahmed@dsinnovators.com"));

        message.setSubject(msgBody(subject, contentObj));
        message.setText(msgBody(body, contentObj), "UTF-8", "html");

        Transport transport = session.getTransport("smtp");
        transport.connect("smtp.gmail.com", "triposocial.info@gmail.com", "dynamic4trip");
        transport.sendMessage(message, message.getAllRecipients());
        transport.close();*/
    }

    private static String msgBody(String body, JSONObject contentObj) throws JSONException {
        int contentKeyIndex, bodyIndex;
        String newBody = "";
        for(bodyIndex = 0; bodyIndex < body.length(); bodyIndex++){

            if(body.charAt(bodyIndex) == '{'){
                contentKeyIndex = bodyIndex + 1;
                String contentKey = "";
                while(body.charAt(contentKeyIndex) != '}'){
                    contentKey += body.charAt(contentKeyIndex);
                    contentKeyIndex++;
                }
                bodyIndex = contentKeyIndex;

                newBody += contentObj.getString(contentKey);

            } else {
                newBody += body.charAt(bodyIndex);
            }
        }
        return newBody;
    }

    private static void myLeaveRequestPatch() {
        LeaveRequest leaveRequest = new LeaveRequest();
        leaveRequest.setLeaveRequestId("dd1566c9-1b2e-496f-b6bf-fc70b8f83e73");
        leaveRequest.setVersion(1);

        /*try {
            leaveService.updateLeaveRequest(leaveRequest, "f9e9a19f-4859-4e8c-a8f4-dc134629a57b", 2);

            System.out.println(new Gson().toJson(LEAVE_DTO_TRANSFORMER.getLeaveRequestDto(
                    leaveService.getLeaveRequestById("dd1566c9-1b2e-496f-b6bf-fc70b8f83e73", null))));

        } catch (CustomException e) {
            e.printStackTrace();
        }*/
    }

    private static void attendanceRead() {
        String csvFile = "/home/sabbir/Downloads/10-03-17.csv";
        BufferedReader br = null;
        String line = "";
        String cvsSplitBy = ",";

        Map<String, String> inMap = new HashMap<>();
        Map<String, String> outMap = new HashMap<>();
        try {

            int CSV_TYPE_COLUMN = 0;
            int CSV_EMPLOYEE_ID_COLUMN = 0;
            int CSV_DATE_TIME_COLUMN = 0;
            int CSV_FUNCTION_KEY_COLUMN = 0;

            br = new BufferedReader(new FileReader(csvFile));
            boolean once = true;
            while ((line = br.readLine()) != null) {
                String[] lineSplit = line.split(cvsSplitBy);

                if(lineSplit.length > 0) {

                    if(!Utility.isNullOrEmpty(lineSplit[0])){

                        //System.out.println(new Gson().toJson(lineSplit));

                        /*if(once){
                            System.out.println("Config start");
                            for(int i=0; i<lineSplit.length; i++){
                                if(lineSplit[i].equals(ReadXMLFile.EVENT_TIME)){
                                    CSV_DATE_TIME_COLUMN = i;
                                }

                                if(lineSplit[i].equals(ReadXMLFile.USER_ID)){
                                    CSV_EMPLOYEE_ID_COLUMN = i;
                                }

                                if(lineSplit[i].equals(ReadXMLFile.TERMINAL_ID)){
                                    CSV_TYPE_COLUMN = i;
                                }

                                if(lineSplit[i].equals(ReadXMLFile.RESULT)){
                                    CSV_FUNCTION_KEY_COLUMN = i;
                                }
                            }
                            once = false;

                        } else {

                            if (lineSplit[CSV_FUNCTION_KEY_COLUMN].equals(ReadXMLFile.STATUS)) {

                                String employeeId = lineSplit[CSV_EMPLOYEE_ID_COLUMN].replace("\'", "");

                                Timestamp newTime = Utility.getTimeStampFromString(lineSplit[CSV_DATE_TIME_COLUMN]);

                                if(inMap.get(employeeId) != null && outMap.get(employeeId) != null) {
                                    Timestamp prevInTime = Utility.getTimeStampFromString(inMap.get(employeeId));
                                    Timestamp prevOutTime = Utility.getTimeStampFromString(outMap.get(employeeId));

                                    if (prevInTime.after(newTime)) {
                                        inMap.put(employeeId, lineSplit[CSV_DATE_TIME_COLUMN]);
                                    }

                                    if (prevOutTime.before(newTime)) {
                                        inMap.put(employeeId, lineSplit[CSV_DATE_TIME_COLUMN]);
                                    }

                                } else {
                                    inMap.put(employeeId,
                                            lineSplit[CSV_DATE_TIME_COLUMN]);

                                    outMap.put(employeeId,
                                            lineSplit[CSV_DATE_TIME_COLUMN]);
                                }

                                *//*if (lineSplit[CSV_TYPE_COLUMN].equals(ReadXMLFile.IN_TIME)) {

                                    if (inMap.get(employeeId) != null) {
                                        Timestamp nextDate = Utility.getTimeStampFromString(lineSplit[CSV_DATE_TIME_COLUMN]);
                                        Timestamp prevDate = Utility.getTimeStampFromString(inMap.get(employeeId));

                                        if (prevDate.after(nextDate)) {
                                            inMap.put(employeeId,
                                                    lineSplit[CSV_DATE_TIME_COLUMN]);
                                        }

                                    } else {
                                        inMap.put(employeeId,
                                                lineSplit[CSV_DATE_TIME_COLUMN]);
                                    }

                                } else if (lineSplit[CSV_TYPE_COLUMN].equals(ReadXMLFile.OUT_TIME)) {
                                    if (outMap.get(employeeId) != null) {
                                        Timestamp nextDate = Utility.getTimeStampFromString(lineSplit[CSV_DATE_TIME_COLUMN]);
                                        Timestamp prevDate = Utility.getTimeStampFromString(outMap.get(employeeId));

                                        if (prevDate.before(nextDate)) {
                                            outMap.put(employeeId,
                                                    lineSplit[CSV_DATE_TIME_COLUMN]);
                                        }

                                    } else {
                                        outMap.put(employeeId,
                                                lineSplit[CSV_DATE_TIME_COLUMN]);
                                    }
                                }*//*
                            }
                        }
*/
                    }
                }
            }

            System.out.println(new Gson().toJson(inMap));
            System.out.println("\n\n");
            System.out.println(new Gson().toJson(outMap));


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
        }
    }

    /*private static void leaveCheckTest(){
        try {
            System.out.println(new Gson().toJson(leaveService.isAvailableLeaveTypes("553fb999-2ceb-4bf0-90f2-ccf52742c8a1",
                    "f9e9a19f-4859-4e8c-a8f4-dc134629a57b")));

        } catch (CustomException e) {
            e.printStackTrace();
        }
    }

    private static void searchLeaveTests() {
        *//*try{
            System.out.println(new Gson().toJson(LEAVE_DTO_TRANSFORMER.getAllLeaveRequestDto(leaveService.searchOrReadLeaveRequests(
                    "5546e9ae-1eff-41fd-906d-879584315118", null, null, null, null, null, null, null, null, null, null, null, null, null, null
            ))));

        }catch (Exception e){
            e.printStackTrace();
        }*//*
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

            //employeeService.saveEmployee(employee);

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
            //employeeService.updateEmployee(employee);

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

            *//*teamDto = transformer.getTeamDto(teamService.getTeamByID("18a30edb-b037-47cb-b0fc-89be50ebd8d1"));


            System.out.println("Final object: " + new Gson().toJson(teamDto));*//*

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
    }*/

    private static void readXmlFile() throws IOException {
        Properties properties = new Properties();
        ClassLoader loader = Thread.currentThread().getContextClassLoader();
        InputStream inputStream = loader.getResourceAsStream("csvConfig.xml");

        properties.loadFromXML(inputStream);

        String inTime = properties.getProperty("csv.inTime");

        System.out.println(inTime);
    }
}
