package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import at.qe.sepm.skeleton.ui.controllers.StatisticsController;
import org.junit.Assert;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.Calendar;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
class StatisticsControllerTest {

    @Autowired
    CurrentUserBean currentUserBean;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void init() {
        StatisticsController statisticsController = new StatisticsController();

        Assert.assertNotNull(statisticsController.getTodayUserModel());
        Assert.assertNull(statisticsController.getChosenDate());


    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void testRebuildChartsUser() {

    }

    @Test
    void rebuildChartsTeam() {
    }

    @Test
    void rebuildChartsDepartment() {
    }

    @Test
    void setDayToBeginning() {
    }
}