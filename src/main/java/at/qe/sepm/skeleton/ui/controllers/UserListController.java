package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * Controller for the user list view.
 *
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
@Component
@Scope("view")
public class UserListController implements Serializable {


    @Autowired
    private UserService userService;


    private String userrole = "";
    private String username = "";
    private String teamname = "";

    /**
     * Returns a list of all users.
     *
     * @return
     */
    public Collection<User> getUsers() {
        if (!username.equals("")) {
            return userService.getAllUsersByUsername(username);
        }
        return userService.getAllUsersByRole(userrole);
    }

    public Collection<User> getUsersNotInTeam() {
        return userService.getAllUsersWithoutTeam();
    }

    public Collection<User> getTeamLeadersWithoutTeam() { return userService.getTeamLeaderWithoutTeam(); }

    public Collection<User> getDepartmentLeadersWithoutTeam() { return userService.getDepartmentLeaderWithoutTeam(); }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public void resetFilter() {
        this.username = "";
        this.userrole = "";
    }

    public String getUserrole() {
        return userrole;
    }

    public void setUserrole(String userrole) {
        this.userrole = userrole;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public List<User> getEmployees(Team team) {
        return userService.getUsersOfTeam(team);
    }

    public User getTeamLeader(Team team) {
        return userService.getTeamLeader(team);
    }

}
