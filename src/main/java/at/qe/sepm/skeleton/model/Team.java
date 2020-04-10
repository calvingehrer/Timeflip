package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Set;


@Entity
public class Team implements Persistable<String>, Serializable {

    private static final long serialVersionTID = 1L;

    @Id
    @Column(length = 100)
    private String teamName;

    @ManyToMany(fetch = FetchType.EAGER)
    private Set<User> employees;
    @OneToOne
    private User leader;


    public Set<User> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<User> employees) {
        this.employees = employees;
    }

    public void addEmployees(User employee){
        this.employees.add(employee);
    }

    public User getLeader() {
        return leader;
    }

    public void setLeader(User leader) {
        this.leader = leader;
    }

    public static long getSerialVersionTID() {
        return serialVersionTID;
    }

    public String getTeamName() {
        return teamName;
    }

    public void setTeamName(String teamName) {
        this.teamName = teamName;
    }

    @Override
    public String toString() {
        return "at.qe.sepm.skeleton.model.Team[ id=" + teamName + " ]";
    }

    @Override
    public String getId() {
        return getTeamName();
    }


    @Override
    public boolean isNew() {
        return false;
    }
}
