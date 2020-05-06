package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Task;
import at.qe.sepm.skeleton.model.TaskEnum;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import org.apache.commons.lang.time.DateUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import java.sql.Date;
import java.time.Instant;
import java.util.List;
import java.util.Set;


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

    public Set<TaskEnum> findDateTasks() {

        Instant start = new java.util.Date().toInstant();
        Date end = (Date) new java.util.Date();
        end = (Date) DateUtils.addDays(end, 1);

        return taskService.findDateTasks(sessionInfoBean.getCurrentUser(), start, end.toInstant());
    }


}
