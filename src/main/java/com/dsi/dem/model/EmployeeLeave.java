package com.dsi.dem.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sabbir on 7/14/16.
 */

@Entity
@Table(name = "dsi_employee_leave")
public class EmployeeLeave {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "employee_leave_id", length = 40)
    private String employeeLeaveId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(name = "total_leave")
    private int totalLeave;

    @Column(name = "total_sick")
    private int totalSick;

    @Column(name = "total_casual")
    private int totalCasual;

    @Column(name = "total_not_notify")
    private int totalNotNotify;

    @Column(name = "total_special_leave")
    private int totalSpecialLeave;

    private int version;

    public String getEmployeeLeaveId() {
        return employeeLeaveId;
    }

    public void setEmployeeLeaveId(String employeeLeaveId) {
        this.employeeLeaveId = employeeLeaveId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public int getTotalLeave() {
        return totalLeave;
    }

    public void setTotalLeave(int totalLeave) {
        this.totalLeave = totalLeave;
    }

    public int getTotalSick() {
        return totalSick;
    }

    public void setTotalSick(int totalSick) {
        this.totalSick = totalSick;
    }

    public int getTotalCasual() {
        return totalCasual;
    }

    public void setTotalCasual(int totalCasual) {
        this.totalCasual = totalCasual;
    }

    public int getTotalNotNotify() {
        return totalNotNotify;
    }

    public void setTotalNotNotify(int totalNotNotify) {
        this.totalNotNotify = totalNotNotify;
    }

    public int getTotalSpecialLeave() {
        return totalSpecialLeave;
    }

    public void setTotalSpecialLeave(int totalSpecialLeave) {
        this.totalSpecialLeave = totalSpecialLeave;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
