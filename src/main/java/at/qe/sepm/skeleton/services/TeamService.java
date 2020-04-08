package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.parameters.P;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.Date;


@Component
@Scope("application")
public class TeamService {

    @Autowired
    private MailService mailService;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAuthority('ADMIN')")
    public Collection<Team> getAllTeams(){return teamRepository.findAll();}


    @PreAuthorize("hasAuthority('ADMIN')")
    public Team saveTeam(Team team) {


       return teamRepository.save(team);
    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEPARTMENTLEADER')")
    public void addNewTeam(Team team) {

        Team newTeam = new Team();

        newTeam.setTeamName(team.getTeamName());

        saveTeam(newTeam);

    }


}
