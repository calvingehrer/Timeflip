package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.DepartmentRepository;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.TeamService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.controllers.DepartmentDetailController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.jupiter.api.Assertions;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import java.io.IOException;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class DepartmentDetailControllerTest {

    DepartmentDetailController departmentDetailController;

    @Autowired
    DepartmentService departmentService;

    @Autowired
    TeamService teamService;

    @Autowired
    UserService userService;


    @Autowired
    DepartmentRepository departmentRepository;

    @Before
    public void init() throws IOException {

        departmentDetailController = new DepartmentDetailController();

        ReflectionTestUtils.setField(departmentDetailController, "teamService", teamService);
        ReflectionTestUtils.setField(departmentDetailController, "departmentService", departmentService);
        ReflectionTestUtils.setField(departmentDetailController, "userService", userService);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void doSaveDepartment() {
        Department department = departmentService.loadDepartment("IT");
        departmentDetailController.setDepartment(department);
        Team team = teamService.loadTeam("Network & Security");
        Assert.assertEquals(2,teamService.getTeamsOfDepartment(department).size());
        User oldLeader = userService.loadUser("user3");
        Assert.assertEquals(oldLeader, userService.getDepartmentLeader(department));


        departmentDetailController.setRemovedTeam(team);
        departmentDetailController.removeTeam();
        User newLeader = userService.loadUser("user34");
        departmentDetailController.setNewLeader(newLeader);
        departmentDetailController.replaceLeader();
        departmentDetailController.doSaveDepartment();

        oldLeader = userService.loadUser("user3");
        newLeader = userService.loadUser("user34");
        Assert.assertEquals(1,teamService.getTeamsOfDepartment(department).size());
        Assert.assertNull(oldLeader.getDepartment());
        Assert.assertEquals(newLeader, userService.getDepartmentLeader(department));


        departmentDetailController.setAddedTeam(team);
        departmentDetailController.addTeam();
        newLeader = userService.loadUser("user3");
        departmentDetailController.setNewLeader(newLeader);
        departmentDetailController.replaceLeader();
        departmentDetailController.doSaveDepartment();
        Assert.assertEquals(2, teamService.getTeamsOfDepartment(department).size());
    }



    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void replaceLeader() {
        departmentDetailController.setDepartment(departmentService.loadDepartment("Management"));
        departmentDetailController.setNewLeader(userService.loadUser("user34"));
        departmentDetailController.replaceLeader();
        Assert.assertEquals(userService.loadUser("user34"), departmentDetailController.getNewLeader());
        departmentDetailController.setNewLeader(userService.loadUser("user1"));
        departmentDetailController.replaceLeader();

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