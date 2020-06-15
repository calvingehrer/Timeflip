package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Interval;
import at.qe.sepm.skeleton.model.Task;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.TimeBean;
import com.sun.istack.NotNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.Serializable;
import java.util.*;


@Component
@Scope("view")
public class TaskListController implements Serializable {

    @Autowired
    TaskService taskService;
    @Autowired
    UserService userService;
    @Autowired
    TimeBean timeBean;

    private Date startOfTimeRange;

    private Date endOfTimeRange;

    private Date chosenDate;

    private String interval = "";

    private String taskType = "";


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

    public String getInterval() {
        return interval;
    }

    public void setInterval(String interval) {
        this.interval = interval;
    }

    public String getTaskType() {
        return taskType;
    }

    public void setTaskType(String taskType) {
        this.taskType = taskType;
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
        User currentUser = userService.getAuthenticatedUser();
        if (this.getInterval().equals("")) {
            return taskService.getAllTasksByType(currentUser,taskType);
        }
        Calendar calendar = Calendar.getInstance(timeBean.getUtcTimeZone());
        if (this.chosenDate.before(new Date())) {
            calendar.setTime(this.chosenDate);

        }
        Date startTimeRange  = new Date();
        Date endTimeRange = new Date();

        switch (this.getInterval()) {
            case "Daily":
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                startTimeRange = calendar.getTime();
                calendar.add(Calendar.DATE, 1);
                endTimeRange = calendar.getTime();
                break;
            case "Weekly":
                calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                startTimeRange = calendar.getTime();
                calendar.add(Calendar.DATE, 7);
                endTimeRange = calendar.getTime();
                break;
            case "Monthly":
                calendar.set(Calendar.DAY_OF_MONTH, 1);
                calendar.set(Calendar.HOUR_OF_DAY, 0);
                calendar.set(Calendar.MINUTE, 0);
                calendar.set(Calendar.SECOND, 0);
                startTimeRange = calendar.getTime();
                calendar.add(Calendar.MONTH, 1);
                endTimeRange = calendar.getTime();
                break;
        }

        return taskService.getAllTasksBetweenDates(currentUser, startTimeRange.toInstant(), endTimeRange.toInstant());
    }

    public List<Task> getSortedTasksFromUser(){
        List<Task> sorted = getTasksFromUser();
        Collections.sort(sorted, (task1, task2) -> task2.getStartTime().compareTo(task1.getStartTime()));
        return sorted;
    }

    /**
     * resets the filter so that all tasks are displayed
     */
    public void resetFilter() {
        this.setInterval("");
        this.setTaskType("");
        this.setStartOfTimeRange(null);
        this.setEndOfTimeRange(null);
    }



}
