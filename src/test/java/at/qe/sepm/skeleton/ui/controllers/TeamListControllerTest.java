package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.TeamService;
import at.qe.sepm.skeleton.ui.controllers.TeamListController;
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
    public void init() {

        teamListController = new TeamListController();

        ReflectionTestUtils.setField(teamListController, "teamService", teamService);

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getTeams() {
        Assert.assertEquals(10, teamListController.getTeams().size());
        teamListController.setTeamName("I");
        Assert.assertEquals(2, teamListController.getTeams().size());
        teamListController.resetFilter();
        teamListController.setDepartment("IT");
        Assert.assertEquals(2, teamListController.getTeams().size());
        teamListController.resetFilter();
        teamListController.setEmployee("user7");
        Assert.assertEquals(1, teamListController.getTeams().size());
        teamListController.resetFilter();
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