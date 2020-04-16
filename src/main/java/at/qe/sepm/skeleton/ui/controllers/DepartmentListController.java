package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.Collection;

@Component
@Scope("view")
public class DepartmentListController {
    @Autowired
    private DepartmentService departmentService;

    private String departmentName = "";

    public Collection<Department> getDepartments(){
        //if(!teamName.equals("")){
        //  return teamService.getAllTeamsByTeamName(teamName);
        // }
        return departmentService.getAllDepartments();
    }
}
