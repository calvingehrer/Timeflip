package at.qe.sepm.skeleton.services;


import at.qe.sepm.skeleton.exceptions.TaskException;
import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.time.ZoneId;
import java.util.*;

@Service
@Scope("application")
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasksFromUser(User user) {
        return taskRepository.findTasksFromUser(user);
    }

    @PreAuthorize("hasAuthority('TEAMLEADER')")
    public List<Task> getAllTasksFromTeam(Team team){
        return taskRepository.findTasksFromTeam(team);
    }


    public long getDuration(Task task) {
        long duration = Duration.between(task.getStartTime(), task.getEndTime()).toMinutes();
        return duration;
    }

    public HashMap<TaskEnum, Long> getUserTasksBetweenDates(User user, Instant start, Instant end) {
        HashMap<TaskEnum, Long> dailyTasks = new HashMap<>();
        List<Task> tasks = taskRepository.findUserTasksBetweenDates(user, start, end);
        return fillTaskList(dailyTasks, tasks);
    }

    public HashMap<TaskEnum, Long> getTeamTasksBetweenDates(Team team, Instant start, Instant end) {
        HashMap<TaskEnum, Long> dailyTasks = new HashMap<>();
        List<Task> tasks = taskRepository.findTeamTasksBetweenDates(team, start, end);
        return fillTaskList(dailyTasks, tasks);
    }

    public HashMap<TaskEnum, Long> getDepartmentTasksBetweenDates(Department department, Instant start, Instant end) {
        HashMap<TaskEnum, Long> dailyTasks = new HashMap<>();
        List<Task> tasks = taskRepository.findDepartmentTasksBetweenDates(department, start, end);
        return fillTaskList(dailyTasks, tasks);
    }

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

    public TimeZone getUtcTimeZone() {
        return TimeZone.getTimeZone(ZoneId.of("UTC"));
    }

    /**
     * Method to save a new Task, that can only be edited in the web application
     **/
    public void saveEditedTask (User user, TaskEnum task, Date date, int startHour, int endHour, int startMinute, int endMinute) throws TaskException {
        if (task == null) {
            throw new TaskException("Please enter a task");
        }
        checkTime(startHour, endHour, startMinute, endMinute);

        Calendar calendar = Calendar.getInstance(getUtcTimeZone());

        calendar.setTime(date);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        calendar.set(Calendar.HOUR_OF_DAY, startHour);
        calendar.set(Calendar.MINUTE, startMinute);
        Instant startTime = calendar.toInstant();

        calendar.set(Calendar.HOUR_OF_DAY, endHour);
        calendar.set(Calendar.MINUTE, endMinute);
        Instant endTime = calendar.toInstant();

        Task toSave = new Task();

        Task taskBefore = taskRepository.findTaskThatFallsInTimeFrame(user,startTime);
        Task taskAfter = taskRepository.findTaskThatFallsInTimeFrame(user,endTime);

        if (taskBefore != null && taskBefore.equals(taskAfter)) {
            if (taskBefore.getTask() == task) {
                return;
            }
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
            taskBefore = null;
            taskAfter = null;

        }
        if (taskBefore != null) {
            if (taskBefore.getTask() == task) {
                taskBefore.setEndTime(endTime);
                taskRepository.save(taskBefore);
                return;
            }
            taskBefore.setEndTime(startTime);
            taskRepository.save(taskBefore);

        }
        if (taskAfter != null) {
            if (taskAfter.getTask() == task) {
                taskAfter.setStartTime(startTime);
                taskRepository.save(taskAfter);
                return;
            }
            taskAfter.setStartTime(endTime);
            taskRepository.save(taskAfter);
        }

        toSave.setTask(task);
        toSave.setUser(user);
        toSave.setTeam(user.getTeam());
        toSave.setDepartment(user.getDepartment());
        toSave.setStartTime(startTime);
        toSave.setEndTime(endTime);
        toSave.setCreateDate(new Date());

        taskRepository.save(toSave);
    }

    /**
     *
     * @param date
     * @throws TaskException
     */

    public void checkIfEarlierThanTwoWeeks (Instant date) throws TaskException {
        Calendar calendar = Calendar.getInstance();
        calendar.getFirstDayOfWeek();
        calendar.add(Calendar.DATE, -7);

        Instant lastMonday = calendar.toInstant();
        if (date.isBefore(lastMonday)) {
            throw new TaskException("The requested date was earlier than last monday. " +
                    "A request has been send to your team leader");
        }
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

}
