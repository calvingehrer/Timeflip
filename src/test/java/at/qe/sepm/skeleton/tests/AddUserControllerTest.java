package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.services.*;
import at.qe.sepm.skeleton.ui.controllers.AddDepartmentController;
import at.qe.sepm.skeleton.ui.controllers.AddTeamController;
import at.qe.sepm.skeleton.ui.controllers.AddTimeflipController;
import at.qe.sepm.skeleton.ui.controllers.AddUserController;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

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
        Assert.assertEquals("testUser1", addUserController.getUser().getUsername());
        addUserController.resetUser();
        Assert.assertNull(addUserController.getUser().getUsername());
    }
}