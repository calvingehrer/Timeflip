package at.qe.sepm.skeleton.model;

import org.hibernate.annotations.GenericGenerator;
import org.springframework.data.domain.Persistable;

import javax.persistence.*;
import java.io.Serializable;
import java.time.Instant;
import java.util.Date;
import java.util.Objects;
import java.util.Set;

/**
 * Entity representing a Task.
 *
 */

@Entity
public class Task implements Persistable<Long>, Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="task_id")
    private Long Id;

    @Column(name = "start_time")
    private Instant startTime;

    @Column(name = "end_time")
    private Instant endTime;

    @Enumerated(EnumType.STRING)
    @Column(name = "task")
    private TaskEnum task;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name="user_id")
    private User user;

    @ManyToOne(targetEntity = Team.class, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name="team_id")
    private Team team;

    @ManyToOne(targetEntity = Department.class, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name="department_id")
    private Department department;

    @Temporal(TemporalType.TIMESTAMP)
    private Date createDate;

    @Override
    public Long getId() {
        return this.Id;
    }

    public TaskEnum getTask() {
        return task;
    }

    public void setTask(TaskEnum task) {
        this.task = task;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Instant getStartTime() {
        return startTime;
    }

    public void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    public Instant getEndTime() {
        return endTime;
    }

    public void setEndTime(Instant endTime) {
        this.endTime = endTime;
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

    public Date getCreateDate() {
        return createDate;
    }

    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }

    @Override
    public String toString() {
        return "at.qe.sepm.skeleton.model.Task[ id=" + Id + " ]";
    }

    @Override
    public boolean isNew() { return null == createDate; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Task)) return false;
        Task task1 = (Task) o;
        return Id.equals(task1.Id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(getId());
    }
}
