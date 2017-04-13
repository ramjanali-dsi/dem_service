package com.dsi.dem.util;

import com.dsi.dem.dto.ContextDto;
import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Employee;
import com.dsi.dem.service.impl.APIProvider;
import org.apache.log4j.Logger;
import org.codehaus.jettison.json.JSONArray;
import org.codehaus.jettison.json.JSONException;
import org.codehaus.jettison.json.JSONObject;
import org.joda.time.DateTime;
import org.joda.time.Interval;

import java.io.*;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by sabbir on 7/13/16.
 */
public class Utility {


    private static final Logger logger = Logger.getLogger(Utility.class);

    public static boolean isNullOrEmpty(String s){

        return s == null || s.isEmpty();
    }

    public static boolean isNullOrEmpty(List list){

        return list == null || list.size() == 0;
    }

    public static String generateRandomString(){
        return UUID.randomUUID().toString();
    }

    public static Date today() {
        return new Date();
    }

    public static Timestamp todayTimeStamp(){
        return new Timestamp(System.currentTimeMillis());
    }

    public static int getDaysBetween(Date d1, Date d2){
        return (int)( (d2.getTime() - d1.getTime()) / (1000 * 60 * 60 * 24) );
    }

    public static Boolean checkDate(Date startDate, Date endDate, Date checkDate) {
        Interval interval = new Interval(new DateTime(startDate),
                new DateTime(endDate));
        return interval.contains(new DateTime(checkDate));
    }

