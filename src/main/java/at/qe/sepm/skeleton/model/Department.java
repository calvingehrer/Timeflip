package at.qe.sepm.skeleton.model;

import javax.persistence.OneToMany;
import java.util.List;
import java.util.Set;

public class Department {

    private int departmentId;

    @OneToMany
    private Set<Team> teams;

    private User headOfDepartment;

    public int getDepartmentId() {
        return departmentId;
    }

    public void setDepartmentId(int departmentId) {
        this.departmentId = departmentId;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
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
