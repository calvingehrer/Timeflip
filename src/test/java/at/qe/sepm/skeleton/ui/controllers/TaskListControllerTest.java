package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.services.*;
import at.qe.sepm.skeleton.ui.beans.TimeBean;
import at.qe.sepm.skeleton.ui.controllers.*;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.*;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TaskListControllerTest {

    TaskListController taskListController;

    @Autowired
    TaskService taskService;

    @Autowired
    UserService userService;

    @Autowired
    TimeBean timeBean;



    @Before
    public void init() {
        taskListController = new TaskListController();

        ReflectionTestUtils.setField(taskListController, "taskService", taskService);
        ReflectionTestUtils.setField(taskListController, "userService", userService);
        ReflectionTestUtils.setField(taskListController, "timeBean", timeBean);
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getSortedTasksFromUser() {
        Assert.assertEquals(2, taskListController.getSortedTasksFromUser().size());
        taskListController.setTaskType("DOKUMENTATION");
        Assert.assertEquals(1, taskListController.getSortedTasksFromUser().size());
        taskListController.setTaskType("AUSZEIT");
        Assert.assertEquals(1, taskListController.getSortedTasksFromUser().size());
        taskListController.setTaskType("KONZEPTION");
        Assert.assertEquals(0, taskListController.getSortedTasksFromUser().size());
        taskListController.setTaskType("DESIGN");
        Assert.assertEquals(0, taskListController.getSortedTasksFromUser().size());
        taskListController.setTaskType("SONSTIGES");
        Assert.assertEquals(0, taskListController.getSortedTasksFromUser().size());
        taskListController.setTaskType("TESTEN");
        Assert.assertEquals(0, taskListController.getSortedTasksFromUser().size());
        taskListController.setTaskType("IMPLEMENTIERUNG");
        Assert.assertEquals(0, taskListController.getSortedTasksFromUser().size());
        taskListController.setTaskType("FEHLERMANAGEMENT");
        Assert.assertEquals(0, taskListController.getSortedTasksFromUser().size());
        taskListController.setTaskType("MEETING");
        Assert.assertEquals(0, taskListController.getSortedTasksFromUser().size());
        taskListController.setTaskType("KUNDENBESPRECHUNG");
        Assert.assertEquals(0, taskListController.getSortedTasksFromUser().size());
        taskListController.setTaskType("FORTBILDUNG");
        Assert.assertEquals(0, taskListController.getSortedTasksFromUser().size());
        taskListController.setTaskType("PROJEKTMANAGEMENT");
        Assert.assertEquals(0, taskListController.getSortedTasksFromUser().size());
        taskListController.setInterval("Monthly");Calendar calendar = Calendar.getInstance();
        calendar.set(2020, 4, 8);
        Date date = calendar.getTime();
        taskListController.setChosenDate(date);
        Assert.assertEquals(2, taskListController.getSortedTasksFromUser().size());
        taskListController.setInterval("Weekly");
        Assert.assertEquals(2, taskListController.getSortedTasksFromUser().size());
        taskListController.setInterval("Daily");
        Assert.assertEquals(1, taskListController.getSortedTasksFromUser().size());
        taskListController.resetFilter();
        Assert.assertEquals(2, taskListController.getSortedTasksFromUser().size());

    }

}