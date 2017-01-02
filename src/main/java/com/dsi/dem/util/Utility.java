package com.dsi.dem.util;

import com.dsi.dem.exception.CustomException;
import com.dsi.dem.exception.ErrorMessage;
import com.dsi.dem.model.Employee;
import org.apache.log4j.Logger;
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

    public static String getLoginObject(Employee employee, String currentUserId, int mode) throws JSONException {
        JSONObject loginObject = new JSONObject();
        if(mode == 1) {
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

        } else if (mode == 2) {
            loginObject.put("modifiedBy", currentUserId);
            loginObject.put("active", employee.isActive());
        }
        return loginObject.toString();
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
