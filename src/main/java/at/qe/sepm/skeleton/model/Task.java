package at.qe.sepm.skeleton.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;
import java.util.Set;

/**
 * Entity representing a Task.
 *
 */

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "task_id")
    private Integer taskId;

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

    public Integer getTaskId() {
        return taskId;
    }

    public void setTaskId(Integer taskId) {
        this.taskId = taskId;
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

    @Override
    public String toString() {
        return "at.qe.sepm.skeleton.model.Task[ id=" + taskId + " ]";
    }
}
