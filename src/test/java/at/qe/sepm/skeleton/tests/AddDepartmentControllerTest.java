package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Room;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.repositories.TeamRepository;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.RoomService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.controllers.AddDepartmentController;
import at.qe.sepm.skeleton.utils.auditlog.Logger;
import org.junit.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.primefaces.PrimeFaces;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class AddDepartmentControllerTest {

    private AddDepartmentController addDepartmentController;

    @Autowired
    private DepartmentService departmentService;

    @Before
    public void initTestClass(){
        addDepartmentController = new AddDepartmentController();

        ReflectionTestUtils.setField(addDepartmentController, "departmentService", departmentService);
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void getHeadOfDepartment() {
        addDepartmentController.setDepartment(departmentService.loadDepartment("IT"));

        System.out.println(addDepartmentController.getDepartment().getDepartmentName());
    }

    @Test
    public void setHeadOfDepartment() {
    }

    @Test
    public void add() {
    }

    @Test
    public void resetDepartment() {
    }
}