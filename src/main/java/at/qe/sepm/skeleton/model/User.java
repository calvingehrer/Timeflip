package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.Instant;
import java.util.*;

import org.hibernate.annotations.GenericGenerator;

/**
 * Entity representing users.
 *
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
@Entity
@Table(name="user")
public class User implements Persistable<String>, Serializable {

    private static final long serialVersionUID = 1L;


    @Id @GeneratedValue(generator="system-uuid")
    @GenericGenerator(name="system-uuid", strategy = "uuid")
    @Column(length = 100)
    private String username;

    @ManyToOne(optional = false)
    private User createUser;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @ManyToOne(optional = true)
    private User updateUser;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    private String password;

    private String firstName;
    private String lastName;
    private String fullName;
    @Email
    private String email;


    boolean enabled;

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_user_role")
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;

    @ElementCollection
    @CollectionTable(name = "user_vacation")
    Set<Vacation> vacations = new HashSet<>();

    @ManyToMany(mappedBy = "employees", fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Set<Team> teams = new HashSet<>();

    @OneToOne(mappedBy = "leader")
    private Team leaderOf;

    @OneToOne(mappedBy = "headOfDepartment")
    private Department headOf;

    public Set<Vacation> getVacations() {
        return vacations;
    }

    public void setVacations(Set<Vacation> vacations) {
        this.vacations = vacations;
    }

    public void addVacation(Vacation vacation) {
        this.vacations.add(vacation);
    }

    @Enumerated(EnumType.STRING)
    private Interval intervall;


    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public Set<UserRole> getRoles() {
        return roles;
    }

    public void setRoles(Set<UserRole> roles) {
        this.roles = roles;
    }

    public User getCreateUser() {
        return createUser;
    }

    public void setCreateUser(User createUser) {
        this.createUser = createUser;
    }

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    public User getUpdateUser() {
        return updateUser;
    }

    public void setUpdateUser(User updateUser) {
        this.updateUser = updateUser;
    }

    public Date getUpdateDate() {
        return updateDate;
    }

    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }


    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Set<Team> getTeams() {
        return teams;
    }

    public void setTeams(Set<Team> teams) {
        this.teams = teams;
    }

    public Team getLeaderOf() {
        return leaderOf;
    }

    public void setLeaderOf(Team leaderOf) {
        this.leaderOf = leaderOf;
    }

    public Department getHeadOf() {
        return headOf;
    }

    public void setHeadOf(Department headOf) {
        this.headOf = headOf;
    }

    @Override
    public int hashCode() {
        int hash = 7;
        hash = 59 * hash + Objects.hashCode(this.username);
        return hash;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == null) {
            return false;
        }
        if (!(obj instanceof User)) {
            return false;
        }
        final User other = (User) obj;
        if (!Objects.equals(this.username, other.username)) {
            return false;
        }
        return true;
    }

    @Override
    public String toString() {
        return firstName + " " + lastName ;
    }

    @Override
    public String getId() {
        return getUsername();
    }


    public Interval getIntervall() {
        return intervall;
    }

    public void setIntervall(Interval intervall) {
        this.intervall = intervall;
    }

    public void setId(String id) {
        setUsername(id);
    }


    @Override
    public boolean isNew() {
        return (null == createDate);
    }

    @Transactional
    public boolean hasVacationInTime(Instant begin, Instant end) {
        return this.getVacations().stream().anyMatch(x -> x.getStart().compareTo(begin) <= 0 && x.getEnd().compareTo(begin) >= 0 || x.getStart().compareTo(end) <= 0 && x.getEnd().compareTo(end) >= 0 || x.getStart().compareTo(begin) >= 0 && x.getEnd().compareTo(end) <= 0);
    }

    public String getFullName() {
        return this.getFirstName() + " " +  this.getLastName();
    }

}
