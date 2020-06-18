package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.TeamService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.utils.MessagesView;
import at.qe.sepm.skeleton.utils.auditlog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Component
@Scope("view")
public class DepartmentDetailController implements Serializable {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private TeamService teamService;

    private Set<Team> addedTeams = new HashSet<>();

    private Set<Team> removedTeams = new HashSet<>();

    private Team addedTeam;

    private Team removedTeam;

    @Autowired
    private Logger<String, User> logger;

    private Department department;

    private User newLeader;

    private User oldLeader;

    public void setDepartment(Department department) {
        this.department = department;
    }

    public User getNewLeader() {
        return newLeader;
    }

    public void setNewLeader(User newLeader) {
        this.newLeader = newLeader;
    }

    public Department getDepartment() {
        return department;
    }

    public Team getAddedTeam() {
        return addedTeam;
    }

    public void setAddedTeam(Team addedTeam) {
        this.addedTeam = addedTeam;
    }

    public Team getRemovedTeam() {
        return removedTeam;
    }

    public void setRemovedTeam(Team removedTeam) {
        this.removedTeam = removedTeam;
    }

    public void doReloadDepartment() {
        department = departmentService.loadDepartment(department.getDepartmentName());
    }

    public void doSaveDepartment() {
        this.departmentService.saveDepartment(department, addedTeams, removedTeams, oldLeader, newLeader);
    }

    /**
     * deletes a department
     * when errors occur like that it is not empty you get a warn message
     * otherwise you get a success message
     */

    public void doDeleteDepartment() {
        if (checkIfDeletionIsAllowed(department)) {
            try {
                this.departmentService.deleteDepartment(department);
                department = null;
            } catch (Exception e) {
                e.printStackTrace();
                logger.logError(e, userService.getAuthenticatedUser());
            }
        } else {
            MessagesView.warnMessage("department deletion", "You can't delete this department");
            return;
        }

        MessagesView.successMessage("department deletion", "Department deleted");
        logger.logUpdate("department was deleted", userService.getAuthenticatedUser());
    }


    /**
     * checks whether there are any teams still  in the department or it
     * still has an departmentleader
     *
     * @param department to check
     */

    private boolean checkIfDeletionIsAllowed(Department department) {
        if (!teamService.getTeamsOfDepartment(department).isEmpty()) {
            return false;
        } else return userService.getDepartmentLeader(department) == null;
    }

    /**
     * replaces the leader
     * as a working department can not have no leader it only is possible to set one to the position
     */

    public void replaceLeader() {
        this.oldLeader = userService.getDepartmentLeader(department);
    }

    public void addTeam() {
        this.addedTeams.add(this.addedTeam);
    }

    public void removeTeam() {
        this.removedTeams.add(this.removedTeam);
    }

    public Set<Team> getAddedTeams() {
        return addedTeams;
    }

    public Set<Team> getRemovedTeams() {
        return removedTeams;
    }
}
