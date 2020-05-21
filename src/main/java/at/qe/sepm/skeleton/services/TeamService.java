package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.TeamRepository;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
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


    @Autowired
    private Logger<String, User> logger;

    @Autowired
    CurrentUserBean currentUserBean;

    /**
     * A Function to get the current user
     */

    @PostConstruct
    public void init() {
        currentUserBean.init();
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }


    @PreAuthorize("hasAuthority('ADMIN')")
    public Team saveTeam(Team team) {

        logger.logUpdate(team.getTeamName(), currentUserBean.getCurrentUser());
        return teamRepository.save(team);


    }

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEPARTMENTLEADER')")
    public void addNewTeam(Set<User> employees, Team team) {
        Team newTeam = new Team();
        newTeam.setTeamName(team.getTeamName());
        newTeam.setDepartment(team.getDepartment());
        saveTeam(newTeam);
        for(User u: employees) {
            u.setTeam(team);
            u.setDepartment(team.getDepartment());
            userService.saveUser(u);
        }
        logger.logCreation(team.getTeamName(), currentUserBean.getCurrentUser());
    }

    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #username")
    public Team loadTeam(String teamName) {
        return teamRepository.findByTeamName(teamName);
    }

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteTeam(Team team) {
        teamRepository.delete(team);
        logger.logDeletion(team.toString(), currentUserBean.getCurrentUser());
    }



    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Team> getAllTeamsByTeamName (String teamName) { return this.teamRepository.getAllTeamsByTeamPrefix(teamName); }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Team> getTeamsWithoutDepartment() { return this.teamRepository.getTeamsWithoutDepartment();}

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsersWithoutTeam() { return userService.getAllUsersWithoutTeam(); }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getUsersOfTeam(Team team) { return userService.getUsersOfTeam(team); }

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Team> getTeamsOfDepartment(Department department) { return teamRepository.findByDepartment(department); }




}
