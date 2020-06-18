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
import at.qe.sepm.skeleton.ui.controllers.DepartmentListController;
import at.qe.sepm.skeleton.ui.controllers.TeamDetailController;
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
public class TeamDetailControllerTest {

    TeamDetailController teamDetailController;


    @Autowired
    DepartmentService departmentService;

    @Autowired
    TeamService teamService;

    @Autowired
    UserService userService;

    @Before
    public void init() {
        teamDetailController = new TeamDetailController();

        ReflectionTestUtils.setField(teamDetailController, "teamService", teamService);
        ReflectionTestUtils.setField(teamDetailController, "userService", userService);

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void checkIfDeletionIsAllowed() {
        Team team = teamService.loadTeam("Coordination & Control");
        Assert.assertFalse(teamDetailController.checkIfDeletionIsAllowed(team));

        for (User u : userService.getUsersOfTeam(team)) {
            teamDetailController.setEmployeeRemove(u);
            teamDetailController.removeEmployee();
        }


        Assert.assertFalse(teamDetailController.checkIfDeletionIsAllowed(team));

        for (User u : userService.getUsersOfTeam(team)) {
            teamDetailController.setEmployeeAdd(u);
            teamDetailController.addEmployee();
        }
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void doSaveTeam() {
        User newLeader = userService.loadUser("user33");
        User oldLeader = userService.loadUser("user7");
        Team team = teamService.loadTeam("Coordination & Control");
        Assert.assertEquals(oldLeader, userService.getTeamLeader(team));


        teamDetailController.setTeam(team);
        teamDetailController.setNewLeader(newLeader);
        teamDetailController.replaceLeader();
        teamDetailController.doSaveTeam();
        newLeader = userService.loadUser("user33");
        oldLeader = userService.loadUser("user7");
        Assert.assertEquals(newLeader, userService.getTeamLeader(team));
        Assert.assertNull(oldLeader.getDepartment());

        teamDetailController.setNewLeader(oldLeader);
        teamDetailController.replaceLeader();
        teamDetailController.doSaveTeam();


        team = teamService.loadTeam("Coordination & Control");
        Department oldDepartment = departmentService.loadDepartment("Management");
        Assert.assertEquals(oldDepartment.getDepartmentName(), team.getDepartment().getDepartmentName());
        Department newDepartment = departmentService.loadDepartment("IT");


        teamDetailController.setDepartment(newDepartment);
        teamDetailController.changeDepartment();
        teamDetailController.doSaveTeam();
        team = teamService.loadTeam("Coordination & Control");
        Assert.assertEquals(newDepartment.getDepartmentName(), team.getDepartment().getDepartmentName());


        teamDetailController.setTeam(team);
        teamDetailController.setDepartment(oldDepartment);
        teamDetailController.changeDepartment();
        teamDetailController.doSaveTeam();
        team = teamService.loadTeam("Coordination & Control");
        Assert.assertEquals(oldDepartment.getDepartmentName(), team.getDepartment().getDepartmentName());
    }

}