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

    private Team teamRemove;

    private Team teamAdd;

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
            System.out.println("hello");
            MessagesView.warnMessage("department deletion", "You can't delete this department");
            return;
        }

        successMessage("department deletion", "Department deleted");
        logger.logUpdate("department was deleted", userService.getAuthenticatedUser());
    }


    public static void warnMessage(String target, String message) {
        addMessage(target, new FacesMessage(FacesMessage.SEVERITY_WARN, "Warning!", message));
    }

    public static void successMessage(String target, String message) {
        addMessage(target, new FacesMessage(FacesMessage.SEVERITY_INFO, "Success!", message));
    }

    private static void addMessage(String target, FacesMessage message) {
        FacesContext.getCurrentInstance().addMessage(target, message);
        MessagesView.successMessage("department deletion", "Department deleted");
    }

    public boolean checkIfDeletionIsAllowed (Department department){
        if (!teamService.getTeamsOfDepartment(department).isEmpty()) {
            return false;
        } else return userService.getDepartmentLeader(department) == null;
    }

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
