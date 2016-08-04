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
    private String emailTypeId;

    @Column(name = "name", length = 50)
    private String emailTypeName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active")
    private boolean isActive;

    public String getEmailTypeId() {
        return emailTypeId;
    }

    public void setEmailTypeId(String emailTypeId) {
        this.emailTypeId = emailTypeId;
    }

    public String getEmailTypeName() {
        return emailTypeName;
    }

    public void setEmailTypeName(String emailTypeName) {
        this.emailTypeName = emailTypeName;
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
