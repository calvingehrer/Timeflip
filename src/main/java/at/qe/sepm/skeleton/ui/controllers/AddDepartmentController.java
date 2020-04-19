package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.DepartmentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

@Component
@Scope("view")
public class AddDepartmentController implements Serializable {
    @Autowired
    private DepartmentService departmentService;

    private Department department = new Department();

    private Team team;

    private User headOfDepartment;

    //private Set<Team> teams = new HashSet<>();


    public DepartmentService getDepartmentService() {
        return departmentService;
    }

    public void setDepartmentService(DepartmentService departmentService) {
        this.departmentService = departmentService;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
        this.team.setDepartment(department);
        //this.teams.add(this.getTeam());
        this.department.setTeams(team);
    }

    public User getHeadOfDepartment() {
        return headOfDepartment;
    }

    public void setHeadOfDepartment(User headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
    }

    /*public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public void setTeams(){
        this.department.setTeams(this.teams);
    }
*/
    public void add(){


        departmentService.addNewDepartment(department);

        resetDepartment();
    }


    public void resetDepartment(){
        this.department = new Department();
        //this.teams = new HashSet<>();
    }

    public void addHeadOfDepartment(){
        this.department.setHeadOfDepartment(headOfDepartment);
    }

    public void addTeam(){
    }


}
