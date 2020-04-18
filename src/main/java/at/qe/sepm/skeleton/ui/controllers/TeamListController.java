package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Component
@Scope("view")
public class TeamListController {

    private Team team;

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

    public Set<User> getUsersNotInTeam(String teamName){


       Collection<Team> collectionTeam = teamService.getUsersNotInTeam(teamName);
        Set<User> setUser = new HashSet<>();

        for(Team team : collectionTeam){
            setUser.addAll(team.getEmployees());
        }
        return setUser;
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
