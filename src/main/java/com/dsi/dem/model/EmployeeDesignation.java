package com.dsi.dem.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.ManyToAny;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sabbir on 7/14/16.
 */

@Entity
@Table(name = "dsi_employee_designation")
public class EmployeeDesignation {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "employee_designation_id", length = 40)
    private String employeeDesignationId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(length = 50)
    private String name;

    @Column(name = "designation_date")
    private Date designationDate;

    @Column(name = "is_current")
    private boolean isCurrent;

    private int version;

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmployeeDesignationId() {
        return employeeDesignationId;
    }

    public void setEmployeeDesignationId(String employeeDesignationId) {
        this.employeeDesignationId = employeeDesignationId;
    }

    public Date getDesignationDate() {
        return designationDate;
    }

    public void setDesignationDate(Date designationDate) {
        this.designationDate = designationDate;
    }

    public boolean isCurrent() {
        return isCurrent;
    }

    public void setCurrent(boolean current) {
        isCurrent = current;
    }

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }
}
