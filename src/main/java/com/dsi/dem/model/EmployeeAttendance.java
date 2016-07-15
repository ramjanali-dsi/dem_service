package com.dsi.dem.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sabbir on 7/14/16.
 */

@Entity
@Table(name = "dsi_employee_attendance")
public class EmployeeAttendance {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "employee_attendance_id", length = 40)
    private String employeeAttendanceId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "is_absent")
    private boolean isAbsent;

    @Column(name = "attendance_date")
    private Date attendanceDate;

    @Column(name = "check_in_time", length = 10)
    private String checkInTime;

    @Column(name = "check_out_time", length = 10)
    private String checkOutTime;

    @Column(name = "total_hour", length = 10)
    private String totalHour;

    public String getEmployeeAttendanceId() {
        return employeeAttendanceId;
    }

    public void setEmployeeAttendanceId(String employeeAttendanceId) {
        this.employeeAttendanceId = employeeAttendanceId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public boolean isAbsent() {
        return isAbsent;
    }

    public void setAbsent(boolean absent) {
        isAbsent = absent;
    }

    public String getTotalHour() {
        return totalHour;
    }

    public void setTotalHour(String totalHour) {
        this.totalHour = totalHour;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getCheckInTime() {
        return checkInTime;
    }

    public void setCheckInTime(String checkInTime) {
        this.checkInTime = checkInTime;
    }

    public String getCheckOutTime() {
        return checkOutTime;
    }

    public void setCheckOutTime(String checkOutTime) {
        this.checkOutTime = checkOutTime;
    }
}
