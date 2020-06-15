package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.services.*;
import at.qe.sepm.skeleton.ui.controllers.AddUserController;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class AddUserControllerTest {

    private AddUserController addUserController;

    @Autowired
    private UserService userService;

    @Before
    public void init() throws IOException {
        addUserController = new AddUserController();
        ReflectionTestUtils.setField(addUserController, "userService", userService);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void add() {
        Assert.assertEquals(36,userService.getAllUsers().size());
        addUserController.add();
        Assert.assertEquals(36, userService.getAllUsers().size());
        addUserController.getUser().setUsername("testUser1");
        addUserController.getUser().setPassword("password");
        addUserController.add();
        Assert.assertEquals(37, userService.getAllUsers().size());
        Assert.assertEquals(0, userService.loadUser("testUser1").getRoles().size());
        addUserController.getUser().setUsername("testUser2");
        addUserController.getUser().setPassword("password");
        addUserController.setAdmin(true);
        addUserController.setEmployee(true);
        addUserController.setTeamleader(true);
        addUserController.setDepartmentleader(true);
        addUserController.add();
        Assert.assertEquals(4, userService.loadUser("testUser2").getRoles().size());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void resetUser() {
        addUserController.setUser(userService.loadUser("user10"));
        Set<UserRole> roleSet = new HashSet<>();
        roleSet.add(UserRole.EMPLOYEE);
        addUserController.setRoles(roleSet);
        addUserController.setDepartmentleader(true);
        addUserController.setTeamleader(true);
        addUserController.setEmployee(true);
        addUserController.setAdmin(true);
        addUserController.getUser().setEnabled(true);
        Assert.assertEquals("user10", addUserController.getUser().getUsername());
        Assert.assertTrue(addUserController.getUser().isEnabled());
        Assert.assertEquals(1, addUserController.getRoles().size());
        Assert.assertTrue(addUserController.isAdmin());
        Assert.assertTrue(addUserController.isEmployee());
        Assert.assertTrue(addUserController.isDepartmentleader());
        Assert.assertTrue(addUserController.isTeamleader());

        addUserController.resetUser();
        Assert.assertNull(addUserController.getUser().getUsername());
        Assert.assertEquals(null, addUserController.getUser().getUsername());
        Assert.assertFalse(addUserController.getUser().isEnabled());
        Assert.assertEquals(0, addUserController.getRoles().size());
        Assert.assertFalse(addUserController.isAdmin());
        Assert.assertFalse(addUserController.isEmployee());
        Assert.assertFalse(addUserController.isDepartmentleader());
        Assert.assertFalse(addUserController.isTeamleader());
    }
}