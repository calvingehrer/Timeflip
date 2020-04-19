package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Scope("view")
public class DepartmentDetailController implements Serializable {

    @Autowired
    private DepartmentService departmentService;

    private Department department = new Department();

    private Team teamRemove;

    private Team teamAdd;

    public Team getTeamAdd() {
        return teamAdd;
    }

    public void setTeamAdd(Team teamAdd) {
        this.teamAdd = teamAdd;
        this.teamAdd.setDepartment(department);
        this.department.setTeams(teamAdd);
    }

    public Team getTeamRemove() {
        return teamRemove;
    }

    public void setTeamRemove(Team teamRemove) {
        this.teamRemove = teamRemove;
        this.teamRemove.setDepartment(department);

        this.department.getTeams().remove(teamRemove);

        this.department.setTeams(this.department.getTeams());
    }

    public void setDepartment(Department department){
        this.department = department;
        doReloadDepartment();
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
        //System.out.println("hey");
        this.departmentService.deleteDepartment(department);
        department = null;
    }
}
