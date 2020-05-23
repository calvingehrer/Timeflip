package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.services.RequestService;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
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
    private RequestService requestService;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UserService userService;

    @Autowired
    CurrentUserBean currentUserBean;


    private TaskEnum task;

    private Date requestedDate;

    private int startHour;

    private int startMinute;

    private int endHour;

    private int endMinute;

    /**
     * initialize the current user
     */

    @PostConstruct
    public void init() {
        currentUserBean.init();
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


    /**
     * method is called when the user is requesting a date that is not in the current or the previous week
     * does not check whether the user is a departmentleader because a depaartmentleader does not have to request
     */

    public void sendRequest() {
        User u = currentUserBean.getCurrentUser();
        requestService.addTaskRequest(u, this.getRequestedDate(), "Editing  " + this.getRequestedDate());
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
        if (!currentUserBean.getCurrentUser().getRoles().contains(UserRole.DEPARTMENTLEADER) && !currentUserBean.getCurrentUser().getRoles().contains(UserRole.ADMIN)) {
            if(taskService.checkIfEarlierThanTwoWeeks(currentUserBean.getCurrentUser(), this.getRequestedDate().toInstant())) {
                MessagesView.errorMessage("Edit Tasks", "You need to request this date first");
                return;
            }
        }
        try {
            taskService.saveEditedTask(currentUserBean.getCurrentUser(), this.getTask(), this.getRequestedDate(), this.getStartHour(), this.getEndHour(), this.getStartMinute(), this.getEndMinute());
        }
        catch (Exception e) {
            MessagesView.errorMessage("Edit Tasks", e.getMessage());
        }
    }

    /**
     *
     */
    public void checkRequestedDate() {

        try {
            taskService.checkIfAfterToday(this.getRequestedDate().toInstant());
        }
        catch(Exception e) {
            MessagesView.errorMessage("Edit Tasks", e.getMessage());
            return;
        }
        try {
            taskService.checkIfEarlierThanTwoWeeks(currentUserBean.getCurrentUser(), this.getRequestedDate().toInstant());
        }
        catch (Exception e) {
            sendRequest();
        }
    }

    /**
     * edit any date
     */

    public void editDate () {
        try {
            taskService.saveEditedTask(currentUserBean.getCurrentUser(), this.getTask(), this.getRequestedDate(), this.getStartHour(), this.getEndHour(), this.getStartMinute(), this.getEndMinute());
        }
        catch (Exception e) {
            MessagesView.errorMessage("Edit Tasks", e.getMessage());

        }

    }


}
