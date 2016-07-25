package com.dsi.dem.model;

import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by sabbir on 7/14/16.
 */

@Entity
@Table(name = "dsi_team")
public class Team {

    @Id
    @GeneratedValue(generator = "system-uuid")
    @GenericGenerator(name = "system-uuid", strategy = "uuid2")
    @Column(name = "team_id", length = 40)
    private String teamId;

    @Column(length = 50)
    private String name;

    @Column(length = 20)
    private String floor;

    @Column(length = 20)
    private String room;

    @Column(name = "member_count")
    private int memberCount;

    @Column(name = "is_active")
    private boolean isActive;

    @Transient
    private List<TeamMember> teamMemberList = new ArrayList<>();

    @Transient
    private List<ProjectTeam> teamProjectList = new ArrayList<>();

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getTeamId() {
        return teamId;
    }

    public void setTeamId(String teamId) {
        this.teamId = teamId;
    }

    public String getFloor() {
        return floor;
    }

    public void setFloor(String floor) {
        this.floor = floor;
    }

    public String getRoom() {
        return room;
    }

    public void setRoom(String room) {
        this.room = room;
    }

    public int getMemberCount() {
        return memberCount;
    }

    public void setMemberCount(int memberCount) {
        this.memberCount = memberCount;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean active) {
        isActive = active;
    }

    public List<TeamMember> getTeamMemberList() {
        return teamMemberList;
    }

    public void setTeamMemberList(List<TeamMember> teamMemberList) {
        this.teamMemberList = teamMemberList;
    }

    public List<ProjectTeam> getTeamProjectList() {
        return teamProjectList;
    }

    public void setTeamProjectList(List<ProjectTeam> teamProjectList) {
        this.teamProjectList = teamProjectList;
    }
}
