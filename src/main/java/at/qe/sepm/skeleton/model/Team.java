package at.qe.sepm.skeleton.model;

import javax.persistence.ManyToMany;
import java.util.List;
import java.util.Set;

public class Team {

    private int teamId;

    @ManyToMany
    private Set<User> employees;
    private User leader;


    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int ID) {
        this.teamId = ID;
    }

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

    @Override
    public String toString() {
        return "at.qe.sepm.skeleton.model.Team[ id=" + teamId + " ]";
    }
}
