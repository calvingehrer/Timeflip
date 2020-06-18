package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.controllers.AddDepartmentController;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;

import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import java.io.IOException;

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

        addDepartmentController.setDepartment(department);
        addDepartmentController.setHeadOfDepartment(testUser);
        Assert.assertEquals(5, departmentService.getAllDepartments().size());

        addDepartmentController.add();

        Assert.assertEquals(6, departmentService.getAllDepartments().size());
        Assert.assertNull(addDepartmentController.getDepartment().getDepartmentName());


        userService.loadUser("testUser").setDepartment(null);
        userService.deleteUser(userService.loadUser("testUser"));
        departmentService.deleteDepartment(departmentService.loadDepartment("dep1"));
        Assert.assertEquals(5, departmentService.getAllDepartments().size());
    }

    @Test
    public void resetDepartment() {
    }
}