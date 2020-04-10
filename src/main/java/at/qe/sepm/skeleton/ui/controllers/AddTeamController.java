package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.OneToMany;
import java.util.HashSet;
import java.util.Set;


@Component
@Scope("view")
public class AddTeamController {

    @Autowired
    private TeamService teamService;

    private Team team = new Team();

    private User leader = new User();

    private User user = new User();

    private Set<User> employees = new HashSet<User>();

    public void add(){

        //team.setEmployees(employees);

        teamService.addNewTeam(team);


    }

    public void addLeader(){
        this.team.setLeader(leader);
    }

    public Team getTeam(){return team;}

    public void addEmployees(){
        employees.add(user);
        this.team.setEmployees(employees);
    }

    public void addEmployee(){
        this.team.setEmployees(employees);
        //team.addEmployees(user);
    }

    public Set<User> getEmployees(){
        return employees;
    }
    //public void addEmployee(){}


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

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public void setEmployees(Set<User> employees) {
        this.employees = employees;
    }
}
