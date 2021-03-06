package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.controllers.UserListController;
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
public class UserListControllerTest {

    UserListController userListController;

    @Autowired
    UserService userService;



    @Before
    public void init() {
        userListController = new UserListController();

        ReflectionTestUtils.setField(userListController, "userService", userService);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getUsers() {
        Assert.assertEquals(36, userListController.getUsers().size());
        userListController.setUsername("user");
        Assert.assertEquals(34, userListController.getUsers().size());
        userListController.resetFilter();
        userListController.setTeamname("Top");
        Assert.assertEquals(3, userListController.getUsers().size());
        userListController.resetFilter();
        userListController.setDepartmentname("IT");
        Assert.assertEquals(6, userListController.getUsers().size());
        userListController.resetFilter();
        userListController.setUserrole("Admin");
        Assert.assertEquals(1, userListController.getUsers().size());
        userListController.resetFilter();
        userListController.setUserrole("Departmentleader");
        Assert.assertEquals(6, userListController.getUsers().size());
        userListController.resetFilter();
        userListController.setUserrole("Teamleader");
        Assert.assertEquals(12, userListController.getUsers().size());
        userListController.resetFilter();
        userListController.setUserrole("Employee");
        Assert.assertEquals(35, userListController.getUsers().size());
        userListController.resetFilter();
        Assert.assertEquals(36, userListController.getUsers().size());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getUsersWithoutTimeflip() {
        Assert.assertEquals(35, userListController.getUsersWithoutTimeflip().size());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getUsersNotInTeam() {
        Assert.assertEquals(3, userListController.getUsersNotInTeam().size());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getTeamLeadersWithoutTeam() {
        Assert.assertEquals(2, userListController.getTeamLeadersWithoutTeam().size());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getDepartmentLeadersWithoutDepartment() {
        Assert.assertEquals(1, userListController.getDepartmentLeadersWithoutDepartment().size());
    }

}