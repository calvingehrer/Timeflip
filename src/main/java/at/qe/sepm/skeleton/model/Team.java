package at.qe.sepm.skeleton.model;

import javax.persistence.ManyToMany;
import java.util.List;

public class Team {

    private int teamId;

    @ManyToMany
    private List<User> employees;
    private User leader;


    public int getTeamId() {
        return teamId;
    }

    public void setTeamId(int ID) {
        this.teamId = ID;
    }

    public List<User> getEmployees() {
        return employees;
    }

    public void setEmployees(List<User> employees) {
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
