package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Room;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.*;
import at.qe.sepm.skeleton.ui.controllers.AddDepartmentController;
import at.qe.sepm.skeleton.ui.controllers.DepartmentDetailController;
import at.qe.sepm.skeleton.ui.controllers.DepartmentListController;
import at.qe.sepm.skeleton.ui.controllers.RaspberryListController;
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
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class RaspberryListControllerTest {

    RaspberryListController raspberryListController;

    @Autowired
    RaspberryService raspberryService;

    @Before
    public void init() {
        raspberryListController = new RaspberryListController();


        ReflectionTestUtils.setField(raspberryListController, "raspberryService", raspberryService);

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getRaspberries() {

        Assert.assertEquals(5, raspberryListController.getRaspberries().size());

    }
}