    public static Boolean checkWeekendOfDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        boolean flag = false;
        if((calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) ||
                calendar.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY){
            flag = true;
        }
        return flag;
    }

    public static Boolean getWeekendBetweenDate(Date startDate, Date endDate){
        Calendar calendar1 = Calendar.getInstance();
        calendar1.setTime(startDate);

        Calendar calendar2 = Calendar.getInstance();
        calendar2.setTime(endDate);

        boolean flag = false;
        while(calendar1.compareTo(calendar2) <= 0){
            if(calendar1.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY
                    || calendar1.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY){

                flag = true;
                break;
            }
            calendar1.add(Calendar.DATE, 1);
        }
        return flag;
    }

    public static boolean isGreater02PM(Date date) {
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        try {
            if(dateFormat.parse(dateFormat.format(date)).after(dateFormat.parse("14:00")))
                return true;

        } catch (ParseException e) {
            logger.error("Date parse error occurs: " + e.getMessage());
        }
        return false;
    }

    public static String getTimeCalculation(String dateTime1, String dateTime2){
        SimpleDateFormat dateFormat = new SimpleDateFormat("HH:mm");
        Date date1;
        Date date2;
        String time = "";
        try{
            date1 = dateFormat.parse(dateTime1);
            date2 = dateFormat.parse(dateTime2);

            long difference = (date2.getTime() - date1.getTime()) / 1000;

            long m = (difference / 60) % 60;
            long h = (difference / (60 * 60)) % 24;
            time += String.format("%02d:%02d", h,m);

        } catch (ParseException e) {
            logger.error("Date parse error occurs: " + e.getMessage());
        }
        logger.info("Time: " + time);
        return time;
    }

    public static int getYearFromDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        return calendar.get(Calendar.YEAR);
    }

    public static Date getDayAfterDate(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);

        calendar.add(Calendar.DATE, 1);
        return calendar.getTime();
    }

    public static Date getFirstDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, 1);

        return getDateFormatFromDate(calendar.getTime());
    }

    public static Date getLastDay(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));

        return getDateFormatFromDate(calendar.getTime());
    }

    public static Date getDateFromAddYear(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.YEAR, 1);
        return calendar.getTime();
    }

    public static Date getDateFormatFromDate(Date date) {
        Date formatDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formatDate = dateFormat.parse(dateFormat.format(date));

        } catch (ParseException e) {
            logger.error("Date parse error occurs: " + e.getMessage());
        }
        return formatDate;
    }

    public static Date getDateFromString(String date) {

        Date formatDate = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formatDate = dateFormat.parse(date);

        } catch (ParseException e) {
            logger.error("Date parse error occurs: " + e.getMessage());
        }
        return formatDate;
    }

    public static String getDateFromStringForAttendance(String date){
        logger.info("Date: " + date);

        Date formatDate = null;
        SimpleDateFormat dateFormat = new SimpleDateFormat("MM/dd/yyyy");
        SimpleDateFormat finalFormat = new SimpleDateFormat("yyyy-MM-dd");
        try {
            formatDate = dateFormat.parse(date);

        } catch (ParseException e) {
            logger.error("Date parse error occurs: " + e.getMessage());
        }
        return finalFormat.format(formatDate);
    }

    public static Timestamp getTimeStampFromString(String date){
        Timestamp timestamp = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        try {
            timestamp = new Timestamp(dateFormat.parse(date).getTime());

        } catch (ParseException e) {
            logger.error("Date parse error occurs: " + e.getMessage());
        }
        return timestamp;
    }

    public static String getTime(String date){
        Timestamp timestamp = null;
        DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm");
        DateFormat timeFormat = new SimpleDateFormat("HH:mm");
        try {
            timestamp = new Timestamp(dateFormat.parse(date).getTime());

        } catch (ParseException e) {
            logger.error("Date parse error occurs: " + e.getMessage());
        }
        return timeFormat.format(timestamp);
    }

    public static String getDate(Date date) {
        DateFormat dateFormat = new SimpleDateFormat("EEE, dd MMM yyyy");
        return dateFormat.format(date);
    }

    public static String getLoginObject(Employee employee, String currentUserId) throws JSONException {
        JSONObject loginObject = new JSONObject();
        loginObject.put("firstName", employee.getFirstName());
        loginObject.put("lastName", employee.getLastName());
        loginObject.put("gender", employee.getInfo().getGender());
        loginObject.put("email", employee.getEmailInfo().get(0).getEmail());
        loginObject.put("phone", employee.getContactInfo().get(0).getPhone());
        loginObject.put("roleId", employee.getRoleId());
        loginObject.put("createBy", currentUserId);
        loginObject.put("modifiedBy", currentUserId);
        loginObject.put("active", employee.isActive());
        loginObject.put("version", 1);

        return loginObject.toString();
    }

    public static String getUserObject(Employee employee, String currentUserId) throws JSONException {
        JSONObject userObject = new JSONObject();
        userObject.put("firstName", employee.getFirstName());
        userObject.put("lastName", employee.getLastName());
        userObject.put("gender", employee.getInfo().getGender());
        userObject.put("email", employee.getEmailInfo().get(0).getEmail());
        userObject.put("phone", employee.getContactInfo().get(0).getPhone());
        userObject.put("roleId", employee.getRoleId());
        userObject.put("createBy", currentUserId);
        userObject.put("modifiedBy", currentUserId);
        userObject.put("version", 1);

        return userObject.toString();
    }

    public static String getContextObject(Employee employee, String userId,
                                          String teamId, int activity) throws JSONException {
        JSONArray contextArray = new JSONArray();
        JSONObject contextObj = new JSONObject();

        if(employee != null){
            contextObj.put("employeeId", employee.getEmployeeId());
        }

        if(teamId != null) {
            contextObj.put("teamId", teamId);
        }

        contextObj.put("userId", userId);
        contextObj.put("activity", activity);

        contextArray.put(contextObj);
        return contextArray.toString();
    }

    public static String getContextObjectForUpdate(String leadAssignId, String leadUnAssignId,
                                                   String teamId) throws JSONException {
        JSONArray contextArray = new JSONArray();
        JSONObject contextObj = new JSONObject();
        contextObj.put("teamId", teamId);
        contextObj.put("userId", leadAssignId);
        contextObj.put("activity", 1);
        contextArray.put(contextObj);

        contextObj = new JSONObject();
        contextObj.put("teamId", teamId);
        contextObj.put("userId", leadUnAssignId);
        contextObj.put("activity", 2);
        contextArray.put(contextObj);

        return contextArray.toString();
    }

    public static ContextDto getContextDtoObj(String context) throws CustomException {
        ContextDto contextDto = new ContextDto();
        List<String> contextList;
        JSONObject contextObj;
        JSONArray contextArray;
        try{
            if(context != null) {
                contextList = new ArrayList<>();
                contextObj = new JSONObject(context);
                if(contextObj.has("teamId")) {
                    contextArray = contextObj.getJSONArray("teamId");

                    for (int i = 0; i < contextArray.length(); i++) {
                        contextList.add(contextArray.getString(i));
                    }
                    contextDto.setTeamId(contextList);
                }

                if(contextObj.has("employeeId")){
                    contextArray = contextObj.getJSONArray("employeeId");
                    contextDto.setEmployeeId(contextArray.getString(0));
                }
                return contextDto;
            }

        } catch (JSONException je){
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0012,
                    Constants.DEM_SERVICE_0012_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_006);
            throw new CustomException(errorMessage);
        }
        return null;
    }

    public static List<String> getAttendanceIgnoreList(){
        List<String> ignoreList = new ArrayList<>();

        String ignoreProp = APIProvider.ATTENDANCE_IGNORE_LIST;
        if(!Utility.isNullOrEmpty(ignoreProp)){

            if(ignoreProp.contains(",")){
                String[] splitIgnoreProp = ignoreProp.split(", ");
                Collections.addAll(ignoreList, splitIgnoreProp);

            } else {
                ignoreList.add(ignoreProp);
            }
        }
        return ignoreList;
    }

    public static void saveToFile(InputStream uploadedInputStream, String uploadedFileLocation) throws CustomException {
        logger.info("Uploaded File Location: " + uploadedFileLocation);

        OutputStream out = null;
        int read = 0;
        byte[] bytes = new byte[1024];

        try {
            out = new FileOutputStream(new File(uploadedFileLocation));
            while ((read = uploadedInputStream.read(bytes)) != -1) {
                out.write(bytes, 0, read);
            }
        } catch (Exception e){
            //ErrorContext errorContext = new ErrorContext(null, null, e.getMessage());
            ErrorMessage errorMessage = new ErrorMessage(Constants.DEM_SERVICE_0008,
                    Constants.DEM_SERVICE_0008_DESCRIPTION, ErrorTypeConstants.DEM_ERROR_TYPE_008);
            throw new CustomException(errorMessage);

        } finally {
            try {
                out.flush();
                out.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
