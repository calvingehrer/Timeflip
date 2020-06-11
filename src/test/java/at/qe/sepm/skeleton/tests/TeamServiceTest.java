package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Team;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.TeamService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import org.junit.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
class TeamServiceTest{


    @Autowired
    TeamService teamService = new TeamService();

    @Autowired
    UserService userService;

    @Autowired
    DepartmentService departmentService;

    @MockBean
    CurrentUserBean currentUserBean;



    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getAllTeams() {
        List<Team> teams = new ArrayList<>(teamService.getAllTeams());
        Assert.assertEquals(10, teams.size(), 0);

    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void saveTeam() {

        Team testTeam = teamService.loadTeam("Top-Management");
        Assert.assertEquals("Management", testTeam.getDepartment().getDepartmentName());

        testTeam.setDepartment(departmentService.loadDepartment("Accounting"));
        teamService.saveTeam(null,null,testTeam);
        Assert.assertEquals("Accounting", testTeam.getDepartment().getDepartmentName());
        Assert.assertEquals(10, teamService.getAllTeams().size(), 0);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void addNewTeam() {

        Team newTeam1 = new Team();
        newTeam1.setTeamName("Team1");
        Assert.assertEquals(10,teamService.getAllTeams().size(), 0);
        teamService.addNewTeam(null, newTeam1);
        Assert.assertEquals(11, teamService.getAllTeams().size(), 0);
        teamService.deleteTeam(newTeam1);

    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void deleteTeam() {

        Team team = new Team();
        team.setTeamName("Team1");
        teamService.addNewTeam(null, team);

        Assert.assertEquals(11, teamService.getAllTeams().size(), 0);

        teamService.deleteTeam(team);

        Assert.assertEquals(10, teamService.getAllTeams().size(), 0);


    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getAllTeamsByTeamName() {

        List<Team> teams = new ArrayList<>(teamService.getAllTeamsByTeamName("A"));
        Assert.assertEquals(1, teams.size(), 0);
        teams = teamService.getAllTeamsByTeamName("I");
        Assert.assertEquals(2, teams.size(), 0);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getTeamsWithoutDepartment() {

        Assert.assertEquals(0, teamService.getTeamsWithoutDepartment().size(), 0);
        Team newTeam = new Team();
        newTeam.setTeamName("Team1");
        teamService.addNewTeam(null, newTeam);
        Assert.assertEquals(1, teamService.getTeamsWithoutDepartment().size(), 0);
        teamService.deleteTeam(newTeam);

    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getTeamsOfDepartment() {


        Department department = departmentService.loadDepartment("Sales & Marketing");

        List<Team> teams = new ArrayList<>(teamService.getTeamsOfDepartment(department));
        Assert.assertEquals(2, teamService.getTeamsOfDepartment(department).size(), 0);

        Team newTeam = new Team();
        newTeam.setTeamName("Team1");
        newTeam.setDepartment(department);

        teamService.addNewTeam(null, newTeam);

        Assert.assertEquals(3, teamService.getTeamsOfDepartment(departmentService.loadDepartment("Sales & Marketing")).size(), 0);

        teamService.deleteTeam(newTeam);


    }
}