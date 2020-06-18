package at.qe.sepm.skeleton.ui.controllers;


import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.TeamService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.utils.MessagesView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Component
@Scope("view")
public class TeamDetailController implements Serializable {

    @Autowired
    private TeamService teamService;

    @Autowired
    private UserService userService;

    private Team team;

    private User employeeAdd;

    private User employeeRemove;

    private User newLeader;

    private Department department;


    private Set<User> addedEmployees = new HashSet<>();

    private Set<User> removedEmployees = new HashSet<>();


    public void setTeam(Team team) {
        this.team = team;
        doReloadTeam();
    }

    public Team getTeam() {
        return team;
    }

    public User getEmployeeAdd() {
        return employeeAdd;
    }

    public void setEmployeeAdd(User employeeAdd) {
        this.employeeAdd = employeeAdd;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public User getEmployeeRemove() {
        return employeeRemove;
    }

    public void setEmployeeRemove(User employeeRemove) {
        this.employeeRemove = employeeRemove;
    }

    public User getNewLeader() {
        return newLeader;
    }

    public void setNewLeader(User newLeader) {
        this.newLeader = newLeader;
    }


    /**
     * loads team again and sets the changes to null
     */

    public void doReloadTeam() {
        addedEmployees.clear();
        removedEmployees.clear();
        team = teamService.loadTeam(team.getTeamName());
    }


    /**
     * deletes the team if it is empty
     * displays a warn message if it is no possible
     * otherwise it displays a success message
     */

    public void doDeleteTeam() {
        if (checkIfDeletionIsAllowed(team)) {
            try {
                this.teamService.deleteTeam(team);
                team = null;
            } catch (Exception e) {
                e.printStackTrace();
            }
        } else {
            MessagesView.warnMessage("team deletion", "You can't delete this team");
            return;
        }

        MessagesView.successMessage("team deletion", "Team deleted");

    }

    /**
     * checks if a deletion is allowed
     *
     * @param team
     * @return
     */

    public boolean checkIfDeletionIsAllowed(Team team) {
        if (!userService.getUsersOfTeam(team).isEmpty()) {
            return false;
        } else return userService.getTeamLeader(team) == null;
    }


    /**
     * add employee to team
     */

    public void addEmployee() {
        this.addedEmployees.add(this.getEmployeeAdd());
    }

    /**
     * removes employee from team
     */

    public void removeEmployee() {
        this.removedEmployees.add(this.getEmployeeRemove());

    }

    /**
     * replaces the team leader
     */

    public void replaceLeader() {
        this.addedEmployees.add(this.getNewLeader());
        this.removedEmployees.add(userService.getTeamLeader(this.getTeam()));
    }

    public void doSaveTeam() {
        teamService.saveTeam(this.addedEmployees, this.removedEmployees, this.getTeam());
        this.addedEmployees.clear();
        this.removedEmployees.clear();
    }

    public void changeDepartment() {
        this.team.setDepartment(this.department);
    }
}
