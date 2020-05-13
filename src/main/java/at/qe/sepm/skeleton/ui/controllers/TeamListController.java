package at.qe.sepm.skeleton.ui.controllers;

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

    private User teamLeader;

    private List<User> employees;

    @Autowired
    private TeamService teamService;

    private String teamName = "";

    public Collection<Team> getTeams(){
        if(!teamName.equals("")){
          return teamService.getAllTeamsByTeamName(teamName);
       }
        return teamService.getAllTeams();
    }

    public Collection<Team> getTeamsWithoutDepartment(){
        return teamService.getTeamsWithoutDepartment();
    }

    public Collection<User> getUsersNotInTeam() {
        return teamService.getAllUsersWithoutTeam();
    }



    public Collection<Team> getTeamsNotInDepartment(Set<Team> teamsInDepartment) {

        Collection<Team> allTeams= teamService.getAllTeams();

        for(Team team : teamsInDepartment){
            // if(allUsers.contains(user)){
            allTeams.remove(team);
            //}
        }
        return allTeams;
    }


    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }



}
