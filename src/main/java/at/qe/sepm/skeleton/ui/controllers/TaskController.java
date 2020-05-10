package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import at.qe.sepm.skeleton.utils.TimeConverter;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.time.ZoneId;
import java.util.*;
import java.time.Instant;
import java.util.stream.Collectors;


@Component
@Scope("view")
public class TaskController {

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    private User currentUser;

    private TaskEnum task;

    private Date startTime;

    private Date endTime;

    @PostConstruct
    public void init() {
        this.setCurrentUser(userService.getAuthenticatedUser());
    }

    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }

    public long duration(Task task) {
        return taskService.getDuration(task);
    }

    public List<Task> getTasksFromUser() {
        return taskService.getAllTasksFromUser(getCurrentUser());
    }

    public TaskEnum getTask() {
        return task;
    }

    public void setTask(TaskEnum task) {
        this.task = task;
    }

    public Date getStartTime() {
        return startTime;
    }

    public void setStartTime(Date startTime) {
        this.startTime = startTime;
    }

    public Date getEndTime() {
        return endTime;
    }

    public void setEndTime(Date endTime) {
        this.endTime = endTime;
    }

    public TimeZone getUtcTimeZone() {
        return TimeZone.getTimeZone(ZoneId.of("UTC"));
    }


    public void editTasks() {
        taskService.saveTask(this.getCurrentUser(), task, this.startTime.toInstant(), this.endTime.toInstant());
    }

    public List<String> completeTask(String query) {
        String upperQuery = query.toUpperCase();
        return TaskEnum.getAllTasks().stream().filter(a -> a.contains(upperQuery)).collect(Collectors.toList());
    }

}
