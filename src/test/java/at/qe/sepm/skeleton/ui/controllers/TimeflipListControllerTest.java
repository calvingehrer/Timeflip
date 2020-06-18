package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.services.TimeflipService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.controllers.TimeflipListController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TimeflipListControllerTest {

    TimeflipListController timeflipListController;

    @Autowired
    UserService userService;

    @Autowired
    TimeflipService timeflipService;

    @Before
    public void init() {
        timeflipListController = new TimeflipListController();

        ReflectionTestUtils.setField(timeflipListController, "userService", userService);
        ReflectionTestUtils.setField(timeflipListController, "timeflipService", timeflipService);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getTimeflipOfUser() {

        userService.getAllUsersByUsername("user1");
        Assert.assertEquals("0C:61:CF:C7:CE:20", timeflipListController.getTimeflipOfUser().getMacAddress());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getTimeflips() {

        Assert.assertEquals(11, timeflipListController.getTimeflips().size());

    }
}