package com.dsi.dem.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sabbir on 7/15/16.
 */

@Entity
@Table(name = "ref_employee_email_type")
public class EmployeeEmailType {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "employee_email_type_id", length = 40)
    private String employeeEmailTypeId;

    @Column(length = 50)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active")
    private boolean isActive;

    public String getEmployeeEmailTypeId() {
        return employeeEmailTypeId;
    }

    public void setEmployeeEmailTypeId(String employeeEmailTypeId) {
        this.employeeEmailTypeId = employeeEmailTypeId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
