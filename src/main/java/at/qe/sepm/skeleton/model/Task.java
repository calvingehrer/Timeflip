package at.qe.sepm.skeleton.model;

/**
 * Entity representing a Task.
 *
 */


public class Task {

    private Integer taskID;

    private TaskEnum task;

    private Integer duration;

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

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
