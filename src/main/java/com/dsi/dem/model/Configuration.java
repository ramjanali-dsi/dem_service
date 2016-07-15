package com.dsi.dem.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;

/**
 * Created by sabbir on 7/15/16.
 */

@Entity
@Table(name = "dsi_configuration")
public class Configuration {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "configuration_id", length = 40)
    private String configurationId;

    @Column(name = "total_leave")
    private int totalLeave;

    @Column(name = "total_sick")
    private int totalSick;

    @Column(name = "total_casual")
    private int totalCasual;

    public String getConfigurationId() {
        return configurationId;
    }

    public void setConfigurationId(String configurationId) {
        this.configurationId = configurationId;
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
}
