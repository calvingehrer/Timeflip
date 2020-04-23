package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;


@Component
@Scope("view")
public class AddTeamController implements Serializable {
    @Autowired
    private TeamService teamService;

    private Team team = new Team();

    private User employee;

    private User leader;

   // private Set<User> employees = new HashSet<>();

    boolean check;


    public void add(){

       // team.setEmployees();
        teamService.addNewTeam(team);

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




    public User getEmployee() {
        return employee;
    }

    public void setEmployee(User employee){
        this.employee = employee;
        this.employee.setTeam(null);

    }

    public void addEmployee(){

    }
}
