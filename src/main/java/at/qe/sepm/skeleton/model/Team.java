package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import java.io.Serializable;
import java.util.List;
import java.util.Set;


@Entity
public class Team implements Persistable<String>, Serializable {

    private static final long serialVersionTID = 1L;

    @Id
    private String teamName;

    @ManyToMany
    private Set<User> employees;
    @OneToOne
    private User leader;


    public Set<User> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<User> employees) {
        this.employees = employees;
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
