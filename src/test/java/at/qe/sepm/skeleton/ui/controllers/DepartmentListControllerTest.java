package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Room;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.RoomService;
import at.qe.sepm.skeleton.services.TeamService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.controllers.AddDepartmentController;
import at.qe.sepm.skeleton.ui.controllers.DepartmentDetailController;
import at.qe.sepm.skeleton.ui.controllers.DepartmentListController;
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
public class DepartmentListControllerTest {

    DepartmentListController departmentListController;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    UserService userService;

    @Before
    public void init() {
        departmentListController = new DepartmentListController();

        ReflectionTestUtils.setField(departmentListController, "departmentService", departmentService);
        ReflectionTestUtils.setField(departmentListController, "userService", userService);

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getDepartments() {
        Assert.assertEquals(5, departmentListController.getDepartments().size());
        departmentListController.setDepartment("IT");
        Assert.assertEquals(1, departmentListController.getDepartments().size());
        departmentListController.resetFilter();
        departmentListController.setTeam("I");
        Assert.assertEquals(1, departmentListController.getDepartments().size());
        departmentListController.resetFilter();
        departmentListController.setEmployee("user2");
        Assert.assertEquals(4, departmentListController.getDepartments().size());
        departmentListController.resetFilter();
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getTeamsOfDepartment() {
        Assert.assertEquals(2, departmentListController.getTeamsOfDepartment(departmentService.loadDepartment("IT")).size());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getDepartmentLeader() {
        Assert.assertEquals("user3", departmentListController.getDepartmentLeader(departmentService.loadDepartment("IT")).getUsername());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getTeamsOfLeadersDepartment() {
        Assert.assertEquals(0, departmentListController.getTeamsOfLeadersDepartment().size());
    }
}