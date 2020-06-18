package at.qe.sepm.skeleton.ui.controllers;

import at.qe.sepm.skeleton.repositories.UserRepository;
import at.qe.sepm.skeleton.services.UserService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;


@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
public class ManageCurrentUserControllerTest {

    ManageCurrentUserController manageCurrentUserController;

    @Autowired
    private UserService userService;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private UserRepository userRepository;

    @Before
    public void initTestClass() {
        manageCurrentUserController = new ManageCurrentUserController();

        manageCurrentUserController.setCurrentUser(userRepository.findFirstByUsername("admin"));

        ReflectionTestUtils.setField(manageCurrentUserController, "userService", userService);
        ReflectionTestUtils.setField(manageCurrentUserController, "passwordEncoder", passwordEncoder);


    }


    @Test
    public void checkOldPasswordTest() {
        manageCurrentUserController.setOldPassword("passwd");
        Assert.assertTrue(manageCurrentUserController.checkOldPassword());

        manageCurrentUserController.setOldPassword("something else");
        Assert.assertFalse(manageCurrentUserController.checkOldPassword());
    }

    @Test
    public void checkConfirmedPasswordTest() {
        manageCurrentUserController.setNewPassword("correctP");
        manageCurrentUserController.setConfirmNew("correctP");

        Assert.assertTrue(manageCurrentUserController.checkConfirmedPassword());

        manageCurrentUserController.setConfirmNew("wrongP");
        Assert.assertFalse(manageCurrentUserController.checkConfirmedPassword());
    }

    /*
     * tried to fix MessagesView NullPointerException with Mockito but did not work out yet
    @Test
    public void changePasswordTest() {
        String oldP = manageCurrentUserController.getCurrentUser().getPassword();
        String newP;

        manageCurrentUserController.setOldPassword("passwd");
        manageCurrentUserController.setNewPassword("secret123");
        manageCurrentUserController.setConfirmNew("secret123");



        manageCurrentUserController.changePassword();

        newP = manageCurrentUserController.getCurrentUser().getPassword();

        Assert.assertNotEquals("Changing the password did not work. The old password was probably no passwd", newP, oldP);
    }
    */


    /*
    * NullPointerException
    @Test
    public void saveUserDetailsTest() {
        manageCurrentUserController.saveUserDetails();
    }
         */

}