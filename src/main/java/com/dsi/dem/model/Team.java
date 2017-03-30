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

    @Column(length = 50)
    private String floor;

    @Column(length = 50)
    private String room;

    @Column(name = "member_count")
    private int memberCount;

    @Column(name = "is_active")
    private boolean isActive;

    private int version;

    @Transient
    private List<TeamMember> members = new ArrayList<>();

    @Transient
    private List<ProjectTeam> projects = new ArrayList<>();

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

    public int getVersion() {
        return version;
    }

    public void setVersion(int version) {
        this.version = version;
    }

    public List<TeamMember> getMembers() {
        return members;
    }

    public void setMembers(List<TeamMember> members) {
        this.members = members;
    }

    public List<ProjectTeam> getProjects() {
        return projects;
    }

    public void setProjects(List<ProjectTeam> projects) {
        this.projects = projects;
    }
}
