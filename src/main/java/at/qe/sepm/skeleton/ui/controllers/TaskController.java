package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Task;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.util.List;


@Component
@Scope("view")
public class TaskController {

    @Autowired
    private TaskService taskService;

    @Autowired
    private SessionInfoBean sessionInfoBean;

    public long duration(Task task) {
        return taskService.getDuration(task);
    }

    public List<Task> getTasksFromUser() {
        return taskService.getAllTasksFromUser(sessionInfoBean.getCurrentUser());
    }




}
