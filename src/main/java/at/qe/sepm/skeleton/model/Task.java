package at.qe.sepm.skeleton.model;

import javax.persistence.*;
import java.util.Set;

/**
 * Entity representing a Task.
 *
 */

@Entity
public class Task {

    @Id
    private String taskID;

    private Integer duration;

    @ElementCollection(targetClass = TaskEnum.class, fetch = FetchType.EAGER)
    @CollectionTable(name = "Task_TaskType")
    @Enumerated(EnumType.STRING)
    private Set<TaskEnum> tasks;

    public String getTaskID() {
        return taskID;
    }

    public void setTaskID(String taskID) {
        this.taskID = taskID;
    }

    public Set<TaskEnum> getTasks() {
        return tasks;
    }

    public void setTasks(Set<TaskEnum> tasks) {
        this.tasks = tasks;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }

    @Override
    public String toString() {
        return "at.qe.sepm.skeleton.model.Task[ id=" + taskID + " ]";
    }
}
