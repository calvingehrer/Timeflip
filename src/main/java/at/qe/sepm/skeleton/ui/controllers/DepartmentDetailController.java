package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.UserService;
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
        this.departmentService.deleteDepartment(department);
        department = null;
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
