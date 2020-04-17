package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.persistence.CollectionTable;
import java.util.Collection;

@Component
@Scope("view")
public class TeamListController {

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
}
