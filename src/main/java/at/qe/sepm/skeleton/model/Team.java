package at.qe.sepm.skeleton.model;

import org.hibernate.annotations.Cascade;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.List;
import java.util.Set;


@Entity
@Table(name = "team")
public class Team implements Persistable<String>, Serializable {

    private static final long serialVersionTID = 1L;

    @Id
    @Column(name="team_name",length = 100)
    private String teamName;


    //@Cascade({CascadeType.SAVE_UPDATE})
    //@JoinTable
    @ManyToMany(mappedBy = "teams", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<User> employees = new HashSet<>();
    @OneToOne
    private User leader;

    @ManyToOne
    private Department department;


    public Set<User> getEmployees() {
        return employees;
    }

    public void setEmployees(Set<User> employees) {
        this.employees = employees;
    }

    public void setEmployees() {
        this.employees = employees;
    }

    public void setEmployees(User employee){
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

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    @Override
    public String toString() {
        return this.getTeamName();
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
