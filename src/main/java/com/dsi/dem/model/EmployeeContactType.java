package com.dsi.dem.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sabbir on 7/15/16.
 */

@Entity
@Table(name = "ref_employee_contact_number_type")
public class EmployeeContactType {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "employee_contact_type_id", length = 40)
    private String employeeContactTypeId;

    @Column(length = 50)
    private String name;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active")
    private boolean isActive;

    public String getEmployeeContactTypeId() {
        return employeeContactTypeId;
    }

    public void setEmployeeContactTypeId(String employeeContactTypeId) {
        this.employeeContactTypeId = employeeContactTypeId;
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
