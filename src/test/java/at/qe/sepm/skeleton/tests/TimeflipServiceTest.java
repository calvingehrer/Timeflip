package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.*;

import at.qe.sepm.skeleton.services.TimeflipService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import org.junit.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;



@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
class TimeflipServiceTest {

    @Autowired
    TimeflipService timeflipService;

    @Autowired
    UserService userService;

    @MockBean
    CurrentUserBean currentUserBean;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getTimeFlipByAddress() {
        Assert.assertEquals("00:80:41:ae:fd:7e", timeflipService.getTimeFlipByAddress("00:80:41:ae:fd:7e").getMacAddress());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getAllTimeflips() {
        Assert.assertEquals(11, timeflipService.getAllTimeflips().size());
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void addNewTimeflip() {
        Timeflip timeflip = new Timeflip();
        timeflip.setMacAddress("00:12:23:vf:as:7s");
        Assert.assertEquals(11, timeflipService.getAllTimeflips().size());

        timeflipService.addNewTimeflip(timeflip, userService.getUsersWithoutTimeflip().get(0));

        Assert.assertEquals(12, timeflipService.getAllTimeflips().size());

        timeflipService.deleteTimeflip(timeflip);

        Assert.assertEquals(11, timeflipService.getAllTimeflips().size());

    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void deleteTimeflip() {
        Timeflip timeflip = timeflipService.getTimeFlipByAddress("00:80:41:ae:fd:7e");
        Assert.assertEquals(11, timeflipService.getAllTimeflips().size());

        timeflipService.deleteTimeflip(timeflip);
        Assert.assertEquals(10, timeflipService.getAllTimeflips().size());

        timeflipService.addNewTimeflip(timeflip, timeflip.getUser());

        Assert.assertEquals(11, timeflipService.getAllTimeflips().size());


    }

    @Test
    @WithMockUser(username = "employee", authorities = {"EMPLOYEE"})
    void loadTimeflip() {
        Assert.assertEquals("00:80:41:ae:fd:7e",timeflipService.loadTimeflip("00:80:41:ae:fd:7e").getId() );
    }

    /*
    @Test
    @WithMockUser(username = "employee", authorities = {"ADMIN"})
    void getTimeflipOfUser() {

        Timeflip timeflip = timeflipService.getTimeFlipByAddress("00:80:41:ae:fd:7e");

        Assert.assertEquals(timeflip.getMacAddress(), timeflipService.getTimeflipOfUser(timeflip.getUser()).getMacAddress());
    }*/

}