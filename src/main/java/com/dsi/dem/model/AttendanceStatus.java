package com.dsi.dem.model;

import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

/**
 * Created by sabbir on 11/2/16.
 */

@Entity
@Table(name = "ref_attendance_status")
public class AttendanceStatus {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "attendance_status_id", length = 40)
    private String attendanceStatusId;

    @Column(name = "attendance_date")
    @Type(type = "date")
    private Date attendanceDate;

    @Column(length = 40)
    private String type;

    private boolean status;

    public String getAttendanceStatusId() {
        return attendanceStatusId;
    }

    public void setAttendanceStatusId(String attendanceStatusId) {
        this.attendanceStatusId = attendanceStatusId;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
}
