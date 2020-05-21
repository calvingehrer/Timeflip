package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Interval;
import at.qe.sepm.skeleton.model.Task;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.TimeZoneBean;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.List;


@Component
@Scope("view")
public class TaskListController implements Serializable {

    @Autowired
    TaskService taskService;
    @Autowired
    UserService userService;
    @Autowired
    TimeZoneBean timeZoneBean;

    private Date startOfTimeRange;

    private Date endOfTimeRange;

    private Date chosenDate;

    private Interval interval = Interval.NONE;

    private User currentUser;

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

    public Date getStartOfTimeRange() {
        return startOfTimeRange;
    }

    public void setStartOfTimeRange(Date startOfTimeRange) {
        this.startOfTimeRange = startOfTimeRange;
    }

    public Date getEndOfTimeRange() {
        return endOfTimeRange;
    }

    public void setEndOfTimeRange(Date endOfTimeRange) {
        this.endOfTimeRange = endOfTimeRange;
    }

    public Date getChosenDate() {
        return chosenDate;
    }

    public void setChosenDate(Date chosenDate) {
        this.chosenDate = chosenDate;
    }

    public Interval getInterval() {
        return interval;
    }

    public void setInterval(Interval interval) {
        this.interval = interval;
    }

    public List<Task> getTasksFromUser() {
        if (this.getInterval()==Interval.NONE) {
            return taskService.getAllTasksBetweenDates(getCurrentUser(), null, null);
        }
        Calendar calendar = Calendar.getInstance(timeZoneBean.getUtcTimeZone());
        calendar.setTime(this.getChosenDate());
        if (this.getInterval()==Interval.DAILY) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            settingTimeRange(calendar, "day",1);
        }
        else if (this.getInterval()==Interval.WEEKLY) {
            calendar.set(Calendar.DATE, Calendar.MONDAY);
            settingTimeRange(calendar, "day", 7);
        }
        else if (this.getInterval()==Interval.MONTHLY) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            settingTimeRange(calendar, "month", 1);
        }
        return taskService.getAllTasksBetweenDates(getCurrentUser(), this.getStartOfTimeRange().toInstant(), this.getEndOfTimeRange().toInstant());
    }

    public void resetFilter() {
        this.setInterval(Interval.NONE);
        this.setStartOfTimeRange(null);
        this.setEndOfTimeRange(null);
    }

    /**
     * method to standardize computation of first and last day of time range
     * @param calendar
     * @param field
     * @param toAdd
     */
    public void settingTimeRange(@NotNull Calendar calendar, @NotNull String field, Integer toAdd) {
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);
        this.setStartOfTimeRange(calendar.getTime());
        if (field.equals("day")) {
            calendar.add(Calendar.DATE, toAdd);
        }
        else if (field.equals("month")) {
            calendar.add(Calendar.MONTH, toAdd);
        }
        this.setEndOfTimeRange(calendar.getTime());
    }



}
