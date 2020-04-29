package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Department;
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

    private Set<User> employees = new HashSet<>();

    private Department department;

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


    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public void addDepartment() {
        this.team.setDepartment(this.department);
    }

    public void setTeamService(TeamService teamService) {
        this.teamService = teamService;
    }

    public void setTeam(Team team) {
        this.team = team;
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

}
