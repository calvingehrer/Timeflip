package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.Logger;
import at.qe.sepm.skeleton.services.TeamService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.utils.MessagesView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import java.io.Serializable;

@Component
@Scope("view")
public class DepartmentDetailController implements Serializable {

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @Autowired
    private TeamService teamService;

    @Autowired
    private Logger<String, User> logger;

    private Department department = new Department();

    public User newLeader;

    public void setDepartment(Department department) {
        this.department = department;
        doReloadDepartment();
    }

    public User getNewLeader() {
        return newLeader;
    }

    public void setNewLeader(User newLeader) {
        this.newLeader = newLeader;
    }

    public Department getDepartment(){
        return department;
    }

    public void doReloadDepartment() {
        department = departmentService.loadDepartment(department.getDepartmentName());
    }

    public void doSaveDepartment(){
        department = this.departmentService.saveDepartment(department);
    }

    /**
     * deletes a department
     * when errors occur like that it is not empty you get a warn message
     * otherwise you get a success message
     */

    public void doDeleteDepartment(){
        if (checkIfDeletionIsAllowed(department)) {
            try {
                this.departmentService.deleteDepartment(department);
                department = null;
            }
            catch (Exception e) {
                e.printStackTrace();
                logger.logError(e, userService.getAuthenticatedUser());
            }
        }
        else {
            MessagesView.warnMessage("department deletion", "You can't delete this department");
            return;
        }

        MessagesView.successMessage("department deletion", "Department deleted");
        logger.logUpdate("department was deleted", userService.getAuthenticatedUser());
    }


    /**
     * checks whether there are any teams still  in the department or it
     * still has an departmentleader
     * @param department
     * @return
     */

    public boolean checkIfDeletionIsAllowed (Department department){
        if (!teamService.getTeamsOfDepartment(department).isEmpty()) {
            return false;
        } else return userService.getDepartmentLeader(department) == null;
    }

    /**
     * replaces the leader
     * as a working department can not have no leader it only is possible to set one to the position
     */

    public void replaceLeader() {
        User oldLeader = userService.getDepartmentLeader(department);
        oldLeader.setDepartment(null);
        userService.saveUser(oldLeader);
        User newLeader = this.getNewLeader();
        newLeader.setDepartment(department);
        userService.saveUser(newLeader);
        logger.logUpdate("New depatmentleader" + newLeader, userService.getAuthenticatedUser());
    }
}
