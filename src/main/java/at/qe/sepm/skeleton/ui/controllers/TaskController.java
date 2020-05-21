package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.services.RequestService;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.utils.MessagesView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;
import java.util.Date;
import java.util.stream.Collectors;


@ManagedBean
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





    public void sendRequest(RequestEnum status) {
        User u = getCurrentUser();
        User handler1 = userService.getTeamLeader(u.getTeam());
        if (u.equals(handler1)) {
            handler1 = null;
        }
        User handler2 = userService.getDepartmentLeader(u.getDepartment());
        requestService.addRequest(u, handler1, handler2, this.requestedDate, status,"Editing  Tasks");
    }

    /**
     * method to complete the tasks
     * @param query
     * @return
     */

    public List<String> completeTask(String query) {
        String upperQuery = query.toUpperCase();
        return TaskEnum.getAllTasks().stream().filter(a -> a.contains(upperQuery)).collect(Collectors.toList());
    }

    /**
     * method to save a task that was within the current or the last week
     * checks first if the date is in the right time frame
     * when the user is a departmentleader or the admin he can edit every date
     * also checks whether the date that was edited is after today
     * when both checks went through it saves the task
     */

    public void editDateWithinTimeFrame() {
        try {
            taskService.checkIfAfterToday(this.getRequestedDate().toInstant());
        }
        catch(Exception e) {
            MessagesView.errorMessage("Edit Tasks", e.getMessage());
            return;
        }
        if (!this.getCurrentUser().getRoles().contains(UserRole.DEPARTMENTLEADER) && !this.getCurrentUser().getRoles().contains(UserRole.ADMIN)) {
            if(taskService.checkIfEarlierThanTwoWeeks(this.getCurrentUser(), this.getRequestedDate().toInstant())) {
                MessagesView.errorMessage("Edit Tasks", "You need to request this date first");
                return;
            }
        }
        try {
            taskService.saveEditedTask(this.getCurrentUser(), this.getTask(), this.getRequestedDate(), this.getStartHour(), this.getEndHour(), this.getStartMinute(), this.getEndMinute());
        }
        catch (Exception e) {
            MessagesView.errorMessage("Edit Tasks", e.getMessage());
        }
    }
    public void editTasks() {

        try {
            taskService.checkIfAfterToday(this.getRequestedDate().toInstant());
        }
        catch(Exception e) {
            MessagesView.errorMessage("Edit Tasks", e.getMessage());
            return;
        }
        try {
            taskService.checkIfEarlierThanTwoWeeks(this.getCurrentUser(), this.getRequestedDate().toInstant());
        }
        catch (Exception e) {
            sendRequest(RequestEnum.OPEN);
        }
    }

    public void editDate () {
        try {
            taskService.saveEditedTask(this.getCurrentUser(), this.getTask(), this.getRequestedDate(), this.getStartHour(), this.getEndHour(), this.getStartMinute(), this.getEndMinute());
        }
        catch (Exception e) {
            MessagesView.errorMessage("Edit Tasks", e.getMessage());

        }

    }


}
