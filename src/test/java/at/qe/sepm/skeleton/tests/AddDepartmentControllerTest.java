package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Room;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.RoomService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.controllers.AddDepartmentController;
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
class AddDepartmentControllerTest {

    @MockBean
    AddDepartmentController addDepartmentController;

    @Autowired
    DepartmentService departmentService;

    //@Autowired
    //UserService userService;


    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getHeadOfDepartment() {
        addDepartmentController.setDepartment(departmentService.loadDepartment("IT"));

        System.out.println(addDepartmentController.getDepartment().getDepartmentName());
    }

    @Test
    void setHeadOfDepartment() {
    }

    @Test
    void add() {
    }

    @Test
    void resetDepartment() {
    }
}