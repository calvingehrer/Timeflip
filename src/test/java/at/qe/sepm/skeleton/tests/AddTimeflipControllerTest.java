package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.*;
import at.qe.sepm.skeleton.services.*;
import at.qe.sepm.skeleton.ui.controllers.AddDepartmentController;
import at.qe.sepm.skeleton.ui.controllers.AddTeamController;
import at.qe.sepm.skeleton.ui.controllers.AddTimeflipController;
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
public class AddTimeflipControllerTest {

    private AddTimeflipController addTimeflipController;

    @Autowired
    private TimeflipService timeflipService;

    @Autowired
    private UserService userService;

    @Before
    public void init() throws IOException {


        addTimeflipController = new AddTimeflipController();
        ReflectionTestUtils.setField(addTimeflipController, "timeflipService", timeflipService);
    }



    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void add() {
        Timeflip timeflip = new Timeflip();
        timeflip.setMacAddress("00:12:23:vf:as:7s");

        User user = userService.loadUser("user10");

        Assert.assertEquals(11,timeflipService.getAllTimeflips().size());

        addTimeflipController.setTimeflip(timeflip);
        addTimeflipController.setUser(user);
        addTimeflipController.add();

        Assert.assertEquals(12,timeflipService.getAllTimeflips().size());
    }

}