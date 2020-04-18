package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Scope("view")
public class TeamDetailController {

    @Autowired
    private TeamService teamService;


    private Team team = new Team();

    private User emloyee;

    private User employee2;


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
        return emloyee;
    }

    public void setEmployee(User employee){
        this.emloyee = employee;
        //this.employees.add(employee);
        this.team.setEmployees(employee);

        //this.employees.add(employee);
    }

    public User getEmployee2() {
        return employee2;
    }

    public void setEmployee2(User employee2) {
        this.employee2 = employee2;
        this.team.getEmployees().remove(employee2);
        this.team.setEmployees(this.team.getEmployees());
    }

    public void addEmployee(){
        //this.employees.add(employee);
        //this.employees.add(employee);
        //this.employees.add(employee);

    }

    public void removeEmployee(){
        team.getEmployees().remove(emloyee);
    }

}
