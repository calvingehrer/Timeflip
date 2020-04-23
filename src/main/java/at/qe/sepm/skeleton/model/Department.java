package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;


@Entity
public class Department implements Persistable<String>, Serializable {

    private static final long serialVersionDID = 1L;

    @Id
    @Column(length = 100)
    private String departmentName;


    @OneToMany(mappedBy = "department", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<Team> teams  = new HashSet<>();


    @OneToOne(mappedBy = "headOf")
    private User headOfDepartment;


    public void setTeams(Team team){
        this.teams.add(team);
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = new HashSet<>();
        this.teams = teams;
    }

    public void removeTeam(Team team){
        this.teams.remove(team);
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

    /*
    public void removeTeam(Team team) {
        teams.remove(team);
        team.setDepartment(null);
    }*/
}
