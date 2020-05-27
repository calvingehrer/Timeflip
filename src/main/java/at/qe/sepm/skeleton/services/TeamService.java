package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.TeamRepository;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import at.qe.sepm.skeleton.utils.auditlog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
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

    /**
     *
     * @return all teams
     */

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    /**
     *
     * @param team
     * @return saved Team
     */

    @PreAuthorize("hasAuthority('ADMIN')")
    public Team saveTeam(Team team) {

        logger.logUpdate(team.getTeamName(), currentUserBean.getCurrentUser());
        return teamRepository.save(team);


    }

    /**
     * adds a new Team
     * @param employees
     * @param team
     */

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEPARTMENTLEADER')")
    public void addNewTeam(Set<User> employees, Team team) {
        Team newTeam = new Team();
        newTeam.setTeamName(team.getTeamName());
        newTeam.setDepartment(team.getDepartment());
        saveTeam(newTeam);
        if(employees != null){
            for(User u: employees) {
                u.setTeam(team);
                u.setDepartment(team.getDepartment());
                userService.saveUser(u);
                mailService.sendEmailTo(u, "New Team", "You have been added to " + newTeam.getTeamName());
            }
        }
        logger.logCreation(team.getTeamName(), currentUserBean.getCurrentUser());
    }

    /**
     *
     * @param teamName
     * @return team by team name
     */

    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #username")
    public Team loadTeam(String teamName) {
        return teamRepository.findByTeamName(teamName);
    }

    /**
     * deletes Team
     * @param team
     */

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteTeam(Team team) {
        teamRepository.delete(team);
        logger.logDeletion(team.toString(), currentUserBean.getCurrentUser());
    }

    /**
     *
     * @param teamName
     * @return Teams starting with the given string
     */

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Team> getAllTeamsByTeamName (String teamName) { return this.teamRepository.getAllTeamsByTeamPrefix(teamName); }

    /**
     *
     * @return teams without department
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Team> getTeamsWithoutDepartment() { return this.teamRepository.getTeamsWithoutDepartment();}

    /**
     *
     * @return users without team
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsersWithoutTeam() { return userService.getAllUsersWithoutTeam(); }

    /**
     *
     * @param team
     * @return users of the given team
     */
    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEPARTMENTLEADER') or hasAuthority('TEAMLEADER')")
    public List<User> getUsersOfTeam(Team team) { return userService.getUsersOfTeam(team); }

    /**
     *
     * @param department
     * @return teams of the department
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Team> getTeamsOfDepartment(Department department) { return teamRepository.findByDepartment(department); }




}
