package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Component
@Scope("view")
public class AddTeamController implements Serializable {
    @Autowired
    private TeamService teamService;

    private Team team = new Team();

    private User employee;

    private User leader;

   private Set<User> employees = new HashSet<>();

    boolean check;


    public void add(){
        teamService.addNewTeam(employees,team);
        resetTeam();
    }

    public void resetTeam(){
        this.team = new Team();
        //this.employees = new HashSet<>();
    }

    public Team getTeam(){return team;}


    public TeamService getTeamService() {
        return teamService;
    }

    public void setTeamService(TeamService teamService) {
        this.teamService = teamService;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee){
        this.employee = employee;
    }

    public void addEmployee(){
        this.employees.add(this.employee);
    }

    public void addLeader() {
        this.employees.add(this.leader);
    }
}
