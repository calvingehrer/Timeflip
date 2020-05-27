package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.exceptions.VacationException;
import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Room;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.model.Vacation;
import at.qe.sepm.skeleton.services.*;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import at.qe.sepm.skeleton.ui.controllers.AddDepartmentController;
import at.qe.sepm.skeleton.utils.auditlog.LogEntry;
import at.qe.sepm.skeleton.utils.auditlog.LogEnum;
import org.junit.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.time.Instant;
import java.util.*;

import static org.apache.coyote.http11.Constants.Z;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
class AddDepartmentControllerTest {


    @Autowired
    DepartmentService departmentService;

    //@Autowired
    AddDepartmentController addDepartmentController = new AddDepartmentController();

    @MockBean
    CurrentUserBean currentUserBean;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void add() {
        Assert.assertEquals(5, departmentService.getAllDepartments().size() );
        Department department = new Department();
        department.setDepartmentName("testDepartmen");

        User heafOf = new User();
        heafOf.setUsername("testUser");

        addDepartmentController.setDepartment(department);
        addDepartmentController.add();
        addDepartmentController.setHeadOfDepartment(heafOf);
        Assert.assertEquals(6, departmentService.getAllDepartments().size() );

    }
}