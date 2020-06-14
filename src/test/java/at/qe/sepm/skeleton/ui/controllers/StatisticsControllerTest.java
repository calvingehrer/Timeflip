package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.services.UserService;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.*;

public class StatisticsControllerTest {

    private StatisticsController statisticsController;

    @Autowired
    TaskService taskService;

    @Autowired
    private UserService userService;

    @Before
    public void initTestClass(){
        statisticsController = new StatisticsController();

        Authentication authentication = Mockito.mock(Authentication.class);

        SecurityContext securityContext = Mockito.mock(SecurityContext.class);
        Mockito.when(securityContext.getAuthentication()).thenReturn(authentication);
        SecurityContextHolder.setContext(securityContext);

        ReflectionTestUtils.setField(statisticsController, "taskService", taskService);
        ReflectionTestUtils.setField(statisticsController, "userService", userService);
    }

    @Test
    @WithMockUser(username = "user2", authorities = {"DEPARTMENTLEADER"})
    public void init() {

    }

    @Test
    @WithMockUser(username = "user2", authorities = {"DEPARTMENTLEADER"})
    public void rebuildChartsUser() {
    }

    @Test
    @WithMockUser(username = "user2", authorities = {"DEPARTMENTLEADER"})
    public void rebuildChartsTeam() {
    }

    @Test
    @WithMockUser(username = "user2", authorities = {"DEPARTMENTLEADER"})
    public void rebuildChartsDepartment() {
    }

    @Test
    @WithMockUser(username = "user2", authorities = {"DEPARTMENTLEADER"})
    public void setDayToBeginning() {
    }
}