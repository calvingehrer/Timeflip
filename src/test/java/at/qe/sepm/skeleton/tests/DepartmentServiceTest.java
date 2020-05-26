package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.DepartmentRepository;
import at.qe.sepm.skeleton.services.DepartmentService;
import org.junit.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
class DepartmentServiceTest {

    @Autowired
    DepartmentService departmentService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getAllDepartments() {
        Assert.assertEquals(5, departmentService.getAllDepartments().size(), 0);

    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void addAndDeleteNewDepartment() {

        Assert.assertEquals(5, departmentService.getAllDepartments().size(), 0);

        User headOfDepartment = new User();
        headOfDepartment.setUsername("headOdDepartment");
        Department department = new Department();
        department.setDepartmentName("TestDEpartment");

        departmentService.addNewDepartment(headOfDepartment, department);

        Assert.assertEquals(6, departmentService.getAllDepartments().size(), 0);

        departmentService.deleteDepartment(department);

        Assert.assertEquals(5, departmentService.getAllDepartments().size(), 0);
    }

    /*@Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getDepartmentByName() {
        Assert.assertEquals("IT", departmentService.getDepartmentByName("IT").getDepartmentName());
    }


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getTeamsOfDepartment() {
        Assert.assertEquals(2, departmentService.getTeamsOfDepartment(departmentService.getDepartmentByName("IT")).size());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getDepartmentLeader() {
        Assert.assertEquals("user3", departmentService.getDepartmentLeader(departmentService.getDepartmentByName("IT")).getUsername());
    }*/
}