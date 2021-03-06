package at.qe.sepm.skeleton.model;

import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import javax.transaction.Transactional;
import javax.validation.constraints.Email;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

/**
 * Entity representing users.
 * <p>
 * This class is part of the skeleton project provided for students of the
 * courses "Software Architecture" and "Software Engineering" offered by the
 * University of Innsbruck.
 */
@Entity
public class User implements Persistable<String>, Serializable {

    private static final long serialVersionUID = 1L;


    @Id
    @Column(length = 100)
    private String username;

    @ManyToOne(optional = false)
    private User createUser;
    @Column(nullable = false)
    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;
    @ManyToOne()
    private User updateUser;
    @Temporal(TemporalType.TIMESTAMP)
    private Date updateDate;

    private String password;

    private String firstName;
    private String lastName;
    @Email
    private String email;


    private boolean enabled;

    @ElementCollection(targetClass = UserRole.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "user_user_role")
    @Enumerated(EnumType.STRING)
    private Set<UserRole> roles;

    @ElementCollection(fetch = FetchType.EAGER)
    @CollectionTable(name = "user_vacation")
    private Set<Vacation> vacations = new HashSet<>();

    @ManyToOne(targetEntity = Team.class, fetch = FetchType.EAGER)
    @JoinColumn(name = "team_id")
    private Team team;

    @ManyToOne
    @JoinColumn(name = "department_id")
    private Department department;
    @Enumerated(EnumType.STRING)
    private Interval intervall;

    public static long getSerialVersionUID() {
        return serialVersionUID;
    }

    public Set<Vacation> getVacations() {
        return vacations;
    }

    public void addVacation(Vacation vacation) {
        this.vacations.add(vacation);
    }

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

    public Team getTeam() {
        return team;
    }

    public void setTeam(Team team) {
        this.team = team;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
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
        return Objects.equals(this.username, other.username);
    }

    @Override
    public String toString() {
        return firstName + " " + lastName;
    }

    @Override
    public String getId() {
        return getUsername();
    }

    public void setId(String id) {
        setUsername(id);
    }

    public Interval getIntervall() {
        return intervall;
    }

    public void setIntervall(Interval intervall) {
        this.intervall = intervall;
    }

    @Override
    public boolean isNew() {
        return (null == createDate);
    }

    @Transactional
    public boolean hasVacationInTime(Instant begin, Instant end) {
        return this.getVacations().stream().anyMatch(x -> x.getStart().compareTo(begin) <= 0 && x.getEnd().compareTo(begin) >= 0 || x.getStart().compareTo(end) <= 0 && x.getEnd().compareTo(end) >= 0 || x.getStart().compareTo(begin) >= 0 && x.getEnd().compareTo(end) <= 0);
    }


}
