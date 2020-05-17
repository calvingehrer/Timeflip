package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.TeamService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.utils.MessagesView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

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

    private Department department = new Department();

    private Team teamRemove;

    private Team teamAdd;

    public User newLeader;

    public void setDepartment(Department department){
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
            }
        }
        else {
            System.out.println("hello");
            MessagesView.warnMessage("department deletion", "You can't delete this department");
            return;
        }

        MessagesView.successMessage("department deletion", "Department deleted");
    }

    public boolean checkIfDeletionIsAllowed (Department department){
        if (!teamService.getTeamsOfDepartment(department).isEmpty()) {
            return false;
        }
        else if (userService.getDepartmentLeader(department) != null) {
            return false;
        }
        return true;
    }

    public void replaceLeader() {
        User oldLeader = userService.getDepartmentLeader(department);
        oldLeader.setDepartment(null);
        userService.saveUser(oldLeader);
        User newLeader = this.getNewLeader();
        newLeader.setDepartment(department);
        userService.saveUser(newLeader);
    }
}
