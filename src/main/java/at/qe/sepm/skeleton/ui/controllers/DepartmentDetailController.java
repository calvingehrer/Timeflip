package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.TeamService;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

@Component
@Scope("view")
public class DepartmentDetailController {

    private DepartmentService departmentService;

    private Department department;

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
        this.departmentService.deleteDepartment(department);
        department = null;
    }
}
