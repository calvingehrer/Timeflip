package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Raspberry;
import at.qe.sepm.skeleton.model.Room;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.RaspberryService;
import at.qe.sepm.skeleton.services.RoomService;
import at.qe.sepm.skeleton.services.UserService;
import org.junit.*;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.security.test.context.support.WithMockUser;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;

import java.util.ArrayList;
import java.util.List;

@RunWith(SpringJUnit4ClassRunner.class)
@SpringBootTest
@WebAppConfiguration
class RaspberryServiceTest {

    @Autowired
    RaspberryService raspberryService;


    @Autowired
    RoomService roomService;

    @Autowired
    UserService userService;

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void getAllRaspberries() {
        Assert.assertEquals(5, raspberryService.getAllRaspberries().size());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void addNewRaspberry() {
        Assert.assertEquals(5, raspberryService.getAllRaspberries().size());
        Raspberry raspberry = new Raspberry();
        raspberry.setRaspberryId("testRaspberry");
        raspberryService.addNewRaspberry(raspberry, null);
        Assert.assertEquals(6, raspberryService.getAllRaspberries().size());
        raspberryService.deleteRaspberry(raspberryService.loadRaspberry("testRaspberry"));
        Assert.assertEquals(5, raspberryService.getAllRaspberries().size());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void loadRaspberry() {
        Assert.assertEquals("4", raspberryService.loadRaspberry("4").getRoom().getRoomNumber());
    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void saveRaspberry() {
        Raspberry raspberry = raspberryService.loadRaspberry("2");
        Assert.assertEquals("admin", raspberry.getCreateUser().getUsername());

        raspberry.setCreateUser(userService.loadUser("user1"));
        raspberryService.saveRaspberry(raspberry);

        Assert.assertEquals("user1", raspberry.getCreateUser().getUsername());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void deleteRaspberry() {
        Assert.assertEquals(5, raspberryService.getAllRaspberries().size());
        Raspberry raspberry = new Raspberry();
        raspberry.setRaspberryId("testRaspberry");
        raspberryService.addNewRaspberry(raspberry, null);
        Assert.assertEquals(6, raspberryService.getAllRaspberries().size());

        raspberryService.deleteRaspberry(raspberryService.loadRaspberry("testRaspberry"));

        Assert.assertEquals(5, raspberryService.getAllRaspberries().size());


    }
}