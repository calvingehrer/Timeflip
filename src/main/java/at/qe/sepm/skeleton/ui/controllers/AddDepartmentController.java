package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;

@Component
@Scope("view")
public class AddDepartmentController implements Serializable {
    @Autowired
    private DepartmentService departmentService;

    private Department department = new Department();

    private Team team;

    private User headOfDepartment;

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Team getTeam() {
        return team;
    }

    /**
     * department is mapped in team so it sets the department field
     *
     * @param team
     */
    public void setTeam(Team team) {
        this.team = team;
        this.team.setDepartment(department);
    }

    public User getHeadOfDepartment() {
        return headOfDepartment;
    }

    public void setHeadOfDepartment(User headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
    }

    /**
     * adds the new department and resets the field
     */

    public void add() {
        departmentService.addNewDepartment(headOfDepartment, department);
        resetDepartment();
    }

    /**
     * resets the field
     */

    public void resetDepartment() {
        this.department = new Department();
    }


}
