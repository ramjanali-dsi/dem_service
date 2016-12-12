package com.dsi.dem.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sabbir on 11/29/16.
 */
@Entity
@Table(name = "ref_holiday_type")
public class HolidayType {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "holiday_type_id", length = 40)
    private String holidayTypeId;

    @Column(name = "name", length = 50)
    private String holidayTypeName;

    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name = "is_active")
    private boolean isActive;

    public String getHolidayTypeId() {
        return holidayTypeId;
    }

    public void setHolidayTypeId(String holidayTypeId) {
        this.holidayTypeId = holidayTypeId;
    }

    public String getHolidayTypeName() {
        return holidayTypeName;
    }

    public void setHolidayTypeName(String holidayTypeName) {
        this.holidayTypeName = holidayTypeName;
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
