package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;

/**
 * Controller for the user list view.
 *
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
@Component
@Scope("view")
public class UserListController {


    private Team team;

    @Autowired
    private UserService userService;

    private TeamDetailController teamDetailController;

    private String userrole = "";
    private String username = "";
    private String teamname = "";
    /**
     * Returns a list of all users.
     *
     * @return
     */
    public Collection<User> getUsers(){
        if(!username.equals("")){
            return userService.getAllUsersByUsername(username);
        }
        return userService.getAllUsersByRole(userrole);
    }


    public Collection<User> getUsersOfTeam(Team team) {

        //if(!teamname.equals("")){
            return userService.getAllUsersOfTeam(team.getTeamName());
        //}
        //else{
        //    return userService.getAllUsers();
        //}

    }

    public String getTeamname() {
        return teamname;
    }

    public void setTeamname(String teamname) {
        this.teamname = teamname;
    }

    public void resetFilter(){
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


    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }
}
