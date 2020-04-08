package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;


@Component
@Scope("view")
public class AddTeamController {

    @Autowired
    private TeamService teamService;

    private Team team = new Team();

    public void add(){
        teamService.addNewTeam(team);


    }

    public Team getTeam(){return team;}
}
