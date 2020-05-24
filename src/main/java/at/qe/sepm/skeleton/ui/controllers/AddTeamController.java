package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.Logger;
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
    @Autowired
    private Logger<String, User> logger;

    private final Set<User> employees = new HashSet<>();

    private Team team = new Team();

    private User employee;

    private Department department;

    public void add() {
        teamService.addNewTeam(employees, team);
        resetTeam();
    }

    public void resetTeam() {
        this.team = new Team();
    }

    public void addDepartment() {
        this.team.setDepartment(this.department);
    }

    public void addEmployee(){
        this.employees.add(this.employee);
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Team getTeam() {
        return team;
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

}
