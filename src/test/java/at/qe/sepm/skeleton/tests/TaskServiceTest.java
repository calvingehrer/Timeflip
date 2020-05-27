package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.exceptions.TaskException;
import at.qe.sepm.skeleton.model.Task;
import at.qe.sepm.skeleton.model.TaskEnum;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.TaskRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import at.qe.sepm.skeleton.ui.beans.TimeBean;
import nl.jqno.equalsverifier.internal.exceptions.AssertionException;
import org.junit.Assert;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;

import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import javax.validation.constraints.AssertTrue;
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
class TaskServiceTest{


    @Autowired
    TaskService taskService;

    @Autowired
    TaskRepository taskRepository;

    @Autowired
    UserRepository userRepository;

    @MockBean
    CurrentUserBean currentUserBean;

    @Autowired
    TimeBean timeBean;


    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
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
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void getDurationTest() {


        Task task = new Task();

        Calendar startDate = Calendar.getInstance();
        Calendar endDate = Calendar.getInstance();

        startDate.set(2020, 5, 9, 17, 30);
        endDate.set(2020, 5, 9, 18, 50);

        task.setStartTime(startDate.toInstant());
        task.setEndTime(endDate.toInstant());


        Assert.assertEquals("Duration should be 80 Minutes but is not",80, taskService.getDuration(task));
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
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
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void getTeamTasksBetweenDatesTest() {
        HashMap<TaskEnum, Long> topManagement = new HashMap<>();

        topManagement.put(TaskEnum.DOKUMENTATION, 60L);
        topManagement.put(TaskEnum.MEETING, 60L);
        topManagement.put(TaskEnum.SONSTIGES, 60L);
        topManagement.put(TaskEnum.DOKUMENTATION, topManagement.get(TaskEnum.DOKUMENTATION)+60L);
        topManagement.put(TaskEnum.DOKUMENTATION, topManagement.get(TaskEnum.DOKUMENTATION)+60L);
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
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void getDepartmentTasksBetweenDatesTest() {
        HashMap<TaskEnum, Long> accounting = new HashMap<>();

        accounting.put(TaskEnum.MEETING, 60L);
        accounting.put(TaskEnum.IMPLEMENTIERUNG, 80L);
        accounting.put(TaskEnum.KUNDENBESPRECHNUNG, 60L);
        accounting.put(TaskEnum.DOKUMENTATION, 60L);
        accounting.put(TaskEnum.MEETING, accounting.get(TaskEnum.MEETING)+60L);
        accounting.put(TaskEnum.IMPLEMENTIERUNG, accounting.get(TaskEnum.IMPLEMENTIERUNG)+80L);
        accounting.put(TaskEnum.DOKUMENTATION, accounting.get(TaskEnum.DOKUMENTATION)+60L);
        accounting.put(TaskEnum.DOKUMENTATION, accounting.get(TaskEnum.DOKUMENTATION)+60L);

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
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void saveEditedTaskTest() {
        User user = userRepository.findFirstByUsername("admin");
        currentUserBean.setCurrentUser(user);

    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void checkIfEarlierThanTwoWeeks() {

        User user = userRepository.findFirstByUsername("user6");

        Calendar inTime = Calendar.getInstance(timeBean.getUtcTimeZone());
        Calendar earlierDate = Calendar.getInstance(timeBean.getUtcTimeZone());

        inTime.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        inTime.add(Calendar.DATE, -3);

        earlierDate.set(2020, Calendar.MAY, 8);

        Assert.assertFalse("getInstant() - 3 days should not be longer ago than last week but is", taskService.checkIfEarlierThanTwoWeeks(user, inTime.toInstant()));
        Assert.assertTrue("Something wrong with checkIfEarlierThanTwoWeeks Method", taskService.checkIfEarlierThanTwoWeeks(user, earlierDate.toInstant()));

    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void checkIfAfterToday() {
        Calendar afterToday = Calendar.getInstance();

        afterToday.add(Calendar.DATE, 2);

        Assertions.assertThrows(TaskException.class, () -> taskService.checkIfAfterToday(afterToday.toInstant()));
    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void checkTime() {


        Assertions.assertThrows(TaskException.class, () -> taskService.checkTime(25L, 10L, 0L, 50L));
        Assertions.assertThrows(TaskException.class, () -> taskService.checkTime(20L, 30L, 0L, 50L));
        Assertions.assertThrows(TaskException.class, () -> taskService.checkTime(2L, 10L, 100L, 50L));
        Assertions.assertThrows(TaskException.class, () -> taskService.checkTime(20L, 10L, 0L, 67));
        Assertions.assertThrows(TaskException.class, () -> taskService.checkTime(26L, 100L, 100L, 400L));


    }

    @Test
    @WithMockUser(username="admin",roles={"USER","ADMIN"})
    void deleteTask() {
        User user = userRepository.findFirstByUsername("admin");
        currentUserBean.setCurrentUser(user);

        List<Task> taskList = taskRepository.findTasksFromUser(user);

        taskRepository.delete(taskList.get(1));

        List<Task> newTaskList = taskRepository.findTasksFromUser(user);

        Assert.assertNotEquals("Tasks should not be the same after deleting one",taskList, newTaskList);
        taskList.remove(1);
        Assert.assertEquals("After deleting the same Task from the taskList both list should be the same", taskList, newTaskList);
    }
}