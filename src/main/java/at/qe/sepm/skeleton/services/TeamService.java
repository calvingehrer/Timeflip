package at.qe.sepm.skeleton.services;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.TaskRepository;
import at.qe.sepm.skeleton.repositories.TeamRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import at.qe.sepm.skeleton.utils.auditlog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Component;

import java.util.Date;
import java.util.HashSet;
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
    private TaskRepository taskRepository;

    @Autowired
    private UserRepository userRepository;

    /**
     * @return all teams
     */

    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Team> getAllTeams() {
        return teamRepository.findAll();
    }

    /**
     * adds a new Team
     *
     * @param employees to add
     * @param team      to add
     */

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEPARTMENTLEADER')")
    public void addNewTeam(Set<User> employees, Team team) {
        Team newTeam = new Team();
        newTeam.setTeamName(team.getTeamName());
        newTeam.setDepartment(team.getDepartment());
        newTeam.setCreateDate(new Date());
        saveTeam(employees, null, newTeam);
        logger.logCreation(team.getTeamName(), userService.getAuthenticatedUser());
    }

    /**
     * @param team to save
     */


    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEPARTMENTLEADER')")
    public void saveTeam(Set<User> addedEmployees, Set<User> removedEmployees, Team team) {
        teamRepository.save(team);
        if (addedEmployees != null) {
            for (User u : addedEmployees) {
                u.setTeam(team);
                u.setDepartment(team.getDepartment());
                taskRepository.findTasksFromUser(u)
                        .forEach(task -> {
                            task.setTeam(team);
                            task.setDepartment(team.getDepartment());
                            taskRepository.save(task);
                        });
                userService.saveUser(u);
                mailService.sendEmailTo(u, "New Team", "You have been added to " + team.getTeamName());
            }
        }

        if (removedEmployees != null) {
            for (User u : removedEmployees) {
                u.setTeam(null);
                u.setDepartment(null);
                taskRepository.findTasksFromUser(u)
                        .forEach(task -> {
                            task.setTeam(null);
                            task.setDepartment(null);
                            taskRepository.save(task);
                        });
                userService.saveUser(u);
            }
        }
        logger.logUpdate(team.getTeamName(), userService.getAuthenticatedUser());

    }

    /**
     * @param teamName to load
     * @return team by team name
     */

    @PreAuthorize("hasAuthority('ADMIN') or principal.username eq #username")
    public Team loadTeam(String teamName) {
        return teamRepository.findByTeamName(teamName);
    }

    /**
     * deletes Team
     *
     * @param team to delete
     */

    @PreAuthorize("hasAuthority('ADMIN')")
    public void deleteTeam(Team team) {
        team.setDepartment(null);
        teamRepository.delete(team);
        logger.logDeletion(team.toString(), userService.getAuthenticatedUser());
    }

    /**
     * @param teamName to find
     * @return Teams starting with the given string
     */

    @PreAuthorize("hasAuthority('ADMIN') or hasAuthority('DEPARTMENTLEADER')")
    public List<Team> getAllTeamsByTeamName(String teamName) {
        return this.teamRepository.getAllTeamsByTeamPrefix(teamName);
    }

    /**
     * @return teams without department
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Team> getTeamsWithoutDepartment() {
        return this.teamRepository.getTeamsWithoutDepartment();
    }

    /**
     * @return users without team
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<User> getAllUsersWithoutTeam() {
        return userService.getAllUsersWithoutTeam();
    }

    /**
     * @param department to find
     * @return teams of the department
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Team> getTeamsOfDepartment(Department department) {
        return teamRepository.findByDepartment(department);
    }

    /**
     * @param department department name
     * @return teams of department searched by string
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public List<Team> getTeamsByDepartmentName(String department) {
        return teamRepository.findByDepartmentPrefix(department);
    }

    /**
     * @param employee employees in team
     * @return teams where users got username prefix
     */
    @PreAuthorize("hasAuthority('ADMIN')")
    public Set<Team> getTeamsWithEmployee(String employee) {
        List<User> employees = userService.getAllUsersByUsername(employee);
        Set<Team> teams = new HashSet<>();
        for (User e : employees) {
            if (e.getTeam() != null)
                teams.add(e.getTeam());
        }
        return teams;
    }


}
