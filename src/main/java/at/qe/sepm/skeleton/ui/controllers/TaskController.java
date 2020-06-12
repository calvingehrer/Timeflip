package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.services.RequestService;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import at.qe.sepm.skeleton.ui.beans.TimeBean;
import at.qe.sepm.skeleton.utils.MessagesView;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.text.SimpleDateFormat;
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

    @Autowired
    TimeBean timeBean;


    private TaskEnum task;

    private Date requestedDate;

    private int startHour;

    private int startMinute;

    private int endHour;

    private int endMinute;

    private Instant startTime;

    private Instant endTime;

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

    public RequestService getRequestService() {
        return requestService;
    }

    public void setRequestService(RequestService requestService) {
        this.requestService = requestService;
    }

    public TaskService getTaskService() {
        return taskService;
    }

    public void setTaskService(TaskService taskService) {
        this.taskService = taskService;
    }

    public UserService getUserService() {
        return userService;
    }

    public void setUserService(UserService userService) {
        this.userService = userService;
    }

    public CurrentUserBean getCurrentUserBean() {
        return currentUserBean;
    }

    public void setCurrentUserBean(CurrentUserBean currentUserBean) {
        this.currentUserBean = currentUserBean;
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

    /**
     * method is called when the user is requesting a date that is not in the current or the previous week
     * does not check whether the user is a departmentleader because a depaartmentleader does not have to request
     */

    public void sendRequest() {
        User u = currentUserBean.getCurrentUser();
        try {
            setStartAndEndTime();
            String pattern = "MM-dd-yyyy HH:mm";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            simpleDateFormat.setTimeZone(timeBean.getUtcTimeZone());
            String startDate = simpleDateFormat.format(timeBean.instantToDate(this.getStartTime()));
            String endDate = simpleDateFormat.format(timeBean.instantToDate(this.getEndTime()));

            requestService.addTaskRequest(u, this.getStartTime(), this.getEndTime(), this.getTask(),  "Changing time frame from " + startDate + " to "  + endDate + " to  " + this.task.toString());
        }
        catch (Exception e){
            MessagesView.errorMessage("Edit Tasks", e.getMessage());
        }

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
            if(taskService.checkIfEarlierThanTwoWeeks(this.getRequestedDate().toInstant())) {
                sendRequest();
                return;
            }
        }
        trySavingTasks();
    }

    private void trySavingTasks() {
        try {
            setStartAndEndTime();
            taskService.saveEditedTask(currentUserBean.getCurrentUser(), this.getTask(), this.getStartTime(), this.getEndTime());
        }
        catch (Exception e) {
            MessagesView.errorMessage("Edit Tasks", e.getMessage());
        }
    }

    /**
     * check if the requested date is not after the current date
     * and if it is within the allowed time frame it tells the user to
     * just edit it.
     * if the given date is longer than two weeks before the current
     * date it sends a request
     */
    public void checkRequestedDate() {

        try {
            taskService.checkIfAfterToday(this.getRequestedDate().toInstant());
        }
        catch(Exception e) {
            MessagesView.errorMessage("Edit Tasks", e.getMessage());
            return;
        }
        if(taskService.checkIfEarlierThanTwoWeeks(this.getRequestedDate().toInstant())) {
            sendRequest();
            MessagesView.successMessage("Editing Tasks", "Request has been sent");
        }
        else {
            MessagesView.warnMessage("Editing Tasks", "You can edit this date without requesting it");
        }

    }

    /**
     * edit any date
     */

    public void editDate () {
        trySavingTasks();

    }

    public void setStartAndEndTime(){
        try {
            taskService.checkTime(this.getStartHour(), this.getEndHour(), this.getStartMinute(), this.getEndMinute());
            Calendar calendar = Calendar.getInstance(timeBean.getUtcTimeZone());

            calendar.setTime(this.getRequestedDate());
            calendar.set(Calendar.SECOND, 0);
            calendar.set(Calendar.MILLISECOND, 0);

            calendar.set(Calendar.HOUR_OF_DAY, startHour);
            calendar.set(Calendar.MINUTE, startMinute);
            this.setStartTime(calendar.toInstant());

            calendar.set(Calendar.HOUR_OF_DAY, endHour);
            calendar.set(Calendar.MINUTE, endMinute);
            this.setEndTime(calendar.toInstant());
        }
        catch (Exception e) {
            MessagesView.errorMessage("Edit Tasks",e.getMessage());
        }
    }

}
