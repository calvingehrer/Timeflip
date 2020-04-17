package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.Collection;


@Component
@Scope("application")
public class TeamService {

    @Autowired
    private MailService mailService;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

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

    //@PreAuthorize("hasAuthority('ADMIN')")
    //public Collection<User> getAllEmployees (String teamName) { return this.teamRepository.g; }



    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEPARTMENTLEADER')")
    public void addNewTeam(Team team) {

        Team newTeam = new Team();

        newTeam.setTeamName(team.getTeamName());
        newTeam.setEmployees(team.getEmployees());
        newTeam.setLeader(team.getLeader());

        saveTeam(newTeam);

    }

    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #username")
    public Team loadTeam(String teamName) {
        return teamRepository.findByTeamName(teamName);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteTeam(Team team){
        if (team.getDepartment() != null) { departmentService.removeTeamfromDepartment(team, team.getDepartment()); }
        userService.removeEmployeesFromTeam(team);
        userService.removeTeamFromLeader(team);
        teamRepository.delete(team);
        System.out.println("");
    }

    /*@PreAuthorize("hasAuthority('ADMIN')")
    public Collection<Team> getAllTeamsByTeamName(String teamName){
        return teamRepository.findByTeamName2(teamName);
    }*/


    @PreAuthorize("hasAuthority('ADMIN')")
    public Collection<Team> getAllTeamsByTeamName (String teamName) { return this.teamRepository.getAllTeamsByTeamPrefix(teamName); }

    @PreAuthorize("hasAuthority('ADMIN')")
    public Collection<Team> getTeamsWithoutDepartment() { return this.teamRepository.getTeamsWithoutDepartment();}
}
