package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Collection;
import java.util.List;

@Component
@Scope("view")
public class DepartmentListController implements Serializable {
    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    private String department = "";
    private String team = "";
    private String employee = "";

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getTeam() {
        return team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getEmployee() {
        return employee;
    }

    public void setEmployee(String employee) {
        this.employee = employee;
    }

    /**
     * @return all department
     */

    public Collection<Department> getDepartments() {
        if (!department.equals("")) {
            return departmentService.getByDepartmentName(department);
        }
        if (!team.equals("")) {
            return departmentService.getByTeamName(team);
        }
        if (!employee.equals("")) {
            return departmentService.getByEmployee(employee);
        }
        return departmentService.getAllDepartments();
    }

    /**
     * @param department
     * @return all teams in the department
     */

    public List<Team> getTeamsOfDepartment (Department department) {
        return departmentService.getTeamsOfDepartment(department);
    }

    /**
     * @param department
     * @return the head of the department
     */

    public User getDepartmentLeader(Department department) {
        return departmentService.getDepartmentLeader(department);
    }

    public List<Team> getTeamsOfLeadersDepartment() {
        return getTeamsOfDepartment(userService.getAuthenticatedUser().getDepartment());
    }

    public void resetFilter() {
        this.department="";
        this.team="";
        this.employee="";
    }
}
