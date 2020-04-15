package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;


@Entity
public class Department implements Persistable<String>, Serializable {

    private static final long serialVersionDID = 1L;

    @Id
    @Column(length = 100)
    private String departmentName;


    @OneToMany(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<Team> teams;

    @OneToOne
    private User headOfDepartment;


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

    public String getDepartmentName() {
        return departmentName;
    }

    public void setDepartmentName(String departmentName) {
        this.departmentName = departmentName;
    }

    public static long getSerialVersionDID() {
        return serialVersionDID;
    }

    @Override
    public String toString() {
        return "at.qe.sepm.skeleton.model.User[ id=" + departmentName + " ]";
    }

    @Override
    public String getId() {
        return getDepartmentName();
    }


    @Override
    public boolean isNew() {
        return false;
    }
}
