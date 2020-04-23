package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.TeamService;
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


    private Team team = new Team();

    private User employeeAdd;

    private User employeeRemove;

    private List<User> employee;


    public void setTeam(Team team){

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
        //this.employees.add(employee);

        //this.employees.add(employee);
    }

    public User getEmployeeRemove() {
        return employeeRemove;
    }

    public void setEmployeeRemove(User employeeRemove) {
        this.employeeRemove = employeeRemove;
        this.employeeRemove.setTeam(null);
    }


}
