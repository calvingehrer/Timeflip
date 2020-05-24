package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Interval;
import at.qe.sepm.skeleton.model.Task;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import at.qe.sepm.skeleton.ui.beans.TimeBean;
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
    TimeBean timeBean;
    @Autowired
    CurrentUserBean currentUserBean;

    private Date startOfTimeRange;

    private Date endOfTimeRange;

    private Date chosenDate;

    private Interval interval = Interval.NONE;

    @PostConstruct
    public void init() {
        currentUserBean.init();
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

    /**
     * filters tasks by interval and date
     * if interval is none as it is from default every task ever documented is displayed
     * if interval is daily tasks from the chosen date are displayed
     * if interval is weekly tasks from the week of the chosen date are displayed
     * if interval is monthly tasks from the month of the chosen date are displayed
     * if the chosen date is after the current date the current tasks in the chosen interval are displayed
     * @return all tasks within a certain time frame
     */

    public List<Task> getTasksFromUser() {
        if (this.getInterval()==Interval.NONE) {
            return taskService.getAllTasksBetweenDates(currentUserBean.getCurrentUser(), null, null);
        }
        Calendar calendar = Calendar.getInstance(timeBean.getUtcTimeZone());
        if (!this.getChosenDate().after(new Date())) {
            calendar.setTime(this.getChosenDate());
        }

        if (this.getInterval()==Interval.DAILY) {
            calendar.set(Calendar.HOUR_OF_DAY, 0);
            settingTimeRange(calendar, "day",1);
        }
        else if (this.getInterval()==Interval.WEEKLY) {
            calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
            settingTimeRange(calendar, "day", 7);
        }
        else if (this.getInterval()==Interval.MONTHLY) {
            calendar.set(Calendar.DAY_OF_MONTH, 1);
            settingTimeRange(calendar, "month", 1);
        }
        return taskService.getAllTasksBetweenDates(currentUserBean.getCurrentUser(), this.getStartOfTimeRange().toInstant(), this.getEndOfTimeRange().toInstant());
    }

    /**
     * resets the filter so that all tasks are displayed
     */

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
