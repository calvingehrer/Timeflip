package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.TeamService;
import at.qe.sepm.skeleton.services.TimeflipService;
import at.qe.sepm.skeleton.ui.controllers.DepartmentDetailController;
import at.qe.sepm.skeleton.ui.controllers.TaskListController;
import at.qe.sepm.skeleton.ui.controllers.TimeflipDetailController;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TimeflipDetailControllerTest {


    TimeflipDetailController timeflipDetailController;

    @Autowired
    TimeflipService timeflipService;


    @Before
    public void init() throws IOException {

        timeflipDetailController = new TimeflipDetailController();


        ReflectionTestUtils.setField(timeflipDetailController, "timeflipService", timeflipService);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void isDesign() {
        Timeflip timeflip = new Timeflip();
        timeflipDetailController.setTimeflip(timeflip);
        Assert.assertFalse(timeflipDetailController.isDesign());
        timeflipDetailController.setDesign(true);
        Assert.assertTrue(timeflipDetailController.isDesign());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void isImplementierung() {
        Timeflip timeflip = new Timeflip();
        timeflipDetailController.setTimeflip(timeflip);
        Assert.assertFalse(timeflipDetailController.isImplementierung());
        timeflipDetailController.setImplementierung(true);
        Assert.assertTrue(timeflipDetailController.isImplementierung());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void isTesten() {
        Timeflip timeflip = new Timeflip();
        timeflipDetailController.setTimeflip(timeflip);
        Assert.assertFalse(timeflipDetailController.isTesten());
        timeflipDetailController.setTesten(true);
        Assert.assertTrue(timeflipDetailController.isTesten());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void isDokumentation() {
        Timeflip timeflip = new Timeflip();
        timeflipDetailController.setTimeflip(timeflip);
        Assert.assertFalse(timeflipDetailController.isDokumentation());
        timeflipDetailController.setDokumentation(true);
        Assert.assertTrue(timeflipDetailController.isDokumentation());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void isFehlermanagement() {
        Timeflip timeflip = new Timeflip();
        timeflipDetailController.setTimeflip(timeflip);
        Assert.assertFalse(timeflipDetailController.isFehlermanagement());
        timeflipDetailController.setFehlermanagement(true);
        Assert.assertTrue(timeflipDetailController.isFehlermanagement());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void isMeeting() {
        Timeflip timeflip = new Timeflip();
        timeflipDetailController.setTimeflip(timeflip);
        Assert.assertFalse(timeflipDetailController.isMeeting());
        timeflipDetailController.setMeeting(true);
        Assert.assertTrue(timeflipDetailController.isMeeting());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void isKundenbesprechung() {
        Timeflip timeflip = new Timeflip();
        timeflipDetailController.setTimeflip(timeflip);
        Assert.assertFalse(timeflipDetailController.isKundenbesprechung());
        timeflipDetailController.setKundenbesprechung(true);
        Assert.assertTrue(timeflipDetailController.isKundenbesprechung());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void isFortbildung() {
        Timeflip timeflip = new Timeflip();
        timeflipDetailController.setTimeflip(timeflip);
        Assert.assertFalse(timeflipDetailController.isFortbildung());
        timeflipDetailController.setFortbildung(true);
        Assert.assertTrue(timeflipDetailController.isFortbildung());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void isProjektmanagement() {
        Timeflip timeflip = new Timeflip();
        timeflipDetailController.setTimeflip(timeflip);
        Assert.assertFalse(timeflipDetailController.isProjektmanagement());
        timeflipDetailController.setProjektmanagement(true);
        Assert.assertTrue(timeflipDetailController.isProjektmanagement());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void isSonstiges() {
        Timeflip timeflip = new Timeflip();
        timeflipDetailController.setTimeflip(timeflip);
        Assert.assertFalse(timeflipDetailController.isSonstiges());
        timeflipDetailController.setSonstiges(true);
        Assert.assertTrue(timeflipDetailController.isSonstiges());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void isAuszeit() {
        Timeflip timeflip = new Timeflip();
        timeflipDetailController.setTimeflip(timeflip);
        Assert.assertFalse(timeflipDetailController.isAuszeit());
        timeflipDetailController.setAuszeit(true);
        Assert.assertTrue(timeflipDetailController.isAuszeit());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void isKonzeption() {
        Timeflip timeflip = new Timeflip();
        timeflipDetailController.setTimeflip(timeflip);
        Assert.assertFalse(timeflipDetailController.isKonzeption());
        timeflipDetailController.setKonzeption(true);
        Assert.assertTrue(timeflipDetailController.isKonzeption());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getTasks() {
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void doSaveTimeflip() {
       Timeflip timeflip = timeflipService.loadTimeflip("00:80:41:ae:fd:7e");
       timeflipDetailController.setTimeflip(timeflip);
       Assert.assertNull(timeflipDetailController.getUser());
       User user = new User();
       user.setUsername("testUser");
       timeflipDetailController.setUser(user);
       timeflipDetailController.doSaveTimeflip();
       Assert.assertNotNull(timeflipDetailController.getUser());
       timeflipDetailController.setUser(null);
       timeflipDetailController.doSaveTimeflip();

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void doDeleteTimeflip() {
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void addTasks() {
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void addUser() {
    }
}