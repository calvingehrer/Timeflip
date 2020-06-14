package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Room;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.RoomService;
import at.qe.sepm.skeleton.services.TeamService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.controllers.*;
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
import java.sql.Ref;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class TeamListControllerTest {

    TeamListController teamListController;

    @Autowired
    TeamService teamService;


    @Autowired
    DepartmentService departmentService;

    @Before
    public void init(){

        teamListController = new TeamListController();

        ReflectionTestUtils.setField(teamListController, "teamService", teamService);

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getTeams() {
        Assert.assertEquals(10, teamListController.getTeams().size());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getTeamsWithoutDepartment() {
        Assert.assertEquals(0, teamListController.getTeamsWithoutDepartment().size());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getUsersNotInTeam() {
        Assert.assertEquals(3, teamListController.getUsersNotInTeam().size());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getTeamsInDepartment() {
        Department department = departmentService.loadDepartment("IT");
        Assert.assertEquals(2, teamListController.getTeamsInDepartment(department).size());
    }
}