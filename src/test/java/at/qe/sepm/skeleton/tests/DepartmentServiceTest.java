package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.DepartmentService;
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

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
class DepartmentServiceTest {

    @Autowired
    DepartmentService departmentService;

    @MockBean
    CurrentUserBean currentUserBean;

    @Autowired
    UserService userService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getAllDepartments() {
        Assert.assertEquals(5, departmentService.getAllDepartments().size(), 0);

    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void addAndDeleteDepartment() {

        User headOfDepartment = new User();
        headOfDepartment.setUsername("headOdDepartment");
        Department department = new Department();
        department.setDepartmentName("TestDepartment");

        departmentService.addNewDepartment(headOfDepartment, department);

        Assert.assertEquals(6, departmentService.getAllDepartments().size());

        headOfDepartment.setDepartment(null);
        userService.saveUser(headOfDepartment);

        departmentService.deleteDepartment(department);

        Assert.assertEquals(5, departmentService.getAllDepartments().size());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getDepartmentByName() {
        Assert.assertEquals("IT", departmentService.loadDepartment("IT").getDepartmentName());
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getTeamsOfDepartment() {
        Assert.assertEquals(2, departmentService.getTeamsOfDepartment(departmentService.loadDepartment("IT")).size());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getDepartmentLeader() {
        Assert.assertEquals("user3", departmentService.getDepartmentLeader(departmentService.loadDepartment("IT")).getUsername());
    }
}