package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.TeamService;
import at.qe.sepm.skeleton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.List;

@Component
@Scope("view")
public class TeamDetailController implements Serializable {

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    private Team team;

    private User employeeAdd;

    private User employeeRemove;

    private User newLeader;

    private List<User> employees;




    public void setTeam(Team team) {
        this.team = team;
        doReloadTeam();
    }


    public Team getTeam(){
        return team;
    }

    public void doReloadTeam() {
        team = teamService.loadTeam(team.getTeamName());
    }

    public void doSaveTeam(){
        team = this.teamService.saveTeam(team);
    }


    public void doDeleteTeam(){
        this.teamService.deleteTeam(team);
        team = null;
    }


    public User getEmployee() {
        return employeeAdd;
    }

    public void setEmployee(User employee){
        this.employeeAdd = employee;
    }

    public void addEmployee() {
        employeeAdd.setTeam(team);
        employeeAdd.setDepartment(team.getDepartment());
        userService.saveUser(employeeAdd);
    }

    public User getEmployeeRemove() {
        return employeeRemove;
    }

    public void setEmployeeRemove(User employeeRemove) {
        this.employeeRemove = employeeRemove;
    }

    public User getNewLeader() {
        return newLeader;
    }

    public void setNewLeader(User newLeader) {
        this.newLeader = newLeader;
    }

    public void removeEmployee() {
        this.employeeRemove.setTeam(null);
        this.employeeRemove.setDepartment(null);
        userService.saveUser(employeeRemove);
    }

    public List<User> getEmployees() {
        return teamService.getUsersOfTeam(this.team);
    }

    public void replaceLeader() {
        User oldLeader = userService.getTeamLeader(team);
        oldLeader.setTeam(null);
        userService.saveUser(oldLeader);
        User newLeader = this.getNewLeader();
        newLeader.setTeam(team);
        userService.saveUser(newLeader);
    }
}
