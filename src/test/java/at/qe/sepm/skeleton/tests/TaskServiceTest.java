package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.TaskEnum;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.TaskService;
import at.qe.sepm.skeleton.services.UserService;
import com.fasterxml.jackson.databind.annotation.JsonAppend;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.test.context.support.WithMockUser;

import java.util.Date;

public class TaskServiceTest {
    @Autowired
    TaskService taskService;
    @Autowired
    UserService userService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    public void  testSaveEditedTask() {
        User adminUser = userService.loadUser("admin");
    }
}
