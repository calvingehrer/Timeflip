package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.TaskRepository;
import at.qe.sepm.skeleton.repositories.UserRepository;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import at.qe.sepm.skeleton.ui.controllers.StatisticsController;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.primefaces.model.chart.PieChartModel;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Calendar;
import java.util.Date;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
class StatisticsControllerTest {

    @Autowired
    StatisticsController statisticsController;

    @Autowired
    UserRepository userRepository;

    @Autowired
    TaskService taskService;

    @Autowired
    TaskRepository taskRepository;

    @MockBean
    CurrentUserBean currentUserBean;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void init() {
        User user = userRepository.findFirstByUsername("admin");
        currentUserBean.setCurrentUser(user);

        Assert.assertNotNull(statisticsController.getTodayUserModel());
        Assert.assertNull(statisticsController.getChosenDate());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void testRebuildChartsUser() {

        Calendar calendar = Calendar.getInstance();
        calendar.set(2020, Calendar.MAY, 7);

        Date date = Date.from(calendar.toInstant());

        statisticsController.setChosenDate(date);

        PieChartModel oldModel = statisticsController.getTodayUserModel();

        Assert.assertNotNull(oldModel);

        statisticsController.rebuildChartsUser();


    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void rebuildChartsTeam() {
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void rebuildChartsDepartment() {
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void setDayToBeginning() {
    }
}