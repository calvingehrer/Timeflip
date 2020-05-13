package at.qe.sepm.skeleton.services;


import at.qe.sepm.skeleton.model.Task;
import at.qe.sepm.skeleton.model.TaskEnum;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.TaskRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.Instant;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@Service
@Scope("application")
public class TaskService {

    @Autowired
    private TaskRepository taskRepository;

    public List<Task> getAllTasksFromUser(User user) {
        return taskRepository.findTasksFromUser(user);
    }


    public long getDuration(Task task) {
        long duration = Duration.between(task.getStartTime(), task.getEndTime()).toMinutes();
        return duration;
    }

    public HashMap<TaskEnum, Long> getTasksBetweenDates(User user, Instant start, Instant end) {
        HashMap<TaskEnum, Long> dailyTasks = new HashMap<>();
        List<Task> tasks = taskRepository.findTasksBetweenDates(user, start, end);
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
     **/
    public void saveEditedTask (User user, TaskEnum task, Instant startTime, Instant endTime) {
        Task toSave = new Task();
        Task taskBefore = taskRepository.findTaskThatFallsInTimeFrame(user,startTime);
        Task taskAfter = taskRepository.findTaskThatFallsInTimeFrame(user,endTime);

        if (taskBefore != null && taskBefore.equals(taskAfter)) {
            Task newTask = new Task();
            newTask.setStartTime(endTime);
            newTask.setEndTime(taskAfter.getEndTime());
            newTask.setUser(user);
            newTask.setTeam(user.getTeam());
            newTask.setDepartment(user.getDepartment());
            newTask.setTask(taskAfter.getTask());
            newTask.setCreateDate(new Date());
            taskRepository.save(newTask);
            if (taskBefore.getTask() == task) {
                taskBefore.setEndTime(endTime);
                taskRepository.save(taskBefore);
                return;
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
}
