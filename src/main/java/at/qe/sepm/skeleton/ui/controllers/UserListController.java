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
 * <p>
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
    private String departmentname = "";

    /**
     * Returns a list of all users.
     */
    public Collection<User> getUsers() {
        if (!this.getUsername().equals("")) {
            return userService.getAllUsersByUsername(this.getUsername());
        }
        if (!this.getTeamname().equals("")) {
            return userService.getAllUsersOfTeamByTeamname(this.getTeamname());
        }
        if (!this.getDepartmentname().equals("")) {
            return userService.getAllUsersOfDepartmentByDepartmentname(this.getDepartmentname());
        }
        return userService.getAllUsersByRole(this.getUserrole());
    }


    public Collection<User> getUsersWithoutTimeflip() {
        return userService.getUsersWithoutTimeflip();
    }

    public Collection<User> getUsersNotInTeam() {
        return userService.getAllUsersWithoutTeam();
    }

    public Collection<User> getTeamLeadersWithoutTeam() {
        return userService.getTeamLeaderWithoutTeam();
    }

    public Collection<User> getDepartmentLeadersWithoutDepartment() {
        return userService.getDepartmentLeaderWithoutDepartment();
    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public String getDepartmentname() {
        return departmentname;
    }

    public void setDepartmentname(String departmentname) {
        this.departmentname = departmentname;
    }

    public void resetFilter() {
        this.username = "";
        this.userrole = "";
        this.teamname = "";
        this.departmentname = "";
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
