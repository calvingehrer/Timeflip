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
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.Calendar;
import java.util.Date;

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
    public void initTestClass() {
        statisticsController = new StatisticsController();

        ReflectionTestUtils.setField(statisticsController, "taskService", taskService);
        ReflectionTestUtils.setField(statisticsController, "teamService", teamService);
        ReflectionTestUtils.setField(statisticsController, "sessionInfoBean", sessionInfoBean);
        ReflectionTestUtils.setField(statisticsController, "departmentService", departmentService);
        ReflectionTestUtils.setField(statisticsController, "userService", userService);
    }

    @Test
    @WithMockUser(username = "user2", authorities = {"DEPARTMENTLEADER"})
    public void initDepartmentLeader() {
        statisticsController.init();
        Assert.assertTrue(statisticsController.getTodayUserModel().isFill());
        Assert.assertFalse(statisticsController.getTeamsOfCurrentDepartment().isEmpty());
        Assert.assertTrue(statisticsController.getMonthDepartmentModel().isFill());
    }

    @Test
    @WithMockUser(username = "user10", authorities = {"TEAMLEADER"})
    public void initTeamLeader() {
        statisticsController.init();
        Assert.assertTrue(statisticsController.getTodayUserModel().isFill());
        Assert.assertTrue(statisticsController.getWeekTeamModel().isFill());
    }

    @Test
    @WithMockUser(username = "user30", authorities = {"EMPLOYEE"})
    public void initEmployee() {
        statisticsController.init();
        Assert.assertTrue(statisticsController.getTodayUserModel().isFill());
        Assert.assertTrue(statisticsController.getWeekUserModel().isFill());
        Assert.assertTrue(statisticsController.getMonthUserModel().isFill());
    }

    @Test
    @WithMockUser(username = "user2", authorities = {"DEPARTMENTLEADER"})
    public void rebuildChartsUser() {
        Calendar calendar = Calendar.getInstance();
        PieChartModel oldTodayModel = statisticsController.getTodayUserModel();
        PieChartModel oldWeekModel = statisticsController.getWeekUserModel();
        PieChartModel oldMonthModel = statisticsController.getMonthUserModel();
        BarChartModel oldBarChartModel = statisticsController.getMonthBarUserModel();

        calendar.set(2019, Calendar.MAY, 6);
        Date date = Date.from(calendar.toInstant());
        statisticsController.setChosenDate(date);
        statisticsController.rebuildChartsUser();

        Assert.assertNotEquals(oldMonthModel, statisticsController.getMonthUserModel());
        Assert.assertNotEquals(oldWeekModel, statisticsController.getWeekUserModel());
        Assert.assertNotEquals(oldTodayModel, statisticsController.getTodayUserModel());
        Assert.assertNotEquals(oldBarChartModel, statisticsController.getMonthBarUserModel());

    }

    @Test
    @WithMockUser(username = "user7", authorities = {"TEAMLEADER"})
    public void rebuildChartsTeam() {
        PieChartModel oldWeekModel = statisticsController.getWeekTeamModel();
        PieChartModel oldMonthModel = statisticsController.getMonthTeamModel();
        BarChartModel oldBarChartModel = statisticsController.getMonthBarTeamModel();


        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.MAY, 6);
        Date date = Date.from(calendar.toInstant());
        statisticsController.setChosenDate(date);
        statisticsController.rebuildChartsTeam();

        Assert.assertNotEquals(oldWeekModel, statisticsController.getWeekTeamModel());
        Assert.assertNotEquals(oldMonthModel, statisticsController.getMonthTeamModel());
        Assert.assertNotEquals(oldBarChartModel, statisticsController.getMonthBarTeamModel());
    }

    @Test
    @WithMockUser(username = "user2", authorities = {"DEPARTMENTLEADER"})
    public void rebuildChartsDepartment() {
        PieChartModel oldMonthModel = statisticsController.getMonthDepartmentModel();
        BarChartModel oldBarChartModel = statisticsController.getMonthBarDepartmentModel();

        Assert.assertEquals(oldMonthModel, statisticsController.getMonthDepartmentModel());

        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.MAY, 6);
        Date date = Date.from(calendar.toInstant());
        statisticsController.setChosenDate(date);
        statisticsController.rebuildChartsDepartment();

        Assert.assertNotEquals(oldMonthModel, statisticsController.getMonthDepartmentModel());
        Assert.assertNotEquals(oldBarChartModel, statisticsController.getMonthBarDepartmentModel());
    }

    @Test
    @WithMockUser(username = "user2", authorities = {"DEPARTMENTLEADER"})
    public void rebuildChartsDepartmentTeamBasis() {
        PieChartModel oldMonthModel = statisticsController.getMonthTeamModel();
        BarChartModel oldBarChartModel = statisticsController.getMonthBarTeamModel();

        statisticsController.init();
        Calendar calendar = Calendar.getInstance();
        calendar.set(2019, Calendar.MAY, 6);
        Date date = Date.from(calendar.toInstant());
        statisticsController.setChosenDate(date);
        statisticsController.rebuildChartsDepartmentTeamBasis();

        Assert.assertNotEquals(oldMonthModel, statisticsController.getMonthTeamModel());
        Assert.assertNotEquals(oldBarChartModel, statisticsController.getMonthBarTeamModel());
    }

    @Test
    @WithMockUser(username = "user2", authorities = {"DEPARTMENTLEADER"})
    public void setDayToBeginning() {
        Calendar calendar = Calendar.getInstance();
        StatisticsController.setDayToBeginning(calendar);

        Calendar testCalendar = Calendar.getInstance();
        testCalendar.set(Calendar.MILLISECOND, 0);
        testCalendar.set(Calendar.SECOND, 0);
        testCalendar.set(Calendar.MINUTE, 0);
        testCalendar.set(Calendar.HOUR_OF_DAY, 0);

        Assert.assertEquals(calendar, testCalendar);
    }
}