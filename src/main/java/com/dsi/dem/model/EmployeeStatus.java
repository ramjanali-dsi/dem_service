package com.dsi.dem.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sabbir on 12/12/16.
 */
@Entity
@Table(name = "ref_employee_status")
public class EmployeeStatus {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "employee_status_id", length = 40)
    private String employeeStatusId;

    @Column(name = "name", length = 50)
    private String employeeStatusName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active")
    private boolean isActive;

    public String getEmployeeStatusId() {
        return employeeStatusId;
    }

    public void setEmployeeStatusId(String employeeStatusId) {
        this.employeeStatusId = employeeStatusId;
    }

    public String getEmployeeStatusName() {
        return employeeStatusName;
    }

    public void setEmployeeStatusName(String employeeStatusName) {
        this.employeeStatusName = employeeStatusName;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }
}
