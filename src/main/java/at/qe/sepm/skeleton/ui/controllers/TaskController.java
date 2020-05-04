package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Task;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;

public class TaskController {

    private Task task;


    @Autowired
    private TaskService taskService;

    @Autowired
    private SessionInfoBean sessionInfoBean;

    public long duration() {
        return taskService.getDuration(this.task);
    }

    public List<Task> getTasksFromUser() {
        return taskService.getAllTasksFromUser(sessionInfoBean.getCurrentUser());
    }

    public Task getTask() {
        return task;
    }

    public void setTask(Task task) {
        this.task = task;
    }




}
