package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Task;
import at.qe.sepm.skeleton.model.TaskEnum;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.UserRole;
import at.qe.sepm.skeleton.services.RequestService;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.TimeBean;
import at.qe.sepm.skeleton.utils.MessagesView;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.ManagedBean;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.util.Calendar;
import java.util.Date;


@ManagedBean
@Component
@Scope("view")
public class TaskController implements Serializable {

    @Autowired
    TimeBean timeBean;
    @Autowired
    private RequestService requestService;
    @Autowired
    private TaskService taskService;
    @Autowired
    private UserService userService;
    private TaskEnum task;

    private Date requestedDate;

    private int startHour;

    private int startMinute;

    private int endHour;

    private int endMinute;

    private Instant startTime;

    private Instant endTime;


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

    private Instant getStartTime() {
        return startTime;
    }

    private void setStartTime(Instant startTime) {
        this.startTime = startTime;
    }

    private Instant getEndTime() {
        return endTime;
    }

    private void setEndTime(Instant endTime) {
        this.endTime = endTime;
    }

    /**
     * method is called when the user is requesting a date that is not in the current or the previous week
     * does not check whether the user is a departmentleader because a depaartmentleader does not have to request
     */

    private void sendRequest() {
        User u = userService.getAuthenticatedUser();
        try {
            setStartAndEndTime();
            String pattern = "MM-dd-yyyy HH:mm";
            SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
            simpleDateFormat.setTimeZone(timeBean.getUtcTimeZone());
            String startDate = simpleDateFormat.format(timeBean.instantToDate(this.getStartTime()));
            String endDate = simpleDateFormat.format(timeBean.instantToDate(this.getEndTime()));

            requestService.addTaskRequest(u, this.getStartTime(), this.getEndTime(), this.getTask(), "Changing time frame from " + startDate + " to " + endDate + " to  " + this.task.toString());
        } catch (Exception e) {
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

    public void editTasks() {
        try {
            taskService.checkIfAfterToday(this.getRequestedDate().toInstant());
        } catch (Exception e) {
            MessagesView.errorMessage("Edit Tasks", e.getMessage());
            return;
        }
        if (!userService.getAuthenticatedUser().getRoles().contains(UserRole.DEPARTMENTLEADER) && !userService.getAuthenticatedUser().getRoles().contains(UserRole.ADMIN)) {
            if (taskService.checkIfEarlierThanTwoWeeks(this.getRequestedDate().toInstant())) {
                sendRequest();
                return;
            }
        }
        trySavingTasks();
    }

    private void trySavingTasks() {
        try {
            setStartAndEndTime();
            taskService.saveEditedTask(userService.getAuthenticatedUser(), this.getTask(), this.getStartTime(), this.getEndTime());
        } catch (Exception e) {
            MessagesView.errorMessage("Edit Tasks", e.getMessage());
        }
    }


    private void setStartAndEndTime() {
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
        } catch (Exception e) {
            MessagesView.errorMessage("Edit Tasks", e.getMessage());
        }
    }

}
