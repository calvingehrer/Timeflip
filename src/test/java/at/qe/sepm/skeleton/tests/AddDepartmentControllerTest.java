package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Room;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.RoomService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.controllers.AddDepartmentController;
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
import java.util.Map;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.user;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class AddDepartmentControllerTest {

   // @Autowired
    private AddDepartmentController addDepartmentController;

    @Autowired
    private DepartmentService departmentService;

    @Autowired
    private UserService userService;

    @Mock
    private FacesContext facesContext;

    @Mock
    private ExternalContext externalContext;

    @Before
    public void init() throws IOException {


        addDepartmentController = new AddDepartmentController();
        ReflectionTestUtils.setField(addDepartmentController, "departmentService", departmentService);
    }



    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void add() {
        Department department = new Department();
        department.setDepartmentName("dep1");

        User testUser = new User();
        testUser.setUsername("testUser");
        departmentService.addNewDepartment(testUser, department);

        addDepartmentController.setDepartment(department);
        addDepartmentController.setHeadOfDepartment(testUser);

        addDepartmentController.add();

        System.out.println(addDepartmentController.getDepartment());
    }

    @Test
    public void resetDepartment() {
    }
}