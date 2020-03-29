package at.qe.sepm.skeleton.model;

import javax.persistence.OneToMany;
import java.util.List;

public class Department {

    private int departmentId;

    @OneToMany
    private List<Team> teams;

    private User headOfDepartment;

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public List<Team> getTeams() {
        return teams;
    }

    public void setTeams(List<Team> teams) {
        this.teams = teams;
    }

    public User getHeadOfDepartment() {
        return headOfDepartment;
    }

    public void setHeadOfDepartment(User headOfDepartment) {
        this.headOfDepartment = headOfDepartment;
    }

    @Override
    public String toString() {
        return "at.qe.sepm.skeleton.model.User[ id=" + departmentId + " ]";
    }
}
