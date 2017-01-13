package com.dsi.dem.dto.transformer;

import com.dsi.dem.model.*;

import java.util.List;

/**
 * Created by sabbir on 1/12/17.
 */
class CommonTransformer {

    static String getProjectList(List<ProjectTeam> projectTeams){
        String projects = "";
        int i = 0;
        for(ProjectTeam projectTeam : projectTeams){
            projects += projectTeam.getProject().getProjectName();

            if(i != projectTeams.size() - 1){
                projects += ", ";
            }
        }
        return projects;
    }

    static String getTeamList(List<TeamMember> teamMembers){
        String teams = "";
        int i = 0;
        for(TeamMember teamMember : teamMembers){
            teams += teamMember.getTeam().getName();

            if(i != teamMembers.size() - 1){
                teams += ", ";
            }
            i++;
        }
        return teams;
    }

    static String getPhone(List<EmployeeContact> contactList){
        String phones = "";
        int i = 0;
        for(EmployeeContact contact : contactList){
            phones += contact.getPhone();

            if(i != contactList.size() - 1){
                phones += ", ";
            }
            i++;
        }
        return phones;
    }

    static String getEmail(List<EmployeeEmail> emailList){
        String emails = "";
        int i = 0;
        for(EmployeeEmail email : emailList){
            emails += email.getEmail();

            if(i != emailList.size() - 1){
                emails += ", ";
            }
            i++;
        }
        return emails;
    }

    static String getDesignation(List<EmployeeDesignation> designationList){
        String currentDesignation = "";
        for(EmployeeDesignation designation : designationList){
            if(designation.isCurrent()){
                currentDesignation += designation.getName();
                break;
            }
        }
        return currentDesignation;
    }
}
