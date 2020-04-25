package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.DepartmentRepository;
import at.qe.sepm.skeleton.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Set;


@Component
@Scope("application")
public class TeamService {

    @Autowired
    private MailService mailService;


    @Autowired
    private UserService userService;

    @Autowired
    private TeamRepository teamRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Team> getAllTeams(){return teamRepository.findAll();}


    @PreAuthorize("hasAuthority('ADMIN')")
    public Team saveTeam(Team team) {


        return teamRepository.save(team);


    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEPARTMENTLEADER')")
    public void addNewTeam(Set<User> employees, Team team) {
        Team newTeam = new Team();
        newTeam.setTeamName(team.getTeamName());
        saveTeam(newTeam);
        for(User u: employees) {
            u.setTeam(team);
            userService.saveUser(u);
        }
    }

    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #username")
    public Team loadTeam(String teamName) {
        return teamRepository.findByTeamName(teamName);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteTeam(Team team){

        teamRepository.delete(team);

    }



    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Team> getAllTeamsByTeamName (String teamName) { return this.teamRepository.getAllTeamsByTeamPrefix(teamName); }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Team> getUsersNotInTeam (String teamname) { return this.teamRepository.getUsersNotInTeam(teamname); }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Team> getTeamsWithoutDepartment() { return this.teamRepository.getTeamsWithoutDepartment();}

    @PreAuthorize("hasAuthority('ADMIN')")
    public User getTeamLeader(Team team) { return userService.getTeamLeader(team); }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsersWithoutTeam() { return userService.getAllUsersWithoutTeam(); }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getUsersOfTeam(Team team) { return userService.getUsersOfTeam(team); }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Team> getTeamsOfDepartment(Department department) { return teamRepository.findByDepartment(department); }


}
