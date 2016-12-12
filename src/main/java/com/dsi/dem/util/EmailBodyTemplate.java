package com.dsi.dem.util;

/**
 * Created by sabbir on 12/9/16.
 */
public class EmailBodyTemplate {

    public static String getEmployeeCreateBody(String password, String firstName, String email){
        return "Dear " + firstName + ",\n" +
                "\n" +
                "Welcome to Dynamic Solution Innovators (DSi). You have been successfully registered as an employee of DSi. You can acces all your information in the following link: \n" +
                "http://103.245.204.114:3000/#/" +
                "\n\n" +
                "The following is your login credential:\n" +
                "Username: " + email + "\n" +
                "Password: " + password + "\n" +
                "\n" +
                "Your initial password has been system generated. We recommend that you change it after you login the first time.\n" +
                "\n" +
                "\n" +
                "See you soon!\n" +
                "Dynamic Solution Innovators";
    }
}
