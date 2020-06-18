package at.qe.sepm.skeleton.tests;

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
public class DepartmentDetailControllerTest {

    DepartmentDetailController departmentDetailController;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    TeamService teamService;

    @Before
    public void init() throws IOException {

        departmentDetailController = new DepartmentDetailController();

        ReflectionTestUtils.setField(departmentDetailController, "teamService", teamService);
    }


    @Test
    public void doReloadDepartment() {
    }

    @Test
    public void doSaveDepartment() {
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void doDeleteDepartment() {

        User headOfDepartment = new User();
        headOfDepartment.setUsername("headOdDepartment");
        Department department = new Department();
        department.setDepartmentName("TestDepartment");

        departmentService.addNewDepartment(headOfDepartment, department);



    }

    @Test
    public void checkIfDeletionIsAllowed() {
    }

    @Test
    public void replaceLeader() {
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void addTeam() {

        Department department = departmentService.loadDepartment("IT");

        Team team = new Team();
        team.setTeamName("Team1");
        departmentDetailController.setDepartment(department);

        Assert.assertEquals(0, departmentDetailController.getAddedTeams().size());

        departmentDetailController.setAddedTeam(team);
        departmentDetailController.addTeam();

        Assert.assertEquals(1, departmentDetailController.getAddedTeams().size());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void removeTeam() {
        Department department = departmentService.loadDepartment("IT");

        Team team = new Team();
        team.setTeamName("testTeam");
        departmentDetailController.setDepartment(department);

        Assert.assertEquals(0, departmentDetailController.getRemovedTeams().size());

        departmentDetailController.setRemovedTeam(team);
        departmentDetailController.removeTeam();

        Assert.assertEquals(1, departmentDetailController.getRemovedTeams().size());

    }
}