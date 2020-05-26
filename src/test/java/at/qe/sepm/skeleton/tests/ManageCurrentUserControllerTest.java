package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.UserService;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
import at.qe.sepm.skeleton.ui.controllers.ManageCurrentUserController;
import org.junit.Assert;
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
class ManageCurrentUserControllerTest {

    @Autowired
    ManageCurrentUserController manageCurrentUserController;

    @Autowired
    CurrentUserBean currentUserBean;

    @Autowired
    UserService userService;

    @Test
    void initTest() {
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getFullNameTest() {

        String fullName = manageCurrentUserController.getFullName();

        Assert.assertEquals("Admin Istrator", fullName);
    }

    @Test
    void checkOldPasswordTest() {
    }

    @Test
    void checkConfirmedPasswordTest() {
    }

    @Test
    void changePasswordTest() {
    }

    @Test
    void saveUserDetailsTest() {
    }
}