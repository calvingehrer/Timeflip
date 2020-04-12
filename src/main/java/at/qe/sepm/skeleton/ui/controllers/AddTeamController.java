package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Component
@Scope("view")
public class AddTeamController {
    @Autowired
    private TeamService teamService;

    private Team team = new Team();

    private User employee = new User();

    //private User leader = new User();

    private Set<User> employees = new HashSet<>();

    boolean check;


    public void add(){

       //team.setEmployees(employees);

        teamService.addNewTeam(team);

        resetTeam();
    }

    public void resetTeam(){
        this.team = new Team();
        this.employees = new HashSet<>();
    }

    public void addLeader(){
        this.team.setLeader(employee);
    }

    public Team getTeam(){return team;}


    public void addEmployee(){
        //employee = new User();
        if(this.employee.getUsername() == null){
            return;
        }
        this.employees.add(employee);
    }

   /* public void setEmployees(Set<User> employees){
        team.setEmployees(this.employees);
    }

    public void setEmployees(User employees){
        team.setEmployees(this.employees);
    }
*/
    public void setEmployees(){
        team.setEmployees(this.employees);
    }


    public Set<User> getEmployees(){
        return employees;
    }

    public TeamService getTeamService() {
        return teamService;
    }

    public void setTeamService(TeamService teamService) {
        this.teamService = teamService;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public boolean isIn(){
        //employee = new User();
        return employees.contains(employee);
    }

    public void setIn(boolean check){
        //employee = new User();
        //this.employees.add(employee);
    }

    public User getLeader() {
        return employee;
    }

    public void setLeader(User leader) {
        //this.employee = new User();
        this.employee = leader;
    }

    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee){
        //this.employee = new User();
        //this.employee = employee;
        this.employees.add(employee);
    }


}
