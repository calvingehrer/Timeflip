package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.services.TeamService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.SessionInfoBean;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.Assert.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class StatisticsControllerTest {

    private StatisticsController statisticsController;

    @Autowired
    TaskService taskService;

    @Autowired
    TeamService teamService;

    @Autowired
    SessionInfoBean sessionInfoBean;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @Before
    public void initTestClass(){
        statisticsController = new StatisticsController();

        ReflectionTestUtils.setField(statisticsController, "taskService", taskService);
        ReflectionTestUtils.setField(statisticsController, "teamService", teamService);
        ReflectionTestUtils.setField(statisticsController, "sessionInfoBean", sessionInfoBean);
        ReflectionTestUtils.setField(statisticsController, "departmentService", departmentService);
        ReflectionTestUtils.setField(statisticsController, "userService", userService);
    }

    @Test
    @WithMockUser(username = "user2", authorities = {"DEPARTMENTLEADER"})
    public void init() {
        statisticsController.init();
        Assert.assertTrue(statisticsController.getTodayUserModel().isFill());
    }

    @Test
    @WithMockUser(username = "user2", authorities = {"DEPARTMENTLEADER"})
    public void rebuildChartsUser() {
    }

    @Test
    @WithMockUser(username = "user7", authorities = {"TEAMLEADER"})
    public void rebuildChartsTeam() {
    }

    @Test
    @WithMockUser(username = "user2", authorities = {"DEPARTMENTLEADER"})
    public void rebuildChartsDepartment() {
    }

    @Test
    @WithMockUser(username = "user2", authorities = {"DEPARTMENTLEADER"})
    public void rebuildChartsDepartmentTeamBasis() {
    }

    @Test
    @WithMockUser(username = "user2", authorities = {"DEPARTMENTLEADER"})
    public void setDayToBeginning() {
    }
}