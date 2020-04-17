package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name="department")
public class Department implements Persistable<String>, Serializable {

    private static final long serialVersionDID = 1L;

    @Id
    @Column(length = 100)
    private String departmentName;


    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<Team> teams  = new HashSet<>();


    @OneToOne
    @JoinColumn(name="head_of_department_username")
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
        return this.getDepartmentName();
    }

    @Override
    public String getId() {
        return getDepartmentName();
    }


    @Override
    public boolean isNew() {
        return false;
    }

    public void addTeam(Team team) {
        this.teams.add(team);
        team.setDepartment(this);
    }

    public void removeTeam(Team team) {
        teams.remove(team);
        team.setDepartment(null);
    }
}
