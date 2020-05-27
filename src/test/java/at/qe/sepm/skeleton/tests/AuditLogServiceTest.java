package at.qe.sepm.skeleton.tests;


import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.*;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import at.qe.sepm.skeleton.utils.auditlog.LogEntry;
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
class AuditLogServiceTest {

    @Autowired
    AuditLogService auditLogService;

    @MockBean
    CurrentUserBean currentUserBean;

    @Autowired
    UserService userService;

    List<LogEntry> emptyList = new ArrayList();

    @Test
    @WithMockUser(username = "departmentLeader", authorities = {"DEPARTMENTLEADER", "ADMIN"})
    void getAllEntries() {

        User user = userService.getAllUsersByUsername("user1").get(0);
        user.setFirstName("test");
        userService.saveUser(user);

       Assert.assertNotEquals(emptyList, auditLogService.getAllEntries());

    }

    @Test
    @WithMockUser(username = "departmentLeader", authorities = {"DEPARTMENTLEADER", "ADMIN"})
    void getAllEntriesByType() {

        Assert.assertEquals(emptyList, auditLogService.getAllEntriesByType("delete"));

        userService.deleteUser(userService.getAllUsersByUsername("user10").get(0));

        Assert.assertNotEquals(emptyList, auditLogService.getAllEntriesByType("delete"));
    }

    @Test
    @WithMockUser(username = "departmentLeader", authorities = {"DEPARTMENTLEADER", "ADMIN"})
    void getAllEntriesByDate() {

    }

    @Test
    @WithMockUser(username = "departmentLeader", authorities = {"DEPARTMENTLEADER", "ADMIN"})
    void getAllEntriesByChangingUser() {
        User user = userService.getAllUsersByUsername("user2").get(0);
        user.setFirstName("test");
        userService.saveUser(user);
        Assert.assertEquals(emptyList, auditLogService.getAllEntriesByChangingUser("departmentLeader"));

    }

}