package com.dsi.dem.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sabbir on 7/14/16.
 */

@Entity
@Table(name = "dsi_employee_contact_number")
public class EmployeeContact {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "employee_contact_number_id", length = 40)
    private String employeeContactId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    private Employee employee;

    @Column(length = 15)
    private String phone;

    @ManyToOne
    @JoinColumn(name = "type_id")
    private EmployeeContactType type;

    public String getEmployeeContactId() {
        return employeeContactId;
    }

    public void setEmployeeContactId(String employeeContactId) {
        this.employeeContactId = employeeContactId;
    }

    public Employee getEmployee() {
        return employee;
    }

    public void setEmployee(Employee employee) {
        this.employee = employee;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public EmployeeContactType getType() {
        return type;
    }

    public void setType(EmployeeContactType type) {
        this.type = type;
    }
}
