package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.TeamService;
import at.qe.sepm.skeleton.ui.controllers.DepartmentDetailController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
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