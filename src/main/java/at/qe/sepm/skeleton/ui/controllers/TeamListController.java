package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;
import java.util.Set;

@Component
@Scope("view")
public class TeamListController implements Serializable {

    private Team team;

    @Autowired
    private TeamService teamService;

    private String teamName = "";
    private String employee = "";
    private String department = "";


    /**
     * @return all teams
     */

    public Collection<Team> getTeams() {
        if (!teamName.equals("")) {
            return teamService.getAllTeamsByTeamName(teamName);
        }
        if (!department.equals("")) {
            return teamService.getTeamsByDepartmentName(department);
        }
        if (!employee.equals("")) {
            return teamService.getTeamsWithEmployee(employee);
        }
        return teamService.getAllTeams();
    }

    /**
     *
     * @return teams without department
     */

    public Collection<Team> getTeamsWithoutDepartment(){
        return teamService.getTeamsWithoutDepartment();
    }

    /**
     *
     * @return users without team
     */

    public Collection<User> getUsersNotInTeam() {
        return teamService.getAllUsersWithoutTeam();
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public Collection<Team> getTeamsInDepartment(Department department) {
        return teamService.getTeamsOfDepartment(department);
    }


    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public void resetFilter() {
        this.department = "";
        this.teamName = "";
        this.employee = "";
    }

}
