package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.Timeflip;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
class HistoryServiceTest {

    @Autowired
    UserService userService;

    @Test
    void getHistoryItems() {
    }

    @Test
    void findHistoryItems() {
    }

    @Test
    @WithMockUser(username="user1", authorities = {"DEPARTMENTLEADER"})
    void testAddAsTask() {

    }

    @Test
    void deleteHistory() {
    }
}