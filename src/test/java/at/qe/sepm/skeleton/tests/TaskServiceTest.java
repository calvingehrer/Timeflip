package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.exceptions.TaskException;
import at.qe.sepm.skeleton.model.Task;
import at.qe.sepm.skeleton.model.TaskEnum;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.TaskRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.ui.beans.TimeBean;
import at.qe.sepm.skeleton.utils.MessagesView;
import org.junit.Assert;
import org.junit.Before;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.Instant;
import java.util.Calendar;
import java.util.HashMap;
import java.util.List;

/**
 * This class tests the functions of the TaskService class
 */

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
class TaskServiceTest {


    @Autowired
    TaskService taskService;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;


    @Autowired
    TimeBean timeBean;


    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void getAllTasksBetweenDatesTest() {


        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        startDate.set(Calendar.MONTH, Calendar.MAY);
        startDate.set(Calendar.DAY_OF_MONTH, 6);

        endDate.set(Calendar.MONTH, Calendar.MAY);
        endDate.set(Calendar.DAY_OF_MONTH, 9);

        Instant start = startDate.toInstant();
        Instant end = endDate.toInstant();


        User user = userRepository.findFirstByUsername("admin");
        Assert.assertEquals(taskService.getAllTasksBetweenDates(user, start, end), taskRepository.findUserTasksBetweenDates(user, start, end));

    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void getDurationTest() {


        Task task = new Task();

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        startDate.set(2020, Calendar.MAY, 9, 17, 30);
        endDate.set(2020, Calendar.MAY, 9, 18, 50);

        task.setStartTime(startDate.toInstant());
        task.setEndTime(endDate.toInstant());


        Assert.assertEquals("Duration should be 80 Minutes but is not", 80, taskService.getDuration(task));
    }

    @Before
    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void getUserTasksBetweenDatesTest() {
        HashMap<TaskEnum, Long> adminTasks = new HashMap<>();

        adminTasks.put(TaskEnum.DOKUMENTATION, 50L);
        adminTasks.put(TaskEnum.AUSZEIT, 40L);

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        startDate.set(Calendar.MONTH, Calendar.MAY);
        startDate.set(Calendar.DAY_OF_MONTH, 6);

        endDate.set(Calendar.MONTH, Calendar.MAY);
        endDate.set(Calendar.DAY_OF_MONTH, 9);

        Instant start = startDate.toInstant();
        Instant end = endDate.toInstant();

        User user = userRepository.findFirstByUsername("admin");
        Assert.assertEquals(adminTasks, taskService.getUserTasksBetweenDates(user, start, end));

    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void getTeamTasksBetweenDatesTest() {
        HashMap<TaskEnum, Long> topManagement = new HashMap<>();

        topManagement.put(TaskEnum.DOKUMENTATION, 60L);
        topManagement.put(TaskEnum.MEETING, 60L);
        topManagement.put(TaskEnum.SONSTIGES, 60L);
        topManagement.put(TaskEnum.DOKUMENTATION, topManagement.get(TaskEnum.DOKUMENTATION) + 60L);
        topManagement.put(TaskEnum.DOKUMENTATION, topManagement.get(TaskEnum.DOKUMENTATION) + 60L);
        topManagement.put(TaskEnum.IMPLEMENTIERUNG, 50L);


        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        startDate.set(Calendar.MONTH, Calendar.MAY);
        startDate.set(Calendar.DAY_OF_MONTH, 6);

        endDate.set(Calendar.MONTH, Calendar.MAY);
        endDate.set(Calendar.DAY_OF_MONTH, 9);

        Instant start = startDate.toInstant();
        Instant end = endDate.toInstant();

        User user = userRepository.findFirstByUsername("user6");
        Assert.assertEquals(topManagement, taskService.getTeamTasksBetweenDates(user.getTeam(), start, end));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void getDepartmentTasksBetweenDatesTest() {
        HashMap<TaskEnum, Long> accounting = new HashMap<>();

        accounting.put(TaskEnum.MEETING, 60L);
        accounting.put(TaskEnum.IMPLEMENTIERUNG, 80L);
        accounting.put(TaskEnum.KUNDENBESPRECHUNG, 60L);
        accounting.put(TaskEnum.DOKUMENTATION, 60L);
        accounting.put(TaskEnum.MEETING, accounting.get(TaskEnum.MEETING) + 60L);
        accounting.put(TaskEnum.IMPLEMENTIERUNG, accounting.get(TaskEnum.IMPLEMENTIERUNG) + 80L);
        accounting.put(TaskEnum.DOKUMENTATION, accounting.get(TaskEnum.DOKUMENTATION) + 60L);
        accounting.put(TaskEnum.DOKUMENTATION, accounting.get(TaskEnum.DOKUMENTATION) + 60L);

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        startDate.set(Calendar.MONTH, Calendar.MAY);
        startDate.set(Calendar.DAY_OF_MONTH, 6);

        endDate.set(Calendar.MONTH, Calendar.MAY);
        endDate.set(Calendar.DAY_OF_MONTH, 9);

        Instant start = startDate.toInstant();
        Instant end = endDate.toInstant();

        User user = userRepository.findFirstByUsername("user2");
        Assert.assertEquals(accounting, taskService.getDepartmentTasksBetweenDates(user.getDepartment(), start, end));
    }


    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void saveEditedTaskTest() {
        User user = userRepository.findFirstByUsername("admin");

        int startHour = 8, endHour = 8, startMinute = 0, endMinute = 50;

        Calendar date = Calendar.getInstance(timeBean.getUtcTimeZone());
        date.set(2020, Calendar.MAY, 7);
        date.set(Calendar.SECOND, 0);
        date.set(Calendar.HOUR_OF_DAY, startHour);
        date.set(Calendar.MILLISECOND, 0);
        date.set(Calendar.MINUTE, startMinute);
        Instant startTime = date.toInstant();

        date.set(Calendar.HOUR_OF_DAY, endHour);
        date.set(Calendar.MINUTE, endMinute);
        Instant endTime = date.toInstant();

        Assertions.assertThrows(TaskException.class, () -> taskService.saveEditedTask(user, null, startTime, endTime));


        List<Task> adminOldTasks = taskRepository.findTasksFromUser(user);

        Task oldTask = adminOldTasks.get(0);

        TaskEnum newTaskType = TaskEnum.IMPLEMENTIERUNG;

        try {
            taskService.saveEditedTask(user, newTaskType, startTime, endTime);
        } catch (Exception e) {
            MessagesView.errorMessage("Test editing Tasks", e.getMessage());
        }

        List<Task> adminNewTasks = taskRepository.findTasksFromUser(user);
        Task newTask = adminNewTasks.get(0);


        Assert.assertNotEquals("Task should be IMPLEMENTIERUNG for newTask and DOKUMENTATION for oldTask", newTask.getTask(), oldTask.getTask());

        oldTask.setTask(newTaskType);

        Assert.assertEquals("Task should be equal now", oldTask, newTask);

        //back to before else getUserTasksBetweenDatesTest() fails
        try {
            taskService.saveEditedTask(user, TaskEnum.DOKUMENTATION, startTime, endTime);
        } catch (Exception e) {
            MessagesView.errorMessage("Test editing Tasks", e.getMessage());
        }

    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void checkIfEarlierThanTwoWeeksTest() {

        Calendar inTime = Calendar.getInstance(timeBean.getUtcTimeZone());
        Calendar earlierDate = Calendar.getInstance(timeBean.getUtcTimeZone());

        inTime.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        inTime.add(Calendar.DATE, -3);

        earlierDate.set(2020, Calendar.MAY, 8);

        Assert.assertFalse("getInstant() - 3 days should not be longer ago than last week but is", taskService.checkIfEarlierThanTwoWeeks(inTime.toInstant()));
        Assert.assertTrue("Something wrong with checkIfEarlierThanTwoWeeks Method", taskService.checkIfEarlierThanTwoWeeks(earlierDate.toInstant()));

    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void checkIfAfterTodayTest() {
        Calendar afterToday = Calendar.getInstance();

        afterToday.add(Calendar.DATE, 2);

        Assertions.assertThrows(TaskException.class, () -> taskService.checkIfAfterToday(afterToday.toInstant()));
    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void checkTimeTest() {


        Assertions.assertThrows(TaskException.class, () -> taskService.checkTime(25L, 10L, 0L, 50L));
        Assertions.assertThrows(TaskException.class, () -> taskService.checkTime(20L, 30L, 0L, 50L));
        Assertions.assertThrows(TaskException.class, () -> taskService.checkTime(2L, 10L, 100L, 50L));
        Assertions.assertThrows(TaskException.class, () -> taskService.checkTime(20L, 10L, 0L, 67));
        Assertions.assertThrows(TaskException.class, () -> taskService.checkTime(26L, 100L, 100L, 400L));


    }

    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void deleteTaskTest() {
        User user = userRepository.findFirstByUsername("admin");

        List<Task> taskList = taskRepository.findTasksFromUser(user);

        taskService.deleteTask(taskList.get(1));

        List<Task> newTaskList = taskRepository.findTasksFromUser(user);

        Assert.assertNotEquals("Tasks should not be the same after deleting one", taskList, newTaskList);
        taskList.remove(1);
        Assert.assertEquals("After deleting the same Task from the taskList both list should be the same", taskList, newTaskList);
    }


    @Test
    @WithMockUser(username = "admin", roles = {"USER", "ADMIN"})
    void deleteTaskOfUser() {
        User user = userRepository.findFirstByUsername("user23");

        List<Task> taskList = taskRepository.findTasksFromUser(user);
        Assert.assertTrue("user23 should have at least 2 tasks", !taskList.isEmpty());

        taskService.deleteTasksOfUser(user);

        List<Task> newTaskList = taskRepository.findTasksFromUser(user);
        Assert.assertTrue("user23 should have no tasks anymore", newTaskList.isEmpty());
    }
}