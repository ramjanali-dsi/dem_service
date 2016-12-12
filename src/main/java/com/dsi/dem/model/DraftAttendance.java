package com.dsi.dem.model;

import com.dsi.dem.util.JsonDateSerializer;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.sql.Timestamp;
import java.util.Date;

/**
 * Created by sabbir on 11/23/16.
 */
@Entity
@Table(name = "ref_draft_attendance_file")
public class DraftAttendance {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "draft_attendance_id", length = 40)
    private String draftAttendanceId;

    @Column(name = "attendance_date")
    @Type(type = "date")
    private Date attendanceDate;

    @Column(name = "created_date")
    private Timestamp createdDate;

    @Column(name = "last_modified_date")
    private Timestamp lastModifiedDate;

    public String getDraftAttendanceId() {
        return draftAttendanceId;
    }

    public void setDraftAttendanceId(String draftAttendanceId) {
        this.draftAttendanceId = draftAttendanceId;
    }

    public Date getAttendanceDate() {
        return attendanceDate;
    }

    public void setAttendanceDate(Date attendanceDate) {
        this.attendanceDate = attendanceDate;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Timestamp getCreatedDate() {
        return createdDate;
    }

    public void setCreatedDate(Timestamp createdDate) {
        this.createdDate = createdDate;
    }

    @JsonSerialize(using = JsonDateSerializer.class)
    public Timestamp getLastModifiedDate() {
        return lastModifiedDate;
    }

    public void setLastModifiedDate(Timestamp lastModifiedDate) {
        this.lastModifiedDate = lastModifiedDate;
    }
}
