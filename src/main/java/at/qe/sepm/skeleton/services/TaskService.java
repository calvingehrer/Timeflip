package at.qe.sepm.skeleton.services;


import at.qe.sepm.skeleton.exceptions.TaskException;
import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.repositories.TaskRepository;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import at.qe.sepm.skeleton.ui.beans.TimeBean;
import at.qe.sepm.skeleton.utils.auditlog.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.time.Duration;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Scope("application")
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;
    @Autowired
    TimeBean timeBean;
    @Autowired
    CurrentUserBean currentUserBean;
    @Autowired
    private Logger<String, User> logger;

    /**
     * A Function to get the current user
     */

    @PostConstruct
    public void init() {
        currentUserBean.init();
    }

    public List<Task> getAllTasksBetweenDates(User user, Instant start, Instant end) {
        if (start == null || end == null) {
            return taskRepository.findTasksFromUser(user);
        }
        return taskRepository.findUserTasksBetweenDates(user, start, end);
    }
    /**
     *
     * @param task
     * @return duration of the task in minutes
     */

    public long getDuration(Task task) {
        long duration = Duration.between(task.getStartTime(), task.getEndTime()).toMinutes();
        return duration;
    }

    /**
     *
     * @param user
     * @param start
     * @param end
     * @return Map of all user task types between two Dates with duration of each task type
     */

    public HashMap<TaskEnum, Long> getUserTasksBetweenDates(User user, Instant start, Instant end) {

        HashMap<TaskEnum, Long> dailyTasks = new HashMap<>();
        List<Task> tasks = taskRepository.findUserTasksBetweenDates(user, start, end);
        return fillTaskList(dailyTasks, tasks);
    }

    /**
     *
     * @param team
     * @param start
     * @param end
     * @return Map of all team task types between two Dates with duration of each task type
     */

    public HashMap<TaskEnum, Long> getTeamTasksBetweenDates(Team team, Instant start, Instant end) {
        HashMap<TaskEnum, Long> dailyTasks = new HashMap<>();
         List<Task> tasks = taskRepository.findTeamTasksBetweenDates(team, start, end);
        return fillTaskList(dailyTasks, tasks);
    }

    /**
     *
     * @param department
     * @param start
     * @param end
     * @return Map of all department task types between two Dates with duration of each task type
     */

    public HashMap<TaskEnum, Long> getDepartmentTasksBetweenDates(Department department, Instant start, Instant end) {
        HashMap<TaskEnum, Long> dailyTasks = new HashMap<>();
        List<Task> tasks = taskRepository.findDepartmentTasksBetweenDates(department, start, end);
        return fillTaskList(dailyTasks, tasks);
    }

    /**
     * fills the list of task types with duration
     * @param dailyTasks
     * @param tasks
     * @return filled list
     */

    private HashMap<TaskEnum, Long> fillTaskList(HashMap<TaskEnum, Long> dailyTasks, List<Task> tasks) {
        tasks.forEach(t -> {
                    long d = getDuration(t);
                    if (dailyTasks.containsKey(t.getTask())) {
                        dailyTasks.put(t.getTask(), dailyTasks.get(t.getTask()) + d);
                    } else {
                        dailyTasks.put(t.getTask(), d);
                    }
                }
        );

        return dailyTasks;
    }

    /**
     * Method to save a new Task, that can only be edited in the web application
     * It checks certain possibilities:
     * 1. If a task and a valid time has been entered
     * 2. If the task before is the same as the task after and has to be splitted
     * 3. If the task before or the task after has the same task type as the given task
     * 4. If the task before or the task after falls into the exact same time frame it only changes the task type
     **/
    public void saveEditedTask (User user, TaskEnum task, Instant startTime, Instant endTime) throws TaskException {
        if (task == null) {
            throw new TaskException("Please enter a task");
        }




        Task toSave = new Task();

        Task taskBefore = taskRepository.findTaskThatFallsInTimeFrame(user,startTime);
        Task taskAfter = taskRepository.findTaskThatFallsInTimeFrame(user,endTime);

        if (taskBefore != null && taskBefore.equals(taskAfter)) {
            if (taskBefore.getTask() == task) {
                return;
            }
            if (Duration.between(taskBefore.getStartTime(),startTime).toMinutes() == 0
                    && Duration.between(taskBefore.getEndTime(),endTime).toMinutes() == 0) {
                taskBefore.setTask(task);
                taskRepository.save(taskBefore);
                return;
            }
            else {
                Task newTask = new Task();
                newTask.setStartTime(endTime);
                newTask.setEndTime(taskAfter.getEndTime());
                newTask.setUser(user);
                newTask.setTeam(user.getTeam());
                newTask.setDepartment(user.getDepartment());
                newTask.setTask(taskAfter.getTask());
                newTask.setCreateDate(new Date());
                taskRepository.save(newTask);
                taskBefore.setEndTime(startTime);
                taskRepository.save(taskBefore);
            }
            taskBefore = null;
            taskAfter = null;

        }
        if (taskBefore != null) {

                if (taskBefore.getTask() == task) {
                    taskBefore.setEndTime(endTime);

                    taskRepository.save(taskBefore);
                    return;
                }

                if (Duration.between(taskBefore.getStartTime(), startTime).toMinutes() == 0){
                    taskBefore.setTask(task);
                    taskBefore.setEndTime(endTime);
                    taskRepository.save(taskBefore);
                    return;
                }
                else {
                    taskBefore.setEndTime(startTime);
                    taskRepository.save(taskBefore);
                }


        }
        if (taskAfter != null) {

                if (taskAfter.getTask() == task) {
                    taskAfter.setStartTime(startTime);
                    taskRepository.save(taskAfter);
                    return;
                }
                if (Duration.between(taskAfter.getStartTime(), endTime).toMinutes() == 0) {
                    taskAfter.setTask(task);
                    taskAfter.setStartTime(startTime);
                    taskRepository.save(taskAfter);
                    return;
                }
                else {
                    taskAfter.setStartTime(endTime);
                    taskRepository.save(taskAfter);
                }


        }

        toSave.setTask(task);
        toSave.setUser(user);
        toSave.setTeam(user.getTeam());
        toSave.setDepartment(user.getDepartment());
        toSave.setStartTime(startTime);
        toSave.setEndTime(endTime);
        toSave.setCreateDate(new Date());

        taskRepository.save(toSave);

        logger.logUpdate(task.toString(), currentUserBean.getCurrentUser());
    }

    /**
     * check if something is earlier than the current or the last week
     * @param date
     * @throws TaskException
     */

    public boolean checkIfEarlierThanTwoWeeks (Instant date) {
        Calendar calendar = Calendar.getInstance(timeBean.getUtcTimeZone());
        calendar.add(Calendar.DATE, -14);
        calendar.set(Calendar.HOUR_OF_DAY,0);
        calendar.set(Calendar.MINUTE,0);
        calendar.set(Calendar.SECOND,0);

        Instant twoWeeks = calendar.toInstant();
        return date.isBefore(twoWeeks);
    }

    /**
     * checks whether the requested date was after today
     * Throws an exception when it is
     * @param date
     * @throws TaskException
     */

    public void checkIfAfterToday(Instant date) throws TaskException {
        Calendar calendar = Calendar.getInstance();
        Instant today = calendar.toInstant();
        if (today.isBefore(date)) {
            throw new TaskException("You can not edit a date that is after today");
        }
    }

    /**
     * checks whether the hours and the minutes are correct
     * @param startHour
     * @param endHour
     * @param startMinute
     * @param endMinute
     * @throws TaskException
     */

    public void checkTime(long startHour, long endHour, long startMinute, long endMinute) throws TaskException {
        if (startHour < 0 || startHour > 24 || endHour < 0 || endHour > 24) {
            throw new TaskException("A day has 24 hours");
        }
        if (startMinute < 0 || startMinute > 60 || endMinute < 0 || endMinute > 60) {
            throw new TaskException("An hour has 60 minutes");
        }
    }

    /**
     * Method to delete Tasks
     * @param task
     */

    public void deleteTask(Task task) {
        task.setUser(null);
        task.setTeam(null);
        task.setDepartment(null);
        taskRepository.delete(task);
        logger.logDeletion(task.getTask().toString(), currentUserBean.getCurrentUser());
    }

    public void deleteTasksOfUser (User user) {
        for (Task t: taskRepository.findTasksFromUser(user)) {
            deleteTask(t);
        }
    }



}
