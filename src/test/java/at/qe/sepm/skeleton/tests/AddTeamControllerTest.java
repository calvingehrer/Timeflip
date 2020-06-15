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
import at.qe.sepm.skeleton.ui.controllers.AddTeamController;
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
public class AddTeamControllerTest {

    private AddTeamController addTeamController;

    @Autowired
    private TeamService teamService;

    @Before
    public void init() throws IOException {
        addTeamController = new AddTeamController();
        ReflectionTestUtils.setField(addTeamController, "teamService", teamService);

    }



    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void add() {
        Team team = new Team();
        team.setTeamName("team1");

        Assert.assertEquals(10,teamService.getAllTeams().size());

        addTeamController.setTeam(team);
        addTeamController.add();

        Assert.assertEquals(11,teamService.getAllTeams().size());
        teamService.deleteTeam(teamService.loadTeam("team1"));
        Assert.assertEquals(10,teamService.getAllTeams().size());
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void addEmployee() {
        User user = new User();
        user.setUsername("testUser");
        addTeamController.setEmployee(user);
        Assert.assertEquals(0, addTeamController.getEmployees().size());
        addTeamController.addEmployee();
        Assert.assertEquals(1, addTeamController.getEmployees().size());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void resetTeam() {
        addTeamController.getTeam().setTeamName("team1");
        Assert.assertEquals("team1", addTeamController.getTeam().getTeamName());
        addTeamController.resetTeam();
        Assert.assertNull(addTeamController.getTeam().getTeamName());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void addDepartment() {
        Department department = new Department();
        department.setDepartmentName("dept1");
        addTeamController.setDepartment(department);
        Assert.assertNull(addTeamController.getTeam().getDepartment());
        addTeamController.addDepartment();
        Assert.assertEquals(department, addTeamController.getTeam().getDepartment());
    }

}