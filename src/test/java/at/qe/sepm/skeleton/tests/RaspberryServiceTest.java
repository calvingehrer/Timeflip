package at.qe.sepm.skeleton.tests;

import at.qe.sepm.skeleton.model.Department;
import at.qe.sepm.skeleton.model.Raspberry;
import at.qe.sepm.skeleton.model.Room;
import at.qe.sepm.skeleton.model.User;
import at.qe.sepm.skeleton.services.DepartmentService;
import at.qe.sepm.skeleton.services.RaspberryService;
import at.qe.sepm.skeleton.ui.beans.CurrentUserBean;
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

    @MockBean
    CurrentUserBean currentUserBean;

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
        Room room = new Room();
        room.setRoomNumber("12");
        raspberry.setRoom(room);
        raspberryService.addNewRaspberry(raspberry, room);
        Assert.assertEquals(6, raspberryService.getAllRaspberries().size());
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
        Room room = new Room();
        room.setRoomNumber("6");
        Assert.assertEquals("2", raspberry.getRoom().getRoomNumber());
        raspberry.setRoom(room);
        raspberryService.saveRaspberry(raspberry);
        Assert.assertEquals("6", raspberry.getRoom().getRoomNumber());

    }

    @Test
    @WithMockUser(username = "admin", authorities = {"ADMIN"})
    void deleteRaspberry() {
        Assert.assertEquals(5, raspberryService.getAllRaspberries().size());
        Raspberry raspberry = raspberryService.getAllRaspberries().get(0);
        raspberryService.deleteRaspberry(raspberry);
        Assert.assertEquals(4, raspberryService.getAllRaspberries().size());
        raspberryService.addNewRaspberry(raspberry, raspberry.getRoom());
        Assert.assertEquals(5, raspberryService.getAllRaspberries().size());



    }
}