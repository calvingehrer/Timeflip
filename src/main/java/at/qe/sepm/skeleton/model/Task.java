package at.qe.sepm.skeleton.model;

import javax.persistence.*;
import java.time.Instant;
import java.util.Set;

/**
 * Entity representing a Task.
 *
 */

@Entity
public class Task {

    @Id
    private Integer taskID;

    private Instant startTime;

    private Instant endTime;

    @Enumerated(EnumType.STRING)
    private TaskEnum task;

    @ManyToOne(targetEntity = User.class, fetch = FetchType.EAGER, optional = true)
    @JoinColumn(name="user_id")
    private User user;

    public Integer getTaskID() {
        return taskID;
    }

    public void setTaskID(Integer taskID) {
        this.taskID = taskID;
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

    @Override
    public String toString() {
        return "at.qe.sepm.skeleton.model.Task[ id=" + taskID + " ]";
    }
}
