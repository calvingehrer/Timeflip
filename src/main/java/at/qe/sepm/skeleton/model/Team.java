package at.qe.sepm.skeleton.model;

import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;


@Entity
public class Team implements Persistable<String>, Serializable {

    private static final long serialVersionTID = 1L;


    @Id
    @Column(name="team_name",length = 100)
    private String teamName;



    @ManyToOne(fetch=FetchType.LAZY)
    @Fetch(FetchMode.JOIN)
    @JoinColumn(name="department_id")
    private Department department;






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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Team)) return false;
        Team team = (Team) o;
        return getTeamName().equals(team.getTeamName()) &&
                Objects.equals(getDepartment(), team.getDepartment());
    }

    @Override
    public int hashCode() {
        return Objects.hash(getTeamName(), getDepartment());
    }
}
