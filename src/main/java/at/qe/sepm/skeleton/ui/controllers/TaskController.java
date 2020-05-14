package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Task;
import at.qe.sepm.skeleton.model.TaskEnum;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.RequestService;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.time.ZoneId;
import java.util.*;
import javax.annotation.PostConstruct;
import java.sql.Date;
import java.time.Instant;
import java.util.stream.Collectors;



@Component
@Scope("view")
public class TaskController implements Serializable  {

    @Autowired
    RequestService requestService;

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    private User currentUser;

    private TaskEnum task;

    private Date requestedDate;

    private int startHour;

    private int startMinute;

    private int endHour;

    private int endMinute;

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

    public int getStartHour() {
        return startHour;
    }

    public void setStartHour(int startHour) {
        this.startHour = startHour;
    }

    public int getStartMinute() {
        return startMinute;
    }

    public void setStartMinute(int startMinute) {
        this.startMinute = startMinute;
    }

    public int getEndHour() {
        return endHour;
    }

    public void setEndHour(int endHour) {
        this.endHour = endHour;
    }



    public int getEndMinute() {
        return endMinute;
    }

    public void setEndMinute(int endMinute) {
        this.endMinute = endMinute;
    }

    public Date getRequestedDate() {
        return requestedDate;
    }

    public void setRequestedDate(Date requestedDate) {
        this.requestedDate = requestedDate;
    }

    public TimeZone getUtcTimeZone() {
        return TimeZone.getTimeZone(ZoneId.of("UTC"));
    }

    public void sendRequest() {
        User u = getCurrentUser();
        User handler = userService.getTeamLeader(u.getTeam());
        requestService.addRequest(u, handler, this.requestedDate, "Editing  Tasks");
    }

    public List<String> completeTask(String query) {
        String upperQuery = query.toUpperCase();
        return TaskEnum.getAllTasks().stream().filter(a -> a.contains(upperQuery)).collect(Collectors.toList());
    }

    public void editDate () {
        Calendar calendar = Calendar.getInstance();
        calendar.setTimeZone(getUtcTimeZone());
        calendar.setTime(this.getRequestedDate());
        calendar.set(Calendar.HOUR_OF_DAY, this.getStartHour());
        calendar.set(Calendar.MINUTE, this.getStartMinute());
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);
        Instant startTime = calendar.toInstant();
        calendar.set(Calendar.HOUR_OF_DAY, this.getEndHour());
        calendar.set(Calendar.MINUTE, this.getEndMinute());
        Instant endTime = calendar.toInstant();
        taskService.saveEditedTask(this.getCurrentUser(), this.task, startTime, endTime);
    }


}
